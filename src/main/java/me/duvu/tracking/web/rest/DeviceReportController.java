package me.duvu.tracking.web.rest;

import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.exception.InvalidFormatException;
import me.duvu.tracking.services.models.ParkingEvent;
import me.duvu.tracking.services.DeviceReportService;
import me.duvu.tracking.services.DeviceService;
import me.duvu.tracking.utils.ReportResourceUtils;
import me.duvu.tracking.web.rest.model.response.RptParkingProjection;
import me.duvu.tracking.web.rest.model.response.RptSpeedProjection;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author beou on 4/30/18 16:57
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/r/device")
public class DeviceReportController {

    private static final String PDF = "pdf";
    private static final String XLSX = "xlsx";

    private final DeviceReportService deviceReportService;
    private final DeviceService deviceService;
    private final ProjectionFactory projectionFactory;
    private final JRPdfExporter jrPdfExporter;
    private final JRXlsxExporter jrXlsxExporter;

    public DeviceReportController(DeviceReportService deviceReportService, DeviceService deviceService,
                                  ProjectionFactory projectionFactory, JRPdfExporter jrPdfExporter, JRXlsxExporter jrXlsxExporter) {
        this.deviceReportService = deviceReportService;
        this.deviceService = deviceService;
        this.projectionFactory = projectionFactory;
        this.jrPdfExporter = jrPdfExporter;
        this.jrXlsxExporter = jrXlsxExporter;
    }

    @GetMapping(value = "/speed/{deviceId}")
    public Page<RptSpeedProjection> getSpeedReport(@PathVariable Long deviceId,
                                                   @RequestParam(name = "from", required = false) Long from,
                                                   @RequestParam(name = "to", required = false) Long to,
                                                   Pageable pageable) {

        return deviceReportService.getSpeedReport(deviceId, from, to, pageable)
                .map(x -> projectionFactory.createProjection(RptSpeedProjection.class, x));
    }

    @GetMapping(value = "/exp/speed/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    public void getSpeedReport(@PathVariable Long deviceId,
                                 @RequestParam(name = "from", required = false) Long from,
                                 @RequestParam(name = "to", required = false) Long to,
                                 @RequestParam(name = "format", required = false, defaultValue = "pdf") String format,
                                 HttpServletResponse response) {

        List<EventData> eventDataList = deviceReportService.getSpeedReport(deviceId, from, to);
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(eventDataList);


        try {
            JasperReport jasperReport = ReportResourceUtils.getSpeedReport();
            Map<String, Object> params = new HashMap<>();
            params.put("DATA_SOURCE", collectionDataSource);

            if (jasperReport != null) {
                log.info("JasperReport OK");
            } else {
                log.info("JasperReport KO");
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            writeToOutputStream(jasperPrint, response, format);
        } catch (JRException | IOException ex) {
            log.error("[>_] exception report", ex);
        }
    }


    @GetMapping(value = "/exp/parking/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    public void exportParkingReport(@PathVariable Long deviceId,
                               @RequestParam(name = "from", required = false) Long from,
                               @RequestParam(name = "to", required = false) Long to,
                               @RequestParam(name = "format", required = false, defaultValue = "pdf") String format,
                               HttpServletResponse response) {

        List<ParkingEvent> parkingEventData = deviceReportService.getParkingReport(deviceId, from, to);
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(parkingEventData);
        try {
            JasperReport jasperReport = ReportResourceUtils.getParkingReport();
            Map<String, Object> params = new HashMap<>();
            params.put("DATA_SOURCE", collectionDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            writeToOutputStream(jasperPrint, response, format);
        } catch (JRException | IOException ex) {
            log.error("[>_] exception report", ex);
        }
    }

    private void writeToOutputStream(JasperPrint jasperPrint, HttpServletResponse response, String format) throws IOException, JRException {
        ServletOutputStream outputStream = null;

            outputStream = response.getOutputStream();
        try {
            switch (format) {
                case PDF:
                    jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    response.setContentType("application/pdf");
                    jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                    jrPdfExporter.exportReport();
                    break;
                case XLSX:
                    jrXlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    jrXlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                    jrXlsxExporter.exportReport();
                    break;
                default:
                    throw new InvalidFormatException("Unknown format");
            }
        } finally {
            if (outputStream != null) {
                ReportResourceUtils.close(outputStream);
            }
        }
    }


    @GetMapping(value = "/parking/{deviceId}")
    public List<RptParkingProjection> getParkingReport(@PathVariable Long deviceId,
                                                       @RequestParam(name = "from", required = false) Long from,
                                                       @RequestParam(name = "to", required = false) Long to) {
        return deviceReportService.getParkingReport(deviceId, from, to).stream()
                .map(x -> projectionFactory.createProjection(RptParkingProjection.class, x)).collect(Collectors.toList());
    }

    @GetMapping(value = "/list")
    public ModelAndView getPdfReportDeviceList() {
        List<Device> deviceList = deviceService.getAll(null);
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(deviceList);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/device_list_report.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, collectionDataSource);

            jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("/home/ubuntu/workspace/example.pdf"));

            jrPdfExporter.exportReport();
        } catch (JRException jre) {
            log.info("[>_] exception report" + jre.getMessage());
            log.error("[>_] exception report", jre);
        }

        return null;
    }
}

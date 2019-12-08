package me.duvu.tracking.utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author beou on 9/22/18 16:40
 */
public class ReportResourceUtils {

    private static final String JR_SPEED_REPORTER            = "JR_SPEED_REPORTER";
    private static final String JR_PARKING_REPORTER          = "JR_PARKING_REPORTER";
    private static final String JR_DEVICE_LIST_REPORTER      = "JR_DEVICE_LIST_REPORTER";

    private static Map<String, JasperReport> jasperReportMap = new HashMap<>();

    static {
        Resource speedReportResource  = new ClassPathResource("/reports/speed_report.jrxml");
        Resource parkingReportResource  = new ClassPathResource("/reports/parking_report.jrxml");
        try {
            JasperReport speedReport = JasperCompileManager.compileReport(speedReportResource.getInputStream());
            JasperReport parkingReport = JasperCompileManager.compileReport(parkingReportResource.getInputStream());
            jasperReportMap.put(JR_SPEED_REPORTER, speedReport);
            jasperReportMap.put(JR_PARKING_REPORTER, parkingReport);
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    public static JasperReport getSpeedReport() {
        return jasperReportMap.get(JR_SPEED_REPORTER);
    }

    public static JasperReport getParkingReport() {
        return jasperReportMap.get(JR_PARKING_REPORTER);
    }

    public static void close(OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException ioex) {
            //
        }
    }
}

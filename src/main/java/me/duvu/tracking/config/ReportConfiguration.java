package me.duvu.tracking.config;

import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author beou on 10/4/18 13:13
 */
@Configuration
public class ReportConfiguration {
    @Bean
    public JRPdfExporter pdfExporter() {
        return new JRPdfExporter();
    }

    @Bean
    JRXlsxExporter xlsExporter() {
        return new JRXlsxExporter();
    }

    @Bean(name = "statusCode")
    StatusCodeUtils statusCode() {
        return StatusCodeUtils.getInstance();
    }

}

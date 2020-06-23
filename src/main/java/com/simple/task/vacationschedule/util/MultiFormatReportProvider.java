package com.simple.task.vacationschedule.util;

import com.simple.task.vacationschedule.model.vacation.Vacation;
import com.simple.task.vacationschedule.model.vacation.dto.ReportVacationDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MultiFormatReportProvider {

    private static JasperReport createReportTemplateFromResource() throws JRException {
        URL resource = MultiFormatReportProvider.class
                .getClassLoader()
                .getResource("vacation_report.jrxml");

        return JasperCompileManager.compileReport(resource.getPath());
    }

    private static List<ReportVacationDto> convertVacationsToReportFormat(List<Vacation> vacs) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return vacs.stream()
                .map(vac -> ReportVacationDto
                        .builder()
                        .fullName(vac.getEmployee().getFullName())
                        .startDate(dateFormat.format(vac.getStartDate()))
                        .endDate(dateFormat.format(vac.getEndDate())).build())
                .collect(Collectors.toList());
    }

    public static byte[] exportReport(List<Vacation> vacs, String format) throws JRException {
        try {
            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(convertVacationsToReportFormat(vacs));
            JasperPrint jasperPrint = JasperFillManager.fillReport(createReportTemplateFromResource(),
                    new HashMap<>(), dataSource);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            switch (format) {
                case "xlsx":
                    JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                    SimpleOutputStreamExporterOutput outXlsName = new SimpleOutputStreamExporterOutput(outputStream);
                    xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    xlsxExporter.setExporterOutput(outXlsName);

                    SimpleXlsxReportConfiguration simpleXlsxReportConfiguration = new SimpleXlsxReportConfiguration();
                    simpleXlsxReportConfiguration.setOnePagePerSheet(false);
                    simpleXlsxReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
                    simpleXlsxReportConfiguration.setDetectCellType(false);
                    simpleXlsxReportConfiguration.setWhitePageBackground(false);
                    xlsxExporter.setConfiguration(simpleXlsxReportConfiguration);

                    xlsxExporter.exportReport();
                    break;
                case "xml":
                    JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);
                    break;
                case "pdf":
                default:
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                    break;
            }

            byte[] result = outputStream.toByteArray();

            outputStream.close();

            return result;
        } catch (IOException e) {
            return new byte[0];
        }
    }
}

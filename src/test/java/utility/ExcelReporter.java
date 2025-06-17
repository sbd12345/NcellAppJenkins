package utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReporter {
    private List<Object[]> testData = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void addTestResult(String testName, String status, long duration, String errorMessage) {
        String timestamp = dateFormat.format(new Date());
        testData.add(new Object[]{testName, status, timestamp, duration + " ms", errorMessage});
    }

    public void generateReport(String filePath) throws IOException {
        // Always create a new workbook — no reading old one
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Results");
        File file = new File(filePath);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Test Case", "Status", "Execution Time", "Duration", "Error Message"};

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }


        int rowNum = 1;
        for (Object[] data : testData) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i].toString());

                if (i == 1) {
                    CellStyle style = workbook.createCellStyle();
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    switch (data[i].toString().toUpperCase()) {
                        case "PASS":
                            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                            break;
                        case "FAIL":
                            style.setFillForegroundColor(IndexedColors.RED.getIndex());
                            break;
                        case "SKIP":
                            style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
                            break;
                    }
                    cell.setCellStyle(style);
                }
            }
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }
}

package com.seleniumframework.core;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ExcelUtils {
    static Workbook workbook;

    private ExcelUtils() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * @param excelFilePath We are loading the Workbook by using the excelFilePath.
     * @throws IOException When the excelFilePath is not found and there is any action doing to that file we are getting io exception
     */
    public static void loadWorkbook(String excelFilePath) throws IOException {
        workbook = WorkbookFactory.create(new File(excelFilePath));
    }

    /**
     * @param sheetName
     * @return number of rows
     */
    public static int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum();
    }

    /**
     * @param sheetName In this method we getting the Cell values
     * @return integer number of cells
     */
    public static int getCellCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * @param sheetName
     * @param rowNum
     * @param cellNum
     * @return "String cellValue"
     */
    public static String getCellData(String sheetName, int rowNum, int cellNum) {
        String cellValue = null;
        Sheet sheet = workbook.getSheet(sheetName);
        Cell cell = sheet.getRow(rowNum).getCell(cellNum);
        if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf(cell.getNumericCellValue());
        } else {
            cellValue = cell.getStringCellValue();
        }
        return cellValue;
    }

    /**
     * @param excelFile
     * @param sheetName
     * @param rowNum
     * @param cellNum
     * @param value
     */
    public static void setCellData(String excelFile, String sheetName, int rowNum, int cellNum, String value) {
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.createCell(cellNum);
        if (value != null) {
            cell.setCellValue(value);
            row.getCell(cellNum).setCellStyle(getStyle(value));
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            ExtentReportManager.logStacktrace(Arrays.toString(e.getStackTrace()));
        }

    }

    /**
     * Closing the workbook
     */
    public static void closeWorkbook() {
        try {
            workbook.close();
        } catch (IOException e) {
            ExtentReportManager.logStacktrace(Arrays.toString(e.getStackTrace()));
        }
    }

    public static Object[][] getDataForDataProvider(String sheetName) {
        int rowNum = getRowCount(sheetName);
        int cellNum = getCellCount(sheetName);
        Object[][] data = new Object[rowNum - 1][cellNum];
        for (int i = 1; i < rowNum; i++) {
            for (int j = 0; j < cellNum; j++) {
                String value = getCellData(sheetName, i, j);
                data[i - 1][j] = value;
            }
        }

        return data;
    }


    private static CellStyle getStyle(String status) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        switch (status.toLowerCase()) {
            case "pass":
                font.setColor(IndexedColors.GREEN.getIndex());
                font.setBold(true);
                style.setFont(font);
                return style;

            case "fail":
                font.setColor(IndexedColors.RED.getIndex());
                font.setBold(true);
                style.setFont(font);
                return style;

            case "blocked":
                font.setColor(IndexedColors.BLUE.getIndex());
                font.setBold(true);
                style.setFont(font);
                return style;
            case "skip":
                font.setColor(IndexedColors.DARK_YELLOW.getIndex());
                font.setBold(true);
                style.setFont(font);
                return style;
            default:
                return style;
        }

    }
}

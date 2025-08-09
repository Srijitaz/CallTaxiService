package com.cab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static List<Map<String, String>> getData(String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(".\\src\\test\\java\\com\\cab\\testdata\\BookingTestData.xlsx"));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("❌ Sheet " + sheetName + " does not exist in the workbook.");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("❌ Header row is missing in sheet: " + sheetName);
            }

            int numCols = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow == null) continue; 

                Map<String, String> dataMap = new LinkedHashMap<>();

                for (int j = 0; j < numCols; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    String key = (headerCell != null) ? headerCell.toString().trim() : "Column" + j;

                    String value = "";
                    Cell valueCell = currentRow.getCell(j);

                    if (valueCell != null) {
                        switch (valueCell.getCellType()) {
                            case STRING:
                                value = valueCell.getStringCellValue().trim();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(valueCell)) {
                                    value = valueCell.getLocalDateTimeCellValue().toString();
                                } else {
                                    double rawValue = valueCell.getNumericCellValue();
                                    value = (rawValue == (int) rawValue)
                                            ? String.valueOf((int) rawValue)
                                            : String.valueOf(rawValue);
                                }
                                break;
                            case BOOLEAN:
                                value = String.valueOf(valueCell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                try {
                                    value = valueCell.getStringCellValue();
                                } catch (IllegalStateException e) {
                                    value = String.valueOf(valueCell.getNumericCellValue());
                                }
                                break;
                            case BLANK:
                            default:
                                value = "";
                                break;
                        }
                    }

                    dataMap.put(key, value);
                }

                dataList.add(dataMap);
            }

        } catch (Exception e) {
            System.out.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList;
    }
}

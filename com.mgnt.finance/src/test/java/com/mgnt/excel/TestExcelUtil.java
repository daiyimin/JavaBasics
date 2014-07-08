package com.mgnt.excel;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Before;
import org.junit.Test;

public class TestExcelUtil {
    private String relativePath = "src\\test\\java\\com\\mgnt\\excel\\";
        
    // copy file (valid on windows only)
    private static void copy(String filename1, String filename2) {
        try {
            Runtime run = Runtime.getRuntime();
            Process pro = run.exec("cmd /c copy " + filename1 + " " + filename2);
            pro.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        // copy orig.xls to test.xls
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path += File.separatorChar + relativePath;
        copy(path + "orig.xls", path + "test.xls");
    }

    @Test
    public void testCopyCell() throws IOException {
        ExcelUtil handler = new ExcelUtil();
        HSSFWorkbook wb = handler.readExcel(relativePath + "orig.xls");
        HSSFSheet sheet = handler.getSheetByIndex(wb, 0);
        String name = sheet.getSheetName();
        
        HSSFRow row = handler.getRow(sheet, 0);
        HSSFCell cell1 = handler.getCell(row, 0);
        
        row = handler.getRow(sheet, 10);
        HSSFCell cell2 = handler.getCell(row,0);
        
        handler.copyCell(cell1, cell2);
        handler.writeExcel(relativePath + "test.xls", wb);
        
        String value = cell1.getStringCellValue();
        CellRangeAddress mr = sheet.getMergedRegion(0);
    }
    
    @Test 
    public void testCopyRect() throws IOException {
        ExcelUtil handler = new ExcelUtil();
        HSSFWorkbook wb = handler.readExcel(relativePath + "orig.xls");
        HSSFSheet sheet0 = handler.getSheetByIndex(wb, 0);
        HSSFSheet sheet1 = handler.getSheetByIndex(wb, 1);

        Rect rect = new Rect(0,0,10,6);
        Point pos = new Point(0, 20);
        
        handler.copyRect(sheet1, sheet0, rect, pos);
        handler.writeExcel(relativePath + "test.xls", wb);
    }
    
    @Test
    public void testGetSheetContentRegion() throws IOException {
        ExcelUtil handler = new ExcelUtil();
        HSSFWorkbook wb = handler.readExcel(relativePath + "orig.xls");
        HSSFSheet sheet0 = handler.getSheetByIndex(wb, 0);
        Rect rect = handler.getSheetContentRegion(sheet0);
        int i = 0;
    }
}

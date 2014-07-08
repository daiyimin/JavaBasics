package com.mgnt.app;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mgnt.excel.ExcelUtil;
import com.mgnt.excel.Point;
import com.mgnt.excel.Rect;

public class Main {
    public static void main(String...args) throws IOException {
        ExcelUtil handler = new ExcelUtil();
        HSSFWorkbook wb = handler.readExcel(args[0] + args[1]);
        
        List<Rect> list = handler.getAllSheetContentRegions(wb);
        
        Rect rect = list.get(0);
        Point pos = new Point(0, rect.getHeight()+1);
        HSSFSheet target = handler.getSheetByIndex(wb, 0);
        HSSFSheet sheet = null;
        for (int i=1; i<list.size(); i++) {
            sheet = handler.getSheetByIndex(wb, i);
            rect = list.get(i);
            handler.copyRect(sheet, target, rect, pos);
            
            pos = new Point(0, pos.getY() + rect.getHeight() + 1);
        }
        
        handler.writeExcel(args[0] + "test.xls", wb);
    }
}

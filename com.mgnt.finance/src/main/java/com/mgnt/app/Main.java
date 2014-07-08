package com.mgnt.app;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mgnt.excel.ExcelUtil;
import com.mgnt.excel.Point;
import com.mgnt.excel.Rect;

public class Main {
    /**
     * arg[0] is the absolute path of input excel<BR/>
     * arg[1] is the filename of input excel
     * @param args
     * @throws IOException
     */
    public static void main(String...args) throws IOException {
/*        ExcelUtil handler = new ExcelUtil();
        HSSFWorkbook wb = handler.readExcel(args[0] + args[1]);
        
        List<Rect> list = handler.getAllSheetContentRegions(wb);
        
        //Start to copy content in sheet[1], sheet[2].. to sheet[0]
        Rect rect = list.get(0);
        Point pos = new Point(0, rect.getHeight()+1); // the next empty row in sheet[0]
        HSSFSheet target = handler.getSheetByIndex(wb, 0); // sheet[0] is the target
        HSSFSheet sheet = null;
        for (int i=1; i<list.size(); i++) {
            sheet = handler.getSheetByIndex(wb, i);  // sheet[i] is the source
            rect = list.get(i); // get content region of sheet[i]
            handler.copyRect(sheet, target, rect, pos);
            
            pos = new Point(0, pos.getY() + rect.getHeight() + 1);  // the next empty row in sheet[0]
        }
        
        // write workbook
        handler.writeExcel(args[0] + "test.xls", wb);
*/    
        CopySheet cs = new CopySheet(args[0], args[1]);
        cs.copySheet();
    }
}

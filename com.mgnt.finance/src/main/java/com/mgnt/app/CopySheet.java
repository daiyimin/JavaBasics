package com.mgnt.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mgnt.excel.CellInspector;
import com.mgnt.excel.ExcelUtil;
import com.mgnt.excel.Point;
import com.mgnt.excel.Rect;

public class CopySheet implements CellInspector{
    private String path;
    private String srcFile;
    private String destFile = "result.xls";
    
    private static String REISSUE = "补开";
    private List<HSSFRow> reissueList = new ArrayList<HSSFRow>();
    
    public CopySheet(String path, String filename)  {
        this.path = path;
        this.srcFile = filename;
    }
    
    /**
     * Copy content in sheet[1], sheet[2].. to sheet[0]
     * @throws IOException
     */
    public void copySheet() throws IOException{
        ExcelUtil handler = new ExcelUtil();
        handler.addInspector(this);
        
        HSSFWorkbook wb = handler.readExcel(path + srcFile);
        
        // get content regions of all sheets
        List<Rect> list = handler.getAllSheetContentRegions(wb);
        
        //Start to copy content in sheet[1], sheet[2].. to sheet[0]
        Rect rect = list.get(0);
        Point pos = new Point(0, rect.getHeight()+1); // the next empty row in sheet[0]
        HSSFSheet target = handler.getSheetByIndex(wb, 0); // sheet[0] is the target
        HSSFSheet sheet = null;
        for (int i=1; i<list.size(); i++) { // list size is same as sheet number
            sheet = handler.getSheetByIndex(wb, i);  // sheet[i] is the source
            rect = list.get(i); // get content region of sheet[i]
            
            if (reissueList.size() > 0) {
                handler.copyRect(sheet, target, rect, pos, reissueList, list.get(i-1));
                pos = new Point(0, pos.getY() + rect.getHeight() + reissueList.size() + 1);  // the next empty row in sheet[0]
                reissueList.clear();
            } else {
                handler.copyRect(sheet, target, rect, pos);
                pos = new Point(0, pos.getY() + rect.getHeight() + 1);  // the next empty row in sheet[0]
            }
        }
        
        // write workbook
        handler.writeExcel(path + destFile, wb);
    }

    /**
     * Check if cell content eqals to 补开
     */
    public void inspect(HSSFCell cell) {
       if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
           return;
       }
       if (REISSUE.equals(cell.getStringCellValue())) {
           reissueList.add(cell.getRow());
       }
    }
}

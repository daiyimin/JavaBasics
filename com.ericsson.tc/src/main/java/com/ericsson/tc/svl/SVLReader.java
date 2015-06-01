package com.ericsson.tc.svl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.ericsson.tc.util.poi.excel.ExcelUtil;

/**
 * Current position of iterator
 * @author eyimdai
 *
 */
class Position {
	/**
	 * sheet that is being read
	 */
	int sheet;
	/**
	 * row in the sheet that is being read
	 */
	int row;
	
	public int getSheet() {
		return sheet;
	}

	public void setSheet(int sheet) {
		this.sheet = sheet;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Position(int sheet, int row) {
		this.sheet = sheet;
		this.row = row;
	}
}

/**
 * SVLReader read an Software Vendor List(SVL) file and act as iterator of 3PPs<br/>
 * It will iterate through every 3PPs that meet the type condition in every sheet of SVL.
 * @author eyimdai
 *
 */
public class SVLReader implements Iterator {
	public static final int PGN_FOSS = 0;
	public static final int PGN_Commercial = 1;
	public static final int PGC_FOSS = 2;
	public static final int PGC_Commercial = 3;
	
    private String path;
    private String srcFile;
    private String password;
    private int type;
    private List<HSSFSheet> sheetList;
    private Map<String, Integer> colName2No;

    private ExcelUtil handler;
    private Position pos;
    
 
	private Logger logger = Logger.getLogger(SVLReader.class);

	/**
     * 
     * @param path
     * 				path of SVL
     * @param filename
     * 				filename of SVL
     * @param type
     * 				Commercial or FOSS
     */
	public SVLReader(String path, String filename, String password, int type) {
        this.path = path;
        this.srcFile = filename;
        this.password = password;
        this.type = type;
        handler = new ExcelUtil();
	}
	
	/**
	 * Read SVL file and find sheets that contains 3PP info
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	public void readSVL() throws IOException, GeneralSecurityException {
        HSSFWorkbook wb = handler.readEncryptedExcel(path + srcFile, password);
        sheetList = new ArrayList<HSSFSheet>();
        
        if (type == PGN_Commercial || type == PGN_FOSS) {
            sheetList.add(wb.getSheet("SVL Details"));
            sheetList.add(wb.getSheet("Virtual Deployment"));
        } else {
        	// TODO: add PGC code
        }
        
        if (sheetList.size() > 0) {
        	colName2No = new HashMap<String, Integer>();
        	for (int i=0; i<30; i++) {
        		// Suppose the 7th row of SVL is 3PP attribute names, and totally 30 columns
        		HSSFCell cell = handler.getCell(sheetList.get(0), 7, i);
        		String cValue = cell.getStringCellValue();
                for (String colName: SVL3PPBean.COLUMNS) {
                	if (cValue.trim().equals(colName)) {
                		colName2No.put(colName, i);
                		break;
                	}
                }
        	}
        	if (colName2No.size() != SVL3PPBean.COLUMNS.length) {
        		logger.error("Some column is not found in SVL!");
        	}
        } else {
        	logger.error("No available sheet in SVL!");
        }
        
        // intialize iterator position, (0,7) means start from sheet 0, row 7
        pos = new Position(0,7); 
	}

	/**
	 * 
	 */
	public boolean hasNext() {
		int maxSheetNo = 0;
		if (type == PGN_Commercial || type == PGN_FOSS) {
			maxSheetNo = 1;
		}

		do {
	        // get the next cell position to read
	        int sheetNo = pos.getSheet();
	        int rowNo = pos.getRow() + 1;
	        int colNo = colName2No.get(SVL3PPBean.SW_TYPE);
    		
    		HSSFCell cell;
    		String cValue;
    		// First, check if next row exceeds limit of current sheet
			int lastRowOfSheet = sheetList.get(0).getLastRowNum();
    		if (rowNo > lastRowOfSheet) {
    			// yes, last row exceeded
				if (sheetNo < maxSheetNo) {
					// reach the end of current sheet, go on search first row of next available sheet
    				pos.setSheet(sheetNo + 1);
    				pos.setRow(7);
        			continue;
				} else {
					// no more sheet available in workbook, quit
					break;
				}
    		} else {
    			// next row is available
    			// read the SW_TYPE of next row
        		cell = handler.getCell(sheetList.get(sheetNo), rowNo, colNo);
        		cValue = cell.getStringCellValue();
    		}
    			
    		if ((type == PGC_Commercial || type == PGN_Commercial) &&
    				cValue.trim().equalsIgnoreCase("Commercial")) {
        		// if SVLReader is aimed to read commercial 3PP and next row is also commercial 3PP. HasNext is true!
    			pos.setRow(rowNo);
    			return true;
    		} else if ((type == PGC_FOSS || type == PGN_FOSS) &&
    				cValue.trim().equalsIgnoreCase("FOSS")) {
        		// if SVLReader is aimed to read FOSS 3PP and next row is also FOSS 3PP. HasNext is true!
    			pos.setRow(rowNo);
    			return true;
    		} else {
    			// if next row is not the type of 3PP we want, go on search next row
    			// but we need to check if there is more row
    			cell = handler.getCell(sheetList.get(sheetNo), rowNo, 0);
    			cValue = cell.getStringCellValue();
    			
    			// at the end of each sheet, SVL list 3PP imported from other products. It starts with "Imported"
    			// use this string as a mark of end of sheet
    			if (cValue.trim().startsWith("IMPORTED") ||
    					cValue.trim().startsWith("Imported")) {
    				if (sheetNo < maxSheetNo) {
    					// reach the end of sheet 0, try next sheet
        				pos.setSheet(sheetNo + 1);
        				pos.setRow(7);
    				} else {
    					// reach the end of sheet 1, no more sheet available in workbook
    					break;
    				}
    			} else {
    				// this sheet has more rows, try next row
    				pos.setRow(rowNo);
    			}
    			continue;
    		}

		} while(true);
		
		return false;
	}

	public SVL3PPBean next() {
        int sheetNo = pos.getSheet();
        int rowNo = pos.getRow();
		SVL3PPBean bean = new SVL3PPBean();
		for (String colName: SVL3PPBean.COLUMNS) {
	        int colNo = colName2No.get(colName);
			HSSFCell cell = handler.getCell(sheetList.get(sheetNo), rowNo, colNo);
			String cellValue = handler.readCell(cell);
			bean.setColByName(colName, cellValue.trim());
		}
		return bean;
	}
	
	public static void main(String...args) {
		String localDir = System.getProperty("user.dir") + "/";
		SVLReader reader = new SVLReader("src/test/java/com/ericsson/tc/poi/", "SVL.xls", "pgpg", PGN_Commercial );
		try {
			reader.readSVL();
			int count = 0;
			while (reader.hasNext()) {
				SVL3PPBean bean = reader.next();
				System.out.println(bean.toString());
				System.out.println(count);
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

}

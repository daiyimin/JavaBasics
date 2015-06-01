package com.ericsson.tc.util.poi.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.standard.StandardDecryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private CellInspector inspector = null;

	public void addInspector(CellInspector inspector) {
		this.inspector = inspector;
	}

	/**
	 * Read excel file and return in workbook
	 * 
	 * @param inputFilename
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook readExcel(String inputFilename) throws IOException {
		FileInputStream readFile;
		HSSFWorkbook wb = null;
		readFile = new FileInputStream(inputFilename);
		wb = new HSSFWorkbook(readFile);
		readFile.close();
		return wb;
	}

	/**
	 * Read excel file and return in workbook
	 * 
	 * @param inputFilename
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public HSSFWorkbook readEncryptedExcel(String inputFilename, String password)
			throws IOException, GeneralSecurityException {
		FileInputStream readFile;
		HSSFWorkbook wb = null;
/*		readFile = new FileInputStream(inputFilename);
		POIFSFileSystem pfs = new POIFSFileSystem(readFile);
		EncryptionInfo encInfo = new EncryptionInfo(pfs);
		Decryptor decryptor = Decryptor.getInstance(encInfo);
		decryptor.verifyPassword(password);
		wb = new HSSFWorkbook(decryptor.getDataStream(pfs));
		readFile.close();
*/
		readFile = new FileInputStream(inputFilename);
        org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword(password);  
		wb = new HSSFWorkbook(readFile);
		readFile.close();
		return wb;
	}

	/**
	 * Save workbook
	 * 
	 * @param outputFilename
	 * @param wb
	 *            workbook
	 * @throws IOException
	 */
	public void writeExcel(String outputFilename, HSSFWorkbook wb)
			throws IOException {
		FileOutputStream writeFile = new FileOutputStream(outputFilename);
		wb.write(writeFile);
		writeFile.flush();
		writeFile.close();
	}

	public HSSFSheet getSheetByIndex(HSSFWorkbook wb, int sheetIndex) {
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		return sheet;
	}

	public HSSFSheet getSheetByName(HSSFWorkbook wb, String name) {
		HSSFSheet sheet = wb.getSheet(name);
		return sheet;
	}

	public HSSFRow getRow(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		return row;
	}

	public HSSFCell getCell(HSSFRow row, int columnIndex) {
		HSSFCell cell = row.getCell(columnIndex);
		return cell;
	}

	public HSSFCell getCell(HSSFSheet sheet, int rowIndex, int columnIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(columnIndex);
		return cell;
	}

	/**
	 * Copy srcCell to distCell
	 * 
	 * @param srcCell
	 * @param distCell
	 */
	public void copyCell(HSSFCell srcCell, HSSFCell distCell) {
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);

		if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
				distCell.setCellValue(srcCell.getDateCellValue());

				// set date format in cell style, otherwise distCell displays
				// date in float number
				HSSFWorkbook workbook = srcCell.getSheet().getWorkbook();
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				HSSFDataFormat format = workbook.createDataFormat();
				cellStyle.setDataFormat(format.getFormat("mm/dd/yyyy hh:MM"));
				distCell.setCellStyle(cellStyle);
			} else {
				distCell.setCellValue(srcCell.getNumericCellValue());
			}
		} else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
			distCell.setCellValue(srcCell.getRichStringCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
			// nothing
		} else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
			distCell.setCellValue(srcCell.getBooleanCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
			distCell.setCellErrorValue(srcCell.getErrorCellValue());
		} else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
			// distCell.setCellFormula(srcCell.getCellFormula());

			// patch: only copy value instead of copy the formula
			srcCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			distCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			distCell.setCellValue(srcCell.getNumericCellValue());
		} else {
			// nothing
		}
	}

	/**
	 * Copy sourceRect on source sheet to the destPos on dest sheet.<BR/>
	 * The destRect can be calculated based on destPos and sourceRect:<BR/>
	 * destRect.left = destPos.x<BR/>
	 * destRect.top = destPos.y<BR/>
	 * destRect.right = destPos.x + sourceRect.width<BR/>
	 * destRect.bottom = destPos.y + sourceRect.height<BR/>
	 * 
	 * @param source
	 * @param dest
	 * @param sourceRect
	 * @param destPos
	 */
	public void copyRect(HSSFSheet source, HSSFSheet dest, Rect sourceRect,
			Point destPos) {
		int targetRowFrom;
		int targetRowTo;
		int targetColumnFrom;
		int targetColumnTo;
		CellRangeAddress region = null;
		// Copy merged cells from source sheet to dest sheet
		for (int i = 0; i < source.getNumMergedRegions(); i++) {
			region = source.getMergedRegion(i);

			if ((region.getFirstRow() >= sourceRect.getTop())
					&& (region.getFirstColumn() >= sourceRect.getLeft())
					&& (region.getLastRow() <= sourceRect.getBottom())
					&& (region.getLastColumn() <= sourceRect.getRight())) {

				targetRowFrom = region.getFirstRow() - sourceRect.getTop()
						+ destPos.getY();
				targetRowTo = region.getLastRow() - sourceRect.getTop()
						+ destPos.getY();
				targetColumnFrom = region.getFirstColumn()
						- sourceRect.getLeft() + destPos.getX();
				targetColumnTo = region.getLastColumn() - sourceRect.getLeft()
						+ destPos.getX();

				CellRangeAddress newRegion = region.copy();

				newRegion.setFirstRow(targetRowFrom);
				newRegion.setFirstColumn(targetColumnFrom);
				newRegion.setLastRow(targetRowTo);
				newRegion.setLastColumn(targetColumnTo);
				dest.addMergedRegion(newRegion);
			}
		}

		// Copy each row in the rectangle
		for (int i = 0; i < sourceRect.getHeight(); i++) {
			HSSFRow sourceRow = source.getRow(sourceRect.getTop() + i);
			if (sourceRow != null) {
				HSSFRow newRow = dest.createRow(destPos.getY() + i);
				newRow.setHeight(sourceRow.getHeight());
				for (int j = 0; j < sourceRect.getWidth(); j++) {
					HSSFCell templateCell = sourceRow.getCell(sourceRect
							.getLeft() + j);
					if (templateCell != null) {
						HSSFCell newCell = newRow
								.createCell(destPos.getX() + j);
						copyCell(templateCell, newCell);
						if (inspector != null) {
							inspector.inspect(templateCell);
						}
					}
				}
			}
		}
	}

	/**
	 * Copy sourceRect on source sheet to the destPos on dest sheet.<BR/>
	 * The destRect can be calculated based on destPos and sourceRect:<BR/>
	 * destRect.left = destPos.x<BR/>
	 * destRect.top = destPos.y<BR/>
	 * destRect.right = destPos.x + sourceRect.width<BR/>
	 * destRect.bottom = destPos.y + sourceRect.height<BR/>
	 * Also need to append the given rows to the last row but one
	 * 
	 * @param source
	 * @param dest
	 * @param sourceRect
	 * @param destPos
	 * @param rows
	 * @param rowRect
	 *            content regions of the rows
	 */
	public void copyRect(HSSFSheet source, HSSFSheet dest, Rect sourceRect,
			Point destPos, List<HSSFRow> rows, Rect rowRect) {
		int targetRowFrom;
		int targetRowTo;
		int targetColumnFrom;
		int targetColumnTo;
		CellRangeAddress region = null;
		// Copy merged cells from source sheet to dest sheet
		for (int i = 0; i < source.getNumMergedRegions(); i++) {
			region = source.getMergedRegion(i);

			if ((region.getFirstRow() >= sourceRect.getTop())
					&& (region.getFirstColumn() >= sourceRect.getLeft())
					&& (region.getLastRow() <= sourceRect.getBottom())
					&& (region.getLastColumn() <= sourceRect.getRight())) {

				targetRowFrom = region.getFirstRow() - sourceRect.getTop()
						+ destPos.getY();
				targetRowTo = region.getLastRow() - sourceRect.getTop()
						+ destPos.getY();
				targetColumnFrom = region.getFirstColumn()
						- sourceRect.getLeft() + destPos.getX();
				targetColumnTo = region.getLastColumn() - sourceRect.getLeft()
						+ destPos.getX();

				CellRangeAddress newRegion = region.copy();

				newRegion.setFirstRow(targetRowFrom);
				newRegion.setFirstColumn(targetColumnFrom);
				newRegion.setLastRow(targetRowTo);
				newRegion.setLastColumn(targetColumnTo);
				dest.addMergedRegion(newRegion);
			}
		}

		// Copy each row in the rectangle
		for (int i = 0; i < sourceRect.getHeight(); i++) {
			if (i == sourceRect.getHeight() - 1) { // reach last row
				// insert reissue rows
				int rowNum = 0;
				for (HSSFRow row : rows) {
					HSSFRow newRow = dest
							.createRow(destPos.getY() + i + rowNum);
					newRow.setHeight(row.getHeight());

					for (int j = 0; j < rowRect.getWidth(); j++) {
						HSSFCell templateCell = row.getCell(rowRect.getLeft()
								+ j);
						if (templateCell != null) {
							HSSFCell newCell = newRow.createCell(destPos.getX()
									+ j);
							copyCell(templateCell, newCell);
						}
					}
					rowNum++;
				}
			}

			HSSFRow sourceRow = source.getRow(sourceRect.getTop() + i);
			if (sourceRow != null) {
				HSSFRow newRow = null;
				if (i == sourceRect.getHeight() - 1) {
					newRow = dest.createRow(destPos.getY() + i + rows.size());
				} else {
					newRow = dest.createRow(destPos.getY() + i);
				}
				newRow.setHeight(sourceRow.getHeight());
				for (int j = 0; j < sourceRect.getWidth(); j++) {
					HSSFCell templateCell = sourceRow.getCell(sourceRect
							.getLeft() + j);
					if (templateCell != null) {
						HSSFCell newCell = newRow
								.createCell(destPos.getX() + j);
						copyCell(templateCell, newCell);
						if (inspector != null) {
							inspector.inspect(templateCell);
						}
					}
				}
			}
		}
	}

	/**
	 * Calculate the regions in sheet which has content<BR/>
	 * Return the region in a rectangle object.
	 * 
	 * @param sheet
	 * @return
	 */
	public Rect getSheetContentRegion(HSSFSheet sheet) {
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		int firstCol = Integer.MAX_VALUE;
		int lastCol = 0;

		if (lastRow == 0) {
			if (sheet.getPhysicalNumberOfRows() == 0) {
				// no row in this sheet
				return null;
			}
		}

		for (int i = firstRow; i <= lastRow; i++) {
			if (sheet.getRow(i) != null) {
				int tmpNum = sheet.getRow(i).getLastCellNum();
				if (tmpNum > lastCol) {
					lastCol = tmpNum;
				}
				tmpNum = sheet.getRow(i).getFirstCellNum();
				if (tmpNum < firstCol) {
					firstCol = tmpNum;
				}
			}
		}
		Rect rect = new Rect(firstRow, firstCol, lastRow, lastCol);
		return rect;
	}

	/**
	 * Get all sheet content regions in a workbook
	 * 
	 * @param workbook
	 * @return
	 */
	public List<Rect> getAllSheetContentRegions(HSSFWorkbook workbook) {
		List<Rect> list = new ArrayList<Rect>();

		int sheetNum = workbook.getNumberOfSheets();
		HSSFSheet sheet = null;
		for (int i = 0; i < sheetNum; i++) {
			sheet = workbook.getSheetAt(i);
			list.add(getSheetContentRegion(sheet));
		}
		return list;
	}
	
	
	public String readCell(HSSFCell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			double dVal = cell.getNumericCellValue();
			return String.valueOf(dVal);
		case Cell.CELL_TYPE_BOOLEAN:
			boolean bVal = cell.getBooleanCellValue();
			return String.valueOf(bVal);
		case Cell.CELL_TYPE_STRING:
			String sVal = cell.getStringCellValue();
			return sVal;
		case Cell.CELL_TYPE_BLANK:
		case Cell.CELL_TYPE_FORMULA:
		case Cell.CELL_TYPE_ERROR:
			return "";
		}
		return null;
	}

}

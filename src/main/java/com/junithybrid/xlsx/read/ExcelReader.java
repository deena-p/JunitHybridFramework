package com.junithybrid.xlsx.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public String fileName;
	public FileInputStream fileInputStream = null;
	public FileOutputStream fileOutputStream = null;

	private XSSFWorkbook wb = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public ExcelReader(String fileName) throws IOException {
		super();
		this.fileName = fileName;

		try {
			fileInputStream = new FileInputStream(new File(fileName));
			wb = new XSSFWorkbook(fileInputStream);
			sheet = wb.getSheetAt(0);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			fileInputStream.close();
		}
	}

	public int rowCount(String sheetName) {
		sheet = wb.getSheet(sheetName);
		return sheet.getPhysicalNumberOfRows();
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		if (isSheetExist(sheetName)) {
			sheet = wb.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			Row columnsRow = sheet.getRow(0);

			Iterator<Cell> cellValues = columnsRow.cellIterator();
			while (cellValues.hasNext()) {
				int colNum = 0;
				if (cellValues.next().toString().equals(colName)) {
					return getCellData(sheetName, colNum, rowNum);
				}
				colNum++;
			}
			System.out.println("Column '"+ colName + "' does not exist");
		}

		return null;
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		if (isSheetExist(sheetName)) {
			sheet = wb.getSheet(sheetName);
			if (rowNum < sheet.getPhysicalNumberOfRows()) {
				row = sheet.getRow(rowNum);
				if (colNum < row.getPhysicalNumberOfCells()) {
					return row.getCell(colNum).toString();
				} else {
					System.out.println("Column number: "+ colNum +" is incorrect");
				}

			} else {
				System.out.println("Row number: "+ rowNum +" is incorrect");
			}
		}
		return null;
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		sheet = wb.getSheet(sheetName);

		return false;
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		sheet = wb.getSheet(sheetName);

		return false;
	}

	public boolean addSheet(String sheetName) {

		return false;
	}

	public boolean removeSheet(String sheetName) {

		return false;
	}

	public boolean addColumn(String sheetName, String colName) {

		return false;
	}

	public boolean removeColumn(String sheetName, int colNum) {

		return false;
	}

	public boolean isSheetExist(String sheetName) {
		sheet = wb.getSheet(sheetName);
		if (sheet != null) {
			return true;
		}
		System.out.println("Sheet '"+ sheetName +"' does not exist");
		return false;
	}

	public int getColumnCount(String sheetName) {
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(0);

		return row.getPhysicalNumberOfCells();
	}

	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {

		return false;
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {

		return 0;
	}

	@SuppressWarnings("unused")
	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default:
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		String sFile = "/home/deena/workspace/JunitHybridFramework/src/main/java/com/junithybrid/xlsx/Suite1.xlsx";
		ExcelReader reader = new ExcelReader(sFile);
		System.out.println(reader.getCellData("Testcase2", 10, 10));
		System.out.println(reader.getCellData("Testcase", "TC_NAME2", 10));
		System.out.println(reader.getCellData("Testcase", 10, 1));

	}

}

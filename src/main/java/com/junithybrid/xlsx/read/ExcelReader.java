package com.junithybrid.xlsx.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junithybrid.test.DriverScript;

public class ExcelReader {
	private final Logger logger = LoggerFactory.getLogger(DriverScript.class);
	public String fileName;
	public FileInputStream fileInputStream = null;
	public FileOutputStream fos = null;

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
			logger.warn(t.getMessage());
		} finally {
			fileInputStream.close();
		}
	}

	public int rowCount(String sheetName) {
		int rowTotal = 0;
		int colTotal = 0;
		sheet = wb.getSheet(sheetName);
		try{
			while (rowTotal < sheet.getPhysicalNumberOfRows()){
				colTotal = sheet.getRow(rowTotal).getPhysicalNumberOfCells();
			
				if (colTotal > 1){
					rowTotal++;
				}
			}
		}catch(Throwable t){
			//t.printStackTrace();
			return rowTotal;
		}
		return rowTotal;
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		if (isSheetExist(sheetName)) {
			sheet = wb.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			Row columnsRow = sheet.getRow(0);
			int colNum = 0;

			Iterator<Cell> cellValues = columnsRow.cellIterator();
			while (cellValues.hasNext()) {
				String temp = cellValues.next().toString();
				//System.out.println(temp);
				if (temp.equals(colName)) {
					String val = getCellData(sheetName, colNum, rowNum); 
					return val;
				}
				colNum++;
			}
			logger.warn("Column '"+ colName + "' does not exist");
		}

		return null;
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		try{
			if (isSheetExist(sheetName)) {
				sheet = wb.getSheet(sheetName);

				if (rowNum < rowCount(sheetName)) {
					row = sheet.getRow(rowNum);
					if (colNum < sheet.getRow(0).getPhysicalNumberOfCells()) {
						String val2 = row.getCell(colNum).toString(); 
						return val2;
					} else {
						logger.warn("Column number: "+ colNum +" is incorrect");
					}

				} else {
					logger.warn("Row number: "+ rowNum +" is incorrect");
				}
			}

		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
	}
	
	public boolean setCellData(String sheetName, int colNum, int rowNum, String data) throws IOException {
		try{
			fos = new FileOutputStream(new File(fileName));
			if (isSheetExist(sheetName)) {
				sheet = wb.getSheet(sheetName);

				if (rowNum < rowCount(sheetName)) {
					row = sheet.getRow(rowNum);
					if (colNum < sheet.getRow(0).getPhysicalNumberOfCells()) {
						row.createCell(colNum);
						row.getCell(colNum).setCellValue(data);
						wb.write(fos);
						return true;
					} else {
						logger.warn("Column number: "+ colNum +" is incorrect");
					}

				} else {
					logger.warn("Row number: "+ rowNum +" is incorrect");
				}
			}

		}catch(Throwable t){
			t.printStackTrace();
		}finally{
			fos.close();
		}
		return false;
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data) throws IOException {
		if (isSheetExist(sheetName)) {
			sheet = wb.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			Row columnsRow = sheet.getRow(0);
			int colNum = 0;

			Iterator<Cell> cellValues = columnsRow.cellIterator();
			while (cellValues.hasNext()) {
				String temp = cellValues.next().toString();
				if (temp.equals(colName)) {
					return setCellData(sheetName, colNum, rowNum, data);
				}
				colNum++;
			}
			logger.warn("Column '"+ colName + "' does not exist");
		}

		return false;
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		sheet = wb.getSheet(sheetName);

		return false;
	}

	public boolean addSheet(String sheetName) throws IOException {
		try{
			fos = new FileOutputStream(new File(fileName));
			wb.createSheet(sheetName);
			wb.write(fos);
		}catch(Throwable t){
			t.printStackTrace();
			return false;
		}finally{
			fos.close();
		}
		return true;
	}

	public boolean removeSheet(String sheetName) throws IOException {
		try{
			fos = new FileOutputStream(new File(fileName));
			wb.removeSheetAt(wb.getSheetIndex(sheetName));
			wb.write(fos);
		}catch(Throwable t){
			t.printStackTrace();
			return false;
		}finally{
			fos.close();
		}
		return true;
	}

	public boolean addColumn(String sheetName, String colName) throws IOException {
		try{
			fos = new FileOutputStream(new File(fileName));
			if (isSheetExist(sheetName)) {
				sheet = wb.getSheet(sheetName);
				Row row = sheet.getRow(0);
				if (row == null){
					sheet.createRow(0);
				}
				int lastCellNo = row.getLastCellNum();
				if (lastCellNo == -1){
					row.createCell(0);
				}else{
					row.createCell(lastCellNo);
				}
				
				row.getCell(lastCellNo).setCellValue(colName);
				wb.write(fos);
				
			}

		}catch(Throwable t){
			t.printStackTrace();
		}finally{
			fos.close();
		}
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
		logger.warn("Sheet '"+ sheetName +"' does not exist");
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
		case Cell.CELL_TYPE_BLANK:
			return null;
		default:
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		//String sFile = "/home/deena/workspace/JunitHybridFramework/src/main/java/com/junithybrid/xlsx/Suite.xlsx";
		String sFile = "/home/deena/workspace/JunitHybridFramework/src/main/java/com/junithybrid/xlsx/Check_Items.xlsx";
		ExcelReader reader = new ExcelReader(sFile);
		//System.out.println(reader.getCellData("Testcase2", 10, 10));
		//System.out.println(reader.getCellData("TestSuite", "TSUITE_RunMode", 2));
		//System.out.println(reader.getCellData("Testcases", 2, 3));
		//System.out.println(reader.getCellData("LoginTest", 6, 2));
		//System.out.println(reader.getCellData("Teststeps", 3, 2));
		
		//reader.addColumn("Sheet5", "Result1");
		System.out.println(reader.setCellData("CheckItems", "Result", 1,"Pass"));
		//System.out.println(reader.rowCount("Testcases") + "is the row Count");
	}

}

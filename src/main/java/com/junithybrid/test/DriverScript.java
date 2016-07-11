package com.junithybrid.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junithybrid.xlsx.read.ExcelReader;

public class DriverScript {

	public static Logger logger = LoggerFactory.getLogger(DriverScript.class);
	public ExcelReader master_TSuite_Reader = null;
	public ExcelReader current_TSuite_Reader = null;
	public int currentTSuiteID;
	public int currentTCaseID;
	public int currentTStepID;
	public int currentTestDataID;
	public String current_TSuite = null;
	public String current_TCaseName = null;
	public String current_TStepName = null;
	public String current_keyWord = null;
	public String fileRunMode = null;

	public Method[] methods;
	public Keywords keywords;
	public String keyword_execution_result = null;
	public ArrayList<String> resultSet;

	public DriverScript(){
		keywords = new Keywords();
		methods = keywords.getClass().getMethods();
	}


	public void initialize() throws IOException {
		logger.info("Initializing the driver script.....");
		master_TSuite_Reader = new ExcelReader(
				System.getProperty("user.dir") + "/src/main/java/com/junithybrid/xlsx/Suite.xlsx");
		logger.info("Executing the Suite.xlsx.....");

		/*
		 * 
		 * check the run mode of Test suite 
		 * check the run mode of the test cases in each test suite 
		 * execute the keywords of test cases serially execute the keywords as many times as number of data sets which are set to Y
		 * 
		 */

		try{
			for (currentTSuiteID = 1; currentTSuiteID < master_TSuite_Reader.rowCount(Constants.TestSuite_SheetName); currentTSuiteID++) {

				if (master_TSuite_Reader.getCellData(Constants.TestSuite_SheetName, Constants.TestSuite_RunMode, currentTSuiteID)
						.equals(Constants.RunMode_Yes)) {
					current_TSuite = master_TSuite_Reader.getCellData(Constants.TestSuite_SheetName,
							Constants.TestSuite_TSUITE_ID, currentTSuiteID);
					logger.info("*****Executing the test suite ID: " + current_TSuite + "********************");

					// Test suite id equals to testcases.xlsx
					current_TSuite_Reader = new ExcelReader(System.getProperty("user.dir")
							+ "/src/main/java/com/junithybrid/xlsx/" + current_TSuite + ".xlsx");



					for (currentTCaseID = 1; currentTCaseID < current_TSuite_Reader.rowCount(Constants.TestCases_SheetName); currentTCaseID++) {

						


						current_TCaseName = current_TSuite_Reader.getCellData(Constants.TestCases_SheetName, Constants.TestCase_TCASE_ID, currentTCaseID);

						//System.out.println(current_TCaseName);

						fileRunMode = current_TSuite_Reader.getCellData(Constants.TestCases_SheetName, Constants.TestCase_RunMode, currentTCaseID);

						if (fileRunMode.equals(Constants.RunMode_Yes)) {

							logger.info("=======Executing the test cases ID: " + current_TCaseName + "=======================");

							if (current_TSuite_Reader.isSheetExist(current_TCaseName)){

								for (currentTestDataID = 1; currentTestDataID < current_TSuite_Reader.rowCount(current_TCaseName); currentTestDataID++) {

									resultSet = new ArrayList<String>();
									fileRunMode = current_TSuite_Reader.getCellData(current_TCaseName, Constants.TestData_RunMode, currentTestDataID); 

									if (fileRunMode.equals(Constants.RunMode_Yes)) {
										executeKeyword();
										createXLSXReport();
									}else{
										createXLSXReport();
									}
									
								}
							}else {
								executeKeyword();
								createXLSXReport();
							}
						}

					}

				}

			}
		}catch(Throwable t){
			t.printStackTrace();
			logger.error(t.toString());
		}

	}

	private void createXLSXReport() throws IOException {
		
		String newColName = Constants.Keyword_Result+(currentTestDataID-1);
		boolean isResultColExist = false;
		
		for (int c=0;c<current_TSuite_Reader.getColumnCount(Constants.TestSteps_SheetName);c++){
			String tempColName = current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName, c, 0);
			//System.out.println(tempColName);
			if (tempColName.equals(newColName)){
				isResultColExist = true;
				break;
			}
		}
		
		if (!isResultColExist){
			current_TSuite_Reader.addColumn(Constants.TestSteps_SheetName, newColName);
			
			
		}
		
		int index = 1;
		for (int i=1;i<current_TSuite_Reader.rowCount(Constants.TestSteps_SheetName);i++){
			
			//System.out.println("===="+current_TCaseName+"===="+current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName, Constants.TestCase_TCASE_ID, i));
			
			if (current_TCaseName.equals(current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName, Constants.TestCase_TCASE_ID, i))){

				if (resultSet.size()==0){
					current_TSuite_Reader.setCellData(Constants.TestSteps_SheetName, newColName, index, Constants.Keyword_Skip);
				}
				for (int z=0;z<resultSet.size();z++){
					String temp = resultSet.get(z);
					if (!temp.equals("Pass")){
						current_TSuite_Reader.setCellData(Constants.TestSteps_SheetName, newColName, index, Constants.Keyword_Fail);
					}else{
						current_TSuite_Reader.setCellData(Constants.TestSteps_SheetName, newColName, index, Constants.Keyword_Pass);
					}

				}
			}
			
			index++;
		}
		
		if (resultSet.size() == 0){
			current_TSuite_Reader.setCellData(current_TCaseName, Constants.Keyword_Result, currentTestDataID, Constants.Keyword_Skip);
			return;
		}else{
			for (int i=0;i<resultSet.size();i++){

				if (resultSet.get(i)!= "Pass"){
					current_TSuite_Reader.setCellData(current_TCaseName, Constants.Keyword_Result, currentTestDataID, Constants.Keyword_Fail);
					return;
				}

			}

		}
		current_TSuite_Reader.setCellData(current_TCaseName, Constants.Keyword_Result, currentTestDataID, Constants.Keyword_Pass);
	}


	public String openBrowser(){
		logger.debug("Opening the browser from parent....");
		return "Test";
	}

	private void executeKeyword() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		logger.info("-----------Executing the test for: " + current_TSuite_Reader.getCellData(current_TCaseName, 1, currentTestDataID) + "------------------");

		for (currentTStepID = 1; currentTStepID < current_TSuite_Reader.rowCount(Constants.TestSteps_SheetName); currentTStepID++) {

			if (current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName,Constants.TestCase_TCASE_ID, currentTStepID).equals(current_TCaseName)) {
				current_keyWord = current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName, Constants.TestSteps_KeyWord, currentTStepID);
				logger.debug("................Executing the keyword: " + current_keyWord	+ "...............");
				for(Method method:methods){
					if (method.getName().equals(current_keyWord)){
						keyword_execution_result = (String) method.invoke(keywords, (Object[])null);
						resultSet.add(keyword_execution_result);
						//System.out.println(keyword_execution_result);
					}
				}
			}
		}

	}

	/*private void executeKeyword(String sKeyword) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		for(Method method:methods){
			if (method.getName().equals(sKeyword)){
				keyword_execution_result = (String) method.invoke(keywords, (Object[])null);
				resultSet.add(keyword_execution_result);
				//System.out.println(keyword_execution_result);
			}
		}
	}*/

	public static void main(String[] args) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DriverScript ds = new DriverScript();
		ds.initialize();
		//ds.executeKeyword("clickButton");
	}
}

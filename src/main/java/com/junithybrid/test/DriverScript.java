package com.junithybrid.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junithybrid.xlsx.read.ExcelReader;

public class DriverScript {

	private final Logger logger = LoggerFactory.getLogger(DriverScript.class);
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

	public void initialize() throws IOException {
		logger.info("Initializing the driver script.....");
		master_TSuite_Reader = new ExcelReader(
				System.getProperty("user.dir") + "/src/main/java/com/junithybrid/xlsx/Suite.xlsx");
		logger.info("Executing the Suite.xlsx.....");

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

						if (current_TSuite_Reader.getCellData(Constants.TestCases_SheetName, Constants.TestCase_RunMode, currentTCaseID).equals(Constants.RunMode_Yes)) {
							current_TCaseName = current_TSuite_Reader.getCellData(Constants.TestCases_SheetName,
									Constants.TestCase_TCASE_ID, currentTCaseID);
							logger.info("=======Executing the test cases ID: " + current_TCaseName + "=======================");

							if (current_TSuite_Reader.isSheetExist(current_TCaseName)){
								for (currentTestDataID = 1; currentTestDataID < current_TSuite_Reader.rowCount(current_TCaseName); currentTestDataID++) {

									if (current_TSuite_Reader.getCellData(current_TCaseName, Constants.TestData_RunMode, currentTestDataID).equals(Constants.RunMode_Yes)) {
										String first_column = current_TSuite_Reader.getCellData(current_TCaseName, 1, currentTestDataID);
										logger.debug("-----------Executing the test for: " + first_column + "------------------");

										for (currentTStepID = 1; currentTStepID < current_TSuite_Reader.rowCount(Constants.TestSteps_SheetName); currentTStepID++) {
											if (current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName,Constants.TestCase_TCASE_ID, currentTStepID).equals(current_TCaseName)) {
												current_keyWord = current_TSuite_Reader.getCellData(
														Constants.TestSteps_SheetName, Constants.TestSteps_KeyWord,
														currentTStepID);
												logger.debug("................Executing the keyword: " + current_keyWord	+ "...............");
											}
										}
									}

								}
							}else{
								logger.warn("Checking if there are keywords present for this testcase");
								for (currentTStepID = 1; currentTStepID < current_TSuite_Reader.rowCount(Constants.TestSteps_SheetName); currentTStepID++) {
									if (current_TSuite_Reader.getCellData(Constants.TestSteps_SheetName,Constants.TestCase_TCASE_ID, currentTStepID).equals(current_TCaseName)) {
										//logger.info("Keywords are found and executing the keywords....");
										current_keyWord = current_TSuite_Reader.getCellData(
												Constants.TestSteps_SheetName, Constants.TestSteps_KeyWord,
												currentTStepID);
										logger.debug("................Executing the keyword: " + current_keyWord	+ "...............");
									}else{
										if (currentTStepID == current_TSuite_Reader.rowCount(Constants.TestSteps_SheetName)-1){
											logger.debug("No keywords found.......");
										}
									}
								}
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

	public static void main(String[] args) throws IOException {
		DriverScript ds = new DriverScript();
		ds.initialize();
	}

	/*
	 * check the run mode of Test suite check the run mode of the test cases in
	 * each test suite execute the keywords of test cases serially execute the
	 * keywords as many times as number of data sets which are set to Y
	 * 
	 * 
	 * 
	 */
}

package com.junithybrid.test;
import static com.junithybrid.test.DriverScript.logger;

public class Keywords{
	

	public Keywords() {
		super();
	}

	public String openBrowser(){
		logger.debug("Opening the browser....");
		
		return Constants.Keyword_Pass;
	}
	
	public String naviage(){
		logger.debug("Navigating to ....");
		
		return Constants.Keyword_Pass;
	}
	
	public String verifyTitle(){
		logger.debug("Verifying title....");
		
		return Constants.Keyword_Pass;
	}
	
	public String clickLink(){
		logger.debug("Clicking link....");
		
		return Constants.Keyword_Pass;
	}
	
	public void writeInInput(){
		logger.debug("Writing input....");
		
	}
	
	public String clickButton(){
		logger.debug("Clicking button....");
		
		return Constants.Keyword_Pass;
	}
	
	public String verifyText(){
		logger.debug("Verifying text....");
		
		return Constants.Keyword_Pass;
	}
}

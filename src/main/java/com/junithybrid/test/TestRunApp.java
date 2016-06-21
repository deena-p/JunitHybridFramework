package com.junithybrid.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunApp {

    //No static, else log.name.IS_UNDEFINED.log
    private final Logger logger = LoggerFactory.getLogger(TestRunApp.class);

	public static void main(String[] args) {

		//Set this before the logger start.
		//System.setProperty("log.name", "abcdefg");

		TestRunApp obj = new TestRunApp();
		obj.start();

	}

	private void start() {

        logger.debug("debug**************************");
        logger.error("error===================");
        logger.info("info............");
        logger.warn("warn------------------");
     	//...
	}

}

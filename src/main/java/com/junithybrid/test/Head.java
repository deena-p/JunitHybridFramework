package com.junithybrid.test;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Head implements Runnable {
	
	static Logger logger = LoggerFactory.getLogger(Head.class);
	private String name;

	public void run() {
		MDC.put("logFileName", getName());
		logger.debug("hello how are you?");
		MDC.remove("logFileName");

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) {
		int count = 1;
		while(count<=10){
			Head head = new Head();/*
			head.setName("head-" + count);
			threadPools.execute(head);
			ThreadPoolExecutor executor = new ThreadPoolExecutor();
			ThreadPools threadPools = new ThreadPoolExecutor();*/
			count++;
		}
	}

}

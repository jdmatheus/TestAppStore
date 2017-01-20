package com.grability.catalogoapps.exceptions;

import org.apache.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class AppUncaughtException implements UncaughtExceptionHandler {
	
	private static Logger logger = Logger.getLogger(AppUncaughtException.class);

	@Override
	public void uncaughtException(Thread thread, Throwable t) {
		logger.fatal("uncaughtException", t);
	}
	

}

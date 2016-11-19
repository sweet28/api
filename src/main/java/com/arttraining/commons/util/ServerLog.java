package com.arttraining.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLog {
	private static Logger logger;
	/**
	 * 根据上次调用栈返回Logger
	 * @return
     */
	public static Logger getLogger(){
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		logger = LoggerFactory.getLogger(stack[2].getClassName()+"."+stack[2].getMethodName());
		return logger;
	}
	public static void main(String[] args) {
		
		ServerLog.getLogger().info("hh");
		ServerLog.getLogger().info("hh");
	}
}

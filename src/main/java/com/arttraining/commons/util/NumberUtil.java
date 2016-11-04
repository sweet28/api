package com.arttraining.commons.util;

public class NumberUtil {
	/**
	  * 判断字符串是否是整数
	  */
	 public static boolean isInteger(String value) {
	  try {
		  Integer.valueOf(value);
		  return true;
	  } catch (NumberFormatException e) {
		  return false;
	  }
	 }
}

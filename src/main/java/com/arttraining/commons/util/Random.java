package com.arttraining.commons.util;

public class Random {

	/**
   	 * 随机指定范围内N个不重复的数
   	 * @param min 指定范围最小值
   	 * @param max 指定范围最大值
   	 * @param n 随机数个数
   	 */
   	public static int[] randomCommon(int min, int max, int n){
   		if (n > (max - min + 1) || max < min) {
               return null;
           }
   		int[] result = new int[n];
   		int count = 0;
   		while(count < n) {
   			int num = (int) (Math.random() * (max - min)) + min;
   			boolean flag = true;
   			for (int j = 0; j < n; j++) {
   				if(num == result[j]){
   					flag = false;
   					break;
   				}
   			}
   			if(flag){
   				result[count] = num;
   				System.out.println(num);
   				count++;
   			}
   		}
   		return result;
   	}
   	
   	/**
   	 * 随机指定范围内N个不重复的数
   	 * @param min 指定范围最小值
   	 * @param max 指定范围最大值
   	 * @param n 随机数个数
   	 */
   	public static String randomCommonStr(int min, int max, int n){
   		if (n > (max - min + 1) || max < min) {
               return null;
           }
   		int[] result = new int[n];
   		String resultStr = "";
   		int count = 0;
   		while(count < n) {
   			int num = (int) (Math.random() * (max - min)) + min;
   			boolean flag = true;
   			for (int j = 0; j < n; j++) {
   				if(num == result[j]){
   					flag = false;
   					break;
   				}
   			}
   			if(flag){
   				result[count] = num;
   				resultStr = resultStr + num;
   				count++;
   			}
   		}
   		return resultStr;
   	}
   	
   	/**
   	 * 随机指定范围内N个不重复的数
   	 * @param min 指定范围最小值
   	 * @param max 指定范围最大值
   	 * @param n 随机数个数
   	 */
   	public static String randomCommonStr(int n){
   		int max = 9;
   		int min = 0;
   		int[] result = new int[n];
   		String resultStr = "";
   		int count = 0;
   		while(count < n) {
   			int num = (int) (Math.random() * (max - min)) + min;
   			boolean flag = true;
   			for (int j = 0; j < n; j++) {
   				if(num == result[j]){
   					flag = false;
   					break;
   				}
   			}
   			if(flag){
   				result[count] = num;
   				resultStr = resultStr + num;
   				count++;
   			}
   		}
   		return resultStr;
   	}
   	
   	/***
   	 * 随机生成1个10--100的数字
   	 * @param args
   	 */
   	public static Integer randomCommonInt() {
   		Integer max= ConfigUtil.RANDOM_MAXVALUE;
   		Integer min= ConfigUtil.RANDOM_MINVALUE;
   		Integer num = (int) (Math.random() * (max - min)) + min;
   		return num;
   	}
   	
   	public static void main(String[] args){
   		System.out.println(randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH).toString());
   	}
}

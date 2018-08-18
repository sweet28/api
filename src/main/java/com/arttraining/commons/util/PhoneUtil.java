package com.arttraining.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
	/**
	 * 手机号验证
	 * @param  str
	 * @return 验证通过返回true
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有: 13+任意数  15+除4的任意数  18+除1和4的任意数  17+除9的任意数  147 
     * 
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		//p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,4,5-9])|(17[0-8])|(19[0-9])|(16[0-8])|(147))\\d{8}$";  
		p = Pattern.compile(regExp);  
		m = p.matcher(str); 
		b = m.matches(); 
		return b;
	}
	/**
	 * 电话号码验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) { 
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{	m = p1.matcher(str);
 		    b = m.matches();  
		}else{
			m = p2.matcher(str);
 			b = m.matches(); 
		}  
		return b;
	}
	/**
	 * 手机号隐藏4位
	 * 传递的参数:手机号
	 * 先判断手机号的长度是否是11位 如果是11位 则隐藏其中的4位
	 * 否则使用云互艺公司随机生成的6位数字
	 * 
	 */
	public static String hiddenPhoneNumber(String phone) {
		String hiddenPhone="";
		if (phone.length()==11){
			hiddenPhone = phone.substring(0,3)+"****"+phone.substring(7,phone.length());
        } else {
        	hiddenPhone="yhy_"+Random.randomCommonStr(10, 100, 6);
        }
		return hiddenPhone;
	}

}

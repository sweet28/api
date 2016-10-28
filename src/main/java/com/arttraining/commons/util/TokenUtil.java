package com.arttraining.commons.util;

import java.util.Date;

public class TokenUtil {
	//todo:生成token
	public static String generateToken(String account, String pwd){
		String token = "1d8edeb159e97a8213359031b93985b6";
		String nowTime = System.currentTimeMillis() + "";
		
		token = MD5.encodeString(account+pwd+nowTime);
		
		return token;
	}
	
	//todo:验证token是否有效
	public static boolean checkToken(String token){
		boolean flag = false;
		
		return flag;
	}
	
	//todo:将token有效期延期，token默认有效期为7天，当天操作过token将有效期延期一天
	public static boolean delayTokenDeadline(String token){
		boolean flag = false;
		
		return flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(generateToken("15201633796", "test"));
	}

}

package com.arttraining.commons.util;

import java.util.Date;

public class TokenUtil {
	public static String generateToken(String account, String pwd){
		String token = "1d8edeb159e97a8213359031b93985b6";
		String nowTime = System.currentTimeMillis() + "";
		
		token = MD5.encodeString(account+pwd+nowTime);
		
		return token;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(generateToken("15201633796", "test"));
	}

}

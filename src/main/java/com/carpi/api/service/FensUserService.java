package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensUser;

public interface FensUserService {

	//粉丝注册
	public JsonResult register(FensUser fensUser,String code_type,String code,String cardNumber);
	
	//登入
	public JsonResult login(FensUser fensUser);
	
	//身份验证(插入身份证信息)
	public JsonResult addCard(FensAuthentication fensAuthentication);
	
	//忘记密码
	public JsonResult forgetPwd(FensUser fensUser,String code_type, String code);
	
}

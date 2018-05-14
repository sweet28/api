package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.FensUser;
import com.github.pagehelper.PageInfo;

public interface FensUserService {

	//粉丝注册
	public JsonResult register(FensUser fensUser,String code_type,String code,String cardNumber);
	
	//登入
	public JsonResult login(FensUser fensUser);
	
	//忘记密码
	public JsonResult forgetPwd(FensUser fensUser,String code_type, String code);
	
	//粉丝团列表
	public PageInfo<FensTeam> selectAll(Integer page,Integer num,Integer fensUserId,String type);
	
	
}

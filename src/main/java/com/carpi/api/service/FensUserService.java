package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensLoginState;
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
	
	//交易密码
	public JsonResult jiaoYi(FensUser fensUser);
	
	//修改交易密码
	public JsonResult updateJiaoYi(FensUser fensUser);

	//修改信息
	public JsonResult updateInfo(FensUser fensUser);
	
	//粉丝团列表
	public PageInfo<FensTeam> selectAll(Integer page,Integer num,Integer fensUserId,String type);
	
	//粉丝算力
	public PageInfo<FensComputingPower> selectComputingPower(Integer page,Integer num,Integer fensUserId);
	
	//添加粉丝算力
	public JsonResult addselectComputingPower(FensComputingPower fensComputingPower);
	
	//修改粉丝算力
	public JsonResult updateComputingPower(FensComputingPower fensComputingPower);
	
	//粉丝算力求和
	public JsonResult selectSum(Integer fensUserId);
	
	//粉丝登入状态列表
	public PageInfo<FensLoginState> selectStatus(Integer page, Integer num);
	
	//插入粉丝登入状态
	public JsonResult addFensLoginState(FensLoginState fensLoginState);
	
	//粉丝团列表2
	public PageInfo<FensUser> selectAllUser(Integer page,Integer num,String phone,String type);
}

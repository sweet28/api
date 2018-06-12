package com.carpi.api.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensLoginState;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.FensUser;
import com.github.pagehelper.PageInfo;

public interface FensUserService {

	// 粉丝注册
	public JsonResult register(FensUser fensUser, String code_type, String code, String cardNumber);

	// 登入（手机号密码）
	public JsonResult login(FensUser fensUser);

	// 登入（手机号验证码）
	public JsonResult login2(String phone, String code, String code_type);

	// 忘记密码
	public JsonResult forgetPwd(FensUser fensUser, String code_type, String code);

	// 修改密码
	public JsonResult updatePwd(String OldPwd, String newPwd, Integer fensUserId);

	// 交易密码
	public JsonResult jiaoYi(FensUser fensUser, String code);
	
	// 校验交易密码
	JsonResult zijin(Integer fensUserId,String zjMiMa);

	// 修改交易密码
		public JsonResult updateJiaoYi(String oldCapitalPwd, String newCapitalPwd, Integer fensUserId,String code,String phone);

	// 修改信息
	public JsonResult updateInfo(FensUser fensUser);

	// 修改信息
	public JsonResult updateInfo2(FensUser fensUser);
	
	// 粉丝团列表
	public PageInfo<FensTeam> selectAll(Integer page, Integer num, Integer fensUserId, String type);

	// 粉丝算力
	public PageInfo<FensComputingPower> selectComputingPower(Integer page, Integer num, Integer fensUserId);

	// 添加粉丝算力
	public JsonResult addselectComputingPower(FensComputingPower fensComputingPower);

	// 修改粉丝算力
	public JsonResult updateComputingPower(FensComputingPower fensComputingPower);

	// 粉丝算力求和
	public JsonResult selectSum(Integer fensUserId);

	// 粉丝登入状态列表
	public PageInfo<FensLoginState> selectStatus(Integer page, Integer num);

	// 插入粉丝登入状态
	public JsonResult addFensLoginState(FensLoginState fensLoginState);

	// 粉丝团列表2
	public PageInfo<FensUser> selectAllUser(Integer page, Integer num, String phone, String type);

	FensUser info(FensUser fensUser);

	// 待审核
	public JsonResult selectDSH(Integer uid);

	List<FensUser> selectListFens(String phone);

	PageInfo<FensUser> selectListQINYOU(String phone);

	JSONObject selectListFens2(String phone);
	
	JsonResult checkFens(Integer id, String phone);

	JSONObject selectFensUserGrade(String phone, Integer uid);
}

package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.github.pagehelper.PageInfo;

public interface FensWalletService {

	//粉丝钱包列表
	public PageInfo<FensWallet> selectAll(Integer page,Integer num,Integer fensUserId);
	
	//新增钱包
	public JsonResult addWallet(FensWallet fensWallet);
	
	//修改钱包
	public JsonResult updateWallet(FensWallet fensWallet);
	
	//钱包转账记录列表
	public PageInfo<FoneyRecord> selectWalletRecord(Integer page,Integer num,Integer fensUserId);
	
	//新增钱包转账记录
	public JsonResult addWalletRecord(FoneyRecord foneyRecord);
	
	//修改钱包转账记录
	public JsonResult updateWalletRecord(FoneyRecord foneyRecord);
}

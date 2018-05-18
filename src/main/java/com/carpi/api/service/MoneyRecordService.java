package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.github.pagehelper.PageInfo;

public interface MoneyRecordService {

	// 粉丝转入转出cpa记录
	public JsonResult zhuan(FoneyRecord foneyRecord, FensWallet fensWallet);

	// 钱包详情
	public JsonResult select(Integer fensUserId);
	
}

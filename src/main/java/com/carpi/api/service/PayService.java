package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.BankCard;

public interface PayService {

	//添加支付宝微信支付
	public JsonResult aliPay(BankCard bankCard);
	
	//根据粉丝id查询资金密码
	public JsonResult checkMiMa(String fensUserId,String mima);
	
	//查询支付宝或者微信
	public JsonResult selectPay(BankCard bankCard);

	//查询支付账户信息
	public JsonResult selectZh(Integer fensUserId);
}

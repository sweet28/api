package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.BankCard;
import com.github.pagehelper.PageInfo;

public interface BankCardService {

	//根据粉丝Id查询粉丝银行卡列表
	public PageInfo<BankCard> selectAll(Integer pageNum,Integer pageSize,Integer fensUserId);
	
	//设置默认使用的银行卡(0：不使用；1：使用)
	public JsonResult updateApply(BankCard bankCard);
	
	//粉丝添加银行卡
	public JsonResult addBlank(BankCard bankCard);
	
	//粉丝修改银行卡 
	public JsonResult updateBlank(BankCard bankCard);
	
	
}

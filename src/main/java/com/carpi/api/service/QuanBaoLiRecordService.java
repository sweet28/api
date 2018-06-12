package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;

public interface QuanBaoLiRecordService {

	//券宝理列表
	public JsonResult selectList();
	
	//券宝理个人订单
	public JsonResult selectOne(Integer fensUserId);
	
	//查询券详情
	public JsonResult xiangQing(Integer id);
}

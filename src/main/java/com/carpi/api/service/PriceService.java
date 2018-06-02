package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;

public interface PriceService {

	//查询今日价格
	public JsonResult selectToday();
	
	//根据时间段查询
	public JsonResult selectall(String startTime,String endTime);
	
	//一周的数据
	public JsonResult selectWeek();
}

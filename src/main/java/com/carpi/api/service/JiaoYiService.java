package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensTransaction;

public interface JiaoYiService {

	// 接单人接单(买单)
	public JsonResult buyDanJieDan(FensTransaction fensTransaction);

	// 买单人已付款（买单）
	public JsonResult buyDanYiFu(FensTransaction fensTransaction);

	// 接单人接单(卖单)
	public JsonResult sellDanJieDan(FensTransaction fensTransaction);

	// 买单人已付款（卖单）
	public JsonResult sellDanYiFu(FensTransaction fensTransaction);
}

package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.PriceMapper;
import com.carpi.api.pojo.Price;
import com.carpi.api.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceMapper priceMapper;

	// 查询今日价格
	@Override
	public JsonResult selectToday() {
		Price price = priceMapper.selectToday();
		if (price != null) {
			return JsonResult.ok(price);
		}
		return JsonResult.build(500, "查询时间出错");
	}

	// 根据时间段查询
	@Override
	public JsonResult selectall(String startTime, String endTime) {

		List<Price> list = priceMapper.selectAll(startTime, endTime);
		if (list != null && list.size() > 0) {
			return JsonResult.ok(list);
		}else{
			return null;
		}
	}

}

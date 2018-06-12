package com.carpi.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.QuanBaoLiMapper;
import com.carpi.api.dao.QuanBaoLiRecordMapper;
import com.carpi.api.dao.QuanDakuanRecordMapper;
import com.carpi.api.pojo.QuanBaoLi;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.pojo.QuanDakuanRecord;
import com.carpi.api.service.QuanBaoLiRecordService;

@Service
public class QuanBaoLiRecordServiceImpl implements QuanBaoLiRecordService {

	@Autowired
	private QuanBaoLiMapper quanBaoLiMapper;

	@Autowired
	private QuanBaoLiRecordMapper quanBaoLiRecordMapper;
	
	@Autowired
	private QuanDakuanRecordMapper quanDakuanRecordMapper;

	// 券宝理列表
	@Override
	public JsonResult selectList() {
		List<QuanBaoLi> list = quanBaoLiMapper.selectList();
		if (list.size() > 0) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "无信息");
	}

	// 券宝理个人订单
	@Override
	public JsonResult selectOne(Integer fensUserId) {
		if (fensUserId == null) {
			return JsonResult.build(500, "请重新登入");
		}
		List<QuanBaoLiRecord> list = quanBaoLiRecordMapper.selectList(fensUserId);
		if (!list.isEmpty()) {
			return JsonResult.ok(list);
		} else {
			return JsonResult.build(500, "无订单信息");
		}

	}

	// 查询券详情
	@Override
	public JsonResult xiangQing(Integer id) {
		if (id == null) {
			return JsonResult.build(500, "请选择订单");
		}
		// 查询订单信息
		QuanBaoLiRecord quanBaoLiRecord = quanBaoLiRecordMapper.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(quanBaoLiRecord)) {
            return JsonResult.build(500, "无订单信息");
		}
	    //查询打款信息(根据券id)
		QuanDakuanRecord quanDakuanRecord = quanDakuanRecordMapper.selectQuan(quanBaoLiRecord.getId());
//		if (StringUtils.isEmpty(quanDakuanRecord)) {
//			 return JsonResult.build(500, "无打款信息");
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ddxx", quanBaoLiRecord);
		map.put("dkxx", quanDakuanRecord);
		return JsonResult.ok(map);
	}

}

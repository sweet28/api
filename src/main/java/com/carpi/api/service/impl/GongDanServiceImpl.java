package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.GongDanMapper;
import com.carpi.api.pojo.GongDan;
import com.carpi.api.service.GongDanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GongDanServiceImpl implements GongDanService{

	@Autowired
	private GongDanMapper gongDanMapper; 
	
	//提交工单
	@Override
	public JsonResult addGongdan(GongDan gongDan) {
		if (gongDan.getFensUserId() == null) {
			return JsonResult.build(500, "系统错误");
		}
		if (gongDan.getType() == null) {
			return JsonResult.build(500, "系统错误1");
		}
		if (gongDan.getProblem() == null) {
			return JsonResult.build(500, "请输入问题表述");
		}
		
		int result = gongDanMapper.insertSelective(gongDan);
		if (result != 1) {
			return JsonResult.build(500, "提交失败，请联系客服");
		}
		return JsonResult.ok();
	}

	//查询历史工单
	@Override
	public PageInfo<GongDan> selectGondan(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<GongDan> list = gongDanMapper.selectList(null);
		PageInfo<GongDan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	
}

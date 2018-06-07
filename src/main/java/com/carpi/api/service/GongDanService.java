package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.GongDan;
import com.github.pagehelper.PageInfo;

public interface GongDanService {

	//提交工单
	public JsonResult addGongdan(GongDan gongDan);
	
	//查询历史工单
	public PageInfo<GongDan> selectGondan(Integer pageNum,Integer pageSize,Integer fensUserId);
}

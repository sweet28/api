package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensEarn;
import com.github.pagehelper.PageInfo;

public interface FensEarnService {

	//粉丝收益列表
	public PageInfo<FensEarn> selectAll(Integer page, Integer num, Integer fensUserId);
	
	//新增粉丝收益
	public JsonResult addFensEarn(FensEarn fensEarn);
	
	//更新粉丝收益
	public JsonResult updateFensRarn(FensEarn fensEarn);
}

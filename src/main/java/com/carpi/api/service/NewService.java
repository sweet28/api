package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.News;
import com.github.pagehelper.PageInfo;

public interface NewService {

	//新闻列表
	public PageInfo<News> selectAll(Integer pageNum,Integer pageSize);
	
	//根据新闻Id查询新闻详情
	public JsonResult selectById(Integer id);
}

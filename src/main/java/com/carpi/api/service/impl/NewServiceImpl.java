package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.NewsMapper;
import com.carpi.api.pojo.News;
import com.carpi.api.service.NewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class NewServiceImpl implements NewService {
	@Autowired
	private NewsMapper newsMapper;

	// 查询新闻列表
	@Override
	public PageInfo<News> selectAll(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsMapper.selectAll();
		PageInfo<News> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 根据id查询新闻详情
	@Override
	public JsonResult selectById(Integer id) {
		News news = newsMapper.selectByPrimaryKey(id);
		if (news == null) {
			return JsonResult.build(500, "不存在该新闻");
		}
		return JsonResult.ok(news);
	}

}

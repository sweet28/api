package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.News;
import com.carpi.api.service.NewService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("news")
public class NewsController {

	@Autowired
	private NewService newService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<News> selectAll(Integer pageNum,Integer pageSize){
		return newService.selectAll(pageNum, pageSize);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectAll(@PathVariable("id") Integer id){
		return newService.selectById(id);
	}
}

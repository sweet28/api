package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/news")
public class NewsNewController {

	@Autowired
	private NewService newService;

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<News> selectAll(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		return newService.selectAll(Integer.valueOf(page), Integer.valueOf(row));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectAll(@PathVariable("id") Integer id) {
		return newService.selectById(id);
	}
}
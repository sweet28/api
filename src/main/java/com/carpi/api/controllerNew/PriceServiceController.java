package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.service.PriceService;

@Controller
@RequestMapping("/jb")
public class PriceServiceController {

	@Autowired
	private PriceService priceService;

	// 查询今日价格
	@RequestMapping(value = "/jt", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectToday(HttpServletRequest request, HttpServletResponse response) {
		return priceService.selectToday();
	}

	// 根据时间段查询
	@RequestMapping(value = "/all", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectall(HttpServletRequest request, HttpServletResponse response) {
		String startTime = request.getParameter("ks");
		String endTime = request.getParameter("js");
		return priceService.selectall(startTime, endTime);
	}

	// 20天的数据
	@RequestMapping(value = "/mouth", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectWeek(HttpServletRequest request, HttpServletResponse response) {
		return priceService.selectWeek();
	}
}

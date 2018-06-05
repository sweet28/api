package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.service.QuanBaoLiRecordService;

@Controller
@RequestMapping("/quan")
public class QuanBaoLiRecordContoller {

	@Autowired
	private QuanBaoLiRecordService quanBaoLiRecordService;

	// 券宝理列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectToday(HttpServletRequest request, HttpServletResponse response) {
		return quanBaoLiRecordService.selectList();
	}

	// 券宝理个人订单
	@RequestMapping(value = "/dd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectOne(HttpServletRequest request, HttpServletResponse response) {
		//粉丝id
		String fensUserId = request.getParameter("uid");
		//类型
		String orderType = request.getParameter("tp");
		return quanBaoLiRecordService.selectOne(Integer.valueOf(fensUserId), Integer.valueOf(orderType));
	}

	// 查询券详情
	@RequestMapping(value = "/xq", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult xiangQing(HttpServletRequest request, HttpServletResponse response) {
		//订单id
		String id = request.getParameter("uid");
		return quanBaoLiRecordService.xiangQing(Integer.valueOf(id));
	}

}

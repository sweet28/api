package com.carpi.api.controllerNew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.service.MoneyRecordService;

@Controller
@RequestMapping("/jl")
public class MoneyRecordNewController {

	@Autowired
	private MoneyRecordService moneyRecordService;

	// 粉丝转入转出cpa记录
	// @RequestMapping(value = "/zhuan", method = RequestMethod.POST, produces =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult zhuan(HttpServletRequest request, HttpServletResponse
	// response) {
	// // 粉丝id
	// String fensUserId = request.getParameter("uid");
	// //交易金额
	// String payment = request.getParameter("je");
	// FensWallet fensWallet = new FensWallet();
	//
	// fensWallet.setFensUserId(Integer.valueOf(fensUserId));
	//
	// FoneyRecord foneyRecord = new FoneyRecord();
	// foneyRecord .setPayment(Double.valueOf(payment));
	// return moneyRecordService.zhuan(foneyRecord, fensWallet);
	// }

	// 钱包详情
//	@RequestMapping(value = "/select", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public JsonResult select(Integer fensUserId) {
//		return moneyRecordService.select(fensUserId);
//	}
}

package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.carpi.api.service.MoneyRecordService;

@Controller
public class MoneyRecordController {

	@Autowired
	private MoneyRecordService moneyRecordService;
	
	//粉丝转入转出cpa记录
	@RequestMapping(value = "/zhuan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult zhuan(FoneyRecord foneyRecord,FensWallet fensWallet){
		return moneyRecordService.zhuan(foneyRecord, fensWallet);
	}

	//钱包详情
	@RequestMapping(value = "/select", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult select(Integer fensUserId){
		return moneyRecordService.select(fensUserId);
	}
	
}
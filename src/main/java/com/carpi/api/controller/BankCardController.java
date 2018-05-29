package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.service.BankCardService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/bank")
public class BankCardController {

	@Autowired
	private BankCardService bankCardService;

	// 根据粉丝Id查询粉丝银行卡列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<BankCard> selectList(Integer pageNum, Integer pageSize, Integer fensUserId) {
		return bankCardService.selectAll(pageNum, pageSize, fensUserId);
	}

	// 设置默认使用的银行卡(0：不使用；1：使用)
	@RequestMapping(value = "/updateApply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateApply(BankCard bankCard) {
		return bankCardService.updateApply(bankCard);
	}

	// 粉丝添加银行卡
	@RequestMapping(value = "/addBlank", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addBlank(BankCard bankCard) {
		return bankCardService.addBlank(bankCard);
	}

	// 粉丝修改银行卡
	@RequestMapping(value = "/updateBlank", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateBlank(BankCard bankCard) {
		return bankCardService.updateBlank(bankCard);
	}

}

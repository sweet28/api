package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.carpi.api.service.FensWalletService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/wallet")
public class FensWalletController {

	@Autowired
	private FensWalletService fensWalletService;

	
//	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public PageInfo<FensWallet> selectAll(Integer page, Integer num, Integer fensUserId) {
//		return fensWalletService.selectAll(page, num, fensUserId);
//	}
	// 粉丝钱包列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectAll(Integer fensUserId) {
		return fensWalletService.selectAll(fensUserId);
	}

	// 新增钱包
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addWallet(FensWallet fensWallet) {
		return fensWalletService.addWallet(fensWallet);
	}

	// 修改钱包
	@RequestMapping(value = "/updatewallet", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateWallet(FensWallet fensWallet) {
		return fensWalletService.updateWallet(fensWallet);
	}

	// 钱包转账记录列表
	@RequestMapping(value = "/selectWalletRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FoneyRecord> selectWalletRecord(Integer page, Integer num, Integer fensUserId) {
		return fensWalletService.selectWalletRecord(page, num, fensUserId);
	}

	// 添加钱包转账记录
	@RequestMapping(value = "/addWalletRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addWalletRecord(FoneyRecord foneyRecord) {
		return fensWalletService.addWalletRecord(foneyRecord);
	}

	// 修改钱包转账记录
	@RequestMapping(value = "/updateWalletRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateWalletRecord(FoneyRecord foneyRecord) {
		return fensWalletService.updateWalletRecord(foneyRecord);
	}

}

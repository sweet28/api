package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("user/qb")
public class FensWalletNewController {

	@Autowired
	private FensWalletService fensWalletService;

	// 粉丝钱包列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectAll(HttpServletRequest request, HttpServletResponse response) {
		String fensUserId = request.getParameter("uid");
		return fensWalletService.selectAll(Integer.valueOf(fensUserId));
	}

	// 新增钱包
	// @RequestMapping(value = "/addwallet", method = RequestMethod.POST, produces =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult addWallet(HttpServletRequest request, HttpServletResponse
	// response) {
	//
	// return fensWalletService.addWallet(fensWallet);
	// }

	// 修改钱包
	// @RequestMapping(value = "/updatewallet", method = RequestMethod.POST,
	// produces = "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult updateWallet(FensWallet fensWallet) {
	// return fensWalletService.updateWallet(fensWallet);
	// }

	// 钱包转账记录列表
	@RequestMapping(value = "/reordzz", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FoneyRecord> selectWalletRecord(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String num = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		return fensWalletService.selectWalletRecord(Integer.valueOf(page), Integer.valueOf(num),
				Integer.valueOf(fensUserId));
	}

	// 添加钱包转账记录
	// @RequestMapping(value = "/addWalletRecord", method = RequestMethod.POST,
	// produces = "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult addWalletRecord(FoneyRecord foneyRecord) {
	// return fensWalletService.addWalletRecord(foneyRecord);
	// }

	// 修改钱包转账记录
	// @RequestMapping(value = "/updateWalletRecord", method = RequestMethod.POST,
	// produces = "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult updateWalletRecord(FoneyRecord foneyRecord) {
	// return fensWalletService.updateWalletRecord(foneyRecord);
	// }
}

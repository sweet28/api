package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.FensUserService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/fenuser")
public class FensUserController {

	@Autowired
	private FensUserService fensUserService;

	// 注册
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult register(FensUser fensUser, String code_type, String code, String cardNumber) {
		return fensUserService.register(fensUser, code_type, code, cardNumber);
	}

	// 登入
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult login(FensUser fensUser) {
		return fensUserService.login(fensUser);
	}

	// 忘记密码
	@RequestMapping(value = "/forgetPwd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult forgetPwd(FensUser fensUser, String code_type, String code) {
		return fensUserService.forgetPwd(fensUser, code_type, code);
	}
	
	//粉丝团列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTeam> slectAll(Integer page,Integer num,Integer fensUserId,String type) {
		return fensUserService.selectAll(page, num, fensUserId,type);
	}
	
	//粉丝算力列表明细
	@RequestMapping(value = "/suanlilist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensComputingPower> suanlilist(Integer page,Integer num,Integer fensUserId) {
		return fensUserService.selectComputingPower(page, num, fensUserId);
	}
	
	//添加粉丝算力明细
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addselectComputingPower(FensComputingPower fensComputingPower) {
		return fensUserService.addselectComputingPower(fensComputingPower);
	}
	
	//修改粉丝算力
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateComputingPower(FensComputingPower fensComputingPower) {
		return fensUserService.updateComputingPower(fensComputingPower);
	}
	
	
	//粉丝算力值和
	@RequestMapping(value = "/sum", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectSum(Integer page,Integer num,Integer fensUserId) {
		return fensUserService.selectSum(fensUserId);
	}
	
	//粉丝团列表2
	@RequestMapping(value = "/list2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensUser> slectAllUser(Integer page,Integer num,String phone,String type) {
		return fensUserService.selectAllUser(page, num, phone,type);
	}
}

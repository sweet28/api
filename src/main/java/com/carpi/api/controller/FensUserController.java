package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.FensUserService;

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
	
	//粉丝交易记录（可查个人）

}

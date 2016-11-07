package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.google.gson.Gson;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Resource
	private UserStuService userStuService;
	
	@RequestMapping(value = "/is_reg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object isRegister(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		
		account = request.getParameter("mobile");
		
		if(account == null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (account.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			UserStu userStu = null;
			userStu = this.userStuService.getUserStuByAccount(account);
			if(userStu != null){
				errorCode = "20024";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20024;
			}else{
				errorCode = "0";
				errorMsg = "ok";
			}
		}
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);

		return gson.toJson(simReBean);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		
		String moblie = "";
		String pwd = "";
		String name = "";
		
		moblie = request.getParameter("mobile");
		pwd = request.getParameter("psw");
		name = request.getParameter("name");
		
		if(moblie == null || pwd == null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}else if(moblie.equals("") || pwd.equals("")){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}else{
			UserStu userStu2 = null;
			userStu2 = new UserStu();
			
			pwd = MD5.encodeString(MD5.encodeString(pwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
			userStu2.setUserMobile(moblie);
			userStu2.setPwd(pwd);
			if(name != null && !("").equals(userStu2)){
				userStu2.setName(name);
			}
			int result = 0;
			result = this.userStuService.insert(userStu2);
			System.out.println(":::::::::::::result::::" + result);
			if(result == 1){
				errorCode = "0";
				errorMsg = "ok";
			}else{
				errorCode = "20037";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20037;
			}
		}
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);

		return gson.toJson(simReBean);
	}

}

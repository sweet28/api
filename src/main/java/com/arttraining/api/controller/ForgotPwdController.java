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
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/forgot_pwd")
public class ForgotPwdController {
	@Resource
	private UserStuService userStuService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object isRegister(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		String pwd = "";
		
		account = request.getParameter("mobile");
		pwd = request.getParameter("new_pwd");
		System.out.println("进入忘记密码："+account+TimeUtil.getTimeStamp());
		if(account == null || pwd == null){
			System.out.println("进入忘记密码2222："+account+TimeUtil.getTimeStamp());
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (account.equals("") || ("").equals(pwd.trim())) {
			System.out.println("进入忘记密码333："+account+TimeUtil.getTimeStamp());
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(PhoneUtil.isMobile(account)){
			System.out.println("进入忘记密码444："+account+TimeUtil.getTimeStamp());
			UserStu userStu = null;
			userStu = this.userStuService.getUserStuByAccount(account);
			if(userStu != null){
				System.out.println("进入忘记密码555："+account+TimeUtil.getTimeStamp());
				pwd = MD5.encodeString(MD5.encodeString(pwd
						+ ConfigUtil.MD5_PWD_STR)
						+ ConfigUtil.MD5_PWD_STR);
				userStu.setPwd(pwd);
				try {
					this.userStuService.updateUserStuBySelective(userStu);
					errorCode = "0";
					errorMsg = "ok";
				}catch(Exception e) {
					errorCode = "20036";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
				}
			}else{
				errorCode = "20022";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;
			}
		}else{
			errorCode = "20044";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
		}
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);
		System.out.println(gson.toJson(simReBean)+"进入忘记密码555："+account+TimeUtil.getTimeStamp());

		return gson.toJson(simReBean);
	}
}

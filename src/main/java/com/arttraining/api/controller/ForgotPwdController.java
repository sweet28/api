package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.pojo.SMSCheckCode;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.SMSService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;

@Controller
@RequestMapping("/forgot_pwd")
public class ForgotPwdController {
	@Resource
	private UserStuService userStuService;
	@Resource
	private SMSService smsService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response){
//		SimpleReBean simReBean = new SimpleReBean();
//		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		String pwd = "";
		String accessToken="";
		Integer i_uid=0;
		
		account = request.getParameter("mobile");
		pwd = request.getParameter("new_pwd");
		//coffee add 
		//String code_type = request.getParameter("code_type");
		String code_type ="identity_code";
		//end
		
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
				
				//coffee add  这里去短信验证码中去手机号最近一次使用的使用时间
				SMSCheckCode smsCCode = null;
				smsCCode = new SMSCheckCode();
				SMSCheckCode smsCheckCode = new SMSCheckCode();
				smsCheckCode.setMobile(account);
				smsCheckCode.setRemarks(code_type);
				smsCCode = this.smsService.getOneSmsInfo(smsCheckCode);
				//如果存在 则判断使用时间是否在60s以内
				if(smsCCode!=null) {
					long useTime = smsCCode.getUsingTime().getTime();
					long nowTime = new Date().getTime();
					long diffSeconds = TimeUtil.diffSeconds(nowTime, useTime);
					if(diffSeconds>ConfigUtil.VERIFY_TIME) {
						errorCode = "20048";
						errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20048;
					}
					else {
						//如果在规定时间内
						userStu.setPwd(pwd);
						try {
							this.userStuService.updateUserStuBySelective(userStu);
							//coffee add //设置短信已使用
							smsCCode.setIsUsed(2);
							this.smsService.update(smsCCode);
							//end
							i_uid=userStu.getId();
							accessToken = TokenUtil.generateToken(account);
							errorCode = "0";
							errorMsg = "ok";
						}catch(Exception e) {
							errorCode = "20036";
							errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
						}
					}
				} else {
					errorCode = "20049";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20049;
				}
				//end
				
			}else{
				errorCode = "20022";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;
			}
		}else{
			errorCode = "20044";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMsg);
		jsonObject.put("uid",i_uid);
		jsonObject.put("user_code", accessToken);
		jsonObject.put("name", "");
		
		return jsonObject;
		
//		simReBean.setError_code(errorCode);
//		simReBean.setError_msg(errorMsg);
//	
//		System.out.println(gson.toJson(simReBean)+"进入忘记密码555："+account+TimeUtil.getTimeStamp());
//
//		return gson.toJson(simReBean);
	}
}

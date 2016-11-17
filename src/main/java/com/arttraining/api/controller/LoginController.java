package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.LoginBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Resource
	private UserStuService userStuService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object loginYHY(HttpServletRequest request, HttpServletResponse response) {
		String name = "";
		String pwd = "";
		
		LoginBean loginBean = new LoginBean();
		Gson gson = new Gson();
		UserStu userStu = null;
		
		String errorCode = "";
		String errorMessage = "";
		String accessToken = "";
		
		name = request.getParameter("name");
		pwd = request.getParameter("pwd");
		System.out.println("登录："+name+"-"+pwd+"-"+TimeUtil.getTimeStamp());
		if(name == null || pwd == null){
			System.out.println("登录1"+"-"+TimeUtil.getTimeStamp());
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}else{
			System.out.println("登录2"+"-"+TimeUtil.getTimeStamp());
			if(name.equals("") || pwd.equals("")){
				System.out.println("登录3"+"-"+TimeUtil.getTimeStamp());
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			}else{
				System.out.println("登录4"+"-"+TimeUtil.getTimeStamp());
				userStu = this.userStuService.getUserStuByAccount(name);
				if(userStu==null) {
					System.out.println("登录5"+"-"+TimeUtil.getTimeStamp());
					//如果查询不存在uid 则重新赋值一个对象
					errorCode = "20022";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;		
				}else{
					System.out.println("登录6"+"-"+TimeUtil.getTimeStamp());
					String pwdData = userStu.getPwd();
					
					if (!MD5.check(
							MD5.encodeString(pwd + ConfigUtil.MD5_PWD_STR)
									+ ConfigUtil.MD5_PWD_STR, pwdData)) {
						System.out.println("登录7"+"-"+TimeUtil.getTimeStamp());
						errorCode = "20023";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20023;
					}else{
						System.out.println("登录8"+"-"+TimeUtil.getTimeStamp());
						accessToken = TokenUtil.generateToken(name);
						errorCode = "0";
						errorMessage = "ok";
						loginBean.setCity(userStu.getCityName());
						loginBean.setEmail(userStu.getEmail());
						loginBean.setHead_pic(userStu.getHeadPic());
						loginBean.setIdentity(userStu.getIdentityName());
						loginBean.setIntentional_college(userStu.getIntentionalCollegeName());
						loginBean.setMobile(userStu.getUserMobile());
						loginBean.setName(userStu.getName());
						loginBean.setOrg(userStu.getOrgName());
						loginBean.setRank(userStu.getRank());
						loginBean.setSchool(userStu.getSchoolName());
						loginBean.setScore(userStu.getScore());
						loginBean.setSex(userStu.getSex());
						loginBean.setSpecialty(userStu.getSpecialtyName());
						loginBean.setUid(userStu.getId());
						loginBean.setUser_code(userStu.getUserCode());
						loginBean.setTitle(userStu.getTitle());
					}
				}
			}
		}

		loginBean.setAccess_token(accessToken);
		loginBean.setError_code(errorCode);
		loginBean.setError_msg(errorMessage);
		System.out.println(gson.toJson(loginBean)+"-"+TimeUtil.getTimeStamp());
		return gson.toJson(loginBean);

	}
	
	@RequestMapping(value = "/exit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object exitYHY(HttpServletRequest request, HttpServletResponse response) {
		SimpleReBean reBean = new SimpleReBean();
		Gson gson = new Gson();
		UserStu userStu = null;
		
		String errorCode = "";
		String errorMessage = "";
		String accessToken = "";
		
		accessToken = request.getParameter("access_token");
		
		if(accessToken == null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}else{
			if(accessToken.equals("")){
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			}else{
				if(TokenUtil.deleteToken(accessToken)){
					errorCode = "0";
					errorMessage = "ok";
				}else{
					errorCode = "20032";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
				}
			}
		}

		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		return gson.toJson(reBean);

	}

}

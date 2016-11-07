package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.LoginBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Resource
	private UserStuService userStuService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getQiniuUploadUrl(HttpServletRequest request, HttpServletResponse response) {
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
		
		if(name == null || pwd == null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}else{
			if(name.equals("") || pwd.equals("")){
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			}else{
				userStu = this.userStuService.getUserStuByAccount(name);
				if(userStu==null) {
					//如果查询不存在uid 则重新赋值一个对象
					errorCode = "20022";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;		
				}else{
					String pwdData = userStu.getPwd();
					
					if(!MD5.check(pwd+ConfigUtil.MD5_PWD_STR, pwdData)){
						errorCode = "20023";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20023;
					}else{
						TokenUtil.generateToken(name);
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
					}
				}
			}
		}

		loginBean.setAccess_token(accessToken);
		loginBean.setError_code(errorCode);
		loginBean.setError_msg(errorMessage);
		
		return gson.toJson(loginBean);

	}

}

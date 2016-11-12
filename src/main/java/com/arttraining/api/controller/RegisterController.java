package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.LoginBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.InviteCodeService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Resource
	private UserStuService userStuService;
	@Resource
	private InviteCodeService invCodeService;
	
	@RequestMapping(value = "/is_reg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object isRegister(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		
		account = request.getParameter("mobile");
		System.out.println("进入注册验证："+account+TimeUtil.getTimeStamp());
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
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String accessToken = "";
		
		String moblie = "";
		String pwd = "";
		String name = "";
		
		System.out.println("进入注册"+TimeUtil.getTimeStamp());
		
		moblie = request.getParameter("mobile");
		pwd = request.getParameter("psw");
		name = request.getParameter("name");
		
		if(moblie == null || pwd == null){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);

			System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(moblie.equals("") || pwd.equals("")){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);

			System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(PhoneUtil.isMobile(moblie)){
			UserStu userStu = null;
			userStu = this.userStuService.getUserStuByAccount(moblie);
			if(userStu != null){
				SimpleReBean simReBean = new SimpleReBean();
				errorCode = "20024";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20024;
				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);

				System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}else{
				UserStu userStu2 = null;
				userStu2 = new UserStu();

				pwd = MD5.encodeString(MD5.encodeString(pwd
						+ ConfigUtil.MD5_PWD_STR)
						+ ConfigUtil.MD5_PWD_STR);
				userStu2.setUserMobile(moblie);
				userStu2.setPwd(pwd);
				
				
				userStu2.setTitle("nomal");//master：达人、brother：师哥师姐、nomal：一般等
				if (name != null && !("").equals(userStu2)) {
					userStu2.setName(name);
				}else{
					userStu2.setName(moblie);
				}
				int result = 0;
				result = this.userStuService.insert(userStu2);
				System.out.println(":::::::::::::result::::" + result);
				if (result == 1) {
					UserStu userBean = this.userStuService.getUserStuByAccount(moblie);
					
					accessToken = TokenUtil.generateToken(moblie);
					errorCode = "0";
					errorMsg = "ok";
					
					LoginBean loginBean = new LoginBean();
					loginBean.setCity(userBean.getCityName());
					loginBean.setEmail(userBean.getEmail());
					loginBean.setHead_pic(userBean.getHeadPic());
					loginBean.setIdentity(userBean.getIdentityName());
					loginBean.setIntentional_college(userBean.getIntentionalCollegeName());
					loginBean.setMobile(userBean.getUserMobile());
					loginBean.setName(userBean.getName());
					loginBean.setOrg(userBean.getOrgName());
					loginBean.setRank(userBean.getRank());
					loginBean.setSchool(userBean.getSchoolName());
					loginBean.setScore(userBean.getScore());
					loginBean.setSex(userBean.getSex());
					loginBean.setSpecialty(userBean.getSpecialtyName());
					loginBean.setUid(userBean.getId());
					loginBean.setUser_code(userBean.getUserCode());
					loginBean.setTitle(userBean.getTitle());
					
					loginBean.setAccess_token(accessToken);
					loginBean.setError_code(errorCode);
					loginBean.setError_msg(errorMsg);
					System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(loginBean));
					return gson.toJson(loginBean);
					
				} else {
					errorCode = "20040";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20040;

					SimpleReBean simReBean = new SimpleReBean();
					simReBean.setError_code(errorCode);
					simReBean.setError_msg(errorMsg);

					System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
					return gson.toJson(simReBean);
				}
			}
			
		}else{
			errorCode = "20044";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;

			SimpleReBean simReBean = new SimpleReBean();
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);

			System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}
	}

}

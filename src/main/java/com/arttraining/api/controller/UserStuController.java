package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.UserNumberBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/users")
public class UserStuController {
	@Resource
	private UserStuService userStuService;
	
	/*@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object test(HttpServletRequest request, HttpServletResponse response) {
		 	try {
		 		//修改
		 		 UserStu useStu = new UserStu();
			     useStu.setName("33");
			     useStu.setId(5);
			        //插入
			      UserStu useStu1 = new UserStu();
			      useStu1.setName("654");
			      useStu1.setId(7);
			       
			      this.userStuService.delAndUpdate(useStu, useStu1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		  
	        return true;
	}*/
	/***
	 * 根据用户ID获取用户数目信息
	 * 传递的参数:uid 用户ID
	 * 
	 */
	
	/***
	 * 根据用户ID获取用户信息
	 * 传递的参数:uid 用户id
	 * **/
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String uid = request.getParameter("uid");
		//创建一个UserStuShowBean对象 默认会对属性进行赋值
		UserStuShowBean userStu = new UserStuShowBean();
		if(uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else {
			//依据传递的uid 来获取相应的用户信息
			Integer i_uid = Integer.valueOf(uid);
			userStu = this.userStuService.showUserStuById(i_uid);
			if(userStu==null) {
				//如果查询不存在uid 则重新赋值一个对象
				userStu = new UserStuShowBean();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;		
			}
			else {
				errorCode = "0";
				errorMessage = "ok";	
			}
		}
		userStu.setError_code(errorCode);
		userStu.setError_msg(errorMessage);
		Gson gson = new Gson();
		return gson.toJson(userStu);
	}

	/**
	 * 根据用户ID修改用户头像信息
	 * 传递的参数:access_token--认证  uid--用户id   head_pic--修改后的头像
	 * 
	 * **/
	@RequestMapping(value = "/update_head", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object updateHead(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String head_pic = request.getParameter("head_pic");
		
		if(access_token==null || uid==null || head_pic==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(access_token.equals("") || uid.equals("") || head_pic.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				Integer i_uid = Integer.valueOf(uid);
				UserStu userStu = new UserStu();
				userStu.setId(i_uid);
				head_pic = ImageUtil.parseAttPath(head_pic);
				userStu.setHeadPic(head_pic);
				
				try {
					this.userStuService.updateUserStuBySelective(userStu);
					errorCode = "0";
					errorMessage = "ok";
					
				}catch(Exception e) {
					errorCode = "20036";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
				}
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		jsonObject.put("head_pic", "");
		
		return jsonObject;
	}
	
	/**
	 * 根据用户ID修改用户密码
	 * 传递的参数:access_token--验证 uid--用户ID  new_pwd--修改后的密码
	 * 
	 * **/
	@RequestMapping(value = "/update_pwd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object updatePwd(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String new_pwd = request.getParameter("new_pwd");
		
		if(access_token==null || uid==null || new_pwd==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(access_token.equals("") || uid.equals("") || new_pwd.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				Integer i_uid = Integer.valueOf(uid);
				UserStu userStu = new UserStu();
				userStu.setId(i_uid);
				
				new_pwd = MD5.encodeString(MD5.encodeString(new_pwd
						+ ConfigUtil.MD5_PWD_STR)
						+ ConfigUtil.MD5_PWD_STR);
				
				userStu.setPwd(new_pwd);
				
				try {
					this.userStuService.updateUserStuBySelective(userStu);
					errorCode = "0";
					errorMessage = "ok";
					
				}catch(Exception e) {
					errorCode = "20036";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
				}
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		
		return jsonObject;
	}
	
	/***
	 * 根据用户ID修改用户手机号码
	 * 传递的参数:
	 * access_token--验证  uid--用户id  mobile--手机号码
	 * 
	 * **/
	@RequestMapping(value = "/change_mobile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object changeMobile(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String mobile = request.getParameter("mobile");
		
		if(access_token==null || uid==null || mobile==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(access_token.equals("") || uid.equals("") || mobile.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				Integer i_uid = Integer.valueOf(uid);
				UserStu userStu = new UserStu();
				userStu.setId(i_uid);
				userStu.setUserMobile(mobile);
				
				try {
					this.userStuService.updateUserStuBySelective(userStu);
					errorCode = "0";
					errorMessage = "ok";
					
				}catch(Exception e) {
					errorCode = "20036";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
				}
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		
		return jsonObject;
	}
	/**
	 * 根据用户ID设置用户信息
	 * 传递的参数:
	 * 必须参数 access_token uid
	 * access_token--验证 uid--用户id name--用户名
	 * sex--性别 city_id-- 城市id city--城市 
	 * identity_id--身份ID identity--身份
	 * school_id--院校ID  school 
	 * org_id--机构ID org--机构
	 * specialty_id 专业ID specialty--专业 
	 * intentional_college_id 意向院校ID intentional_college--意向院校  email--邮箱
	 * 
	 * ***/
	@RequestMapping(value = "/set_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object setInfo(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		//可选参数
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String city_id = request.getParameter("city_id");
		String city = request.getParameter("city");
		String identity_id = request.getParameter("identity_id");
		String identity = request.getParameter("identity");
		String school_id = request.getParameter("school_id");
		String school = request.getParameter("school");
		String org_id = request.getParameter("org_id");
		String org = request.getParameter("org");
		String specialty_id = request.getParameter("specialty_id");
		String specialty = request.getParameter("specialty");
		String intentional_college_id = request.getParameter("intentional_college_id");
		String intentional_college = request.getParameter("intentional_college");
		String email = request.getParameter("email");
		
		
		System.out.println(access_token+"=="+uid+"=="+name);
		System.out.println(sex+"=="+city_id+"=="+city);
		System.out.println(identity_id+"=="+identity+"=="+school_id);
		System.out.println(school+"=="+org_id+"=="+org);
		System.out.println(specialty_id+"=="+specialty+"=="+intentional_college_id);
		System.out.println(intentional_college+"=="+email);
		
		
		
		if(access_token==null || uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				Integer i_uid = Integer.valueOf(uid);
				Integer i_city_id=null;
				Integer i_identity_id=null;
				Integer i_school_id=null;
				Integer i_org_id=null;
				Integer i_specialty_id=null;
				Integer i_intentional_college_id=null;
				
				if(city_id!=null && !city_id.equals("") && NumberUtil.isInteger(city_id)) {
					i_city_id=Integer.valueOf(city_id);
				}
				if(identity_id!=null && !identity_id.equals("") && NumberUtil.isInteger(identity_id)) {
					i_identity_id=Integer.valueOf(identity_id);
				}
				if(school_id!=null && !school_id.equals("") && NumberUtil.isInteger(school_id)) {
					i_school_id=Integer.valueOf(school_id);
				}
				if(org_id!=null && !org_id.equals("") && NumberUtil.isInteger(org_id)) {
					i_org_id=Integer.valueOf(org_id);
				}
				if(specialty_id!=null && !specialty_id.equals("") && NumberUtil.isInteger(specialty_id)) {
					i_specialty_id=Integer.valueOf(specialty_id);
				}
				if(intentional_college_id!=null && !intentional_college_id.equals("") 
						&& NumberUtil.isInteger(intentional_college_id)) {
					i_intentional_college_id=Integer.valueOf(intentional_college_id);
				}
					
				UserStu userStu = new UserStu();
				userStu.setId(i_uid);
				userStu.setName(name);
				userStu.setSex(sex);
				userStu.setCityId(i_city_id);
				userStu.setCityName(city);
				userStu.setIdentityId(i_identity_id);
				userStu.setIdentityName(identity);
				userStu.setSchoolId(i_school_id);
				userStu.setSchoolName(school);
				userStu.setOrgId(i_org_id);
				userStu.setOrgName(org);
				userStu.setSpecialtyId(i_specialty_id);
				userStu.setSpecialtyName(specialty);
				userStu.setIntentionalCollegeId(i_intentional_college_id);
				userStu.setIntentionalCollegeName(intentional_college);
				userStu.setEmail(email);
				
				try {
					this.userStuService.setUserStuInfoBySelective(userStu);
					errorCode = "0";
					errorMessage = "ok";
					
				}catch(Exception e) {
					errorCode = "20036";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
				}
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		
		System.out.println(jsonObject);
		
		return jsonObject;
	}
	/***
	 * 根据用户ID获取用户数目信息
	 * 传递的参数:
	 * uid--用户ID
	 */
	@RequestMapping(value = "/num", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object num(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//用户ID--必选参数
		String uid = request.getParameter("uid");
		
		UserNumberBean userNum = new UserNumberBean();
		
		if(uid==null || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//用户ID
			Integer i_uid = Integer.valueOf(uid);
			userNum = this.userStuService.getUserNumberByUid(i_uid);
			if(userNum==null) {
				userNum = new UserNumberBean();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				errorCode = "0";
				errorMessage = "ok";
			}
		}
		userNum.setError_code(errorCode);
		userNum.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(userNum);
	}
}

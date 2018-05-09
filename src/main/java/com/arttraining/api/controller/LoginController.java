package com.arttraining.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.arttraining.api.bean.LoginBeanV2;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Resource
	private UserStuService userStuService;
	@Resource
	private TokenService tokenService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
		//coffee add
		String login_way="yhy";
		//end
		
		ServerLog.getLogger().warn("name:"+name+"-pwd:"+pwd);
		//System.out.println("登录："+name+"-"+pwd+"-"+TimeUtil.getTimeStamp());
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
				userStu = this.userStuService.getUserByMobileAndRemarks(name, login_way);
				//userStu = this.userStuService.getUserStuByAccount(name);
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
						//coffee add 1215
						//1.判断数据库是否有该用户的登录token 如果没有 则新增 如果有 则修改
						Integer user_id=userStu.getId();
						String user_type="stu";
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("user_id", user_id);
						map.put("user_type", user_type);
						Token token=this.tokenService.getOneTokenInfo(map);
						Date date=new Date();
						if(token!=null) {
							token.setToken(accessToken);
							token.setLoginTime(date);
							token.setIsDeleted(0);
							this.tokenService.updateTokenInfo(token);
						} else {
							token = new Token();
							token.setLoginTime(date);
							token.setToken(accessToken);
							token.setUserId(user_id);
							token.setUserType(user_type);
							this.tokenService.insertTokenInfo(token);
						}
						//end
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
						//coffee add
						loginBean.setBbs_num(userStu.getBbsNum());
						loginBean.setComment_num(userStu.getCommentNum());
						loginBean.setFans_num(userStu.getFansNum());
						loginBean.setFavorite_num(userStu.getFavoriteNum());
						loginBean.setFollow_num(userStu.getFollowNum());
						loginBean.setGroup_num(userStu.getGroupNum());
						loginBean.setWork_num(userStu.getWorkNum());
						//end
						
						//coffee add 0104  修改最近一次登录时间
						UserStu upd_user=new UserStu();
						upd_user.setId(user_id);
						upd_user.setLastLoginTime(date);
						this.userStuService.updateUserStuBySelective(upd_user);
						//end
					}
				}
			}
		}

		loginBean.setAccess_token(accessToken);
		loginBean.setError_code(errorCode);
		loginBean.setError_msg(errorMessage);
		ServerLog.getLogger().warn(gson.toJson(loginBean));
		//System.out.println(gson.toJson(loginBean)+"-"+TimeUtil.getTimeStamp());
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
		ServerLog.getLogger().warn("access_token:"+accessToken);
		if(accessToken == null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}else{
			if(accessToken.equals("")){
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			}else{
				//coffee add 1215 
				//1.先判断token是否存在 如果存在 直接修改 如果不存在 直接删除token
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("token", accessToken);
				Token token=this.tokenService.getOneTokenInfo(map);
				if(token!=null) {
					Date date=new Date();
					token.setLoginTime(date);
					token.setIsDeleted(1);
					this.tokenService.updateTokenInfo(token);
				} else {
					//end
					if(TokenUtil.deleteToken(accessToken)){
						errorCode = "0";
						errorMessage = "ok";
					}else{
						errorCode = "20032";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
					}
				}
			}
		}

		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);

	}

}

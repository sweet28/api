package com.arttraining.api.controller;

import java.io.File;
import java.io.InputStream;
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
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.ThirdLogin;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.HttpURLConnectionUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.SignleUploadPresenter;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login_v2")
public class LoginControllerV2 {
	@Resource
	private UserStuService userStuService;
	@Resource
	private TokenService tokenService;
	
	/**
	 * 第三方登录
	 * 依据传递的登录方式来获取参数
	 * 登录方式(wx/qq/sina/facebook/twitter)
	 * 目前只设置了微信/QQ/新浪微博三种登录方式 
	 * 
	 */
//	@RequestMapping(value = "/third/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public @ResponseBody Object thirdLogin(HttpServletRequest request, HttpServletResponse response) {
//		String errorCode = "";
//		String errorMessage = "";
//		
//		//以下是必选参数
//		String login_way=request.getParameter("login_way");
//		//如果是微信授权登录
//		if(login_way.equals("wx")) {
//			//获取微信登录方式的参数
//			String openid=request.getParameter("openid");
//			String uid=request.getParameter("uid");
//			String name =request.getParameter("name");
//			String gender =request.getParameter("gender");
//			String iconurl =request.getParameter("iconurl");
//			String fileName="";
//			//判断获取到的微信用户头像是否为空 如果不为空 则从网络抓取图片 保存到本地
//			if(iconurl!=null && iconurl.trim().equals("")) {
//				//上传至七牛云空间的文件名--命名方式
//				fileName=System.currentTimeMillis()+Random.randomCommonStr(6)+".png";
//				SignleUploadPresenter signUp=new SignleUploadPresenter();
//				signUp.downloadIconToQiNiuYun(iconurl,fileName);
//			}
//			//微信传递的性别是f--女 m--男
//			//1.首先判断是否用微信登录过  如果微信登录过
//			
//			ThirdLogin third=new ThirdLogin();
//			third.setOpenid(openid);
//			third.setUid(uid);
//			third.setName(name);
//			third.setGender(gender);
//			third.setIconurl(iconurl);
//			third.setLoginWay("wx");
//			
//		} else if(login_way.equals("qq")) {
//			//获取QQ登录方式的参数
//			String uid=request.getParameter("uid");
//			String name =request.getParameter("name");
//			String gender =request.getParameter("gender");
//			String iconurl =request.getParameter("iconurl");
//			
//			//QQ传递的性别是女/男  
//			if(gender!=null) {
//				
//			}
//		} 
//	}
	
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

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

import com.arttraining.api.bean.LoginBeanV2;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.SMSCheckCode;
import com.arttraining.api.pojo.ThirdLogin;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.SMSService;
import com.arttraining.api.service.impl.ScoreService;
import com.arttraining.api.service.impl.ThirdLoginService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WalletService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.PhoneUtil;
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
	
	@Resource
	private ThirdLoginService thirdLoginService;
	@Resource
	private SMSService smsService;
	
	@Resource
	private ScoreService scoreService;
	
	@Resource
	private WalletService walletService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object loginYHY(HttpServletRequest request, HttpServletResponse response) {
		String name = "";
		String pwd = "";
		
		LoginBeanV2 loginBean = new LoginBeanV2();
		Gson gson = new Gson();
		UserStu userStu = null;
		
		String errorCode = "";
		String errorMessage = "";
		String accessToken = "";
		String login_way="yhy";
		
		name = request.getParameter("name");
		pwd = request.getParameter("pwd");
		//login_way=request.getParameter("login_way");
		
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
				userStu=this.userStuService.getUserByMobileAndRemarks(name, login_way);
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
						loginBean.setIs_bind("yes");
						//end
						
						//coffee add 0104  修改最近一次登录时间
						UserStu upd_user=new UserStu();
						upd_user.setId(user_id);
						upd_user.setLastLoginTime(date);
						this.userStuService.updateUserStuBySelective(upd_user);
						//end
						
						//coffee add 0301 如果记录过该登录用户的积分信息 则不插入 否则进行插入
//						this.scoreService.recordUserScoreInfoByLogin(user_id, user_type);
						//end
						
						//coffee add 0302 如果记录过该登录用户的云币信息 则不插入 否则进行插入
//						this.walletService.recordUserCloudMoneyByLogin(user_id, user_type);
						//end
					}
				}
			}
		}

		loginBean.setAccess_token(accessToken);
		loginBean.setError_code(errorCode);
		loginBean.setError_msg(errorMessage);
		ServerLog.getLogger().warn(gson.toJson(loginBean));
		return gson.toJson(loginBean);

	}
	
	/***
	 * 验证手机号是否绑定过之后
	 * 直接短信发送校验码
	 */
	@RequestMapping(value = "/register/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String accessToken = "";
		
		String mobile = "";
		String login_way = "";
		String uid="";
		//coffee add 
		String code_type ="reg_code";
		//end
				
		mobile = request.getParameter("mobile");
		login_way = request.getParameter("login_way");
		uid=request.getParameter("uid");
		
		ServerLog.getLogger().warn("mobile:"+mobile+"-login_way:"+login_way+"-uid:"+uid);
		if(mobile == null || login_way==null || uid==null){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(mobile.equals("") || login_way.equals("") || uid.equals("")){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(PhoneUtil.isMobile(mobile)){
			UserStu userStu = null;
			userStu = this.userStuService.getUserByMobileAndRemarks(mobile, login_way);
			if(userStu != null){
				SimpleReBean simReBean = new SimpleReBean();
				errorCode = "20068";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20068;
				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);
				ServerLog.getLogger().warn(gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}else{
				//coffee add  这里去短信验证码中去手机号最近一次使用的使用时间
				SMSCheckCode smsCCode = null;
				smsCCode = new SMSCheckCode();
				SMSCheckCode smsCheckCode = new SMSCheckCode();
				smsCheckCode.setMobile(mobile);
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
						SimpleReBean simReBean = new SimpleReBean();
						simReBean.setError_code(errorCode);
						simReBean.setError_msg(errorMsg);
						ServerLog.getLogger().warn(gson.toJson(simReBean));
						return gson.toJson(simReBean);
					}
					else {
						//coffee add begin
						//1.首先依据登录方式和uid去查询第三方登录用户信息
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("uid", uid);
						map.put("login_way", login_way);
						ThirdLogin third = this.thirdLoginService.getLoginInfoByUid(map);
						if(third!=null) {
							Date date=new Date();
							String fileName="";
							String iconurl=third.getIconurl();
							//判断获取到的微信用户头像是否为空 如果不为空 则从网络抓取图片 保存到本地
							if(iconurl!=null && !iconurl.trim().equals("")) {
								//上传至七牛云空间的文件名--命名方式
								fileName=System.currentTimeMillis()+Random.randomCommonStr(6)+".png";
								SignleUploadPresenter signUp=new SignleUploadPresenter();
								signUp.downloadIconToQiNiuYun(iconurl,fileName);
							}
							UserStu userStu2 = new UserStu();
							userStu2.setUserMobile(mobile);
							userStu2.setCreateTime(date);
							userStu2.setTitle("normal");//master：达人、brother：师哥师姐、nomal：一般等
							userStu2.setRemarks(login_way);
							userStu2.setHeadPic(fileName);
							userStu2.setLastLoginTime(date);
							userStu2.setName(third.getName());
							userStu2.setSex(third.getGender());
							userStu2.setUid(uid);
							
							int result = 0;
							result = this.userStuService.insert(userStu2);
							if (result == 1) {
								//coffee add //设置短信已使用
								smsCCode.setIsUsed(2);
								this.smsService.update(smsCCode);
								//end
								UserStu userBean = this.userStuService.getUserByMobileAndRemarks(mobile, login_way);
								accessToken = TokenUtil.generateToken(mobile);
								Integer user_id=userBean.getId();
								String user_type="stu";
								Map<String, Object> tokenMap=new HashMap<String, Object>();
								tokenMap.put("user_id", user_id);
								tokenMap.put("user_type", user_type);
								Token token=this.tokenService.getOneTokenInfo(tokenMap);
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
								errorMsg = "ok";
								
								LoginBeanV2 loginBean = new LoginBeanV2();
								//设置返回的token
								loginBean.setAccess_token(accessToken);
								loginBean.setCity(userBean.getCityName());
								loginBean.setEmail(userBean.getEmail());
								String head_pic= userBean.getHeadPic();
								loginBean.setHead_pic(head_pic);
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
								//coffee add
								loginBean.setBbs_num(userBean.getBbsNum());
								loginBean.setComment_num(userBean.getCommentNum());
								loginBean.setFans_num(userBean.getFansNum());
								loginBean.setFavorite_num(userBean.getFavoriteNum());
								loginBean.setFollow_num(userBean.getFollowNum());
								loginBean.setGroup_num(userBean.getGroupNum());
								loginBean.setWork_num(userBean.getWorkNum());
								loginBean.setIs_bind("yes");
								//end
								loginBean.setError_code(errorCode);
								loginBean.setError_msg(errorMsg);
								
								//coffee add 0301 如果记录过该登录用户的积分信息 则不插入 否则进行插入
								this.scoreService.recordUserScoreInfoByRegister(user_id,user_type);
								//end
								
								//coffee add 0302 如果记录过该登录用户的云币信息 则不插入 否则进行插入
								this.walletService.recordUserCloudMoneyByLogin(user_id, user_type);
								//end
								
								ServerLog.getLogger().warn(gson.toJson(loginBean));
								return gson.toJson(loginBean);
							
							} else {
								errorCode = "20040";
								errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20040;
	
								SimpleReBean simReBean = new SimpleReBean();
								simReBean.setError_code(errorCode);
								simReBean.setError_msg(errorMsg);
								ServerLog.getLogger().warn(gson.toJson(simReBean));
								return gson.toJson(simReBean);
							}
						  } else {
							  errorCode = "20069";
							  errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20069;
	
							  SimpleReBean simReBean = new SimpleReBean();
							  simReBean.setError_code(errorCode);
							  simReBean.setError_msg(errorMsg);
							  ServerLog.getLogger().warn(gson.toJson(simReBean));
							  return gson.toJson(simReBean);
						  }
						}
					} else {
						errorCode = "20049";
						errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20049;
						SimpleReBean simReBean = new SimpleReBean();
						simReBean.setError_code(errorCode);
						simReBean.setError_msg(errorMsg);
						ServerLog.getLogger().warn(gson.toJson(simReBean));
						return gson.toJson(simReBean);
					}
				}
				
			} else{
				errorCode = "20044";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
	
				SimpleReBean simReBean = new SimpleReBean();
				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);
				ServerLog.getLogger().warn(gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}
	}

	/***
	 * 第三方登录 如果未绑定过手机号
	 * 则直接进入绑定手机号的界面
	 */
	@RequestMapping(value = "/register/is_reg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object isRegister(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		//以下是必选参数
		account = request.getParameter("mobile");
		String login_way=request.getParameter("login_way");
		ServerLog.getLogger().warn("mobile:"+account+"-login_way:"+login_way);
		if(account == null || login_way==null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (account.equals("") || login_way.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(PhoneUtil.isMobile(account)){
			UserStu userStu = null;
			userStu = this.userStuService.getUserByMobileAndRemarks(account, login_way);
			if(userStu != null){
				errorCode = "20068";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20068;
			}else{
				errorCode = "0";
				errorMsg = "ok";
			}
		}else{
			errorCode = "20044";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
		}
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);
		ServerLog.getLogger().warn(gson.toJson(simReBean));
		return gson.toJson(simReBean);
	}
	/**
	 * 第三方登录
	 * 依据传递的登录方式来获取参数
	 * 登录方式(wx/qq/sina/facebook/twitter)
	 * 目前只设置了微信/QQ/新浪微博三种登录方式 
	 * 
	 */
	@RequestMapping(value = "/third/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object thirdLogin(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		LoginBeanV2 loginBean = new LoginBeanV2();
		//以下是必选参数
		String login_way=request.getParameter("login_way");
		//以下不是必选参数
		String openid="";
		String uid="";
		String name ="";
		String gender ="";
		String iconurl ="";
		//是否需要绑定注册用户信息
		String is_bind="";
		if(login_way==null || login_way.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		} else {
			UserStu userStu = null;
			//如果是微信授权登录
			if(login_way.equals("wx")) {
				//获取微信登录方式的参数
				openid=request.getParameter("openid");
				uid=request.getParameter("uid");
				name =request.getParameter("name");
				gender =request.getParameter("gender");
				iconurl =request.getParameter("iconurl");
				
				ServerLog.getLogger().warn("login_way:"+login_way+"-openid:"+openid+"-uid:"+uid+
						"-name:"+name+"-gender:"+gender+"-iconurl:"+iconurl);
				
				//微信传递的性别是f--女 m--男
				//QQ传递的性别是女/男  
				if(gender!=null) {
					if(gender.equals("男") || gender.equals("1")) {
						gender="m";
					} else if(gender.equals("女") || gender.equals("2")) {
						gender="f";
					}
				}
				//1.首先判断是否用微信登录过  如果微信登录过 则直接进行登录操作 否则进入绑定手机号界面
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("openid", openid);
				map.put("uid", uid);
				map.put("login_way", login_way);
				System.out.println("0000000");
				ThirdLogin third=this.thirdLoginService.getLoginInfoByUid(map);
				System.out.println("1111111");
				//如果用微信登录过 则直接返回登录信息
				if(third!=null) {
					System.out.println("2222222");
					//获取第三方登录用户信息
					userStu = this.userStuService.getUserStuByUidAndRemarks(uid, login_way);
					if(userStu!=null) {
						loginBean = getLoginInfoByUserStu(userStu);
						is_bind="yes";
						errorCode="0";
						errorMessage="ok";
					} else {
						is_bind="no";
						
						errorCode="0";
						errorMessage="ok";
						//如果查询不存在uid 则重新赋值一个对象
						//errorCode = "20022";
						//errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;	
					}
				} else {//如果尚未用微信进行第三方登录过 
					//则先进行在第三方登录表中新增一条记录
					System.out.println("3333333");
					is_bind="no";
//					String fileName="";
//					//判断获取到的微信用户头像是否为空 如果不为空 则从网络抓取图片 保存到本地
//					if(iconurl!=null && iconurl.trim().equals("")) {
//						//上传至七牛云空间的文件名--命名方式
//						fileName=System.currentTimeMillis()+Random.randomCommonStr(6)+".png";
//						SignleUploadPresenter signUp=new SignleUploadPresenter();
//						signUp.downloadIconToQiNiuYun(iconurl,fileName);
//					}
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					ThirdLogin third_ins=new ThirdLogin();
					third_ins.setOpenid(openid);
					third_ins.setUid(uid);
					third_ins.setName(name);
					third_ins.setIconurl(iconurl);
					third_ins.setGender(gender);
					third_ins.setCreateTime(date);
					third_ins.setOrderCode(time);
					third_ins.setLoginWay(login_way);
					this.thirdLoginService.insertOneThirdInfo(third_ins);
					errorCode="0";
					errorMessage="ok";
				}
			} else if(login_way.equals("qq")) {
				//获取QQ登录方式的参数
				uid=request.getParameter("uid");
				name =request.getParameter("name");
				gender =request.getParameter("gender");
				iconurl =request.getParameter("iconurl");
				
				ServerLog.getLogger().warn("login_way:"+login_way+"-uid:"+uid+
						"-name:"+name+"-gender:"+gender+"-iconurl:"+iconurl);
				//QQ传递的性别是女/男  
				if(gender!=null) {
					if(gender.equals("男")) {
						gender="m";
					} else if(gender.equals("女")) {
						gender="f";
					}
				}
				//1.首先判断是否用QQ登录过  如果QQ登录过 则直接进行登录操作 否则进入绑定手机号界面
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uid", uid);
				map.put("login_way", login_way);
				System.out.println("0000000");
				ThirdLogin third=this.thirdLoginService.getLoginInfoByUid(map);
				System.out.println("111111");
				//如果用QQ登录过 则直接返回登录信息
				if(third!=null) {
					System.out.println("2222222");
					//获取第三方登录用户信息
					userStu = this.userStuService.getUserStuByUidAndRemarks(uid, login_way);
					if(userStu!=null) {
						loginBean = getLoginInfoByUserStu(userStu);
						is_bind="yes";
						errorCode="0";
						errorMessage="ok";
					} else {
						is_bind="no";
						
						errorCode="0";
						errorMessage="ok";
						//如果查询不存在uid 则重新赋值一个对象
//						errorCode = "20022";
//						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;	
					}
				} else {//如果尚未用QQ进行第三方登录过 
					//则先进行在第三方登录表中新增一条记录
					System.out.println("333333");
					is_bind="no";
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					ThirdLogin third_ins=new ThirdLogin();
					third_ins.setOpenid(openid);
					third_ins.setUid(uid);
					third_ins.setName(name);
					third_ins.setIconurl(iconurl);
					third_ins.setGender(gender);
					third_ins.setCreateTime(date);
					third_ins.setOrderCode(time);
					third_ins.setLoginWay(login_way);
					this.thirdLoginService.insertOneThirdInfo(third_ins);
					errorCode="0";
					errorMessage="ok";
				}
			} 
		}
		loginBean.setError_code(errorCode);
		loginBean.setError_msg(errorMessage);
		loginBean.setIs_bind(is_bind);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(loginBean));
		return gson.toJson(loginBean);
	}
	//封装一个方法 用于返回登录成功的信息
	public LoginBeanV2 getLoginInfoByUserStu(UserStu userStu) {
		LoginBeanV2 loginBean = new LoginBeanV2();
		//1.判断数据库是否有该用户的登录token 如果没有 则新增 如果有 则修改
		String mobile= userStu.getUserMobile();
		String accessToken = TokenUtil.generateToken(mobile);
		Integer user_id=userStu.getId();
		String user_type="stu";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("user_type", user_type);
		Token token=this.tokenService.getOneTokenInfo(map);
		//获取当前时间
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
		//设置返回的token
		loginBean.setAccess_token(accessToken);
		loginBean.setCity(userStu.getCityName());
		loginBean.setEmail(userStu.getEmail());
		String head_pic= userStu.getHeadPic();
		loginBean.setHead_pic(head_pic);
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
		
		//coffee add 0301 如果记录过该登录用户的积分信息 则不插入 否则进行插入
		this.scoreService.recordUserScoreInfoByLogin(user_id, user_type);
		//end
		
		//coffee add 0302 如果记录过该登录用户的云币信息 则不插入 否则进行插入
		this.walletService.recordUserCloudMoneyByLogin(user_id, user_type);
		//end
		
		return loginBean;
	}

}

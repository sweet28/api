package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.LoginBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.SMSCheckCode;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.InviteCodeService;
import com.arttraining.api.service.impl.SMSService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.InviteCodeUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.ServerLog;
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
	@Resource
	private SMSService smsService;
	
	@RequestMapping(value = "/is_reg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object isRegister(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		
		account = request.getParameter("mobile");
		//coffee add 0105
		String login_way="yhy";
		//end
		ServerLog.getLogger().warn("mobile:"+account);
		//System.out.println("进入注册验证："+account+TimeUtil.getTimeStamp());
		if(account == null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (account.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(PhoneUtil.isMobile(account)){
			UserStu userStu = null;
			userStu = this.userStuService.getUserByMobileAndRemarks(account, login_way);
			//userStu = this.userStuService.getUserStuByAccount(account);
			if(userStu != null){
				errorCode = "20024";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20024;
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
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String accessToken = "";
		
		String moblie = "";
		String pwd = "";
		String name = "";
		
		//coffee add 
		//String code_type = request.getParameter("code_type");
		String code_type ="reg_code";
		//end
				
		//System.out.println("进入注册"+TimeUtil.getTimeStamp());
		//coffee 0105
		String login_way="yhy";
		//end
		moblie = request.getParameter("mobile");
		pwd = request.getParameter("psw");
		name = request.getParameter("name");
		ServerLog.getLogger().warn("mobile:"+moblie+"-psw:"+pwd+"-name:"+name);
		if(moblie == null || pwd == null){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(moblie.equals("") || pwd.equals("")){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(PhoneUtil.isMobile(moblie)){
			UserStu userStu = null;
			userStu=this.userStuService.getUserByMobileAndRemarks(moblie, login_way);
			//userStu = this.userStuService.getUserStuByAccount(moblie);
			if(userStu != null){
				SimpleReBean simReBean = new SimpleReBean();
				errorCode = "20024";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20024;
				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);
				ServerLog.getLogger().warn(gson.toJson(simReBean));
				//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}else{
				//coffee add  这里去短信验证码中去手机号最近一次使用的使用时间
				SMSCheckCode smsCCode = null;
				smsCCode = new SMSCheckCode();
				SMSCheckCode smsCheckCode = new SMSCheckCode();
				smsCheckCode.setMobile(moblie);
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
						//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
						return gson.toJson(simReBean);
					}
					else {
						//coffee add begin
						UserStu userStu2 = null;
						userStu2 = new UserStu();

						pwd = MD5.encodeString(MD5.encodeString(pwd
								+ ConfigUtil.MD5_PWD_STR)
								+ ConfigUtil.MD5_PWD_STR);
						userStu2.setUserMobile(moblie);
						userStu2.setPwd(pwd);
						userStu2.setCreateTime(TimeUtil.getTimeStamp());
						userStu2.setRemarks(login_way);
						userStu2.setTitle("normal");//master：达人、brother：师哥师姐、nomal：一般等
						
						String inviteCode = "";
						String memberCode = "";
						inviteCode = InviteCodeUtil.toSerialCode(0, 7, ConfigUtil.USER_FLAG_STU);
						IdWorker idWorker = new IdWorker(0, 0);
						memberCode = idWorker.nextId() + "";
						
						if(!"".equals(inviteCode)){
							userStu2.setUid(inviteCode);
						}
						if(!"".equals(memberCode)){
							userStu2.setUserCode(memberCode);
						}
						
						if (name != null && !("").equals(userStu2)) {
							userStu2.setName(name);
						}else{
							String hidden_mobile=PhoneUtil.hiddenPhoneNumber(moblie);
							userStu2.setName(hidden_mobile);
						}
						int result = 0;
						result = this.userStuService.insert(userStu2);
						System.out.println(":::::::::::::result::::" + result);
						if (result == 1) {
							//coffee add //设置短信已使用
							smsCCode.setIsUsed(2);
							this.smsService.update(smsCCode);
							//end
							
							UserStu userBean = this.userStuService.getUserByMobileAndRemarks(moblie, login_way);
							//UserStu userBean = this.userStuService.getUserStuByAccount(moblie);
							accessToken = TokenUtil.generateToken(moblie);
							errorCode = "0";
							errorMsg = "ok";
							
							LoginBean loginBean = new LoginBean();
							loginBean.setCity(userBean.getCityName());
							loginBean.setEmail(userBean.getEmail());
							String headPic = userBean.getHeadPic();
							loginBean.setHead_pic(headPic);
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
							//end
							
							loginBean.setAccess_token(accessToken);
							loginBean.setError_code(errorCode);
							loginBean.setError_msg(errorMsg);
							ServerLog.getLogger().warn(gson.toJson(loginBean));
							//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(loginBean));
							return gson.toJson(loginBean);
							
						} else {
							errorCode = "20040";
							errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20040;

							SimpleReBean simReBean = new SimpleReBean();
							simReBean.setError_code(errorCode);
							simReBean.setError_msg(errorMsg);
							ServerLog.getLogger().warn(gson.toJson(simReBean));
							//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
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
					//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
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
			//System.out.println(TimeUtil.getTimeStamp()+"-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}
	}

}

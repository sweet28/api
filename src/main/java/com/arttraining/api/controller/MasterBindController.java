package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.SMSCheckCode;

import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.InviteCodeService;
import com.arttraining.api.service.impl.SMSService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/bind/masters")
public class MasterBindController {
	@Resource
	private InviteCodeService invCodeService;
	@Resource
	private SMSService smsService;
	@Resource
	private UserTecService userTecService;
	
	@RequestMapping(value = "/is_bind", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object isBind(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		
		account = request.getParameter("mobile");
		ServerLog.getLogger().warn("mobile:"+account);
		
		if(account == null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (account.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(PhoneUtil.isMobile(account)){
			UserTech tec = null;
			tec = this.userTecService.getMasterInfoByName(account);
			//如果教师已经绑定过手机号 则提醒已经绑定过
			if(tec != null){
				errorCode = "20077";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20077;
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
		
		String mobile = "";
		//coffee add 
		String code_type ="bind_code";
		//end
		
		mobile = request.getParameter("mobile");
		String uid=request.getParameter("uid");
		
		ServerLog.getLogger().warn("mobile:"+mobile+"-uid:"+uid);
		if(mobile == null || uid==null){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(mobile.equals("") || uid.equals("")){
			SimpleReBean simReBean = new SimpleReBean();
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(PhoneUtil.isMobile(mobile)){
			UserTech tec = null;
			tec=this.userTecService.getMasterInfoByName(mobile);
			//如果
			if(tec != null){
				SimpleReBean simReBean = new SimpleReBean();
				errorCode = "20077";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20077;
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
						Integer i_uid=Integer.valueOf(uid);
						//coffee add begin
						UserTech upd_tec = new UserTech();
						upd_tec.setId(i_uid);
						upd_tec.setUserMobile(mobile);
						
						int result = 0;
						result = this.userTecService.updateMasterInfoByPrimaryKeySelective(upd_tec);
						System.out.println(":::::::::::::result::::" + result);
						if (result == 1) {
							//coffee add //设置短信已使用
							smsCCode.setIsUsed(2);
							this.smsService.update(smsCCode);
							//end
							errorCode = "0";
							errorMsg = "ok";
							
							SimpleReBean simReBean = new SimpleReBean();
							simReBean.setError_code(errorCode);
							simReBean.setError_msg(errorMsg);
							ServerLog.getLogger().warn(gson.toJson(simReBean));
							return gson.toJson(simReBean); 
							
						} else {
							errorCode = "20078";
							errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20078;

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

}

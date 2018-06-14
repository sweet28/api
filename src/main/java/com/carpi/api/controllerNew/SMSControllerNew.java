package com.carpi.api.controllerNew;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.SMSCheckCode;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.SMSService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.DaYuServiceUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Controller
@RequestMapping("/quanbaoli/sms")
public class SMSControllerNew {
	@Resource
	private SMSService smsService;
	
	@RequestMapping(value = "/code/send", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonResult yanzhengma(HttpServletRequest request, HttpServletResponse response) throws ClientException{
//		SimpleReBean simReBean = new SimpleReBean();
//		Gson gson = new Gson();
		String mobile = "";
		mobile = request.getParameter("mobile");
		ServerLog.getLogger().warn("mobile:"+ mobile);
		//System.out.println("进入验证码发送"+TimeUtil.getTimeStamp()+mobile+"-"+codeType);
		if(mobile == null){
			System.out.println("进入验证码发送：空"+TimeUtil.getTimeStamp());
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
			
		} else if (mobile.equals("")) {
			System.out.println("进入验证码发送:手机号-type空"+TimeUtil.getTimeStamp());
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
			
		} else if(PhoneUtil.isMobile(mobile)){
			
			System.out.println("2222===");
			
			SMSCheckCode smsCCode = null;
			smsCCode = new SMSCheckCode();
			SMSCheckCode smsCheckCode = new SMSCheckCode();
			smsCheckCode.setMobile(mobile);
			
			System.out.println("333===");
			
			smsCCode = this.smsService.getSMSCheckCode(smsCheckCode);
			
			System.out.println("444===");
			
			if(smsCCode != null){
				
				System.out.println("进入验证码发送：验证码存在"+TimeUtil.getTimeStamp());
				
				long expireTime = smsCCode.getExpireTime().getTime();
				long createTime = smsCCode.getCreateTime().getTime();
				long nowTime = new Date().getTime();
				long diffSeconds = TimeUtil.diffSeconds(nowTime, createTime);
				System.out.println("createTime:"+createTime+"-nowTime:"+nowTime+"-diffSeconds:"+diffSeconds);
				if(diffSeconds < 60){
					System.out.println("时间间隔太小，老弟你刷短信纳是吧，果断拒绝你");
//					errorCode = "20046";
//					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20046;
					return JsonResult.build(20046, ErrorCodeConfigUtil.ERROR_MSG_ZH_20046);
				}else{
					JSONObject jo = DaYuServiceUtil.sendSms2(mobile);
					
					System.out.println("进入验证码发送：最新发送"+TimeUtil.getTimeStamp());
					System.out.println("==================+"+jo);
					if(jo.getBoolean(ConfigUtil.PARAMETER_ERROR_CODE)){
						smsCCode.setIsUsed(1);//设置短信已使用，发送新短信
						smsCCode.setUsingTime(TimeUtil.getTimeStamp());
						this.smsService.update(smsCCode);
						
						String smsCode = jo.getString(ConfigUtil.PARAMETER_ERROR_MSG);
						SMSCheckCode newSms = new SMSCheckCode();
						newSms.setMobile(mobile);
						newSms.setCheckCode(smsCode);
						newSms.setCreateTime(TimeUtil.getTimeStamp());
						newSms.setIsUsed(0);
						newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
						this.smsService.insert(newSms);
						
//						errorCode = "0";
//						errorMsg = "ok";
						JsonResult.ok();
						
					}else{
						//发送失败
						System.out.println("进入验证码发送：失败"+TimeUtil.getTimeStamp());
//						errorCode = "20047";
//						errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20047;
						
						
						smsCCode.setIsUsed(1);//设置短信已使用，发送新短信
						smsCCode.setUsingTime(TimeUtil.getTimeStamp());
						this.smsService.update(smsCCode);
						return JsonResult.build(20047,ErrorCodeConfigUtil.ERROR_MSG_ZH_20047);
						
//						String smsCode = Random.randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH);
//						SMSCheckCode newSms = new SMSCheckCode();
//						newSms.setMobile(mobile);
//						newSms.setCheckCode(smsCode);
//						newSms.setCreateTime(TimeUtil.getTimeStamp());
//						newSms.setIsUsed(0);
//						newSms.setRemarks(codeType);
//						newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
//						this.smsService.insert(newSms);
						
//						errorCode = "101";
//						errorMsg = ""+smsCode;
					}
				}
				
			}else{
				//发送验证码
				JSONObject jo = DaYuServiceUtil.sendSms2(mobile);
				System.out.println("进入验证码发送：最新发送"+TimeUtil.getTimeStamp());
				
				if(jo.getBoolean(ConfigUtil.PARAMETER_ERROR_CODE)){
					
					System.out.println("发送成功并"+TimeUtil.getTimeStamp());
					
					String smsCode = jo.getString(ConfigUtil.PARAMETER_ERROR_MSG);
					
					SMSCheckCode newSms = new SMSCheckCode();
					newSms.setMobile(mobile);
					newSms.setCheckCode(smsCode);
					newSms.setCreateTime(TimeUtil.getTimeStamp());
					newSms.setIsUsed(0);
					newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
					this.smsService.insert(newSms);
					
					return JsonResult.ok();
//					errorCode = "0";
//					errorMsg = "ok";
				}else{
					System.out.println("进入验证码发送：发送失败2"+TimeUtil.getTimeStamp());
//					errorCode = "20047";
//					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20047;
					
					return JsonResult.build(20047, ErrorCodeConfigUtil.ERROR_MSG_ZH_20047);
//					String smsCode = Random.randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH);
//					SMSCheckCode newSms = new SMSCheckCode();
//					newSms.setMobile(mobile);
//					newSms.setCheckCode(smsCode);
//					newSms.setCreateTime(TimeUtil.getTimeStamp());
//					newSms.setIsUsed(0);
//					newSms.setRemarks(codeType);
//					newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
//					this.smsService.insert(newSms);
//					
//					errorCode = "101";
//					errorMsg = ""+smsCode;
				}
			}
		}else{
			System.out.println("1111111=====");
//			errorCode = "20044";
//			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
			return JsonResult.build(20044, ErrorCodeConfigUtil.ERROR_MSG_ZH_20044);
		}
		
//		simReBean.setError_code(errorCode);
//		simReBean.setError_msg(errorMsg);
//		ServerLog.getLogger().warn(gson.toJson(simReBean));
//		return gson.toJson(simReBean);
		return JsonResult.ok();
	}
}
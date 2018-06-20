package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.arttraining.commons.util.IDCardUtil;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.FensAuthenticationMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensUser;
import com.google.code.kaptcha.Constants;
import com.google.gson.Gson;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Controller
@RequestMapping("/sms")
public class SMSController {
	@Resource
	private SMSService smsService;
	
	@Autowired
    private ImageCaptchaService imageCaptchaService;
	
	@Autowired
	private FensUserMapper fensUserMapper;
	
	@Autowired
	private FensAuthenticationMapper fensAuthenticationMapper;
	
	@RequestMapping(value = "/code/send", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonResult yanzhengma(HttpServletRequest request, HttpServletResponse response) throws ClientException{
		
		HttpSession session = request.getSession();
		
		Boolean isResponseCorrect =Boolean.FALSE;
        String captcha = request.getParameter("captcha");
		String generateCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		String phone = request.getParameter("sh");
		String idCardNum = request.getParameter("sf_cd");
		
		if(generateCode == null){
			return JsonResult.build(500, "请点击图片验证码刷新，并重新填写验证码");
		}
		
		if(generateCode.equals("----")){
			return JsonResult.build(500, "请点击图片验证码刷新，并重新填写验证码");
		}
		
		if(captcha == null){
			return JsonResult.build(500, "请填写正确的图片验证码");
		}
		
		if (phone == null) {
			return JsonResult.build(500, "请填写手机号");
		}

		if (idCardNum == null) {
			return JsonResult.build(500, "请检查身份证是否正确");
		}
        
        isResponseCorrect = generateCode.equals(captcha);
        
        // 查询该手机号是否注册过
        FensUser fu = new FensUser();
        fu.setPhone(phone);
		FensUser user = fensUserMapper.selectRegister(fu);
		if (user != null) {
			return JsonResult.build(20024, ErrorCodeConfigUtil.ERROR_MSG_ZH_20024);
		}
		
		boolean isIdcard = IDCardUtil.isIDCard(idCardNum);
		if (isIdcard == false) {
			return JsonResult.build(500, "请检查身份证是否正确");
		}

		// 校验身份证号码是否被绑定
		FensAuthentication cardInfo = fensAuthenticationMapper.selectCardInfo(idCardNum);
		if (cardInfo != null) {
			return JsonResult.build(500, "该身份证已被绑定，如需进一步操作，请联系管理员");
		}
		
        if (isResponseCorrect) {
        	session.setAttribute(Constants.KAPTCHA_SESSION_KEY, "----");
			String mobile = "";
			
			mobile = request.getParameter("mobile");
			if(mobile == null){
				return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
				
			} else if (mobile.equals("")) {
				return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
				
			} else if(PhoneUtil.isMobile(mobile)){
				
				SMSCheckCode smsCCode = null;
				smsCCode = new SMSCheckCode();
				SMSCheckCode smsCheckCode = new SMSCheckCode();
				smsCheckCode.setMobile(mobile);
				
				smsCCode = this.smsService.getSMSCheckCode(smsCheckCode);
				
				if(smsCCode != null){
					
					long expireTime = smsCCode.getExpireTime().getTime();
					long createTime = smsCCode.getCreateTime().getTime();
					long nowTime = new Date().getTime();
					long diffSeconds = TimeUtil.diffSeconds(nowTime, createTime);
					System.out.println("createTime:"+createTime+"-nowTime:"+nowTime+"-diffSeconds:"+diffSeconds);
					if(diffSeconds < 120){
						System.out.println("时间间隔太小，老弟你刷短信纳是吧，果断拒绝你");
						return JsonResult.build(20046, ErrorCodeConfigUtil.ERROR_MSG_ZH_20046);
					}else{
						JSONObject jo = DaYuServiceUtil.sendSms(mobile);
						
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
							
							JsonResult.ok();
							
						}else{
							//发送失败
							smsCCode.setIsUsed(1);//设置短信已使用，发送新短信
							smsCCode.setUsingTime(TimeUtil.getTimeStamp());
							this.smsService.update(smsCCode);
							return JsonResult.build(20047,ErrorCodeConfigUtil.ERROR_MSG_ZH_20047);
						}
					}
					
				}else{
					//发送验证码
					JSONObject jo = DaYuServiceUtil.sendSms(mobile);
					
					if(jo.getBoolean(ConfigUtil.PARAMETER_ERROR_CODE)){
						
						String smsCode = jo.getString(ConfigUtil.PARAMETER_ERROR_MSG);
						
						SMSCheckCode newSms = new SMSCheckCode();
						newSms.setMobile(mobile);
						newSms.setCheckCode(smsCode);
						newSms.setCreateTime(TimeUtil.getTimeStamp());
						newSms.setIsUsed(0);
						newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
						this.smsService.insert(newSms);
						
						return JsonResult.ok();
					}else{
						return JsonResult.build(20047, ErrorCodeConfigUtil.ERROR_MSG_ZH_20047);
					}
				}
			}else{
				return JsonResult.build(20044, ErrorCodeConfigUtil.ERROR_MSG_ZH_20044);
			}
			
			return JsonResult.ok();
		}else{
			return JsonResult.build(500, "请输入正确的图片校验码");
		}
	}
	
	@RequestMapping(value = "/news/info/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonResult smsCodeSend(HttpServletRequest request, HttpServletResponse response) throws ClientException{
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
				if(diffSeconds < 180){
					System.out.println("时间间隔太小，老弟你刷短信纳是吧，果断拒绝你");
//					errorCode = "20046";
//					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20046;
					return JsonResult.build(20046, ErrorCodeConfigUtil.ERROR_MSG_ZH_20046);
				}else{
					JSONObject jo = DaYuServiceUtil.sendSms(mobile);
					
					System.out.println("进入验证码发送：最新发送"+TimeUtil.getTimeStamp());
					
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
				JSONObject jo = DaYuServiceUtil.sendSms(mobile);
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
			return JsonResult.build(20044, ErrorCodeConfigUtil.ERROR_MSG_ZH_20044);
		}
		
		return JsonResult.ok();
	}

	@RequestMapping(value = "/verification_code/send", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object sendCode(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String mobile = "";
		String codeType = "";
		
		mobile = request.getParameter("mobile");
		codeType = request.getParameter("code_type");
		ServerLog.getLogger().warn("mobile:"+mobile+"-code_type:"+codeType);
		//System.out.println("进入验证码发送"+TimeUtil.getTimeStamp()+mobile+"-"+codeType);
		
		if(mobile == null || codeType == null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			System.out.println("进入验证码发送：空"+TimeUtil.getTimeStamp());
		} else if (mobile.equals("") || codeType.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			System.out.println("进入验证码发送:手机号-type空"+TimeUtil.getTimeStamp());
		} else if(PhoneUtil.isMobile(mobile)){
			System.out.println("2222===");
			
			SMSCheckCode smsCCode = null;
			smsCCode = new SMSCheckCode();
			SMSCheckCode smsCheckCode = new SMSCheckCode();
			smsCheckCode.setMobile(mobile);
			//smsCheckCode.setRemarks(codeType);
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
					errorCode = "20046";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20046;
				}else{
					//JSONObject jo = null;//DaYuServiceUtil.sendSMSCode(mobile, codeType);
					JSONObject jo = DaYuServiceUtil.sendSMSCode(mobile, codeType);
					System.out.println("进入验证码发送：最新发送"+TimeUtil.getTimeStamp());
					
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
						newSms.setRemarks(codeType);
						newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
						this.smsService.insert(newSms);
						
						errorCode = "0";
						errorMsg = "ok";
						
					}else{
						System.out.println("进入验证码发送：失败"+TimeUtil.getTimeStamp());
						errorCode = "20047";
						errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20047;
						
						
						smsCCode.setIsUsed(1);//设置短信已使用，发送新短信
						smsCCode.setUsingTime(TimeUtil.getTimeStamp());
						this.smsService.update(smsCCode);
						
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
				JSONObject jo = DaYuServiceUtil.sendSMSCode(mobile, codeType);
				System.out.println("进入验证码发送：最新发送"+TimeUtil.getTimeStamp());
				
				if(jo.getBoolean(ConfigUtil.PARAMETER_ERROR_CODE)){
					System.out.println("发送成功并"+TimeUtil.getTimeStamp());
					String smsCode = jo.getString(ConfigUtil.PARAMETER_ERROR_MSG);
					SMSCheckCode newSms = new SMSCheckCode();
					newSms.setMobile(mobile);
					newSms.setCheckCode(smsCode);
					newSms.setCreateTime(TimeUtil.getTimeStamp());
					newSms.setIsUsed(0);
					newSms.setRemarks(codeType);
					newSms.setExpireTime(TimeUtil.getTimeByMinute(ConfigUtil.ALIDAYU_SMS_EXPIRE_TIME));
					this.smsService.insert(newSms);
					
					errorCode = "0";
					errorMsg = "ok";
				}else{
					System.out.println("进入验证码发送：发送失败2"+TimeUtil.getTimeStamp());
					errorCode = "20047";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20047;
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
			errorCode = "20044";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
		}
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);
		ServerLog.getLogger().warn(gson.toJson(simReBean));
		return gson.toJson(simReBean);
	}
	
	@RequestMapping(value = "/verification_code/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object verifyCode(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String mobile = "";
		String codeType = "";
		String smsCode = "";
		
		mobile = request.getParameter("mobile");
		codeType = request.getParameter("code_type");
		smsCode = request.getParameter("ver_code");
		ServerLog.getLogger().warn("mobile:"+mobile+"-code_type:"+codeType+"-ver_code:"+smsCode);
		//System.out.println("进入验证码验证"+TimeUtil.getTimeStamp()+mobile+"-"+codeType+"-"+smsCode);
		
		if(mobile == null || codeType == null){
			System.out.println("验证码验证1");
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println("验证码验证8"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		} else if (mobile.equals("") || codeType.equals("")) {
			System.out.println("验证码验证2");
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println("验证码验证8"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		} else if (smsCode.equals("") || smsCode.equals("")) {
			System.out.println("验证码验证3");
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println("验证码验证8"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		} else{
			System.out.println("验证码验证4");
			SMSCheckCode smsCCode = null;
			smsCCode = new SMSCheckCode();
			SMSCheckCode smsCheckCode = new SMSCheckCode();
			smsCheckCode.setMobile(mobile);
			smsCheckCode.setRemarks(codeType);
			smsCheckCode.setCheckCode(smsCode);
			smsCCode = this.smsService.getSMSCheckCode(smsCheckCode);
			if(smsCCode != null){
				System.out.println("验证码验证5");
				long expireTime = smsCCode.getExpireTime().getTime();
				long nowTime = new Date().getTime();
				long expireSeconds = TimeUtil.diffSeconds(expireTime, nowTime);
				if(expireSeconds < 0){
					System.out.println("进入验证码验证2"+TimeUtil.getTimeStamp());
					errorCode = "20048";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20048;
					
					simReBean.setError_code(errorCode);
					simReBean.setError_msg(errorMsg);
					ServerLog.getLogger().warn(gson.toJson(simReBean));
					//System.out.println("验证码验证8"+gson.toJson(simReBean));
					return gson.toJson(simReBean);
				}else{
					System.out.println("验证码验证6");
					errorCode = "0";
					errorMsg = "ok";
					//coffee add //设置短信已使用
					smsCCode.setIsUsed(1);
					smsCCode.setUsingTime(TimeUtil.getTimeStamp());
					this.smsService.update(smsCCode);
					//end
					simReBean.setError_code(errorCode);
					simReBean.setError_msg(errorMsg);
					ServerLog.getLogger().warn(gson.toJson(simReBean));
					//System.out.println("验证码验证8"+gson.toJson(simReBean));
					return gson.toJson(simReBean);
				}
			}else{
				System.out.println("验证码验证7");
				errorCode = "20049";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20049;
				
				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);
				ServerLog.getLogger().warn(gson.toJson(simReBean));
				//System.out.println("验证码验证8"+gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}
		}
	}
}

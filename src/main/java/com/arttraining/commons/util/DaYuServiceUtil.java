package com.arttraining.commons.util;

import java.util.Date;

import org.aspectj.apache.bcel.classfile.Code;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.arttraining.api.pojo.course;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class DaYuServiceUtil {
	public static JSONObject sendSMSCode(String mobile, String codeType) {
		JSONObject resultJo = new JSONObject();
		String freeSingName = "CPA";
		String templateCode = "SMS_11655386";

		if (codeType.equals("") || codeType == null) {
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "验证码类型为空");
			return resultJo;
		} else if (codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_REG)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_REG;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_REG;
		} else if (codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_LOGIN)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_LOGIN;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_LOGIN;
		} else if (codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_ACTIVITY)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_ACTIVITY;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_ACTIVITY;
		} else if (codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_CHANGE)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_CHANGE;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_CHANGE_PWD;
		} else if (codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_IDENTITY)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_IDENTITY;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_IDENTITY;
		} else if (codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_BIND)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_IDENTITY;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_IDENTITY;
		}

		String code = Random.randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH);
		TaobaoClient client = new DefaultTaobaoClient(ConfigUtil.ALIDAYU_SMS_URL, ConfigUtil.ALIDAYU_SMS_APPKEY,
				ConfigUtil.ALIDAYU_SMS_SECRET);
		String json = "{\"code\":\"" + code + "\",\"product\":\"" + ConfigUtil.ALIDAYU_SMS_PRODUCT + "\"}";
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("1");
		req.setSmsType("normal");
		req.setSmsFreeSignName(freeSingName);
		req.setRecNum(mobile);
		req.setSmsTemplateCode(templateCode);
		req.setSmsParamString(json);

		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			JSONObject jo = JSONObject.parseObject(rsp.getBody());
			System.out.println(rsp.getBody());
			if (jo.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
				resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, true);
				resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, code);
			} else {
				resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
				resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "发送失败，重新发送");
			}
			// {"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"104462254657^1106066881088","success":true},"request_id":"439efahgw9o0"}}
			// {"error_response":{"code":15,"msg":"Remote service
			// error","sub_code":"isv.MOBILE_NUMBER_ILLEGAL","sub_msg":"号码格式错误","request_id":"rxn0m6mwsjqy"}}
		} catch (ApiException e) {
			e.printStackTrace();
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "发送失败，重新发送");
		}

		return resultJo;
	}

	public static JSONObject sendSms(String mobile) throws ClientException {

		JSONObject resultJo = new JSONObject();
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ConfigUtil.SMS_ACCESSKEYID,
				ConfigUtil.SMS_ACCESSKEYSECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", ConfigUtil.SMS_PRODUCT, ConfigUtil.SMS_DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(mobile);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("carpai");
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode("SMS_136065264");
		// 生成验证码
		String code = Random.randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"name\":\"name\", \"code\":" + code + "}");

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");
		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, true);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, code);
		} else {
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "发送失败，重新发送");
		}

		return resultJo;
	}
	
	public static JSONObject sendSms2(String mobile) throws ClientException {

		JSONObject resultJo = new JSONObject();
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ConfigUtil.SMS_ACCESSKEYID,
				ConfigUtil.SMS_ACCESSKEYSECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", ConfigUtil.SMS_PRODUCT, ConfigUtil.SMS_DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(mobile);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("carpai");
		// 必填:短信模板-可在短信控制台中找到（券保理）
//		request.setTemplateCode("SMS_136398607");
		request.setTemplateCode("SMS_139970290");
		// 生成验证码
		String code = Random.randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//		request.setTemplateParam("{\"name\":\"name\", \"code\":" + code + "}");
		String subtime = TimeUtil.getTimeByDate(new Date());
		request.setTemplateParam("{\"submittime\":\"" + subtime + "\"}");

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");
		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, true);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, code);
		} else {
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "发送失败，重新发送");
		}
		return resultJo;
	}

}

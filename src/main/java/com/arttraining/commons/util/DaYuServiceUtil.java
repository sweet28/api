package com.arttraining.commons.util;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class DaYuServiceUtil {
	public static JSONObject sendSMSCode(String mobile, String codeType) {
		JSONObject resultJo = new JSONObject();
		String freeSingName = "艺培达人";
		String templateCode = "SMS_11655386";
		
		if(codeType.equals("") || codeType == null){
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "验证码类型为空");
			return resultJo;
		}else if(codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_REG)){
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_REG;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_REG;
		}else if(codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_LOGIN)){
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_LOGIN;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_LOGIN;
		}else if(codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_ACTIVITY)){
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_ACTIVITY;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_ACTIVITY;
		}else if(codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_CHANGE)){
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_CHANGE;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_CHANGE_PWD;
		}else if(codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_IDENTITY)){
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_IDENTITY;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_IDENTITY;
		}else if(codeType.equals(ConfigUtil.ALIDAYU_SMS_CODE_TYPE_BIND)) {
			freeSingName = ConfigUtil.ALIDAYU_SMS_FREE_SIGN_NAME_BIND;
			templateCode = ConfigUtil.ALIDAYU_SMS_TEMPLATE_CODE_BIND;
		}
		
		String code = Random.randomCommonStr(ConfigUtil.ALIDAYU_SMS_CHECK_CODE_LENGTH);
		TaobaoClient client = new DefaultTaobaoClient(ConfigUtil.ALIDAYU_SMS_URL, ConfigUtil.ALIDAYU_SMS_APPKEY,
				ConfigUtil.ALIDAYU_SMS_SECRET);
		String json = "{\"code\":\"" + code + "\",\"product\":\""
				+ ConfigUtil.ALIDAYU_SMS_PRODUCT + "\"}";
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
			if(jo.containsKey("alibaba_aliqin_fc_sms_num_send_response")){
				resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, true);
				resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, code);
			}else{
				resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
				resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "发送失败，重新发送");
			}
			// {"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"104462254657^1106066881088","success":true},"request_id":"439efahgw9o0"}}
			//{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.MOBILE_NUMBER_ILLEGAL","sub_msg":"号码格式错误","request_id":"rxn0m6mwsjqy"}}
		} catch (ApiException e) {
			e.printStackTrace();
			resultJo.put(ConfigUtil.PARAMETER_ERROR_CODE, false);
			resultJo.put(ConfigUtil.PARAMETER_ERROR_MSG, "发送失败，重新发送");
		}

		return resultJo;
	}
}

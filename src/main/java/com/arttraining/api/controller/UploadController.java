package com.arttraining.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.TokenUtil;
import com.qiniu.util.Auth;

@Controller
@RequestMapping("/upload")
public class UploadController {
	// 依据用户uid查询爱好者用户信息
	@RequestMapping(value = "/get_token/qiniu/{access_token}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getQiniuToken(
			@PathVariable("access_token") String token) {
		String QNToken = "";
		String errorCode = "";
		String errorMessage = "";
		//todo:判断token是否有效
		boolean tokenFlag = TokenUtil.checkToken(token);
		if(tokenFlag){
			Auth auth = Auth.create(ConfigUtil.QINIU_AK, ConfigUtil.QINIU_SK);
			QNToken = auth.uploadToken(ConfigUtil.QINIU_BUCKET, null, ConfigUtil.QINIU_EXPIRES, ConfigUtil.QINIU_POLICY);
			if(QNToken.equals("") || QNToken == null){
				errorCode = "";
				errorMessage = "";
				QNToken = "";
			}else{
				errorCode = "0";
				errorMessage = "ok";
			}
		}else{
			errorCode = "20039";
			errorMessage = "登录失效，请重新登录";
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put(ConfigUtil.PARAMETER_QINIU_TOKEN, QNToken);

		return jsonObject;

	}

}

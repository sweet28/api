package com.arttraining.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
public class ExceptionController {
	 /**请求异常
	 * @return
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/error_404", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object error_404() { 
		String errorCode="10010";
		String errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_10010;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;

	}
	/**
	 * 服务器异常
	 * @return
	 * String
	 */
	@RequestMapping(value ="/error_500", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object error_500() {
		String errorCode="10010";
		String errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_10010;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
}

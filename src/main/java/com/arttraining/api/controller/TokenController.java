package com.arttraining.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TokenUtil;

@Controller
@RequestMapping("/token")
public class TokenController {
	
	/**
	 * 验证token是否有效
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object verify(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		
		ServerLog.getLogger().warn("access_token:"+access_token);
		if(access_token==null || access_token.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				errorCode = "0";
				errorMessage = "ok";
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

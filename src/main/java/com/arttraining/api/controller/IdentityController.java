package com.arttraining.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.IdentityListBean;
import com.arttraining.api.service.impl.IdentityService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
@RequestMapping("/identity")
public class IdentityController {
	@Resource
	private IdentityService identityService;
	
	/***
	 * 获取身份列表
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//获取身份列表
		List<IdentityListBean> identityList = this.identityService.getIdentityList();
		if(identityList.size()>0) {
			errorCode = "0";
			errorMessage = "ok";
		}
		else {
			identityList = new ArrayList<IdentityListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("identitys", identityList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

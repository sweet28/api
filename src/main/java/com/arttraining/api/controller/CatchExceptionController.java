package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.pojo.AppException;
import com.arttraining.api.service.impl.ExceptionService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
@RequestMapping("/exception")
public class CatchExceptionController {
	@Resource
	private ExceptionService exceptionService;
	
	@RequestMapping(value = "/receive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object receive(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下参数是必选的
		String app_type=request.getParameter("app_type");
		String version_no=request.getParameter("version_no");
		String version_name=request.getParameter("version_name");
		String device_name=request.getParameter("device_name");
		String exception=request.getParameter("exception");
		String device_os=request.getParameter("device_os");
		
		ServerLog.getLogger().warn("app_type:"+app_type+"-version_no:"+version_no+
				"-version_name:"+version_name+"-device_name:"+device_name+"-exception:"+exception+
				"-device_os:"+device_os);
		
		Date date = new Date();
		AppException ex=new AppException();
		ex.setAppType(app_type);
		ex.setCreateTime(date);
		ex.setDeviceName(device_name);
		if(version_no!=null && NumberUtil.isInteger(version_no)) {
			ex.setVersionNo(Integer.valueOf(version_no));
		}
		ex.setVersionName(version_name);
		ex.setException(exception);
		ex.setAppType(app_type);
		ex.setRemarks(device_os);
		this.exceptionService.insertExceptionInfo(ex);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.VersionUpdateBean;
import com.arttraining.api.pojo.AppVersion;
import com.arttraining.api.service.impl.AppVersionService;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/version")
public class VersionController {
	@Resource
	private AppVersionService appVersionService;
	
	/***
	 * 通知APP更新版本的接口
	 * 传递的参数:
	 * version_no--版本号
	 * version_name--版本名称
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object update(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String version_no=request.getParameter("version_no");
		String version_name=request.getParameter("version_name");
		
		ServerLog.getLogger().warn("version_no:"+version_no+"-version_name:"+version_name);
		VersionUpdateBean versionReBean = new VersionUpdateBean();
		
		if(version_no==null || version_name==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(version_no.equals("") || version_name.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(version_no)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//版本号ID
			Integer i_version_no=Integer.valueOf(version_no);
			//获取最新的一条版本信息
			AppVersion version=this.appVersionService.getOneVersionInfo();
			if(version!=null) {
				Integer db_version=version.getVersionNo();
				if(db_version>i_version_no) {
					versionReBean.setDescrible(version.getDescrible());
					Date date=version.getCreateTime();
					versionReBean.setUpdate_time(TimeUtil.getTimeByDate(date));
					versionReBean.setVersion_name(version.getVersionName());
					versionReBean.setVersion_url(version.getAppDownUrl());
					versionReBean.setVersion_no(db_version);
					errorCode = "0";
					errorMessage = "ok";
				} else {
					errorCode = "20062";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20062;
				}
			} else {
				errorCode = "20062";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20062;
			}
		}
		versionReBean.setError_code(errorCode);
		versionReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(versionReBean));
		return gson.toJson(versionReBean);
	}
}

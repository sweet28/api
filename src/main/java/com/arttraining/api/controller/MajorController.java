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
import com.arttraining.api.bean.MajorListBean;
import com.arttraining.api.service.impl.MajorService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;

@Controller
@RequestMapping("/major")
public class MajorController {
	@Resource
	private MajorService majorService;
	
	/***
	 * 获取一级专业列表
	 * 
	 */
	@RequestMapping(value = "/list/level_one", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listLevelOne(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//获取一级专业列表
		List<MajorListBean> majorList = this.majorService.getOneLevelMajorByList();
		if(majorList.size()>0) {
			errorCode = "0";
			errorMessage = "ok";
		}
		else {
			majorList = new ArrayList<MajorListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("majors", majorList);
		
		return jsonObject;
	}
	/***
	 * 获取专业列表
	 * 
	 */
//	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
//		String errorCode = "";
//		String errorMessage = "";
//		
//	}

}

package com.arttraining.api.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.service.impl.InformationService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/information")
public class InformationController {
	@Resource
	private InformationService informationService;
	
	/***
	 * 
	 * 获取头条信息列表
	 * 暂未设置传递任何参数,故在此未获取任何参数
	 * 如果数据库尚未获取到任何头条信息,错误码20008 内容为空
	 * 
	 * ***/
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//调用service执行获取头条信息列表
		List<InformationListBean> informationList = this.informationService.getInformationList();
		//判断是否有内容
		if(informationList.size()>0) {
			errorCode="0";
			errorMessage="ok";	
		}
		else {
			errorCode="20007";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("informations", informationList);

		return jsonObject;
	}
	/***
	 * 根据头条ID获取头条信息
	 * 传递参数:头条ID--info_id
	 * 
	 * **/
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";

		InformationShowBean informationShow = new InformationShowBean();
		//获取传递的info_id参数,判断传递的参数是否不存在或者是否为空
		String info_id=request.getParameter("info_id");
		if(info_id==null || info_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(info_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			Integer i_info_id = Integer.valueOf(info_id);
			informationShow = this.informationService.getOneInformation(i_info_id);
			if(informationShow==null) {
				informationShow = new InformationShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				errorCode="0";
				errorMessage="ok";
			}
		}
		informationShow.setError_code(errorCode);
		informationShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(informationShow);
	}
}

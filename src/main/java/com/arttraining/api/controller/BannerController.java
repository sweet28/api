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
import com.arttraining.api.bean.BannerListBean;
import com.arttraining.api.bean.BannerShowBean;
import com.arttraining.api.service.impl.BannerService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/banner")
public class BannerController {
	@Resource
	private BannerService bannerService;
	
	/**
	 * 获取轮播列表
	 * 
	 * **/
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//调用service执行获取轮播信息列表
		List<BannerListBean> bannerList = this.bannerService.getBannerList();
		//判断是否有内容
		if(bannerList.size()>0) {
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
		jsonObject.put("banners", bannerList);
		
		return jsonObject;
	}
	
	/**
	 * 根据广告ID获取轮播详情信息
	 * 传递的参数:ad_id--广告ID
	 * 
	 * ***/
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";

		BannerShowBean bannerShow = new BannerShowBean();
		//获取传递的ad_id参数,判断传递的参数是否不存在或者是否为空
		String ad_id=request.getParameter("ad_id");
		if(ad_id==null || ad_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(ad_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			Integer i_ad_id = Integer.valueOf(ad_id);
			bannerShow = this.bannerService.getOneBanner(i_ad_id);
			if(bannerShow==null) {
				bannerShow = new BannerShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				errorCode="0";
				errorMessage="ok";
			}
		}
		bannerShow.setError_code(errorCode);
		bannerShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(bannerShow);
	}
}

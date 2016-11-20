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
import com.arttraining.api.bean.CityListBean;
import com.arttraining.api.bean.CityListReBean;
import com.arttraining.api.bean.CitySortListBean;
import com.arttraining.api.bean.ProvinceListBean;
import com.arttraining.api.bean.ProvinceListReBean;
import com.arttraining.api.service.impl.CityService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/common")
public class CityController {
	@Resource
	private CityService cityService;
	/***
	 * 根据省份获取城市列表
	 * 传递的参数:province--不是必选参数
	 * 
	 */
	@RequestMapping(value = "/get_city/by_province", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getCityByProvince(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下不是必选参数
		String province = request.getParameter("province");
		ServerLog.getLogger().warn("province:"+province);
		
		CityListReBean cityReBean = new CityListReBean();
		
		if(province==null || province.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else {
			//依据省份查询城市列表信息
			List<CityListBean> cityList = this.cityService.getCityListByProvince(province);
			if(cityList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";
				cityReBean.setCitys(cityList);
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		cityReBean.setError_code(errorCode);
		cityReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(cityReBean));
		return gson.toJson(cityReBean);
	}
	/***
	 * 获取省份列表
	 * 
	 */
	@RequestMapping(value = "/get_province", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getProvince(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		ProvinceListReBean provinceReBean = new ProvinceListReBean();
		
		//获取省份列表
		List<ProvinceListBean> provinceList = this.cityService.getProvinceList();
		if(provinceList.size()>0) {
			errorCode = "0";
			errorMessage = "ok";
			provinceReBean.setProvince(provinceList);
		}
		else {
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		provinceReBean.setError_code(errorCode);
		provinceReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(provinceReBean));
		return gson.toJson(provinceReBean);
	}
	
	/****
	 * 获取城市列表
	 * 
	 */
	@RequestMapping(value = "/get_city", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getCity(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String province = request.getParameter("province");
		ServerLog.getLogger().warn("province:"+province);
		//获取城市列表信息
		List<CitySortListBean> cityList = this.cityService.getCityListBySort(province);
		if(cityList.size()>0) {
			errorCode = "0";
			errorMessage = "ok";
		}
		else {
			cityList = new ArrayList<CitySortListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("citys", cityList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

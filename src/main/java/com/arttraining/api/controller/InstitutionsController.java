package com.arttraining.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.InstitutionConditionBean;
import com.arttraining.api.bean.InstitutionConditionReBean;
import com.arttraining.api.bean.InstitutionsListBean;
import com.arttraining.api.bean.InstitutionsShowBean;
import com.arttraining.api.bean.InstitutionsShowReBean;
import com.arttraining.api.service.impl.InstitutionService;
import com.arttraining.api.service.impl.InstitutionsConditionService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/institutions")
public class InstitutionsController {
	@Resource
	private InstitutionService institutionService;
	@Resource
	private InstitutionsConditionService institutionsConditionService;
	
	/***
	 * 获取院校地域、类型条件列表
	 * 
	 */
	@RequestMapping(value = "/conditions", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object conditions(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		InstitutionConditionReBean institutionReBean = new InstitutionConditionReBean();
		//获取院校地域、类型条件列表 
		List<InstitutionConditionBean> conditionList = this.institutionsConditionService.getConditionList();
		if(conditionList.size()>0) {
			institutionReBean.setConditions(conditionList);
			errorCode = "0";
			errorMessage = "ok";
		} else {
			institutionReBean= new InstitutionConditionReBean();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		institutionReBean.setError_code(errorCode);
		institutionReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(institutionReBean));
		return gson.toJson(institutionReBean);
	}
	/***
	 * 获取院校信息列表
	 * city_name--城市 provinces_name--省份
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下2个参数不是必选参数
		String condition_type=request.getParameter("condition_type");
		String condition_name=request.getParameter("condition_name");
		
		//String city_name=request.getParameter("city_name");
		//String provinces_name=request.getParameter("provinces_name");
		//ServerLog.getLogger().warn("city_name:"+city_name+"-provinces_name:"+provinces_name);
		ServerLog.getLogger().warn("condition_type:"+condition_type+"-condition_name:"+condition_name);
		
		//获取院校信息列表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", condition_type);
		map.put("name", condition_name);
		
		List<InstitutionsListBean> institutionsList = this.institutionService.getInstitutionsList(map);
		if(institutionsList.size()>0) {
			errorCode = "0";
			errorMessage = "ok";
		}
		else {
			institutionsList = new ArrayList<InstitutionsListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("institutions", institutionsList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/***
	 * 根据院校ID获取院校详情信息
	 * 传递的参数:institution_id--院校ID
	 * 
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String institution_id = request.getParameter("institution_id");
		ServerLog.getLogger().warn("institution_id:"+institution_id);
		
		InstitutionsShowReBean showReBean = new InstitutionsShowReBean();
		
		if(institution_id==null || institution_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(institution_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//院校ID
			Integer i_institution_id=Integer.valueOf(institution_id);
			InstitutionsShowBean institution=this.institutionService.getInstitutionsShow(i_institution_id);
			if(institution==null) {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				showReBean.setInstitutions(institution);
				errorCode = "0";
				errorMessage = "ok";
			}
		}
		showReBean.setError_code(errorCode);
		showReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(showReBean));
		return gson.toJson(showReBean);
	}

}

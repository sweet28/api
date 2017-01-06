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
import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.pojo.Information;
import com.arttraining.api.service.impl.InformationService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/information_v2")
public class InformationControllerV2 {
	@Resource
	private InformationService informationService;
	
	/***
	 * 
	 * 获取头条信息列表
	 * 暂未设置传递任何参数,故在此未获取任何参数
	 * 如果数据库尚未获取到任何头条信息,错误码20007 内容为空
	 * 
	 * ***/
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是传递的参数
		String type=request.getParameter("type");
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("type:"+type+"-self:"+self);
		Integer offset=-1;
		Integer limit=ConfigUtil.INFO_PAGESIZE;
		
		List<InformationListBean> informationList = new ArrayList<InformationListBean>();
		//如果未传递type
		if(self==null || self.equals("")) {
			offset=-1;
		}
		else if(!NumberUtil.isInteger(self)) {
			offset=-10;
		}
		else
			offset=Integer.valueOf(self);
					
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("type", type);
			map.put("offset", offset);
			map.put("limit", limit);
			//调用service执行获取头条信息列表
			informationList = this.informationService.getInformationListByType(map);
			//判断是否有内容
			if(informationList.size()>0) {
				errorCode="0";
				errorMessage="ok";	
			}
			else {
				informationList = new ArrayList<InformationListBean>();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("informations", informationList);
		ServerLog.getLogger().warn(jsonObject.toString());
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
		ServerLog.getLogger().warn("info_id:"+info_id);
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
				//查看某一个浏览量时 需要随机生成一个浏览量
				Information upd_info=new Information();
				upd_info.setId(i_info_id);
				upd_info.setBrowseNum(Random.randomCommonInt());
				this.informationService.updateOneInformation(upd_info);
				//end
				errorCode="0";
				errorMessage="ok";
			}
		}
		informationShow.setError_code(errorCode);
		informationShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(informationShow));
		return gson.toJson(informationShow);
	}
}

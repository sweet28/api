package com.arttraining.api.controller;

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

import com.arttraining.api.bean.HelpListBean;
import com.arttraining.api.bean.HelpListReBean;
import com.arttraining.api.bean.HelpShowBean;
import com.arttraining.api.service.impl.HelpService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/help")
public class HelpController {
	@Resource
	private HelpService helpService;
	
	/***
	 * 获取帮助信息列表
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		HelpListReBean helpReBean = new HelpListReBean();
		
		//以下是分页参数
		String self = request.getParameter("self");
	
		ServerLog.getLogger().warn("self:"+self);
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		if(self==null || self.equals("")) {
			 offset = -1;
		}
		else if(!NumberUtil.isInteger(self)) {
			offset = -10;
		}
		else
			offset = Integer.valueOf(self);
		
		if(offset == -10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("offset", offset);
			map.put("limit", limit);
			
			List<HelpListBean> helpList = this.helpService.getHelpList(map);
			if(helpList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";
				helpReBean.setHelps(helpList);
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}	
		}
		helpReBean.setError_code(errorCode);
		helpReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(helpReBean));
		return gson.toJson(helpReBean);
	}
	/***
	 * 根据ID获取帮助信息
	 * 传递的参数:help_id--帮助ID
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		HelpShowBean helpShow = new HelpShowBean();
		
		//以下是必选参数
		String help_id = request.getParameter("help_id");
		
		ServerLog.getLogger().warn("help_id:"+help_id);
		
		if(help_id==null || help_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}	
		else if(!NumberUtil.isInteger(help_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//帮助ID
			Integer i_help_id = Integer.valueOf(help_id);
			helpShow = this.helpService.getHelpShow(i_help_id);
			if(helpShow==null) {
				helpShow = new HelpShowBean();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				errorCode = "0";
				errorMessage = "ok";
			}
		}
		helpShow.setError_code(errorCode);
		helpShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(helpShow));
		return gson.toJson(helpShow);
	}
	
}

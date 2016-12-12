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
import com.arttraining.api.bean.MasterInfoListBean;
import com.arttraining.api.bean.MasterInfoShowBean;
import com.arttraining.api.service.impl.InformationForTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/info")
public class MasterInfoController {
	@Resource
	private InformationForTecService informationForTecService;
	/***
	 * 名师端资讯详情接口
	 */
	@RequestMapping(value = "/master/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterShow(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String info_id=request.getParameter("info_id");
		ServerLog.getLogger().warn("info_id"+info_id);
		
		MasterInfoShowBean infoBean=new MasterInfoShowBean();
		
		if(info_id==null || info_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(info_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//资讯ID
			Integer i_info_id=Integer.valueOf(info_id);
			infoBean=this.informationForTecService.getInfoShowByMaster(i_info_id);
			if(infoBean!=null) {
				errorCode="0";
				errorMessage="ok";
			} else {
				infoBean=new MasterInfoShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		infoBean.setError_code(errorCode);
		infoBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(infoBean));
		return gson.toJson(infoBean);
	}
	/***
	 * 名师端资讯列表接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/master/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<MasterInfoListBean> infos=new ArrayList<MasterInfoListBean>();
		//以下不是必选参数
		String self=request.getParameter("self");
		String list_num=request.getParameter("list_num");
		
		ServerLog.getLogger().warn("self:"+self+"-list_num:"+list_num);
		
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		Integer num=0;
		if(self==null) {
			offset=-1;
		} else if(!NumberUtil.isInteger(self)) {
			offset=-10;
		} else {
			if(list_num!=null && NumberUtil.isInteger(list_num)) {
				num=Integer.valueOf(list_num);
				if(num<limit) {
					limit=num;
				}
			} 
			offset=Integer.valueOf(self);
		}
		
		if(offset==-10) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//依据传递的页码和分页时当前位置ID来获取名师端咨询列表信息
			Map<String, Object> map= new HashMap<String, Object>();
			map.put("offset", offset);
			map.put("limit", limit);
			infos = this.informationForTecService.getInfoListByMaster(map);
			if(infos.size()>0) {
				errorCode="0";
				errorMessage="ok";
			} else {
				infos=new ArrayList<MasterInfoListBean>();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("infos", infos);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

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
import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.bean.TecherShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.UserOrgService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/techer")
public class TecherController {
	@Resource
	private UserTecService userTecService;
	@Resource
	private UserOrgService userOrgService;
	
	/**
	 * 获取名师列表
	 * 默认显示10条记录
	 * 传递的参数是当前位置的ID
	 * self
	 * hot--热度 college--院校 spec--专业 city--城市
	 * 
	 * **/
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String self=request.getParameter("self");
		String college=request.getParameter("college");
		String spec=request.getParameter("spec");
		String city=request.getParameter("city");
		
		List<TecherListBean> teacherList = new ArrayList<TecherListBean>();
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		//self--当前位置ID 在这里指的是名师ID 
		if(self==null || self.equals("")) {
			offset=-1;
		}
		else //如果传递的self不是数值字符串  返回-10
		if(!NumberUtil.isInteger(self)){
			offset=-10;
		}
		else {
			offset=Integer.valueOf(self);
		}
		
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			teacherList = this.userTecService.getTecherListBySelective(spec, city, college, offset, limit);
			if(teacherList.size()>0) {
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;	
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("tec", teacherList);
		
		return jsonObject;
	}
	/**
	 * 首页的获取名师列表接口，每次只返回两个名师
	 * 传递的参数:
	 * self--当前位置ID(类似于页码--必选参数)
	 * 
	 * **/
	@RequestMapping(value = "/list/index", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listIndex(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<TecherListBean> teacherList = new ArrayList<TecherListBean>();
		
		String self=request.getParameter("self");
		Integer offset=-1;
		Integer limit = ConfigUtil.HOMEPAGE_PAGESIZE;
		if(self==null || self.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else //如果传递的self不是数值字符串  返回-10
		if(!NumberUtil.isInteger(self)){
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			offset=(Integer.valueOf(self)-1)*limit;
			teacherList = this.userTecService.getTecherListIndexBySelective(offset,limit);
			if(teacherList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";	
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;	
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("tec", teacherList);
		
		return jsonObject;
	}
	/**
	 * 根据名师ID获取名师信息
	 * 传递的参数:
	 * tec_id--名师ID
	 * 
	 * **/
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		TecherShowBean tecShow = new TecherShowBean();
		
		String tec_id = request.getParameter("tec_id");
		if(tec_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(tec_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(tec_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else {
			Integer i_tec_id = Integer.valueOf(tec_id);
			//依据名师tec_id--名师ID来查询相应的名师信息
			UserTech userTec = this.userTecService.selectOneUserTecById(i_tec_id);
			if(userTec==null) {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;		
			}
			else {
				errorCode = "0";
				errorMessage = "ok";		
				
				//查询到相应的名师详情,判断是否有所属的机构
				Integer i_org_id = userTec.getOrgId();
				//如果所属机构不存在,则无需进行一次机构查询
				TecherShowOrgBean org=null;
				if(i_org_id==null || i_org_id==0) {
					org = new TecherShowOrgBean();
				}
				else {
					org = this.userOrgService.getOneOrgByTecShow(i_org_id);
				}	
				tecShow.setTec_id(userTec.getId());
				tecShow.setName(userTec.getName());
				tecShow.setPic(userTec.getHeadPic());
				tecShow.setComment(userTec.getCommentNum());
				tecShow.setFans_num(userTec.getFansNum());
				tecShow.setAuth(userTec.getAuthentication());
				tecShow.setLike_num(userTec.getLikeNum());
				tecShow.setCity(userTec.getCityName());
				tecShow.setCollege(userTec.getSchoolName());
				tecShow.setAss_pay(userTec.getAssPay());
				tecShow.setTitle(userTec.getTitle());
				tecShow.setSpecialty(userTec.getSpecialtyName());
				tecShow.setIntroduction(userTec.getIntroduction());
				tecShow.setOrg(org);
			}
		}
		tecShow.setError_code(errorCode);
		tecShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(tecShow);
	}

}

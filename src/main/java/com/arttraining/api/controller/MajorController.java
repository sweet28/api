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
import com.arttraining.api.bean.MajorLevelListBean;
import com.arttraining.api.bean.MajorListBean;
import com.arttraining.api.service.impl.MajorService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
@RequestMapping("/major")
public class MajorController {
	@Resource
	private MajorService majorService;
	
	/**
	 * 获取二级专业列表
	 */
	@RequestMapping(value = "/list/level_two", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listLevelTwo(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String father_id=request.getParameter("father_id");
		
		ServerLog.getLogger().warn("father_id:"+father_id);
		List<MajorListBean> majorList = new ArrayList<MajorListBean>();
		if(father_id==null || father_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(father_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//父级专业ID
			Integer i_father_id=Integer.valueOf(father_id);
			majorList= this.majorService.getTwoLevelMajorByList(i_father_id);
			if(majorList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";
			}
			else {
				majorList = new ArrayList<MajorListBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("majors", majorList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
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
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/***
	 * 获取专业列表
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<MajorLevelListBean> majorList = new ArrayList<MajorLevelListBean>();
		
		//先获取一级专业列表所有的ID
		List<Integer> oneLevelList = this.majorService.getAllOneLevelMajor();
		if(oneLevelList.size()>0) {
			for(Integer major_id:oneLevelList) {
				MajorLevelListBean major = this.recursiveMajor(major_id);
				majorList.add(major);
			}
			errorCode = "0";
			errorMessage = "ok";
		}
		else {
			majorList = new ArrayList<MajorLevelListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("majors", majorList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/****
	 * 解析专业列表json
	 * 
	 */
	public MajorLevelListBean recursiveMajor(Integer major_id) {
		//根据major_id获取节点对象(SELECT * from t_yp_major where id=?)
		MajorLevelListBean major = this.majorService.getMajorNodeById(major_id);
		//查询major_id下的所有子节点(SELECT * FROM t_yp_major t WHERE father_id=?)
		List<MajorLevelListBean> childMajorNodes = this.majorService.getMajorNodeByFid(major_id);
		//遍历子节点
		for(MajorLevelListBean child : childMajorNodes){
			MajorLevelListBean n = this.recursiveMajor(child.getMajor_id()); //递归
			major.getSon_majors().add(n);
		}
		 
		return major;
	}
}

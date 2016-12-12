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
import com.arttraining.api.bean.MasterCourseListBean;
import com.arttraining.api.service.impl.CourseService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
@RequestMapping("/course")
public class MasterCourseController {
	@Resource
	private CourseService courseService;
	
	/***
	 * 名师端课程列表接口
	 * 
	 */
	@RequestMapping(value = "/master/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-self:"+self);
		//分页用到的参数
		Integer limit=ConfigUtil.PAGESIZE;
		Integer offset=-1;
		
		List<MasterCourseListBean> courseBean= new ArrayList<MasterCourseListBean>();
		
		if(self==null) {
			offset=-1;
		} else if(!NumberUtil.isInteger(self)) {
			offset=-10;
		} else {
			offset=Integer.valueOf(self);
		}
		
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("offset", offset);
			map.put("limit", limit);
			courseBean=this.courseService.getCourseListByMaster(map);
			if(courseBean.size()>0) {
				errorCode = "0";
				errorMessage = "ok";
			} else {
				courseBean= new ArrayList<MasterCourseListBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("courses", courseBean);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

package com.arttraining.api.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.arttraining.api.bean.ActivityListBean;
import com.arttraining.api.bean.ActivityListReBean;
import com.arttraining.api.bean.ActivityOrgBean;
import com.arttraining.api.bean.ActivityShowBean;
import com.arttraining.api.pojo.Activities;
import com.arttraining.api.service.impl.ActivitiesService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/activities")
public class ActivitiesController {
	@Resource
	private ActivitiesService activitiesService;
	
	/****
	 * 获取活动列表
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是分页参数
		String self = request.getParameter("self");
		
		ServerLog.getLogger().warn("self:"+self);
		ActivityListReBean activity = new ActivityListReBean();
		
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
			List<ActivityListBean>  activityList= this.activitiesService.getActivityList(map);
			if(activityList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";
				activity.setActivities(activityList);
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}	
			
		}
		activity.setError_code(errorCode);
		activity.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(activity));
		return gson.toJson(activity);
	}
	
	/**
	 * 根据活动ID获取活动详情
	 * 传递的参数:
	 * active_id--活动ID
	 * 
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		ActivityShowBean activityShow = new ActivityShowBean();
		
		//以下参数是必选参数
		String active_id = request.getParameter("active_id");
		ServerLog.getLogger().warn("active_id:"+active_id);
		
		if(active_id==null || active_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(active_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//活动ID
			Integer i_active_id=Integer.valueOf(active_id);
			//依据活动ID查询活动详情
			Activities activity = this.activitiesService.getOneActivityByPrimaryKey(i_active_id);
			if(activity==null) {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				//组装主办单位 承办单位 支持单位 赞助单位list
				List<ActivityOrgBean> activ_org = new ArrayList<ActivityOrgBean>();
				ActivityOrgBean host_org = new ActivityOrgBean();
				host_org.setType("主办单位");
				host_org.setName(activity.getHostOrg());
				activ_org.add(host_org);
				
				ActivityOrgBean contractors = new ActivityOrgBean();
				contractors.setType("承办单位");
				contractors.setName(activity.getContractors());
				activ_org.add(contractors);
				
				ActivityOrgBean support_org = new ActivityOrgBean();
				support_org.setType("支持单位");
				support_org.setName(activity.getSupportOrg());
				activ_org.add(support_org);
				
				ActivityOrgBean sponsor_org = new ActivityOrgBean();
				sponsor_org.setType("赞助单位");
				sponsor_org.setName(activity.getSponsorOrg());
				activ_org.add(sponsor_org);
				
				activityShow.setActiv_org(activ_org);
				//组装其他信息
				activityShow.setActiv_id(i_active_id);
				activityShow.setActiv_sta_time(activity.getStartTime());
				activityShow.setActiv_end_time(activity.getEndTime());
				activityShow.setSign_sta_time(activity.getSignUpStaTime());
				activityShow.setSign_end_time(activity.getSignUpEndTime());
				
				Date create_time= activity.getCreateTime();
				activityShow.setCreate_time(TimeUtil.getTimeByDate(create_time));
				activityShow.setAddress(activity.getAddress());
				activityShow.setMoney(activity.getMoney());
				activityShow.setSign_num(activity.getSignUpNum());
				activityShow.setContent(activity.getContent());
				activityShow.setPic(activity.getPic());
				activityShow.setTitle(activity.getTitle());
				
				errorCode = "0";
				errorMessage = "ok";
			}
		}
		activityShow.setError_code(errorCode);
		activityShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(activityShow));
		return gson.toJson(activityShow);
	}

}

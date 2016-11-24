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

import com.arttraining.api.bean.MasterAssessmentBean;
import com.arttraining.api.bean.MasterAssessmentReBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.service.impl.AssessmentService;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/assessments")
public class MasterAssessmentsController {
	@Resource
	private AssessmentService assessmentService;
	@Resource
	private OrdersService ordersService;
	/***
	 * 根据用户ID获取名师待测评列表
	 * 传递的参数:access_token--验证
	 * uid--用户ID self--分页时传递的位置ID
	 */
	@RequestMapping(value = "/list/no", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listNo(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		//以下参数不是必选参数
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-self:"+self);
		//以下用于分页
		Integer limit = ConfigUtil.PAGESIZE;
		Integer offset=-1;
		//返回对象
		MasterAssessmentReBean assessmentReBean = new MasterAssessmentReBean();
		if(access_token==null || uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			if(self==null || self.equals("")) {
				 offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				 offset=-10;
			} else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//根据名师ID获取名师待测评列表--状态为0
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("limit", limit);
				map.put("offset", offset);
				map.put("uid", i_uid);
				List<MasterAssessmentBean> assessmentList = this.assessmentService.getAssessmentNoListByMaster(map);
				if(assessmentList.size()>0) {
					//循环读取测评信息
					for (MasterAssessmentBean assessment : assessmentList) {
						Integer type = 0;
						Integer order_id = assessment.getOrder_id();
						String order_number=assessment.getOrder_number();
						map.put("type", type);
						map.put("order_id", order_id);
						map.put("order_number", order_number);
						//获取作品相关的信息
						OrderWorkBean work = this.ordersService.getWorkInfoByListMy(map);
						if(work!=null) {
							assessment.setWork_id(work.getWork_id());
							assessment.setWork_title(work.getWork_title());
							assessment.setWork_pic(work.getWork_pic());
						}
					}
					assessmentReBean.setAssessments(assessmentList);
					errorCode = "0";
					errorMessage = "ok";
				} else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		assessmentReBean.setError_code(errorCode);
		assessmentReBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(assessmentReBean));
		return gson.toJson(assessmentReBean);
	}
	/***
	 * 根据用户ID获取已测评明细
	 * 传递的参数:access_token--验证
	 * uid--用户ID self--分页时传递的位置ID
	 */
	@RequestMapping(value = "/list/yes", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listYes(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		//以下参数不是必选参数
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-self:"+self);
		//以下用于分页
		Integer limit = ConfigUtil.PAGESIZE;
		Integer offset=-1;
		//返回对象
		MasterAssessmentReBean assessmentReBean = new MasterAssessmentReBean();
		
		if(access_token==null || uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			if(self==null || self.equals("")) {
				 offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				 offset=-10;
			} else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//根据名师ID获取名师待测评列表--状态为1
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("limit", limit);
				map.put("offset", offset);
				map.put("uid", i_uid);
				List<MasterAssessmentBean> assessmentList = this.assessmentService.getAssessmentYesListByMaster(map);
				if(assessmentList.size()>0) {
					//循环读取测评信息
					for (MasterAssessmentBean assessment : assessmentList) {
						Integer type = 0;
						Integer order_id = assessment.getOrder_id();
						String order_number=assessment.getOrder_number();
						map.put("type", type);
						map.put("order_id", order_id);
						map.put("order_number", order_number);
						//获取作品相关的信息
						OrderWorkBean work = this.ordersService.getWorkInfoByListMy(map);
						if(work!=null) {
							assessment.setWork_id(work.getWork_id());
							assessment.setWork_title(work.getWork_title());
							assessment.setWork_pic(work.getWork_pic());
						}
					}
					assessmentReBean.setAssessments(assessmentList);
					errorCode = "0";
					errorMessage = "ok";
				} else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		assessmentReBean.setError_code(errorCode);
		assessmentReBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(assessmentReBean));
		return gson.toJson(assessmentReBean);
	}
}

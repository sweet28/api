package com.arttraining.api.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.pojo.Recommend;
import com.arttraining.api.service.impl.RecommendService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;

@Controller
@RequestMapping("/recommend")
public class RecommendController {
	@Resource
	private RecommendService recommendService;
	/**
	 * 发送建议反馈
	 * 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下4个参数是不可选参数
		String uid = request.getParameter("uid");
		String utype =request.getParameter("utype");
		String pic=request.getParameter("pic");
		String phone=request.getParameter("phone");
		//以下1个参数是必选参数
		String content=request.getParameter("content");
		
		ServerLog.getLogger().warn("uid:"+uid+"-utype:"+utype+"-pic:"+pic+"-phone:"+phone+
				"-content:"+content);
		
		//用户ID
		Integer i_uid=0;
		if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
			i_uid=0;
		}
		else
			i_uid=Integer.valueOf(uid);
		
		//发送建议反馈信息
		Date date = new Date();
		String time = TimeUtil.getTimeByDate(date);
		
		Recommend recommend = new Recommend();
		recommend.setContent(content);
		recommend.setCreateTime(Timestamp.valueOf(time));
		recommend.setRecPersonId(i_uid);
		recommend.setRecPersonType(utype);
		recommend.setRemarks(pic);
		recommend.setContactWay(phone);
		recommend.setOrderCode(time);
		
		try {
			this.recommendService.insertOneRecommend(recommend);
			errorCode = "0";
			errorMessage = "ok";
		} catch (Exception e) {
			errorCode = "20051";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20051;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
	}

}

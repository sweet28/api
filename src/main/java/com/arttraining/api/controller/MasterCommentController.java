package com.arttraining.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksTecComment;
import com.arttraining.api.service.impl.WorksTecCommentService;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/tech_comments")
public class MasterCommentController {
	@Resource
	private WorksTecCommentService worksTecCommentService;
	
	/****
	 * 获取动态的名师点评列表
	 * 
	 */
//	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
//		String errorCode = "";
//		String errorMessage = "";
//	}
	
	/**
	 * 名师进行点评
	 * 传递的参数:access_token--验证
	 * uid--用户ID
	 * tec_id--名师ID
	 * work_id--作品ID
	 * content--内容
	 * content_type--类型
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String tec_id=request.getParameter("tec_id");
		String work_id=request.getParameter("work_id");
		String content_type=request.getParameter("content_type");
		//以下不是必选参数
		String content=request.getParameter("content");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-tec_id:"+tec_id+"-work_id:"+work_id+"-content:"+content+
				"-content_type:"+content_type);
		
		if(access_token==null || uid==null || tec_id==null || work_id==null || content_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") || tec_id.equals("")
				|| work_id.equals("") || content_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(tec_id) || !NumberUtil.isInteger(work_id) 
				|| !NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//名师ID
			Integer i_tec_id=Integer.valueOf(tec_id);
			//爱好者用户ID
			Integer i_uid=Integer.valueOf(uid);
			//作品ID
			Integer i_work_id=Integer.valueOf(work_id);
			
			//首先判断是否重复点评 
			//begin
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("work_id", i_work_id);
			map.put("uid", i_tec_id);
			WorksTecComment comment=this.worksTecCommentService.getTecCommentByMaster(map);
			if(comment!=null) {
				errorCode = "20058";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20058;
			} else {	
				//获取时间
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				//新增名师点评信息
				WorksTecComment tecComment = new WorksTecComment();
				tecComment.setContent(content);
				tecComment.setContentType(content_type);
				tecComment.setCreateTime(Timestamp.valueOf(time));
				tecComment.setForeignKey(i_work_id);
				tecComment.setVisitor(i_tec_id);
				tecComment.setVisitorType("tec");
				tecComment.setHost(i_uid);
				tecComment.setHostType("stu");
				tecComment.setOrderCode(time);
				tecComment.setType("comment");
				
				//更新作品表中的名师点评数
				Works works=new Works();
				works.setId(i_work_id);
				works.setTecCommentNum(1);
				
				try {
					this.worksTecCommentService.insertTecCommentAndUpdateNum(tecComment, works);
					errorCode = "0";
					errorMessage = "ok";	
				} catch (Exception e) {
					errorCode = "20057";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20057;	
				}
			}
		}
		SimpleReBean reBean = new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}
	/**
	 * 对名师点评进行追问回复
	 * 传递的参数:access_token--验证
	 * uid--用户ID
	 * tec_id--名师ID
	 * work_id--作品ID
	 * content--内容
	 * content_type--类型
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object reply(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String tec_id=request.getParameter("tec_id");
		String work_id=request.getParameter("work_id");
		String content=request.getParameter("content");
		String content_type=request.getParameter("content_type");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-tec_id:"+tec_id+"-work_id:"+work_id+"-content:"+content+
				"-content_type:"+content_type);
		
		if(access_token==null || uid==null || tec_id==null || work_id==null || content==null || content_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") || tec_id.equals("")
				|| work_id.equals("") || content.equals("") || content_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(tec_id) || !NumberUtil.isInteger(work_id) 
				|| !NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//名师ID
			Integer i_tec_id=Integer.valueOf(tec_id);
			//爱好者用户ID
			Integer i_uid=Integer.valueOf(uid);
			//作品ID
			Integer i_work_id=Integer.valueOf(work_id);
			
			//首先判断是否重复回复点评 begin
			
			//end		
			
			//获取时间
			Date date = new Date();
			String time = TimeUtil.getTimeByDate(date);
			//新增名师点评信息
			WorksTecComment tecComment = new WorksTecComment();
			tecComment.setContent(content);
			tecComment.setContentType(content_type);
			tecComment.setCreateTime(Timestamp.valueOf(time));
			tecComment.setForeignKey(i_work_id);
			tecComment.setVisitor(i_tec_id);
			tecComment.setVisitorType("tec");
			tecComment.setHost(i_uid);
			tecComment.setHostType("stu");
			tecComment.setOrderCode(time);
			tecComment.setType("comment");
			
			//名师回复时 不进行更新作品点评数
			try {
				this.worksTecCommentService.insertOneTecComment(tecComment);
				errorCode = "0";
				errorMessage = "ok";	
			} catch (Exception e) {
				errorCode = "20057";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20057;	
			}
		}
		SimpleReBean reBean = new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}
}

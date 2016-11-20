package com.arttraining.api.controller;

import java.sql.Timestamp;
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

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.CommentStatusListBean;
import com.arttraining.api.bean.CommentsBean;
import com.arttraining.api.bean.CommentsHostBean;
import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSComment;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesComment;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksComment;
import com.arttraining.api.service.impl.BBSCommentService;
import com.arttraining.api.service.impl.BBSService;
import com.arttraining.api.service.impl.StatusCommentService;
import com.arttraining.api.service.impl.StatusesService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksCommentService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/comments")
public class CommentController {
	@Resource
	private BBSCommentService bbsCommentService;
	@Resource
	private UserStuService userStuService;
	@Resource
	private BBSService bbsSerivce;
	@Resource
	private StatusCommentService statusCommentService;
	@Resource
	private StatusesService statusService;
	@Resource
	private WorksService workService;
	@Resource
	private WorksCommentService workCommentService;
	
	/***
	 * 获取首页帖子动态的评论列表
	 * 传递的参数
	 * uid--用户ID  utype--用户类型 status_id--动态ID
	 * 
	 */
	@RequestMapping(value = "/list/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下1个参数是必选参数
		String status_id = request.getParameter("status_id");
		//以下不是必选参数
		String self = request.getParameter("self");
		
		//返回的bean结果
		CommentStatusListBean commentBBS = new CommentStatusListBean();
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-self:"+self);
		
		Integer offset = -1;
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(status_id==null || status_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset = -1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset = -10;
			}
			else
				offset = Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode="20033";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
				//动态ID
				Integer i_status_id = Integer.valueOf(status_id);
				
				//1.查询评论信息
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("fid", i_status_id); 
			    map.put("offset", offset);
			    map.put("limit", limit);
			    
				List<CommentsVisitorBean> bbsCommentList = this.bbsCommentService.getBBSCommentByList(map);
				//2.组装评论信息
				List<CommentsBean> commentList = new ArrayList<CommentsBean>();
				//组装其他人评论列表
				for(CommentsVisitorBean bbsComment:bbsCommentList) {
					CommentsBean comment = new CommentsBean();
					String comm_type = bbsComment.getComm_type();
					Integer host_id = bbsComment.getHost_id();
			
					comment.setCity(bbsComment.getCity());
					comment.setComm_type(comm_type);
					comment.setComment_id(bbsComment.getComment_id());
					comment.setContent(bbsComment.getContent());
					comment.setIdentity(bbsComment.getIdentity());
					comment.setName(bbsComment.getName());
					comment.setTime(bbsComment.getTime());
					comment.setUser_id(bbsComment.getUser_id());
					comment.setUser_type(bbsComment.getUser_type());
					comment.setUser_pic(bbsComment.getUser_pic());
					
					CommentsHostBean hb = new CommentsHostBean();
					if(comm_type.equals("reply")) {
						String name = this.userStuService.getUserNameById(host_id);
						hb.setName(name);
						hb.setUser_id(host_id);
						hb.setUser_type(bbsComment.getHost_type());
					}
					comment.setReply(hb);
					commentList.add(comment);
				}
				commentBBS.setComment_num(commentList.size());
				commentBBS.setComments(commentList);
				errorCode="0";
				errorMessage="ok";
			}
		}
		commentBBS.setError_code(errorCode);
		commentBBS.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(commentBBS));
		return gson.toJson(commentBBS);
	}
	/****
	 * 根据用户ID创建一条首页帖子的评论
	 * 传递的参数:	access_token--验证 uid--用户ID utype--用户类型
	 * status_id--动态ID content--评论内容
	 * 
	 */
	@RequestMapping(value = "/create/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下5个参数是必选参数
		String access_token=request.getParameter("access_token");
		String status_id = request.getParameter("status_id");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String content = request.getParameter("content");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-status_id:"+status_id+"-uid:"+uid+"-utype:"+utype+"-content:"+content);
		
		if(access_token==null || status_id==null || uid==null || utype==null || content==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || status_id.equals("") 
				|| uid.equals("") || utype.equals("") || content.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID和动态ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//首先依据帖子ID 获取首页帖子相关信息
				BBS bbs = this.bbsSerivce.getBBSById(i_status_id);
				Integer owner=0;
				String owner_type="";
				if(bbs!=null) {
					owner=bbs.getOwner();
					owner_type=bbs.getOwnerType();
				}
				//新增帖子评论信息
				BBSComment bbsComment = new BBSComment();
				bbsComment.setContent(content);
				bbsComment.setCreateTime(Timestamp.valueOf(time));
				bbsComment.setForeignKey(i_status_id);
				bbsComment.setHost(owner);
				bbsComment.setHostType(owner_type);
				bbsComment.setType("comment");
				bbsComment.setVisitor(i_uid);
				bbsComment.setVisitorType(utype);
				bbsComment.setOrderCode(time);
				
				//更新爱好者用户表中的评论数(这里的评论数指的是被评论数)
				UserStu user = null;
				if(owner_type!=null && owner_type.equals("stu")) {
					user=new UserStu();
					user.setId(owner);
					user.setCommentNum(1);
				}
				
				try {
					this.bbsCommentService.insertAndUpdateBBSComment(bbsComment, i_status_id, user);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					errorCode="20050";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20050;
				}
			}
			else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
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
	/***
	 * 回复一条首页帖子的评论
	 * access_token--验证 uid--用户ID
	 * utype--用户类型 comm_u_id--评论人ID
	 * comm_u_type--评论人类型
	 * status_id--动态类型  content--内容
	 * 
	 */
	@RequestMapping(value = "/reply/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object replyBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下7个参数是必选参数
		String access_token =request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String comm_u_id=request.getParameter("comm_u_id");
		String comm_u_type=request.getParameter("comm_u_type");
		String status_id=request.getParameter("status_id");
		String content=request.getParameter("content");
	
		ServerLog.getLogger().warn("access_token:"+access_token+"-status_id:"+status_id+"-uid:"+uid+"-utype:"+utype+"-content:"+content+
				"-comm_u_id:"+comm_u_id+"-comm_u_type:"+comm_u_type);
		
		if(access_token==null || uid==null || utype==null || comm_u_id==null || 
				comm_u_type==null || status_id==null || content==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| comm_u_id.equals("") || comm_u_type.equals("")
				|| status_id.equals("") || content.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id) 
				|| !NumberUtil.isInteger(comm_u_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID和动态ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				//评论人ID
				Integer i_comm_uid=Integer.valueOf(comm_u_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//新增帖子评论信息
				BBSComment bbsComment = new BBSComment();
				bbsComment.setContent(content);
				bbsComment.setCreateTime(Timestamp.valueOf(time));
				bbsComment.setForeignKey(i_status_id);
				bbsComment.setHost(i_comm_uid);
				bbsComment.setHostType(comm_u_type);
				bbsComment.setType("reply");
				bbsComment.setVisitor(i_uid);
				bbsComment.setVisitorType(utype);
				bbsComment.setOrderCode(time);
				
				//更新爱好者用户表中的评论数(这里的评论数指的是被评论数)
				UserStu user = null;
				if(comm_u_type!=null && comm_u_type.equals("stu")) {
					user=new UserStu();
					user.setId(i_comm_uid);
					user.setCommentNum(1);
				}
				
				try {
					this.bbsCommentService.insertAndUpdateBBSComment(bbsComment, i_status_id,user);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					errorCode="20050";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20050;
				}		
			}else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
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
	/***
	 * 获取小组动态的评论列表
	 * uid--用户ID  utype--用户类型 status_id--动态ID
	 */
	@RequestMapping(value = "/list/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下1个参数是必选参数
		String status_id = request.getParameter("status_id");
		//以下不是必选参数
		String self = request.getParameter("self");
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-self:"+self);
		
		//返回的bean结果
		CommentStatusListBean commentStatus = new CommentStatusListBean();
	
		Integer offset = -1;
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(status_id==null || status_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset = -1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset = -10;
			}
			else
				offset = Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode="20033";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
				//动态ID
				Integer i_status_id = Integer.valueOf(status_id);
				
				//1.查询评论信息
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("fid", i_status_id); 
			    map.put("offset", offset);
			    map.put("limit", limit);
			    
				List<CommentsVisitorBean> statusCommentList = this.statusCommentService.getStatusCommentByList(map);
				//2.组装评论信息
				List<CommentsBean> commentList = new ArrayList<CommentsBean>();
				//组装其他人评论列表
				for(CommentsVisitorBean statusComment:statusCommentList) {
					CommentsBean comment = new CommentsBean();
					String comm_type = statusComment.getComm_type();
					Integer host_id = statusComment.getHost_id();
			
					comment.setCity(statusComment.getCity());
					comment.setComm_type(comm_type);
					comment.setComment_id(statusComment.getComment_id());
					comment.setContent(statusComment.getContent());
					comment.setIdentity(statusComment.getIdentity());
					comment.setName(statusComment.getName());
					comment.setTime(statusComment.getTime());
					comment.setUser_id(statusComment.getUser_id());
					comment.setUser_type(statusComment.getUser_type());
					comment.setUser_pic(statusComment.getUser_pic());
					
					CommentsHostBean hb = new CommentsHostBean();
					if(comm_type.equals("reply")) {
						String name = this.userStuService.getUserNameById(host_id);
						hb.setName(name);
						hb.setUser_id(host_id);
						hb.setUser_type(statusComment.getHost_type());
					}
					comment.setReply(hb);
					commentList.add(comment);
				}
				commentStatus.setComment_num(commentList.size());
				commentStatus.setComments(commentList);
				errorCode="0";
				errorMessage="ok";
			}
		}
		commentStatus.setError_code(errorCode);
		commentStatus.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(commentStatus));
		return gson.toJson(commentStatus);
	}
	/***
	 * 根据用户ID创建一条小组动态的评论
	 * 传递的参数:	access_token--验证 uid--用户ID utype--用户类型
	 * status_id--动态ID content--评论内容
	 */
	@RequestMapping(value = "/create/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下5个参数是必选参数
		String access_token=request.getParameter("access_token");
		String status_id = request.getParameter("status_id");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String content = request.getParameter("content");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-status_id:"+status_id+"-uid:"+uid+"-utype:"+utype+"-content:"+content);
		
		if(access_token==null || status_id==null || uid==null || utype==null || content==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || status_id.equals("") 
				|| uid.equals("") || utype.equals("") || content.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID和动态ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//首先依据小组动态ID 获取小组动态相关信息
				Statuses status = this.statusService.getStatusesById(i_status_id);
				Integer owner=0;
				String owner_type="";
				if(status!=null) {
					owner=status.getOwner();
					owner_type=status.getOwnerType();
				}
				//新增小组动态评论信息
				StatusesComment statusComment = new StatusesComment();
				statusComment.setContent(content);
				statusComment.setCreateTime(Timestamp.valueOf(time));
				statusComment.setForeignKey(i_status_id);
				statusComment.setHost(owner);
				statusComment.setHostType(owner_type);
				statusComment.setType("comment");
				statusComment.setVisitor(i_uid);
				statusComment.setVisitorType(utype);
				statusComment.setOrderCode(time);
				
				//更新爱好者用户表中的评论数(这里的评论数指的是被评论数)
				UserStu user = null;
				if(owner_type!=null && owner_type.equals("stu")) {
					user=new UserStu();
					user.setId(owner);
					user.setCommentNum(1);
				}
				
				try {
					this.statusCommentService.insertAndUpdateStatusComment(statusComment, i_status_id,user);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					errorCode="20050";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20050;
				}
			}
			else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		
		return jsonObject;
	}
	/***
	 * 回复一条小组动态的评论
	 * access_token--验证 uid--用户ID
	 * utype--用户类型 comm_u_id--评论人ID
	 * comm_u_type--评论人类型
	 * status_id--动态类型  content--内容
	 * 
	 */
	@RequestMapping(value = "/reply/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object replyGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下7个参数是必选参数
		String access_token =request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String comm_u_id=request.getParameter("comm_u_id");
		String comm_u_type=request.getParameter("comm_u_type");
		String status_id=request.getParameter("status_id");
		String content=request.getParameter("content");
	
		ServerLog.getLogger().warn("access_token:"+access_token+"-status_id:"+status_id+"-uid:"+uid+"-utype:"+utype+"-content:"+content+
				"-comm_u_id:"+comm_u_id+"-comm_u_type:"+comm_u_type);
		
		if(access_token==null || uid==null || utype==null || comm_u_id==null || 
				comm_u_type==null || status_id==null || content==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| comm_u_id.equals("") || comm_u_type.equals("")
				|| status_id.equals("") || content.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id) 
				|| !NumberUtil.isInteger(comm_u_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID和动态ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				//评论人ID
				Integer i_comm_uid=Integer.valueOf(comm_u_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//新增小组动态评论信息
				StatusesComment statusComment = new StatusesComment();
				statusComment.setContent(content);
				statusComment.setCreateTime(Timestamp.valueOf(time));
				statusComment.setForeignKey(i_status_id);
				statusComment.setHost(i_comm_uid);
				statusComment.setHostType(comm_u_type);
				statusComment.setType("reply");
				statusComment.setVisitor(i_uid);
				statusComment.setVisitorType(utype);
				statusComment.setOrderCode(time);
				
				//更新爱好者用户表中的评论数(这里的评论数指的是被评论数)
				UserStu user = null;
				if(comm_u_type!=null && comm_u_type.equals("stu")) {
					user=new UserStu();
					user.setId(i_comm_uid);
					user.setCommentNum(1);
				}
				
				try {
					this.statusCommentService.insertAndUpdateStatusComment(statusComment, i_status_id,user);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					errorCode="20050";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20050;
				}		
			}else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
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
	/***
	 * 获取作品的评论列表
	 * 传递的参数
	 * uid--用户ID  utype--用户类型 status_id--动态ID
	 * 
	 */
	@RequestMapping(value = "/list/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下1个参数是必选参数
		String status_id = request.getParameter("status_id");
		//以下不是必选参数
		String self = request.getParameter("self");
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-self:"+self);
		
		//返回的bean结果
		CommentStatusListBean commentWork = new CommentStatusListBean();
		
		Integer offset = -1;
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(status_id==null || status_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset = -1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset = -10;
			}
			else
				offset = Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode="20033";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
				//动态ID
				Integer i_status_id = Integer.valueOf(status_id);
				
				//1.查询评论信息
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("fid", i_status_id); 
			    map.put("offset", offset);
			    map.put("limit", limit);
			    
				List<CommentsVisitorBean> workCommentList = this.workCommentService.getWorkCommentByList(map);
				//2.组装评论信息
				List<CommentsBean> commentList = new ArrayList<CommentsBean>();
				//组装其他人评论列表
				for(CommentsVisitorBean workComment:workCommentList) {
					CommentsBean comment = new CommentsBean();
					String comm_type = workComment.getComm_type();
					Integer host_id = workComment.getHost_id();
			
					comment.setCity(workComment.getCity());
					comment.setComm_type(comm_type);
					comment.setComment_id(workComment.getComment_id());
					comment.setContent(workComment.getContent());
					comment.setIdentity(workComment.getIdentity());
					comment.setName(workComment.getName());
					comment.setTime(workComment.getTime());
					comment.setUser_id(workComment.getUser_id());
					comment.setUser_type(workComment.getUser_type());
					comment.setUser_pic(workComment.getUser_pic());
					
					CommentsHostBean hb = new CommentsHostBean();
					if(comm_type.equals("reply")) {
						String name = this.userStuService.getUserNameById(host_id);
						hb.setName(name);
						hb.setUser_id(host_id);
						hb.setUser_type(workComment.getHost_type());
					}
					comment.setReply(hb);
					commentList.add(comment);
				}
				commentWork.setComment_num(commentList.size());
				commentWork.setComments(commentList);
				errorCode="0";
				errorMessage="ok";
			}
		}
		commentWork.setError_code(errorCode);
		commentWork.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(commentWork));
		return gson.toJson(commentWork);
	}
	/***
	 * 根据用户ID创建一条作品的评论
	 * 传递的参数:	access_token--验证 uid--用户ID utype--用户类型
	 * status_id--动态ID content--评论内容
	 * 
	 */
	@RequestMapping(value = "/create/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下5个参数是必选参数
		String access_token=request.getParameter("access_token");
		String status_id = request.getParameter("status_id");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String content = request.getParameter("content");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-status_id:"+status_id+"-uid:"+uid+"-utype:"+utype+"-content:"+content);
		
		if(access_token==null || status_id==null || uid==null || utype==null || content==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || status_id.equals("") 
				|| uid.equals("") || utype.equals("") || content.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID和动态ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//首先依据帖子ID 获取首页帖子相关信息
				Works work = this.workService.getWorksById(i_status_id);
				Integer owner=0;
				String owner_type="";
				if(work!=null) {
					owner=work.getOwner();
					owner_type=work.getOwnerType();
				}
				//新增作品评论信息
				WorksComment workComment = new WorksComment();
				workComment.setContent(content);
				workComment.setCreateTime(Timestamp.valueOf(time));
				workComment.setForeignKey(i_status_id);
				workComment.setHost(owner);
				workComment.setHostType(owner_type);
				workComment.setType("comment");
				workComment.setVisitor(i_uid);
				workComment.setVisitorType(utype);
				workComment.setOrderCode(time);
				
				//更新爱好者用户表中的评论数(这里的评论数指的是被评论数)
				UserStu user = null;
				if(owner_type!=null && owner_type.equals("stu")) {
					user=new UserStu();
					user.setId(owner);
					user.setCommentNum(1);
				}
				
				try {
					this.workCommentService.insertAndUpdateWorkComment(workComment, i_status_id,user);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					errorCode="20050";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20050;
				}
			}
			else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code", "");
		jsonObject.put("name", "");
		
		return jsonObject;
		
	}
	/***
	 * 回复一条作品的评论
	 * access_token--验证 uid--用户ID
	 * utype--用户类型 comm_u_id--评论人ID
	 * comm_u_type--评论人类型
	 * status_id--动态类型  content--内容
	 * 
	 */
	@RequestMapping(value = "/reply/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object replyWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下7个参数是必选参数
		String access_token =request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String comm_u_id=request.getParameter("comm_u_id");
		String comm_u_type=request.getParameter("comm_u_type");
		String status_id=request.getParameter("status_id");
		String content=request.getParameter("content");
	
		ServerLog.getLogger().warn("access_token:"+access_token+"-status_id:"+status_id+"-uid:"+uid+"-utype:"+utype+"-content:"+content+
				"-comm_u_id:"+comm_u_id+"-comm_u_type:"+comm_u_type);
		
		if(access_token==null || uid==null || utype==null || comm_u_id==null || 
				comm_u_type==null || status_id==null || content==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| comm_u_id.equals("") || comm_u_type.equals("")
				|| status_id.equals("") || content.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id) 
				|| !NumberUtil.isInteger(comm_u_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID和动态ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				//评论人ID
				Integer i_comm_uid=Integer.valueOf(comm_u_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//新增小组动态评论信息
				WorksComment workComment = new WorksComment();
				workComment.setContent(content);
				workComment.setCreateTime(Timestamp.valueOf(time));
				workComment.setForeignKey(i_status_id);
				workComment.setHost(i_comm_uid);
				workComment.setHostType(comm_u_type);
				workComment.setType("reply");
				workComment.setVisitor(i_uid);
				workComment.setVisitorType(utype);
				workComment.setOrderCode(time);
				
				//更新爱好者用户表中的评论数(这里的评论数指的是被评论数)
				UserStu user = null;
				if(comm_u_type!=null && comm_u_type.equals("stu")) {
					user=new UserStu();
					user.setId(i_comm_uid);
					user.setCommentNum(1);
				}
				try {
					this.workCommentService.insertAndUpdateWorkComment(workComment, i_status_id, user);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					errorCode="20050";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20050;
				}		
			}else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
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

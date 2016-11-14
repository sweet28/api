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
import com.arttraining.api.bean.LikeUserBean;
import com.arttraining.api.bean.LikeUserPicBean;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSLike;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesLike;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksLike;
import com.arttraining.api.service.impl.BBSLikeService;
import com.arttraining.api.service.impl.BBSService;
import com.arttraining.api.service.impl.StatusesLikeService;
import com.arttraining.api.service.impl.StatusesService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksLikeService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;

@Controller
@RequestMapping("/like")
public class LikeController {
	@Resource
	private UserStuService userStuService;
	@Resource
	private BBSService bbsService;
	@Resource
	private BBSLikeService bbsLikeService;
	@Resource
	private WorksLikeService worksLikeService;
	@Resource
	private WorksService worksService;
	@Resource
	private StatusesLikeService statusesLikeService;
	@Resource
	private StatusesService statusesService;
	
	/***
	 * 根据ID获取点赞用户头像列表
	 * 传递参数: like_id--被点赞信息id 表(bbs/works/statuses/org/tec) 
	 * 点赞信息id like表
	 * status：动态(首页发的帖子) work：测评作品  org：机构  tec：名师   g_stus：小组动态
	 * 传递self prev next
	 * 
	 * **/
	@RequestMapping(value = "/list/pic/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listPicBBS(HttpServletRequest request, HttpServletResponse response) {
		String like_id="";
		String self="";
		
		String errorCode = "";
		String errorMessage = "";

		//被点赞信息id 即bbs/works/statuses/org/tec
		like_id = request.getParameter("like_id");
		self=request.getParameter("self");
		
		JSONObject jsonObject = new JSONObject();
		List<LikeUserPicBean> bbsLikeList = new ArrayList<LikeUserPicBean>();
		
		if(like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			//被点赞信息id
			Integer i_like_id = Integer.valueOf(like_id);
			Integer offset=-1;
			Integer limit=ConfigUtil.PAGESIZE;
			//依据被点赞信息id查询以下的点赞列表
			//如果尚未传递self 默认显示最新的固定条点赞消息
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else
			if(!NumberUtil.isInteger(self)) {
				offset=-10;
			}
			else {
				offset=Integer.valueOf(self);
			}
			//传递的self不是数值字符串类型
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
			}
			else {
				bbsLikeList = this.userStuService.listBBSLikeUserPicByFid(i_like_id, offset, limit);
				if(bbsLikeList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("users",bbsLikeList);
		
		return jsonObject;
	}
	@RequestMapping(value = "/list/pic/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listPicWork(HttpServletRequest request, HttpServletResponse response) {
		String like_id="";
		String self="";
		
		String errorCode = "";
		String errorMessage = "";

		//被点赞信息id 即bbs/works/statuses/org/tec
		like_id = request.getParameter("like_id");
		self=request.getParameter("self");
		
		JSONObject jsonObject = new JSONObject();
		List<LikeUserPicBean> workLikeList = new ArrayList<LikeUserPicBean>();
		
		if(like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			//被点赞信息id
			Integer i_like_id = Integer.valueOf(like_id);
			Integer offset=-1;
			Integer limit=ConfigUtil.PAGESIZE;
			//依据被点赞信息id查询以下的点赞列表
			//如果尚未传递self 默认显示最新的固定条点赞消息
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
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
				workLikeList = this.userStuService.listWorksLikeUserPicByFid(i_like_id, offset, limit);
				if(workLikeList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("users",workLikeList);
		
		return jsonObject;
	}
	@RequestMapping(value = "/list/pic/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listPicGstus(HttpServletRequest request, HttpServletResponse response) {
		String like_id="";
		String self="";
		
		String errorCode = "";
		String errorMessage = "";

		//被点赞信息id 即bbs/works/statuses/org/tec
		like_id = request.getParameter("like_id");
		self=request.getParameter("self");
		
		JSONObject jsonObject = new JSONObject();
		List<LikeUserPicBean> statusesLikeList = new ArrayList<LikeUserPicBean>();
		
		if(like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			//被点赞信息id
			Integer i_like_id = Integer.valueOf(like_id);
			Integer offset=-1;
			Integer limit=ConfigUtil.PAGESIZE;
			//依据被点赞信息id查询以下的点赞列表
			//如果尚未传递self 默认显示最新的固定条点赞消息
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
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
				statusesLikeList = this.userStuService.listStatusesLikeUserPicByFid(i_like_id, offset, limit);
				if(statusesLikeList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("users",statusesLikeList);
		
		return jsonObject;
	}
	
	/**
	 * 根据ID获取点赞列表
	 * 传递参数: like_id--被点赞信息id 表(bbs/works/statuses/org/tec) 
	 * 点赞信息id like表
	 * status/bbs：动态(首页发的帖子) work：测评作品  org：机构  tec：名师   g_stus：小组动态
	 * 传递self prev next
	 * **/
	@RequestMapping(value = "/list/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listBBS(HttpServletRequest request, HttpServletResponse response) {
		String like_id="";
		String self="";
		
		String errorCode = "";
		String errorMessage = "";

		//被点赞信息id 即bbs/works/statuses/org/tec
		like_id = request.getParameter("like_id");
		self=request.getParameter("self");
		
		JSONObject jsonObject = new JSONObject();
		List<LikeUserBean> bbsLikeList = new ArrayList<LikeUserBean>();
		
		if(like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			//被点赞信息id
			Integer i_like_id = Integer.valueOf(like_id);
			Integer offset=-1;
			Integer limit=ConfigUtil.PAGESIZE;
			//依据被点赞信息id查询以下的点赞列表
			//如果尚未传递self 默认显示最新的固定条点赞消息
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else
			if(!NumberUtil.isInteger(self)) {
				offset=-10;
			}
			else {
				offset=Integer.valueOf(self);
			}
			//如果传递的self==-10 说明传递的分页当前位置ID不是数值字符串
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
			}
			else {
				bbsLikeList = this.userStuService.listBBSLikeUserByFid(i_like_id, offset, limit);
				if(bbsLikeList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("users",bbsLikeList);
		
		return jsonObject;
	}
	@RequestMapping(value = "/list/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listWork(HttpServletRequest request, HttpServletResponse response) {
		String like_id="";
		String self="";
		
		String errorCode = "";
		String errorMessage = "";
		
		//被点赞信息id 即bbs/works/statuses/org/tec
		like_id = request.getParameter("like_id");
		self=request.getParameter("self");
		
		JSONObject jsonObject = new JSONObject();
		List<LikeUserBean> worksLikeList = new ArrayList<LikeUserBean>();
		
		if(like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			//被点赞信息id
			Integer i_like_id =  Integer.valueOf(like_id);
			Integer offset=-1;
			Integer limit=ConfigUtil.PAGESIZE;
			//依据被点赞信息id查询以下的点赞列表
			//如果尚未传递self 默认显示最新的固定条点赞消息
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
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
				worksLikeList = this.userStuService.listWorksLikeUserByFid(i_like_id, offset, limit);
				if(worksLikeList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("users",worksLikeList);
		
		return jsonObject;
	}
	@RequestMapping(value = "/list/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listGstus(HttpServletRequest request, HttpServletResponse response) {
		String like_id="";
		String self="";
		
		String errorCode = "";
		String errorMessage = "";

		//被点赞信息id 即bbs/works/statuses/org/tec
		like_id = request.getParameter("like_id");
		self=request.getParameter("self");
		
		JSONObject jsonObject = new JSONObject();
		List<LikeUserBean> statusesLikeList = new ArrayList<LikeUserBean>();
		
		if(like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			//被点赞信息id
			Integer i_like_id =  Integer.valueOf(like_id);
			Integer offset=-1;
			Integer limit=ConfigUtil.PAGESIZE;
			//依据被点赞信息id查询以下的点赞列表
			//如果尚未传递self 默认显示最新的固定条点赞消息
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
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
				statusesLikeList = this.userStuService.listStatusesLikeUserByFid(i_like_id, offset, limit);
				if(statusesLikeList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("users",statusesLikeList);
		
		return jsonObject;
	}
	
	/**
	 * 添加点赞信息
	 * 传递参数:
	 * access_token--OAuth uid--用户id utype--点赞人的类型 like_id--被点赞信息id
	 * 1103 下午 拆分传递的接口 将点赞类型拆分
	 * status：动态(首页发的帖子) work：测评作品  org：机构  tec：名师   g_stus：小组动态
	 * 
	 * **/
	@RequestMapping(value = "/create/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createBBS(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = "";
		String uid = "";
		String like_id="";
		String utype="";
		
		String errorCode = "";
		String errorMessage = "";
		//以下4个是必选参数
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		like_id = request.getParameter("like_id");
		utype=request.getParameter("utype");
		
		if(accessToken == null || uid == null || utype == null || like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(accessToken.equals("") || uid.equals("") || utype.equals("") || like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(accessToken);
			if (tokenFlag) {
				//首先用户uid是否对当前的帖子id进行点赞了
				Integer i_uid =  Integer.valueOf(uid);
				Integer i_like_id =  Integer.valueOf(like_id);
				BBSLike likeIsExist = this.bbsLikeService.selectBBSLikeByUidAndFid(i_like_id, i_uid);
				//如果当前用户已经对该帖子进行点赞,则进行提醒
				if(likeIsExist!=null) {
					errorCode="20030";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20030;
				}
				else {
					//依据传递的被点赞id获取相应的信息 bbs
					BBS bbs = this.bbsService.getBBSById(i_like_id);
					if(bbs==null) {
						errorCode="20007";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
					else {
						//开始添加点赞信息
						BBSLike bbsLike = new BBSLike();
						bbsLike.setVisitor(i_uid);
						bbsLike.setVisitorType(utype);
						bbsLike.setHost(bbs.getOwner());
						bbsLike.setHostType(bbs.getOwnerType());
						bbsLike.setForeignKey(i_like_id);
						//获取点赞时间
						bbsLike.setCreateTime(TimeUtil.getTimeStamp());
						
						try {
							this.bbsLikeService.insertAndUpdateBBS(bbsLike, i_like_id);
							//添加点赞成功
							errorCode = "0";
							errorMessage = "ok";
							
						} catch (Exception e) {
							errorCode = "20034";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20034;
						}
					}
			  }
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
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
	@RequestMapping(value = "/create/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createWork(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = "";
		String uid = "";
		String like_id="";
		String utype="";
		
		String errorCode = "";
		String errorMessage = "";
		
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		like_id = request.getParameter("like_id");
		utype=request.getParameter("utype");
		
		JSONObject jsonObject = new JSONObject();
		
		if(accessToken == null || uid == null || utype == null || like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(accessToken.equals("") || uid.equals("") || utype.equals("") || like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(accessToken);
			if (tokenFlag) {
				//首先判断用户uid是否对测评作品id进行点赞了
				Integer i_uid =  Integer.valueOf(uid);
				Integer i_like_id =  Integer.valueOf(like_id);
				
				WorksLike workIsExist = this.worksLikeService.selectWorksLikeByUidAndFid(i_like_id, i_uid);
				if(workIsExist!=null) {
					errorCode="20030";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20030;
				}
				else {
				//如果token有效,继续判断类型来执行相应的操作
				//依据传递的被点赞id获取相应的信息
					Works works = this.worksService.getWorksById(i_like_id);
					if(works==null) {
						errorCode="20007";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
					else {
						WorksLike worksLike = new WorksLike();
						worksLike.setVisitor(i_uid);
						worksLike.setVisitorType(utype);
						worksLike.setHost(works.getOwner());
						worksLike.setHostType(works.getOwnerType());
						worksLike.setForeignKey(i_like_id);
						worksLike.setCreateTime(TimeUtil.getTimeStamp());
						
						try {
							this.worksLikeService.insertAndUpdateWork(worksLike, i_like_id);;
							//添加点赞成功
							errorCode = "0";
							errorMessage = "ok";
							
						} catch (Exception e) {
							errorCode = "20034";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20034;
						}
					}
				}
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
			jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
			jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
			jsonObject.put("uid", 0);
			jsonObject.put("user_code", "");
			jsonObject.put("name", "");
			
			return jsonObject;
	}
	
	@RequestMapping(value = "/create/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createGstus(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = "";
		String uid = "";
		String like_id="";
		String utype="";
		
		String errorCode = "";
		String errorMessage = "";
		
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		like_id = request.getParameter("like_id");
		utype=request.getParameter("utype");
		
		JSONObject jsonObject = new JSONObject();
		
		if(accessToken == null || uid == null || utype == null || like_id==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(accessToken.equals("") || uid.equals("") || utype.equals("") || like_id.equals("")){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(like_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		}
		else{
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(accessToken);
			if (tokenFlag) {
				//首先判断用户uid是否对测评作品id进行点赞了
				Integer i_uid = Integer.valueOf(uid);
				Integer i_like_id = Integer.valueOf(like_id);
				
				StatusesLike statusesIsExist = this.statusesLikeService.selectStatusesLikeByUidAndFid(i_like_id, i_uid);
				if(statusesIsExist!=null) {
					errorCode="20030";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20030;
				}
				else {
				//如果token有效,继续判断类型来执行相应的操作
				//依据传递的被点赞id获取相应的信息
					Statuses statuses = this.statusesService.getStatusesById(i_like_id);
					if(statuses==null) {
						errorCode="20007";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
					else {
						StatusesLike statusesLike = new StatusesLike();
						statusesLike.setVisitor(i_uid);
						statusesLike.setVisitorType(utype);
						statusesLike.setHost(statuses.getOwner());
						statusesLike.setHostType(statuses.getOwnerType());
						statusesLike.setForeignKey(i_like_id);
						statusesLike.setCreateTime(TimeUtil.getTimeStamp());
						
						try {
							this.statusesLikeService.insertAndUpdateStatus(statusesLike, i_like_id);
							//添加点赞成功
							errorCode = "0";
							errorMessage = "ok";
							
						} catch (Exception e) {
							errorCode = "20034";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20034;
						}
					}
			  }
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
			jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
			jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
			jsonObject.put("uid",0);
			jsonObject.put("user_code","");
			jsonObject.put("name","");
			
			return jsonObject;
	}
}

package com.arttraining.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.CommentsBean;
import com.arttraining.api.bean.CommentsHostBean;
import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageAdvertiseBean;
import com.arttraining.api.bean.HomePageAttBean;
import com.arttraining.api.bean.HomePageBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.bean.StatusesShowBean;
import com.arttraining.api.bean.WorkCommentDetailBean;
import com.arttraining.api.bean.WorkCommentTecInfoBean;
import com.arttraining.api.bean.WorkShowBean;
import com.arttraining.api.bean.WorkTecCommentBean;
import com.arttraining.api.bean.WorkTecCommentsListBean;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSAttachment;
import com.arttraining.api.pojo.BBSForward;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesAttachment;
import com.arttraining.api.pojo.StatusesForward;
import com.arttraining.api.service.impl.AdvertiseService;
import com.arttraining.api.service.impl.BBSCommentService;
import com.arttraining.api.service.impl.BBSForwardService;
import com.arttraining.api.service.impl.BBSService;
import com.arttraining.api.service.impl.StatusCommentService;
import com.arttraining.api.service.impl.StatusesForwardService;
import com.arttraining.api.service.impl.StatusesService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksCommentService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.api.service.impl.WorksTecCommentService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/statuses")
public class StatusesController {
	@Resource
	private BBSService bbsService;
	@Resource
	private StatusesService statusesService;
	@Resource
	private WorksService worksService;
	@Resource
	private AdvertiseService adService;
	@Resource
	private BBSForwardService bbsForwardService;
	@Resource
	private StatusesForwardService statusForwardService;
	@Resource
	private BBSCommentService bbsCommentService;
	@Resource
	private StatusCommentService statusCommentService;
	@Resource
	private UserStuService userStuService;
	@Resource
	private WorksCommentService workCommentService;
	@Resource
	private WorksTecCommentService workTecCommentService;
	
	/**
	 * 发布帖子
	 * 传递的参数:
	 * access_token--验证 uid--用户id utype--用户类型
	 * stus_type--动态类型  title--标题 conten--内容  
	 * attr--附件路径 attr_type--附件类型 theme_id--话题id tag_id--标签id
	 * 一期: 标题 内容  附件路径 附件类型 用户类型 uid
	 * 
	 * ***/
	@RequestMapping(value = "/publish/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publishBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype=request.getParameter("utype");
		//可选参数
		String title=request.getParameter("title");
		String content = request.getParameter("content");
		String attr = request.getParameter("attr");
		String attr_type=request.getParameter("attr_type");
		
		if(access_token==null || uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户id
				Integer i_uid = Integer.valueOf(uid);
				//新增帖子信息
				BBS bbs = new BBS();
				bbs.setOwner(i_uid);
				bbs.setOwnerType(utype);
				bbs.setTitle(title);
				bbs.setContent(content);
				bbs.setCreateTime(TimeUtil.getTimeStamp());
				
				//新增帖子对应的附件信息
				BBSAttachment bbsAttr = null;
				if(attr_type!=null && !attr_type.equals("")) {
					bbsAttr = new BBSAttachment();
					bbsAttr.setStorePath(attr);
					bbsAttr.setCreateTime(TimeUtil.getTimeStamp());
					bbsAttr.setType(attr_type);
				}
				try {
					this.bbsService.insertBBSAndInsertAttr(bbs, bbsAttr);
					errorCode = "0";
					errorMessage = "ok";
					
				}catch(Exception e) {
					errorCode = "20037";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20037;
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
	
	/**
	 * 发布小组动态
	 * access_token--验证 uid--用户id utype--用户类型
	 * stus_type--动态类型  title--标题 conten--内容  
	 * attr--附件路径 attr_type--附件类型 theme_id--话题id tag_id--标签id
	 * group_id--小组id
	 * 一期: 标题 内容  附件路径 附件类型 用户类型 uid
	 * 
	 */
	@RequestMapping(value = "/publish/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publishGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype=request.getParameter("utype");
		String group_id = request.getParameter("group_id");
		//可选参数
		String title=request.getParameter("title");
		String content = request.getParameter("content");
		String attr = request.getParameter("attr");
		String attr_type=request.getParameter("attr_type");
		
		if(access_token==null || uid==null || utype==null || content==null || group_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") || content.equals("") || group_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) ||  !NumberUtil.isInteger(group_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户id
				Integer i_uid = Integer.valueOf(uid);
				//小组id
				Integer i_group_id = Integer.valueOf(group_id);
				
				//新增小组动态信息
				Statuses status = new Statuses();
				status.setOwner(i_uid);
				status.setOwnerType(utype);
				status.setTitle(title);
				status.setContent(content);
				status.setCreateTime(TimeUtil.getTimeStamp());
				status.setGroupId(i_group_id);
				
				//新增小组动态对应的附件信息
				StatusesAttachment statusAttr = null;
				if(attr_type!=null && !attr_type.equals("")) {
					statusAttr = new StatusesAttachment();
					statusAttr.setStorePath(attr);
					statusAttr.setCreateTime(TimeUtil.getTimeStamp());
					statusAttr.setType(attr_type);
				}
				try {
					this.statusesService.insertStatusAndUpdateAttr(status, statusAttr);
					errorCode = "0";
					errorMessage = "ok";
					
				}catch(Exception e) {
					errorCode = "20037";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20037;
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
	
	/***
	 * 获取首页最新动态、帖子列表
	 * 传递的参数uid--不可选
	 * 默认首页帖子显示10个  测评作品显示2个
	 * 
	 */
	@RequestMapping(value = "/public_timeline/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publicTimelineBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//定义一个首页列表对象
		List<Object> statusesList = new ArrayList<Object>();
		//分页时 最后一个位置ID(当前首页帖子的ID)
		String self = request.getParameter("self");
		
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		if(self==null || self.equals("")) {
			offset=-1;
		}
		else if(!NumberUtil.isInteger(self)) {
			offset=-10;
		}
		else
			offset=Integer.valueOf(self);
		
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
			uid="0";
		}
		if(utype==null) {
			utype="";
		}
		//用户ID
		Integer i_uid = Integer.valueOf(uid);
		//2. 查询2条测评动态详情
		if(offset==-1) {
			Integer worksLimit = ConfigUtil.HOMEWORK_PAGESIZE;	
			List<HomePageStatusesBean> worksList = this.worksService.getWorksListByHomepage(worksLimit);
			if(worksList.size()==0) {
				worksList = new ArrayList<HomePageStatusesBean>();
			}
			else {
				//填充作品详情信息
				for (HomePageStatusesBean work : worksList) {
					Integer s_id = work.getStus_id();
					
					//判断是否点赞或点评--(传递当前用户的ID和类型)
					Map<String, Object> map = new HashMap<String, Object>();  
					map.put("s_id", s_id);  
					map.put("u_id", i_uid);
					map.put("u_type", utype);
					
					HomeLikeOrCommentBean isExistLike = this.worksService.getIsLikeOrCommentOrAtt(map);   
					if(isExistLike!=null) {
						work.setIs_like((String)map.get("is_like"));
						work.setIs_comment((String)map.get("is_comment"));
						
						String att_type = isExistLike.getAtt_type();
						if(att_type!=null && !att_type.equals("")) {
							Integer att_id = isExistLike.getAtt_id();
							String duration= isExistLike.getDuration();
							String thumbnail=isExistLike.getThumbnail();
							String store_path=isExistLike.getStore_path();
							List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
							work.setAtt(attList);
						}
				   }
					statusesList.add(work);
			   }
			}
		}		
		//2. 查询10条帖子详情
		List<HomePageStatusesBean> bbsList = this.bbsService.getBBSListByHomepage(offset, limit);
		if(bbsList.size()==0) {
			bbsList = new ArrayList<HomePageStatusesBean>();
		}
		else {
			//填充帖子详情信息
			for (HomePageStatusesBean bbs : bbsList) {
			   Integer s_id = bbs.getStus_id();
			
			   //判断是否点赞或点评--(传递当前用户的ID和类型)
			   Map<String, Object> map = new HashMap<String, Object>();  
			   map.put("s_id", s_id);  
			   map.put("u_id", i_uid);
			   map.put("u_type", utype);
			     
		       HomeLikeOrCommentBean isExistLike = this.bbsService.getIsLikeOrCommentOrAtt(map);
			   if(isExistLike!=null) {
				   bbs.setIs_like((String)map.get("is_like"));
				   bbs.setIs_comment((String)map.get("is_comment"));
				   String att_type = isExistLike.getAtt_type();
				   if(att_type!=null && !att_type.equals("")) {
					   Integer att_id = isExistLike.getAtt_id();
					   String duration= isExistLike.getDuration();
					   String thumbnail=isExistLike.getThumbnail();
					   String store_path=isExistLike.getStore_path();
					   List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
					   bbs.setAtt(attList);
				   } 
			   }
			   statusesList.add(bbs);
			}
		}
		//3.查询广告信息
		if(offset==-1) {
			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
			boolean isExistAd = false;
			if(ad!=null) {
				isExistAd=true;
			}
			//4.查询话题信息
			//HomePageThemeBean theme = new HomePageThemeBean();
			if(statusesList.size()>0) {
				if(isExistAd) {
					if(statusesList.size()>4) {
						statusesList.add(3,ad);
					}
					else
						statusesList.add(ad);
				}
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			 }
		  }
		 else {
			if(statusesList.size()>0) {
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			 }
		  }
		}
	
		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(statusesList);
		Gson gson = new Gson();
		
		return gson.toJson(homepage);
	}

	/**
	 * 获取首页用户发布的帖子
	 * 传递的参数:uid --用户ID
	 * 
	 * **/
	@RequestMapping(value = "/user_timeline/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object userTimelineBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//定义一个首页列表对象
		List<Object> statusesList = new ArrayList<Object>();
		//以下参数是必选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		//以下参数用于分页
		String self = request.getParameter("self");
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(uid.equals("") || utype.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(uid)) {
				offset=-10;
			}
			else 
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode="20033";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
			Integer i_uid = Integer.valueOf(uid);	
			//1. 查询该用户10条帖子详情
			List<HomePageStatusesBean> bbsList = this.bbsService.getBBSListByUid(i_uid, offset, limit);
			if(bbsList.size()==0) {
				bbsList = new ArrayList<HomePageStatusesBean>();
			}
			else {
			//填充帖子详情信息
			for (HomePageStatusesBean bbs : bbsList) {
			   Integer s_id = bbs.getStus_id();
			   
			   //这里传递的是当前登录用户的ID和类型
			   Map<String, Object> map = new HashMap<String, Object>();  
		       map.put("s_id", s_id);  
		       map.put("u_id", i_uid);
		       map.put("u_type", utype);
		       
		       HomeLikeOrCommentBean isExistLike= this.bbsService.getIsLikeOrCommentOrAtt(map);
		       bbs.setIs_like((String)map.get("is_like"));
			   bbs.setIs_comment((String)map.get("is_comment"));
			   
			   if(isExistLike!=null) {
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
						bbs.setAtt(attList);
					}
			   }
				statusesList.add(bbs);
			}
		  }
			//2.查询广告信息
//			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
//			boolean isExistAd = false;
//			if(ad!=null) {
//				isExistAd=true;
//			}
			//3.查询主题信息
			//HomePageThemeBean theme = new HomePageThemeBean();
			
			if(statusesList.size()>0) {
//				if(isExistAd) {
//					if(statusesList.size()>4) {
//						statusesList.add(3,ad);
//					}
//					else {
//						statusesList.add(ad);					
//					}
//				}
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		  }
		}

		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(statusesList);
		Gson gson = new Gson();
		
		return gson.toJson(homepage);
	}
	/**
	 * 获取用户的作品列表
	 * 传递的参数:uid--用户id
	 * 
	 * **/
	@RequestMapping(value = "/user_timeline/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object userTimelineWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//定义一个首页列表对象
		List<Object> statusesList = new ArrayList<Object>();
		
		//以下参数是必选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		//以下参数用于分页
		String self = request.getParameter("self");
		
		Integer offset = -1;		
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(uid.equals("") || utype.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(uid)) {
				offset=-10;
			}
			else 
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode="20033";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
			Integer i_uid = Integer.valueOf(uid);	
			List<HomePageStatusesBean> worksList = this.worksService.getWorkListByUid(i_uid, offset, limit);
			if(worksList.size()==0) {
				worksList = new ArrayList<HomePageStatusesBean>();
			}
			else {
				//填充作品详情信息
				for (HomePageStatusesBean work : worksList) {		
					Integer s_id = work.getStus_id();
					
					//传递的是当前登录用户的ID和类型
					Map<String, Object> map = new HashMap<String, Object>();  
					map.put("s_id", s_id);  
					map.put("u_id", i_uid);
					map.put("u_type", utype);
					
					HomeLikeOrCommentBean isExistLike = this.worksService.getIsLikeOrCommentOrAtt(map);
						   
					if(isExistLike!=null) {
						work.setIs_like((String)map.get("is_like"));
						work.setIs_comment((String)map.get("is_comment"));
						String att_type = isExistLike.getAtt_type();
						if(att_type!=null && !att_type.equals("")) {
							Integer att_id = isExistLike.getAtt_id();
							String duration= isExistLike.getDuration();
							String thumbnail=isExistLike.getThumbnail();
							String store_path=isExistLike.getStore_path();
							List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
							work.setAtt(attList);
						}
				   }
					statusesList.add(work);
				}
			  }
			//2.查询广告信息
			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
			boolean isExistAd = false;
			if(ad!=null) {
				isExistAd=true;
			}
			//3.查询主题信息
			//HomePageThemeBean theme = new HomePageThemeBean();
			
		    if(statusesList.size()>0) {
				if(isExistAd) {
					if(statusesList.size()>4) {
						statusesList.add(3,ad);
					}
					else {
						statusesList.add(ad);
					}
				}
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		  }
		}
		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(statusesList);
		Gson gson = new Gson();
		
		return gson.toJson(homepage);
	}
	/**
	 * 转发帖子
	 * 传递的参数:
	 * access_token--验证  uid--用户id  status_id被转发动态id
	 * type -- 用户类型
	 * 
	 * **/
	@RequestMapping(value = "/report/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object reportBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String status_id = request.getParameter("status_id");
		String utype=request.getParameter("utype");
		
		if(access_token==null || uid==null || status_id==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || status_id.equals("") || utype.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID 和被转发帖子ID 
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				//依据被转发帖子ID查询帖子
				BBS bbs = this.bbsService.getBBSById(i_status_id);
				if(bbs==null) {
					errorCode = "20038";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20038;
				}
				else {
					//如果存在转发帖子信息 则更改用户的发布状态
					BBSForward bbsForward = new BBSForward();
					bbsForward.setCreateTime(TimeUtil.getTimeStamp());
					bbsForward.setVisitor(i_uid);
					bbsForward.setVisitorType(utype);
					bbsForward.setHost(bbs.getOwner());
					bbsForward.setHostType(bbs.getOwnerType());

					//新增一条帖子信息
					BBS bbsNew = new BBS();
					bbsNew.setOwner(i_uid);
					bbsNew.setOwnerType(utype);
					bbsNew.setTitle(bbs.getTitle());
					bbsNew.setContent(bbs.getContent());
					bbsNew.setAttachment(bbs.getAttachment());
					bbsNew.setArtType(bbs.getArtType());
					bbsNew.setTheme(bbs.getTheme());
					bbsNew.setTag(bbs.getTag());
					bbsNew.setCreateTime(TimeUtil.getTimeStamp());
					bbsNew.setRemarks("forward");
					
					try {
						this.bbsForwardService.insertOneBBSForward(bbsForward, bbsNew);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						errorCode = "20039";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20039;
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
	
	/**
	 * 获取帖子详情
	 * 传递的参数:
	 * status_id--动态帖子id
	 * 
	 * */
	@RequestMapping(value = "/show/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object showBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String status_id = request.getParameter("status_id");
		//以下不是必选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		StatusesShowBean bbs = new StatusesShowBean();
		
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
			if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
				uid="0";
			}
			if(utype==null) {
				utype="";
			}
			//用户ID
			Integer i_uid = Integer.valueOf(uid);
			//动态ID
			Integer i_status_id = Integer.valueOf(status_id);
			//获取帖子详情
			bbs = this.bbsService.getOneBBSById(i_status_id);
			if(bbs==null) {
				bbs = new StatusesShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				//传递当前用户的ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("s_id", i_status_id);  
			    map.put("u_id", i_uid);
			    map.put("u_type", utype);
			    //判断是否点赞 或点评
			    HomeLikeOrCommentBean isExistLike= this.bbsService.getIsLikeOrCommentOrAtt(map);
				   
				if(isExistLike!=null) {
					bbs.setIs_like((String)map.get("is_like"));
					bbs.setIs_comment((String)map.get("is_comment"));
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
						bbs.setAtt(attList);
					}
				}
				//1.查询评论信息
				List<CommentsVisitorBean> bbsCommentList = this.bbsCommentService.getBBSCommentByShow(i_status_id, limit);
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
				bbs.setComments(commentList);
				//4.查询广告信息
				HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
				if(ad!=null) {
					bbs.setAd(ad);
				}
				errorCode="0";
				errorMessage="ok";
			}
		}
		bbs.setError_code(errorCode);
		bbs.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(bbs);
	}
	/**
	 * 获取小组动态详情
	 * 传递的参数:
	 * status_id--动态帖子id
	 * */
	@RequestMapping(value = "/show/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object showGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String status_id = request.getParameter("status_id");
		//以下不是必选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
				
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		StatusesShowBean status = new StatusesShowBean();
		
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
			if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
				uid="0";
			}
			if(utype==null) {
				utype="";
			}
			
			//动态ID
			Integer i_status_id = Integer.valueOf(status_id);
			//获取小组动态详情
			status = this.statusesService.getOneStatusByid(i_status_id);
			if(status==null) {
				status = new StatusesShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				//判断是否点赞 或点评 
				Integer i_uid = status.getOwner();
				
				//传递的是当前登录用户ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("s_id", i_status_id);  
			    map.put("u_id", i_uid);
			    map.put("u_type", utype);
			    
			    HomeLikeOrCommentBean isExistLike= this.statusesService.getIsLikeOrCommentOrAtt(map);

				if(isExistLike!=null) {
					status.setIs_like((String)map.get("is_like"));
				    status.setIs_comment((String)map.get("is_comment"));
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
						status.setAtt(attList);
					}
				}
				//1.查询评论信息
				List<CommentsVisitorBean> statusCommentList = this.statusCommentService.getStatusCommentByShow(i_status_id, limit);
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
				status.setComments(commentList);
				//4.查询广告信息
				HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
				if(ad!=null) {
					status.setAd(ad);
				}
				errorCode="0";
				errorMessage="ok";
			}
		}
		status.setError_code(errorCode);
		status.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(status);
	}
	/**
	 * 获取作品详情
	 * 传递的参数:
	 * status_id--动态帖子id
	 * */
	@RequestMapping(value = "/show/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object showWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String status_id = request.getParameter("status_id");
		//以下不是必选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		WorkShowBean work = new WorkShowBean();
		
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
			if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
				uid="0";
			}
			if(utype==null) {
				utype="";
			}
			//用户ID
			Integer i_uid = Integer.valueOf(uid);
			//动态ID
			Integer i_status_id = Integer.valueOf(status_id);
			//获取作品详情
			work = this.worksService.getOneWorkByid(i_status_id);
			if(work==null) {
				work = new WorkShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				//传递当前用户的ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
				map.put("s_id", i_status_id);  
				map.put("u_id", i_uid);
				map.put("u_type", utype);
				//判断是否点赞 或点评
				HomeLikeOrCommentBean isExistLike= this.worksService.getIsLikeOrCommentOrAtt(map);
				if(isExistLike!=null) {
					work.setIs_like((String)map.get("is_like"));
					work.setIs_comment((String)map.get("is_comment"));
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
						work.setAtt(attList);
					}
				}
				//1.查询名师点评信息
				List<WorkCommentTecInfoBean> tecCommentList = this.workTecCommentService.getUserInfoByWorkShow(i_status_id);
				work.setTec_comment_num(tecCommentList.size());
				//组装名师点评信息
				List<WorkTecCommentsListBean> tec_comments_list = new ArrayList<WorkTecCommentsListBean>();
				for(WorkCommentTecInfoBean tecComment:tecCommentList) {
					WorkTecCommentsListBean list = new WorkTecCommentsListBean();
					WorkCommentDetailBean detail = new WorkCommentDetailBean();
					detail.setTec(tecComment);
					//获取名师点评和回复信息
					
					//传递评论名师或者回复名师ID
					Integer tec_id = tecComment.getTec_id();
					map.put("fid",i_status_id);
					map.put("uid",tec_id);
					map.put("limit",1);
					List<WorkTecCommentBean> tec_comments = this.workTecCommentService.getTecCommentByWorkShow(map);
					
					detail.setTec_comments(tec_comments);
					list.setComment_detail(detail);
					tec_comments_list.add(list);
				}
				work.setTec_comments_list(tec_comments_list);
				
				//1.查询评论信息
				List<CommentsVisitorBean> workCommentList = this.workCommentService.getWorkCommentByShow(i_status_id, limit);
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
					work.setComments(commentList);
					//4.查询广告信息
					HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
					if(ad!=null) {
						work.setAd(ad);
					}
					errorCode="0";
					errorMessage="ok";
				}
			}
			work.setError_code(errorCode);
			work.setError_msg(errorMessage);
				
			Gson gson = new Gson();
			return gson.toJson(work);
		
	}
  /**
	 * statuses/
	 * 获取小组动态列表
	 * 传递的参数：
	 * group_id--小组id
	 * 
	 * */
	@RequestMapping(value = "/public_timeline/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publicTimelineGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//定义一个首页列表对象
		List<Object> gstusList = new ArrayList<Object>();
		//以下3个参数是不可选参数
		String self = request.getParameter("self");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		//以下1个参数是必选参数
		String group_id = request.getParameter("group_id");
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(group_id==null || group_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(group_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
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
			
			if(offset == -10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
			}
			else {
			//如果用户id 默认查询uid=0 即尚未登录的用户
			if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
				uid="0";
			}
			if(utype==null) {
				utype="";
			}
			Integer i_uid = Integer.valueOf(uid);
			//小组id
			Integer i_group_id = Integer.valueOf(group_id);
			//1. 查询指定所在小组发的10条动态列表信息
			List<HomePageStatusesBean> statusList = this.statusesService.getStatusesListByGid(i_group_id, offset, limit);
			if(statusList.size()==0) {
				statusList = new ArrayList<HomePageStatusesBean>();
			}
			else {
			//填充小组信息详情信息
			for (HomePageStatusesBean status : statusList) {
			   Integer s_id = status.getStus_id();
			   
			   //在这里传递的是当前登录用户ID和类型
			   Map<String, Object> map = new HashMap<String, Object>();  
			   map.put("s_id", s_id);
			   map.put("u_id", i_uid);
			   map.put("u_type", utype);
			 
		       HomeLikeOrCommentBean isExistLike = this.statusesService.getIsLikeOrCommentOrAtt(map);
		     
		       if(isExistLike!=null) {
		    	   status.setIs_like((String)map.get("is_like"));
			       status.setIs_comment((String)map.get("is_comment"));
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
						status.setAtt(attList);
					}
			   }
		       gstusList.add(status);
			}
		  }
			//2.查询广告信息
			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
			boolean isExistAd = false;
			if(ad!=null) {
				isExistAd=true;
			}
			//3.查询主题信息
			//HomePageThemeBean theme = new HomePageThemeBean();
			
			if(gstusList.size()>0) {
				if(isExistAd) {
					if(gstusList.size()>4) {
						gstusList.add(3,ad);
					}
					else {
						gstusList.add(ad);
					}
				}
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		  }
		}

		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(gstusList);
		Gson gson = new Gson();
		
		return gson.toJson(homepage);
	}
	/**
	 * statuses/user_timeline/g_stus
	 * 获取小组用户发布的动态列表
	 * 传递的参数:group_id --小组ID
	 * uid--用户id
	 * 
	 * **/
	@RequestMapping(value = "/user_timeline/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object userTimelineGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//定义一个首页列表对象
		List<Object> gstusList = new ArrayList<Object>();
		
		String group_id = request.getParameter("group_id");
		//以下2个参数是不可选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String self = request.getParameter("self");
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(group_id==null || uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(group_id.equals("") || uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(group_id) || !NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
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
			
			if(offset == -10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
			}
			else {
			//小组id
			Integer i_group_id = Integer.valueOf(group_id);
			//用户id
			Integer i_uid = Integer.valueOf(uid);
			//1. 查询指定用户 所在小组发的10条动态列表信息
			List<HomePageStatusesBean> statusList = this.statusesService.getStatusesListByUidAndGid(i_uid, i_group_id, offset, limit);
			if(statusList.size()==0) {
				statusList = new ArrayList<HomePageStatusesBean>();
			}
			else {
			//填充帖子详情信息
			for (HomePageStatusesBean status : statusList) {
			   Integer s_id = status.getStus_id();
			   
			   //传递当前登录用户的ID和类型
			   Map<String, Object> map = new HashMap<String, Object>();  
			   map.put("s_id", s_id);  
			   map.put("u_id", i_uid);
			   map.put("u_type", utype);
			   
		       HomeLikeOrCommentBean isExistLike = this.statusesService.getIsLikeOrCommentOrAtt(map);
		       status.setIs_like((String)map.get("is_like"));
		       status.setIs_comment((String)map.get("is_comment"));
			   
		       if(isExistLike!=null) {
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
						status.setAtt(attList);
					}
			   }
		       gstusList.add(status);
			}
		  }
			//2.查询广告信息
			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
			boolean isExistAd = false;
			if(ad!=null) {
				isExistAd=true;
			}
			//3.查询主题信息
			//HomePageThemeBean theme = new HomePageThemeBean();
			
			if(gstusList.size()>0) {
				if(isExistAd) {
					if(gstusList.size()>4) {
						gstusList.add(3,ad);
					}
					else {
						gstusList.add(ad);
					}
				}
				errorCode="0";
				errorMessage="ok";
			}
			else {
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		  }
		}

		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(gstusList);
		Gson gson = new Gson();
		
		return gson.toJson(homepage);
	}
	/**
	 * 转发小组动态
	 * access_token--验证  uid--用户id  status_id被转发动态id
	 * type -- 用户类型  group_id --小组ID
	 * */
	@RequestMapping(value = "/report/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object reportGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String status_id = request.getParameter("status_id");
		String type=request.getParameter("type");
		String group_id = request.getParameter("group_id");
		
		if(access_token==null || uid==null || status_id==null || type==null || group_id==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || status_id.equals("") || type.equals("") || group_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id) || !NumberUtil.isInteger(group_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID 和被转发小组动态ID 用户所在小组id
				Integer i_uid = Integer.valueOf(uid);
				Integer i_status_id = Integer.valueOf(status_id);
				Integer i_group_id = Integer.valueOf(group_id);
				//依据被转发小组动态ID查询小组动态
				Statuses status = this.statusesService.getStatusesById(i_status_id);
				if(status==null) {
					errorCode = "20038";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20038;
				}
				else {
					//如果存在转发动态信息 则更改用户的发布状态
					StatusesForward statusForward = new StatusesForward();
					statusForward.setCreateTime(TimeUtil.getTimeStamp());
					statusForward.setVisitor(i_uid);
					statusForward.setVisitorType(type);
					statusForward.setHost(status.getOwner());
					statusForward.setHostType(status.getOwnerType());

					//新增一条动态信息
					Statuses statusNew = new Statuses();
					statusNew.setOwner(i_uid);
					statusNew.setOwnerType(type);
					statusNew.setTitle(status.getTitle());
					statusNew.setContent(status.getContent());
					statusNew.setAttachment(status.getAttachment());
					statusNew.setArtType(status.getArtType());
					statusNew.setTheme(status.getTheme());
					statusNew.setTag(status.getTag());
					statusNew.setGroupId(i_group_id);
					statusNew.setCreateTime(TimeUtil.getTimeStamp());
					statusNew.setRemarks("forward");
					
					try {
						this.statusForwardService.insertOneStatusForward(statusForward, statusNew);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						errorCode = "20039";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20039;
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
	//封装一个方法用于解析json数据 然后将其拆解
	public List<HomePageAttBean> parseAttPath(Integer att_id,String att_type,
			String duration,String thumbnail,String store_path) {
		List<HomePageAttBean> attList = new ArrayList<HomePageAttBean>();
		HomePageAttBean h = null;
		 
		String path="";
		//首先判断是否是Json
		JSONArray jsonArray = JSONArray.parseArray(store_path);
		for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
	          JSONObject jsonObject=(JSONObject)iterator.next();
	          h = new HomePageAttBean();
	          h.setAtt_id(att_id);
	          h.setAtt_type(att_type);
	          
	          h.setDuration(duration);
	          path = ConfigUtil.QINIU_BUCKET_COM_URL+"/"+jsonObject.getString("store_path");
	          h.setStore_path(path);
	          h.setThumbnail(thumbnail);
	          attList.add(h);
	    }
		
		return attList;
	}
}

	
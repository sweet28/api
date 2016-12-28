package com.arttraining.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.service.impl.AdvertiseService;
import com.arttraining.api.service.impl.BBSCommentService;
import com.arttraining.api.service.impl.BBSForwardService;
import com.arttraining.api.service.impl.BBSService;
import com.arttraining.api.service.impl.StatusCommentService;
import com.arttraining.api.service.impl.StatusesForwardService;
import com.arttraining.api.service.impl.StatusesService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksCommentService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.api.service.impl.WorksTecCommentService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.EmojiUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.JPushClientUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.ServerLog;
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
	@Resource
	private TokenService tokenService;
	
	public String emoji = "";
	
	/**
	 * 发布帖子
	 * 传递的参数:
	 * access_token--验证 uid--用户id utype--用户类型
	 * stus_type--动态类型  title--标题 conten--内容  
	 * attr--附件路径 attr_type--附件类型 theme_id--话题id tag_id--标签id
	 * 一期: 标题 内容  附件路径 附件类型 用户类型 uid
	 * thumbnail--缩略图
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
		String duration=request.getParameter("duration");
		String thumbnail=request.getParameter("thumbnail");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-title:"+title+
				"-content:"+content+"-attr:"+attr+"-attr_type:"+attr_type+"-duration:"+duration+"-thumbnail:"+thumbnail);
		
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
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				title = EmojiUtil.resolveToNullFromEmoji(title);
				content = EmojiUtil.resolveToNullFromEmoji(content);
				if(!"".equals(content.trim())){
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					
					//用户id
					Integer i_uid = Integer.valueOf(uid);
					//新增帖子信息
					BBS bbs = new BBS();
					bbs.setOwner(i_uid);
					bbs.setOwnerType(utype);
					bbs.setTitle(title);
					bbs.setContent(content);
					bbs.setCreateTime(Timestamp.valueOf(time));
					bbs.setOrderCode(time);
					bbs.setAttachment(thumbnail);
					
					//新增帖子对应的附件信息
					BBSAttachment bbsAttr = null;
					if(attr_type!=null && !attr_type.equals("")) {
						bbsAttr = new BBSAttachment();
						bbsAttr.setStorePath(attr);
						bbsAttr.setCreateTime(Timestamp.valueOf(time));
						bbsAttr.setType(attr_type);
						bbsAttr.setDuration(duration);
						bbsAttr.setOrderCode(time);
						bbsAttr.setThumbnail(thumbnail);
					}
					//发布帖子时 更新用户发帖量
					UserStu user = new UserStu();
					user.setId(i_uid);
					user.setBbsNum(1);
					
					try {
						this.bbsService.insertBBSAndInsertAttr(bbs, bbsAttr,user);
						errorCode = "0";
						errorMessage = "ok";
						
					}catch(Exception e) {
						errorCode = "20037";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20037;
					}
				}else{
					errorCode = "20067";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20067;
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
		
		ServerLog.getLogger().warn(jsonObject.toString());
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
		String duration=request.getParameter("duration");
		String thumbnail=request.getParameter("thumbnail");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-title:"+title+
				"-content:"+content+"-attr:"+attr+"-attr_type:"+attr_type+"-duration:"+duration+"-group_id:"+group_id+
				"-thumbnail:"+thumbnail);
		
		title = EmojiUtil.resolveToNullFromEmoji(title);
		content = EmojiUtil.resolveToNullFromEmoji(content);
		
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
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户id
				Integer i_uid = Integer.valueOf(uid);
				//小组id
				Integer i_group_id = Integer.valueOf(group_id);
				
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				//新增小组动态信息
				Statuses status = new Statuses();
				status.setOwner(i_uid);
				status.setOwnerType(utype);
				status.setTitle(title);
				status.setContent(content);
				status.setCreateTime(Timestamp.valueOf(time));
				status.setGroupId(i_group_id);
				status.setOrderCode(time);
				status.setAttachment(thumbnail);
				
				//新增小组动态对应的附件信息
				StatusesAttachment statusAttr = null;
				if(attr_type!=null && !attr_type.equals("")) {
					statusAttr = new StatusesAttachment();
					statusAttr.setStorePath(attr);
					statusAttr.setCreateTime(Timestamp.valueOf(time));
					statusAttr.setType(attr_type);
					statusAttr.setDuration(duration);
					statusAttr.setOrderCode(time);
					statusAttr.setThumbnail(thumbnail);
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
		ServerLog.getLogger().warn(jsonObject.toString());
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
		
		//以下参数不是必选参数
		String access_token = request.getParameter("access_token");
		if(access_token!=null && !access_token.equals("")) {
			// todo:判断token是否有效
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				TokenUtil.delayTokenDeadline(access_token);
			}
		}
		
		//定义一个首页列表对象
		List<Object> statusesList = new ArrayList<Object>();
		//分页时 最后一个位置ID(当前首页帖子的ID)
		String self = request.getParameter("self");
		
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-self:"+self);
		
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
		//2. 查询6条测评动态详情
//		if(offset==-1) {
//			Integer worksLimit = ConfigUtil.HOMEWORK_PAGESIZE;	
//			List<HomePageStatusesBean> worksList = this.worksService.getWorksListByHomepage(worksLimit);
//			if(worksList.size()==0) {
//				worksList = new ArrayList<HomePageStatusesBean>();
//			}
//			else {
//				//填充作品详情信息
//				for (HomePageStatusesBean work : worksList) {
//					Integer s_id = work.getStus_id();
//					
//					//判断是否点赞或点评--(传递当前用户的ID和类型)
//					Map<String, Object> map = new HashMap<String, Object>();  
//					map.put("s_id", s_id);  
//					map.put("u_id", i_uid);
//					map.put("u_type", utype);
//					
//					HomeLikeOrCommentBean isExistLike = this.worksService.getIsLikeOrCommentOrAtt(map);   
//					work.setIs_like((String)map.get("is_like"));
//					work.setIs_comment((String)map.get("is_comment"));
//					if(isExistLike!=null) {
//						String att_type = isExistLike.getAtt_type();
//						if(att_type!=null && !att_type.equals("")) {
//							Integer att_id = isExistLike.getAtt_id();
//							String duration= isExistLike.getDuration();
//							String thumbnail=isExistLike.getThumbnail();
//							String store_path=isExistLike.getStore_path();
//							List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,6);
//							work.setAtt(attList);
//						}
//				   }
//					statusesList.add(work);
//			   }
//			}
//		}		
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
		       bbs.setIs_like((String)map.get("is_like"));
			   bbs.setIs_comment((String)map.get("is_comment"));
		       if(isExistLike!=null) {
				   String att_type = isExistLike.getAtt_type();
				   if(att_type!=null && !att_type.equals("")) {
					   Integer att_id = isExistLike.getAtt_id();
					   String duration= isExistLike.getDuration();
					   String thumbnail=isExistLike.getThumbnail();
					   String store_path=isExistLike.getStore_path();
					   List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,1);
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
					if(statusesList.size()>5) {
						statusesList.add(5,ad);
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

		ServerLog.getLogger().warn(gson.toJson(homepage));
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
		//coffee add 1220
		String access_token=request.getParameter("access_token");
		//end
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		//以下参数用于分页
		String self = request.getParameter("self");
		
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-self:"+self);
		
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
			// todo:判断token是否有效
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			// boolean tokenFlag = this.tokenService.checkToken(access_token);
			boolean tokenFlag = true;
			if (tokenFlag) {	
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
								List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,1);
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
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}

		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(statusesList);
		Gson gson = new Gson();
		
		ServerLog.getLogger().warn(gson.toJson(homepage));
		
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
		//coffee add 1220
		String access_token=request.getParameter("access_token");
		//end
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		//以下参数用于分页
		String self = request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-self:"+self);
		
		Integer offset = -1;		
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(access_token==null || uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {	
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
						work.setIs_like((String)map.get("is_like"));
						work.setIs_comment((String)map.get("is_comment"));	   
						if(isExistLike!=null) {
							String att_type = isExistLike.getAtt_type();
							if(att_type!=null && !att_type.equals("")) {
								Integer att_id = isExistLike.getAtt_id();
								String duration= isExistLike.getDuration();
								String thumbnail=isExistLike.getThumbnail();
								String store_path=isExistLike.getStore_path();
								List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,6);
								work.setAtt(attList);
							}
					   }
						statusesList.add(work);
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
				} else {
						errorCode="20028";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(statusesList);
		Gson gson = new Gson();
		
		ServerLog.getLogger().warn(gson.toJson(homepage));
		
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
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-status_id:"+status_id);
		
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
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
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
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					//如果存在转发帖子信息 则更改用户的发布状态
					BBSForward bbsForward = new BBSForward();
					bbsForward.setCreateTime(Timestamp.valueOf(time));
					bbsForward.setVisitor(i_uid);
					bbsForward.setVisitorType(utype);
					bbsForward.setHost(bbs.getOwner());
					bbsForward.setHostType(bbs.getOwnerType());
					bbsForward.setOrderCode(time);
					
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
					bbsNew.setCreateTime(Timestamp.valueOf(time));
					bbsNew.setRemarks("forward");
					bbsNew.setOrderCode(time);
					
					//新增帖子附件表
					BBSAttachment bbsAttachment = new BBSAttachment();
					bbsAttachment.setForeignKey(i_status_id);
					bbsAttachment.setCreateTime(Timestamp.valueOf(time));
					bbsAttachment.setOrderCode(time);
					
					//更新用户的帖子数量
					UserStu user = new UserStu();
					user.setId(i_uid);
					user.setBbsNum(1);
					
					//coffee add 1215 新增推送信息
					Map<String, Object> push_map=new HashMap<String, Object>();
					push_map.put("user_type", "stu");
					push_map.put("push_type", "alert_msg");
					String alias=""+bbs.getOwner();
					push_map.put("alias",alias );
					String alert="亲,有人转发了你的帖子哟";
					push_map.put("alert",alert);
					String extra_key="";
					push_map.put("extra_key",extra_key);
					String extra_value="";
					push_map.put("extra_value",extra_value);
					String content=alert;
					push_map.put("content",content);
					String content_type="";
					push_map.put("content_type",content_type);
					JPushClientUtil.push_all_alias_alert_msg(push_map);
					//end
					
					try {
						this.bbsForwardService.insertOneBBSForward(bbsForward, bbsNew,user,bbsAttachment);
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
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
		
	}
	/***
	 * 查看我评论过的帖子列表信息
	 * 传递的参数:
	 * uid--用户ID
	 * utype--用户类型
	 * self--分页时的最后位置ID.
	 */
	@RequestMapping(value = "/show_my/bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object showMyBBS(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//定义一个首页列表对象
		List<Object> statusesList = new ArrayList<Object>();
				
		//以下两个是必选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		//以下参数不是必选参数
		String self=request.getParameter("self");
		ServerLog.getLogger().warn("self:"+self+"-uid:"+uid+"-utype:"+utype);
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		Integer offset=-1;
		if(uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(uid.equals("") || utype.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else 
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode="20032";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1. 查询该用户10条帖子详情
				List<HomePageStatusesBean> bbsList = this.bbsService.getBBSListByMyComment(i_uid, offset, limit);
				if(bbsList.size()==0) {
					bbsList = new ArrayList<HomePageStatusesBean>();
				} else {
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
								List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,1);
								bbs.setAtt(attList);
							}
					   }
						statusesList.add(bbs);
					}
				}
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
		
		ServerLog.getLogger().warn(gson.toJson(homepage));
		
		return gson.toJson(homepage);
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
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-uid:"+uid+"-utype:"+utype);
		
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
			try {//在这里 更新帖子浏览量
				BBS bro_bbs=new BBS();
				bro_bbs.setId(i_status_id);
				bro_bbs.setBrowseNum(Random.randomCommonInt());
				this.bbsService.updateBBSNumber(bro_bbs);
				
				//传递当前用户的ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("s_id", i_status_id);  
			    map.put("u_id", i_uid);
			    map.put("u_type", utype);
			    //判断是否点赞 或点评
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
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,1);
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
			} catch (Exception e) {
				errorCode="20054";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20054;
			}
		  }
		}
		bbs.setError_code(errorCode);
		bbs.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(bbs));
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
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-uid:"+uid+"-utype:"+utype);
		
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
				try {
				//更新动态浏览数
				Statuses bro_status = new Statuses();
				bro_status.setId(i_status_id);
				bro_status.setBrowseNum(Random.randomCommonInt());
				this.statusesService.updateStatusNumber(bro_status);
				
				//判断是否点赞 或点评 
				Integer i_uid = status.getOwner();
				
				//传递的是当前登录用户ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
			    map.put("s_id", i_status_id);  
			    map.put("u_id", i_uid);
			    map.put("u_type", utype);
			    
			    HomeLikeOrCommentBean isExistLike= this.statusesService.getIsLikeOrCommentOrAtt(map);
			    status.setIs_like((String)map.get("is_like"));
			    status.setIs_comment((String)map.get("is_comment"));
				if(isExistLike!=null) {
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,3);
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
			} catch (Exception e) {
				errorCode="20054";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20054;
			}
		  }
		}
		status.setError_code(errorCode);
		status.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(status));
		
		return gson.toJson(status);
	}
	/***
	 * 查看我的作品详情(本人的)
	 * 传递的参数:
	 * status_id--动态作品ID
	 * uid--用户ID
	 * utype--用户类型
	 */
	@RequestMapping(value = "/show_my/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object showMyWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String status_id = request.getParameter("status_id");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-uid:"+uid+"-utype:"+utype);
		//默认10条记录
		Integer limit = ConfigUtil.PAGESIZE;
		WorkShowBean work = new WorkShowBean();
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(status_id==null || uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(status_id.equals("") || uid.equals("") || utype.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(status_id) || !NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//用户ID
			Integer i_uid=Integer.valueOf(uid);
			//作品ID
			Integer i_status_id=Integer.valueOf(status_id);
			//获取作品详情
			work = this.worksService.getOneWorkByid(i_status_id);
			if(work==null) {
				work = new WorkShowBean();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}else {
				try {
				//更新作品的浏览量
				Works bro_work = new Works();
				bro_work.setBrowseNum(Random.randomCommonInt());
				bro_work.setId(i_status_id);
				this.worksService.updateWorksNumber(bro_work);
					
				//传递当前用户的ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
				map.put("s_id", i_status_id);  
				map.put("u_id", i_uid);
				map.put("u_type", utype);
				//判断是否点赞 或点评
				HomeLikeOrCommentBean isExistLike= this.worksService.getIsLikeOrCommentOrAtt(map);
				work.setIs_like((String)map.get("is_like"));
				work.setIs_comment((String)map.get("is_comment"));
				if(isExistLike!=null) {
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,6);
						work.setAtt(attList);
					}
				}
				//1.查询名师点评信息
				List<WorkCommentTecInfoBean> tecCommentList = this.workTecCommentService.getUserInfoByWorkShow(i_status_id);
				//组装名师点评信息
				List<WorkTecCommentsListBean> tec_comments_list = new ArrayList<WorkTecCommentsListBean>();
				for(WorkCommentTecInfoBean tecComment:tecCommentList) {
					WorkTecCommentsListBean list = new WorkTecCommentsListBean();
					
					list.setTec(tecComment);
					//获取名师点评和回复信息
					
					//传递评论名师或者回复名师ID
					Integer tec_id = tecComment.getTec_id();
					map.put("fid",i_status_id);
					map.put("uid",tec_id);
					List<WorkTecCommentBean> tec_comments = this.workTecCommentService.getTecCommentByWorkShow(map);
					
					list.setTec_comments(tec_comments);
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
//					//4.查询广告信息
//					HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
//					if(ad!=null) {
//						work.setAd(ad);
//					}
					errorCode="0";
					errorMessage="ok";
					
				} catch (Exception e) {
					errorCode="20054";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20054;
				}
			  }
			}
		work.setError_code(errorCode);
		work.setError_msg(errorMessage);	
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(work));
		return gson.toJson(work);
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
		
		ServerLog.getLogger().warn("status_id:"+status_id+"-uid:"+uid+"-utype:"+utype);
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
				try {
				//更新作品的浏览量
				Works bro_work = new Works();
				bro_work.setBrowseNum(Random.randomCommonInt());
				bro_work.setId(i_status_id);
				this.worksService.updateWorksNumber(bro_work);
					
				//传递当前用户的ID和类型
				Map<String, Object> map = new HashMap<String, Object>();  
				map.put("s_id", i_status_id);  
				map.put("u_id", i_uid);
				map.put("u_type", utype);
				//判断是否点赞 或点评
				HomeLikeOrCommentBean isExistLike= this.worksService.getIsLikeOrCommentOrAtt(map);
				work.setIs_like((String)map.get("is_like"));
				work.setIs_comment((String)map.get("is_comment"));
				if(isExistLike!=null) {
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,6);
						work.setAtt(attList);
					}
				}
				//1.查询名师点评信息
				List<WorkCommentTecInfoBean> tecCommentList = this.workTecCommentService.getUserInfoByWorkShow(i_status_id);
				//work.setTec_comment_num(tecCommentList.size());
				//组装名师点评信息
				List<WorkTecCommentsListBean> tec_comments_list = new ArrayList<WorkTecCommentsListBean>();
				for(WorkCommentTecInfoBean tecComment:tecCommentList) {
					WorkTecCommentsListBean list = new WorkTecCommentsListBean();
					
					list.setTec(tecComment);
					//获取名师点评和回复信息
					
					//传递评论名师或者回复名师ID
					Integer tec_id = tecComment.getTec_id();
					map.put("fid",i_status_id);
					map.put("uid",tec_id);
					map.put("limit",1);
					List<WorkTecCommentBean> tec_comments = this.workTecCommentService.getTecCommentByWorkShow(map);
					
					list.setTec_comments(tec_comments);
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
					
				} catch (Exception e) {
					errorCode="20054";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20054;
				}
			  }
			}
			work.setError_code(errorCode);
			work.setError_msg(errorMessage);
				
			Gson gson = new Gson();
			ServerLog.getLogger().warn(gson.toJson(work));
			
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
		
		ServerLog.getLogger().warn("uid:"+uid+"-utype:"+utype+"-self:"+self+"-group_id:"+group_id);
		
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
		       status.setIs_like((String)map.get("is_like"));
		       status.setIs_comment((String)map.get("is_comment"));
		       if(isExistLike!=null) {
					String att_type = isExistLike.getAtt_type();
					if(att_type!=null && !att_type.equals("")) {
						Integer att_id = isExistLike.getAtt_id();
						String duration= isExistLike.getDuration();
						String thumbnail=isExistLike.getThumbnail();
						String store_path=isExistLike.getStore_path();
						List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,3);
						status.setAtt(attList);
					}
			   }
		       gstusList.add(status);
			}
		  }
//			//2.查询广告信息
//			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
//			boolean isExistAd = false;
//			if(ad!=null) {
//				isExistAd=true;
//			}
			//3.查询主题信息
			//HomePageThemeBean theme = new HomePageThemeBean();
			
			if(gstusList.size()>0) {
//				if(isExistAd) {
//					if(gstusList.size()>4) {
//						gstusList.add(3,ad);
//					}
//					else {
//						gstusList.add(ad);
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
		homepage.setStatuses(gstusList);
		Gson gson = new Gson();
		
		ServerLog.getLogger().warn(gson.toJson(homepage));
		
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
		
		//coffee add 1220
		String access_token=request.getParameter("access_token");
		//end
		String group_id = request.getParameter("group_id");
		//以下2个参数是不可选参数
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String self = request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-group_id:"+group_id+"-uid:"+uid
				+"-utype:"+utype+"-self:"+self);
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		//如果用户id 默认查询uid=0 即尚未登录的用户
		if(access_token==null || group_id==null || uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(access_token.equals("") || group_id.equals("") || uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(group_id) || !NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {	
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
							List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path,3);
							status.setAtt(attList);
						}
				   }
			       gstusList.add(status);
				}
			  }
	//			//2.查询广告信息
	//			HomePageAdvertiseBean ad = this.adService.getOneAdByHomepage();
	//			boolean isExistAd = false;
	//			if(ad!=null) {
	//				isExistAd=true;
	//			}
				//3.查询主题信息
				//HomePageThemeBean theme = new HomePageThemeBean();
				
				if(gstusList.size()>0) {
	//				if(isExistAd) {
	//					if(gstusList.size()>4) {
	//						gstusList.add(3,ad);
	//					}
	//					else {
	//						gstusList.add(ad);
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
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;	
			}
		}

		HomePageBean homepage = new HomePageBean();
		homepage.setError_code(errorCode);
		homepage.setError_msg(errorMessage);
		homepage.setStatuses(gstusList);
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(homepage));
		return gson.toJson(homepage);
	}
	/**
	 * 转发小组动态
	 * access_token--验证  uid--用户id  status_id被转发动态id
	 * utype -- 用户类型  group_id --小组ID
	 * */
	@RequestMapping(value = "/report/g_stus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object reportGstus(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String status_id = request.getParameter("status_id");
		String utype=request.getParameter("utype");
		String group_id = request.getParameter("group_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-status_id:"+status_id+
				"-group_id:"+group_id);
		
		if(access_token==null || uid==null || status_id==null || utype==null || group_id==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || status_id.equals("") || utype.equals("") || group_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(status_id) || !NumberUtil.isInteger(group_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
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
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					
					//如果存在转发动态信息 则更改用户的发布状态
					StatusesForward statusForward = new StatusesForward();
					statusForward.setCreateTime(Timestamp.valueOf(time));
					statusForward.setVisitor(i_uid);
					statusForward.setVisitorType(utype);
					statusForward.setHost(status.getOwner());
					statusForward.setHostType(status.getOwnerType());
					statusForward.setOrderCode(time);

					//新增一条动态信息
					Statuses statusNew = new Statuses();
					statusNew.setOwner(i_uid);
					statusNew.setOwnerType(utype);
					statusNew.setTitle(status.getTitle());
					statusNew.setContent(status.getContent());
					statusNew.setAttachment(status.getAttachment());
					statusNew.setArtType(status.getArtType());
					statusNew.setTheme(status.getTheme());
					statusNew.setTag(status.getTag());
					statusNew.setGroupId(i_group_id);
					statusNew.setCreateTime(Timestamp.valueOf(time));
					statusNew.setRemarks("forward");
					statusNew.setOrderCode(time);
					
					//新增帖子附件表
					StatusesAttachment statusAttachment = new StatusesAttachment();
					statusAttachment.setForeignKey(i_status_id);
					statusAttachment.setCreateTime(Timestamp.valueOf(time));
					statusAttachment.setOrderCode(time);
					
					//coffee add 1215 新增推送信息
					Map<String, Object> push_map=new HashMap<String, Object>();
					push_map.put("user_type", "stu");
					push_map.put("push_type", "alert_msg");
					String alias=""+status.getOwner();
					push_map.put("alias",alias );
					String alert="亲,有人转发了你的动态哟";
					push_map.put("alert",alert);
					String extra_key="";
					push_map.put("extra_key",extra_key);
					String extra_value="";
					push_map.put("extra_value",extra_value);
					String content=alert;
					push_map.put("content",content);
					String content_type="";
					push_map.put("content_type",content_type);
					JPushClientUtil.push_all_alias_alert_msg(push_map);
					//end
					try {
						this.statusForwardService.insertOneStatusForward(statusForward, statusNew, statusAttachment);
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
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
	}
	//封装一个方法用于解析json数据 然后将其拆解
	public List<HomePageAttBean> parseAttPath(Integer att_id,String att_type,
			String duration,String thumbnail,String store_path,Integer type) {
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
	          path = jsonObject.getString("store_path");
	          path=ImageUtil.parsePicPath(path, type);
	          
	          String tmpPath = path;
	          if(att_type.equals("pic")){
	        	  path = path + "-1024X768";
	          }
	          
	          h.setStore_path(path);
	          thumbnail=ImageUtil.parsePicPath(thumbnail, type);
	          
	          if(att_type.equals("pic")){
	        	  thumbnail = tmpPath + "-400X247";
	          }
	          if(att_type.equals("video")){
	        	  thumbnail = thumbnail + "-400X247";
	          }
	          
	          h.setThumbnail(thumbnail);
	          attList.add(h);
	    }
		
		return attList;
	}
}

	
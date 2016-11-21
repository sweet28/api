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
import com.arttraining.api.bean.FollowCreateBean;
import com.arttraining.api.bean.FollowFansBean;
import com.arttraining.api.bean.FollowUserBean;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.FollowService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;

@Controller
@RequestMapping("/follow")
public class FollowController {
	@Resource
	private FollowService followService;
	
	/**
	 * 添加关注
	 * 传递的参数:access_token--验证
	 * uid--用户ID
	 * utype--用户类型  type--关注的类型 follow_id--被关注信息ID
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下5个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String type=request.getParameter("type");
		String follow_id=request.getParameter("follow_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-follow_id:"+follow_id+"-utype:"+utype+
				"-type:"+type);
		
		if(access_token==null || uid==null || utype==null || follow_id==null || type==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| follow_id.equals("") || type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(follow_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//用户ID和关注信息ID
			Integer i_uid = Integer.valueOf(uid);
			Integer i_follow_id=Integer.valueOf(follow_id);
			//依据关注类型的不同 查询不同的表
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("id", i_follow_id);
			map.put("uid", i_uid);
			map.put("utype", utype);
			//首先判断登录是否重复对名师/机构/爱好者用户关注
			Follow isExist=this.followService.getIsExistFollow(map);
			if(isExist!=null) {
				errorCode = "20055";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20055;
			}
			else{
			FollowCreateBean userinfo = this.followService.getUserInfoByFollowCreate(map);
			if(userinfo!=null) {
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				
				Integer host_id=userinfo.getId();
				//新增关注信息
				Follow follow = new Follow();
				follow.setVisitor(i_uid);
				follow.setVisitorType(utype);
				follow.setHost(host_id);
				follow.setHostType(type);
				follow.setHostName(userinfo.getName());
				follow.setCreateTime(Timestamp.valueOf(time));
				follow.setOrderCode(time);
				
				//更新用户的关注量和粉丝量
				UserStu follow_user = null;
				if(utype.equals("stu")) {
					follow_user = new UserStu();
					follow_user.setId(i_uid);
					follow_user.setFollowNum(1);
				}
				UserStu fan_user=null; 
				if(type.equals("stu")) {
					fan_user = new UserStu();
					fan_user.setId(host_id);
					fan_user.setFollowNum(1);
				}
				
				try {
					this.followService.insertOneFollowAndUpdateNum(follow, follow_user, fan_user);
					errorCode = "0";
					errorMessage = "ok";
				} catch (Exception e) {
					errorCode = "20052";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20052;
				}
			   }
			else {
				errorCode = "20052";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20052;
			  }
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
	 * 根据用户ID获取用户粉丝列表
	 * (可理解别人关注他的用户列表)
	 */
	@RequestMapping(value = "/fans/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object fansList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下2个参数是必选参数
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		//以下1个参数不是必选参数 分页时所用
		String self = request.getParameter("self");
		Integer offset=-1;
		Integer limit = ConfigUtil.PAGESIZE;
		
		ServerLog.getLogger().warn("uid:"+uid+"-utype:"+utype+"-self:"+self);
		
		//用户ID获取用户粉丝列表
		List<FollowFansBean> fansList = new ArrayList<FollowFansBean>();
		
		if(uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			}
			else 
				offset = Integer.valueOf(self);
			//用户ID
			Integer i_uid = Integer.valueOf(uid);
			//依据用户类型查询不同的信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("utype", utype);
			map.put("uid", i_uid);
			map.put("offset", offset);
			map.put("limit", limit);
			//获取粉丝列表
			fansList = this.followService.getFollowFansList(map);
			 if(fansList.size()>0) {
				//循环读取粉丝列表信息
				 for (FollowFansBean fans : fansList) {
					 Integer fans_id = fans.getUid();
					 String fans_type = fans.getUtype();
					 FollowUserBean user = this.followService.getFollowUserById(fans_id, fans_type);
					 if(user!=null) {
						 fans.setName(user.getName());
						 fans.setCity(user.getCity());
						 fans.setHead_pic(user.getHead_pic());
						 fans.setIdentity(user.getIdentity());
					 }
				 }
				errorCode = "0";
				errorMessage = "ok";
			 } else {
				fansList = new ArrayList<FollowFansBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			 }
			
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("follows",fansList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/***
	 * 根据用户ID获取用户关注列表
	 * (可理解他关注别人的用户列表)
	 */
	@RequestMapping(value = "/follow/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object followList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下2个参数是必选参数
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		//以下1个参数不是必选参数 分页时所用
		String self = request.getParameter("self");
		Integer offset=-1;
		Integer limit = ConfigUtil.PAGESIZE;
		
		ServerLog.getLogger().warn("uid:"+uid+"-utype:"+utype+"-self:"+self);
		
		//用户ID获取用户关注列表
		List<FollowFansBean> followList = new ArrayList<FollowFansBean>();
		
		if(uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			}
			else 
				offset = Integer.valueOf(self);
			//用户ID
			Integer i_uid = Integer.valueOf(uid);
			//依据用户类型查询不同的信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("utype", utype);
			map.put("uid", i_uid);
			map.put("offset", offset);
			map.put("limit", limit);
			//获取关注列表
			followList = this.followService.getFollowList(map);
			 if(followList.size()>0) {
				//循环读取关注列表信息
				for (FollowFansBean follow : followList) {
					 Integer follow_id = follow.getUid();
					 String follow_type = follow.getUtype();
					 FollowUserBean user = this.followService.getFollowUserById(follow_id, follow_type);
					 if(user!=null) {
						 follow.setName(user.getName());
						 follow.setCity(user.getCity());
						 follow.setHead_pic(user.getHead_pic());
						 follow.setIdentity(user.getIdentity());
					 }
				 }
				errorCode = "0";
				errorMessage = "ok";
			 } else {
				 followList = new ArrayList<FollowFansBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			 }
			
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("follows",followList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

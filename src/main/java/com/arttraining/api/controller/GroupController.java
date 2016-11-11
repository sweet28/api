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
import com.arttraining.api.bean.GroupListBean;
import com.arttraining.api.bean.GroupListMyBean;
import com.arttraining.api.bean.GroupListMyReBean;
import com.arttraining.api.bean.GroupListReBean;
import com.arttraining.api.bean.GroupShowBean;
import com.arttraining.api.bean.GroupShowStatusBean;
import com.arttraining.api.bean.GroupShowUserBean;
import com.arttraining.api.bean.GroupUserBean;
import com.arttraining.api.bean.GroupUserReBean;
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageAttBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.Group;
import com.arttraining.api.pojo.GroupUser;
import com.arttraining.api.service.impl.AdvertiseService;
import com.arttraining.api.service.impl.GroupService;
import com.arttraining.api.service.impl.GroupUserService;
import com.arttraining.api.service.impl.StatusesService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/group")
public class GroupController {
	@Resource
	private GroupService groupService;
	@Resource
	private GroupUserService groupUserService;
	@Resource
	private StatusesService statusService;
	@Resource
	private AdvertiseService adService;
	
	
	/**
	 * 获取小组列表
	 * 
	 * */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		GroupListReBean groupBean = new GroupListReBean();
		
		String self = request.getParameter("self");
		
		Integer offset = -1;
		if(self==null || self.equals("")) {
			offset = -1;
		}
		else if(!NumberUtil.isInteger(self)) {
			offset = -10;
		}
		else {
			offset = Integer.valueOf(self);
		}
			
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			//小组列表
			Integer limit = ConfigUtil.PAGESIZE;	
			List<GroupListBean> groupList = this.groupService.getGroupList(offset, limit);
			if(groupList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";
				groupBean.setGroups(groupList);
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;	
			}
			
		}
		groupBean.setError_code(errorCode);
		groupBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(groupBean);
	}
	/**
	 * 根据用户ID获取用户小组信息
	 * 传递的参数:
	 * access_token --验证 uid--用户id type--用户类型
	 * */
	@RequestMapping(value = "/list_my", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listMy(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		GroupListMyReBean myGroupBean = new GroupListMyReBean();
		
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype=request.getParameter("utype");
		String self = request.getParameter("self");
		
		Integer offset = -1;
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
			if(self==null || self.equals("")) {
				offset = -1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset = -10;
			}
			else {
				offset = Integer.valueOf(self);
			}
				
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
			}
			else {
				//小组列表
				Integer limit = ConfigUtil.PAGESIZE;	
				//用户ID
				Integer i_uid = Integer.valueOf(uid);
				
				Map<String, Object> infoMap = new HashMap<String, Object>();
				infoMap.put("offset", offset);
				infoMap.put("limit", limit);
				infoMap.put("uid", i_uid);
				infoMap.put("utype", utype);
				
				List<GroupListMyBean> myGroupList = this.groupService.getGroupListMy(infoMap);
				if(myGroupList.size()>0) {
					errorCode = "0";
					errorMessage = "ok";
					myGroupBean.setGroups(myGroupList);
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;	
				}
			}
				
		  }
		myGroupBean.setError_code(errorCode);
		myGroupBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(myGroupBean);
	}
	/***
	 * 根据小组ID获取小组详情信息
	 * 传递的参数:
	 * group_id--小组id
	 * 
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		GroupShowBean groupShow = new GroupShowBean();
		
		String group_id = request.getParameter("group_id");
		
		Integer offset=-1;
		Integer limit = ConfigUtil.PAGESIZE;
		
		String self = request.getParameter("self");
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
				offset=-1;
			}
			else
			if(!NumberUtil.isInteger(self)) {
				offset=-10;
			}
			else
				offset = Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
			}
			else {
				//用户id
				Integer i_group_id = Integer.valueOf(group_id);
				//1.依据小组ID查询小组信息
				groupShow = this.groupService.getGroupShowById(i_group_id,limit);
				//2.获取小组成员列表信息
				List<GroupShowUserBean> usersList = groupShow.getUsers();
				//3.查询对应小组成员列表的头像信息
				Map<String, Object> infoMap = new HashMap<String, Object>();
				for(GroupShowUserBean user:usersList) {
					String utype = user.getUtype();
					Integer uid = user.getUid();
					infoMap.put("utype", utype);
					infoMap.put("uid", uid);
					
					this.groupService.getUerPicByIdAndType(infoMap);
					user.setHead_pic((String)infoMap.get("pic"));
				}
				//4.用户信息
				groupShow.setUsers(usersList);
				
				//小组动态
				List<Object> statuses = new ArrayList<Object>();
				
				//5.查询小组成员所发布的小组动态信息
				List<HomePageStatusesBean> statusList = this.statusService.getGroupStatusesByGid(i_group_id, offset, limit);
				//填充小组动态信息
				for (HomePageStatusesBean status : statusList) {
					Integer s_id = status.getStus_id();
					Integer i_uid = status.getOwner();
					String utype = status.getOwner_type();
					
					Map<String, Object> map = new HashMap<String, Object>();  
					map.put("s_id", s_id);  
					map.put("u_type", utype);  
					map.put("u_id", i_uid);
					//用户信息存储过程
					GroupShowStatusBean isExistUser = this.statusService.getGroupStatusUserByUid(map);
					if(isExistUser!=null) {
						String city = isExistUser.getCity();
						String identity=isExistUser.getIdentity();
						String owner_name = isExistUser.getOwner_name();
						String owner_head_pic =isExistUser.getOwner_head_pic();
						status.setCity(city);
						status.setIdentity(identity);
						status.setOwner_name(owner_name);
						status.setOwner_head_pic(owner_head_pic);
					}
					//点赞信息存储过程
					HomeLikeOrCommentBean isExistLike = this.statusService.getIsLikeOrAtt(map);
					status.setIs_like((String)map.get("is_like"));
						   
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
					statuses.add(status);
			    }
				//6.小组动态信息
				groupShow.setStatuses(statuses);
				errorCode = "0";
				errorMessage = "ok";
			}
		}
		groupShow.setError_code(errorCode);
		groupShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(groupShow);
	}
	/***
	 * 根据小组ID获取小组成员列表
	 * 传递的参数:
	 * group_id--小组id
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object users(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String group_id = request.getParameter("group_id");
		String self = request.getParameter("self");
		
		Integer offset = -1;
		
		GroupUserReBean userReBean = new GroupUserReBean();
		
		if(group_id==null || group_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else
		if(!NumberUtil.isInteger(group_id)) {
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
			else {
				offset = Integer.valueOf(self);
			}
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
			}
			else {
				//小组列表
				Integer limit = ConfigUtil.PAGESIZE;
				//小组ID
				Integer i_group_id = Integer.valueOf(group_id);
				List<GroupUser> groupUserList = this.groupUserService.getGroupUserListByGid(i_group_id, offset, limit);
				
				//小组用户信息列表
				List<GroupUserBean> userBeanList = new ArrayList<GroupUserBean>();
				Map<String,Object> infoMap = new HashMap<String, Object>();
				for(GroupUser groupUser:groupUserList) {
					String utype = groupUser.getUserType();
					Integer uid = groupUser.getGroupId();
					String identity = groupUser.getIdentity();
					
					infoMap.put("utype", utype);
					infoMap.put("uid", uid);
					GroupUserBean userBean = this.groupUserService.getGroupUserInfoByUid(infoMap);
					userBean.setIdentity(identity);
					userBean.setTime("");
					userBeanList.add(userBean);
				}
				userReBean.setUsers(userBeanList);
			}
		}
		userReBean.setError_code(errorCode);
		userReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		return gson.toJson(userReBean);
	}
	/***
	 * 用户创建小组
	 * 传递的参数:
	 * access_token---验证 uid--用户id utype--用户类型
	 * group_name--小组名称
	 * introduce--简介
	 * pic--小组头像 tag--标签 classify--分类

	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下5个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String group_name = request.getParameter("group_name");
		String introduce = request.getParameter("introduce");
		//以下不是必选参数
		String pic =  request.getParameter("pic");
		String tag =  request.getParameter("tag");
		String classify =  request.getParameter("classify");
		
		if(access_token==null || uid==null || utype==null || group_name==null || introduce==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else
		if(access_token.equals("") || uid.equals("") || utype.equals("") || group_name.equals("") || introduce.equals("")) {
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
				//创建小组的组主
				Integer i_uid = Integer.valueOf(uid);
				String gname = group_name.trim();
				//首先判断是否重复创建了小组
				Group isExist = this.groupService.getIsRepeatGroup(i_uid, utype, gname );
				if(isExist==null) {
					//获取2015年设置的时间戳
					long preTime = ConfigUtil.DEFINE_TIMESTAMP;
					//获取当前时间
					long curTime = System.currentTimeMillis(); 
					long time = curTime-preTime;
					//1.创建小组信息
					Group group = new Group();
					group.setOwner(i_uid);
					group.setOwnerType(utype);
					group.setName(group_name);
					group.setNumber(String.valueOf(time));
					group.setIntroduce(introduce);
					group.setTag(tag);
					group.setCreateTime(TimeUtil.getTimeStamp());
					group.setClassify(classify);
					group.setPic(pic);
					group.setPeopleNum(1);
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					
					this.groupService.getUerPicByIdAndType(map);
					
					//2.创建小组成员信息
					GroupUser groupUser = new GroupUser();
					groupUser.setIdentity("owner");
					groupUser.setCreateTime(TimeUtil.getTimeStamp());
					groupUser.setUserId(i_uid);
					groupUser.setUserType(utype);
					groupUser.setHeadPic((String)map.get("pic"));
				
					try {
						this.groupService.insertOneGroupAndUser(group, groupUser);
						errorCode = "0";
						errorMessage = "ok";	
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20041";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20041;	
					}
				}
				else {
					errorCode = "20042";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20042;	
				}
				
			}
		
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("group_id", 0);
		jsonObject.put("group_code","");
		
		return jsonObject;
	} 
	/***
	 * 用户加入小组
	 * 传递的参数：access_token--验证 uid--用户id group_id--小组ID
	 * utype--用户类型
	 * 
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object join(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下4个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String group_id = request.getParameter("group_id");
		
		if(access_token==null || uid==null || utype==null || group_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else
		if(access_token.equals("") || uid.equals("") || utype.equals("") || group_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(group_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			//小组id
			Integer i_group_id = Integer.valueOf(group_id);
			//用户id
			Integer i_uid = Integer.valueOf(uid);
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", i_uid);
			map.put("utype", utype);
			
			this.groupService.getUerPicByIdAndType(map);
			
			//1.创建小组成员信息
			GroupUser groupUser = new GroupUser();
			groupUser.setIdentity("host");
			groupUser.setCreateTime(TimeUtil.getTimeStamp());
			groupUser.setUserId(i_uid);
			groupUser.setUserType(utype);
			groupUser.setHeadPic((String)map.get("pic"));
			groupUser.setGroupId(i_group_id);
			
			//2.修改小组信息
			Group group = new Group();
			group.setId(i_group_id);
			
			try {
				this.groupUserService.updateGroupAndUserByCreate(group, groupUser);
				errorCode = "0";
				errorMessage = "ok";	
			} catch (Exception e) {
				errorCode = "20043";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20043;	
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("group_id", 0);
		jsonObject.put("group_code","");
		
		return jsonObject;
	} 
	/***
	 * 用户退出小组
	 * 传递的参数：access_token--验证 uid--用户id group_id--小组ID
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object exit(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下4个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String group_id = request.getParameter("group_id");
		
		if(access_token==null || uid==null || utype==null || group_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else
		if(access_token.equals("") || uid.equals("") || utype.equals("") || group_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(group_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			//小组id
			Integer i_group_id = Integer.valueOf(group_id);
			//用户id
			Integer i_uid = Integer.valueOf(uid);
			//1.修改小组成员信息
			GroupUser groupUser = new GroupUser();
			groupUser.setIsDeleted(1);
			groupUser.setDeleteTime(TimeUtil.getTimeStamp());
			groupUser.setUserId(i_uid);
			groupUser.setUserType(utype);
			groupUser.setGroupId(i_group_id);
			
			//2.修改小组信息
			Group group = new Group();
			group.setId(i_group_id);
			
			try {
				this.groupUserService.updateGroupAndUserByExit(group, groupUser);
				errorCode = "0";
				errorMessage = "ok";	
			} catch (Exception e) {
				errorCode = "20043";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20043;	
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("group_id", 0);
		jsonObject.put("group_code","");
		
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

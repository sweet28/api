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

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.beanv2.LiveJoinBean;
import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.beanv2.LiveStopBean;
import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.pojo.LiveLike;
import com.arttraining.api.pojo.LiveMember;
import com.arttraining.api.service.impl.LiveCommentService;
import com.arttraining.api.service.impl.LiveDetailService;
import com.arttraining.api.service.impl.LiveLikeService;
import com.arttraining.api.service.impl.LiveMemberService;
import com.arttraining.api.service.impl.LiveService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.LiveUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;
import com.qiniu.pili.PiliException;


@Controller
@RequestMapping("/live")
public class LiveController {
	@Resource
	private TokenService tokenService;
	@Resource
	private LiveService liveService;
	@Resource
	private LiveDetailService liveDetailService;
	@Resource
	private LiveCommentService liveCommentService;
	@Resource
	private LiveLikeService liveLikeService;
	@Resource
	private LiveMemberService liveMemberService;
	
	/***
	 * 直播结束
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object stop(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String room_id=request.getParameter("room_id");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-room_id:"+room_id);
		
		LiveStopBean stop=new LiveStopBean();
		
		if(access_token==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//房间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//1.先依据房间ID来获取相应的直播信息
				Live live = this.liveService.getOneLiveInfoByRoomId(i_room_id);
				System.out.println(000);
				//如果存在直播信息 
				if(live!=null) {
					System.out.println(111);
					//直播结束时间 获取当前时间
					Date endDate = new Date();
					long endTime = endDate.getTime();
					//直播开始时间
					Date startDate=live.getStartTime();
					long startTime=startDate.getTime();
					long  diff = (endTime - startTime)/1000;
					
					int live_time=Integer.parseInt(String.valueOf(diff));
					System.out.println(live_time);
					
					//获取直播观看人次
					int live_number=this.liveDetailService.getLiveNumberByRoomId(i_room_id);
					stop.setLive_time(live_time);
					stop.setLike_number(live.getLikeNumber());
					stop.setLive_number(live_number);
					stop.setReward_number(0);
					//保存直播数据的回放路径
					String fname="";
					//判断直播时长是否小于5分钟 如果小于5分钟 将不会保存直播数据
					System.out.println(3333);
					if(live_time>300) {
						try {
							fname=LiveUtil.saveLiveStream(live.getStreamKey(), startTime, endTime);
						} catch (PiliException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							fname="";
						}
					}
					System.out.println(fname);
					//修改主播房间信息
					Live upd_live=new Live();
					upd_live.setId(i_room_id);
					upd_live.setEndTime(endDate);
					upd_live.setDuration(String.valueOf(diff));
					if(!fname.equals("")) {
						upd_live.setAttachment(fname);
					}
					System.out.println(444);
					this.liveService.updateLiveInfo(upd_live);
					System.out.println(555);
					errorCode="0";
					errorMessage="ok";
				} else {
					errorCode = "20075";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20075;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		stop.setError_code(errorCode);
		stop.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(stop));
		return gson.toJson(stop);
	}
	
	/***
	 * 直播点赞主播
	 */
	@RequestMapping(value = "/create/like", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createLike(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
						"-utype:"+utype+"-room_id:"+room_id);
		
		if(access_token==null || uid==null || utype==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//房间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.先依据房间ID来获取相应的直播信息
				Live live = this.liveService.getOneLiveInfoByRoomId(i_room_id);
				//如果存在直播信息 
				if(live!=null) {
					Integer owner=live.getOwner();
					String owner_type=live.getOwnerType();
					
					//新增一条直播点赞信息
					LiveLike like=new LiveLike();
					//获取当前时间
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					like.setVisitor(i_uid);
					like.setVisitorType(utype);
					like.setHost(owner);
					like.setHostType(owner_type);
					like.setForeignKey(i_room_id);
					like.setCreateTime(date);
					like.setOrderCode(time);
					
					//修改直播点赞数
					Live upd_live= new Live();
					upd_live.setId(i_room_id);
					upd_live.setLikeNumber(1);
					
					try {
						this.liveLikeService.insertLiveLikeAndUpdateLive(like, upd_live);
						errorCode="0";
						errorMessage="ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20074";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20074;
					}
				} else {
					errorCode = "20074";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20074;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/***
	 * 房间评论信息列表
	 */
	@RequestMapping(value = "/comment/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object commentList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
				
		//以下不是必选参数
		String self=request.getParameter("self");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
						"-utype:"+utype+"-room_id:"+room_id+"-self:"+self);
		
		Integer offset=-1;
		Integer limit=ConfigUtil.COMMENT_PAGESIZE;
		
		List<LiveCommentBean> comment_list = new ArrayList<LiveCommentBean>();
		
		if(access_token==null || uid==null || utype==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//如果未传递type
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
				boolean tokenFlag = tokenService.checkToken(access_token);
				if (tokenFlag) {
					//房间ID
					Integer i_room_id=Integer.valueOf(room_id);
					//用户ID
					Integer i_uid=Integer.valueOf(uid);
					//获取评论列表信息
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("room_id", i_room_id);
					map.put("offset", offset);
					map.put("limit", limit);
					System.out.println(0000);
					comment_list = this.liveCommentService.getLiveCommentByRoomId(map);
					System.out.println(111);
					if(comment_list.size()>0) {
						System.out.println(comment_list.size());
						errorCode="0";
						errorMessage="ok";
					} else {
						System.out.println(2222);
						comment_list=new ArrayList<LiveCommentBean>();
						errorCode = "20007";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
				} else {
					errorCode = "20028";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("comment_list",comment_list);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/***
	 * 客户端/主播端房间成员信息列表
	 */
	@RequestMapping(value = "/member/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object memberList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		
		//以下不是必选参数
		String self=request.getParameter("self");
		//coffee add 0110
		String name=request.getParameter("name");
		//end
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-room_id:"+room_id+"-self:"+self+"-name:"+name);
		
		Integer offset=-1;
		Integer limit=ConfigUtil.MEMBER_PAGESIZE;
		
		List<LiveMemberBean> member_list=new ArrayList<LiveMemberBean>();
		
		if(access_token==null || uid==null || utype==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//如果未传递type
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
				boolean tokenFlag = tokenService.checkToken(access_token);
				if (tokenFlag) {
					//房间ID
					Integer i_room_id=Integer.valueOf(room_id);
					//用户ID
					Integer i_uid=Integer.valueOf(uid);
					//获取成员列表信息(头像 姓名)
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("room_id", i_room_id);
					map.put("offset", offset);
					map.put("limit", limit);
					map.put("name", name);
					//然后获取其他人信息
					member_list = this.liveMemberService.getLiveMemberByRoomId(map);
					if(member_list.size()<=0) {
						member_list = new ArrayList<LiveMemberBean>();
					}
					//先获取主播信息
					LiveMemberBean live_owner=this.liveMemberService.getLiveOwnerByRoomId(map);
					if(live_owner!=null) {
						member_list.add(0, live_owner);
					} 
					if(member_list.size()>0) {
						errorCode = "0";
						errorMessage = "ok";
					} else {
						errorCode = "20007";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
				} else {
					errorCode = "20028";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("member_list",member_list);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/***
	 * 直播评论接口
	 * 
	 */
	@RequestMapping(value = "/online/comment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object onlineComment(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String content=request.getParameter("content");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-room_id:"+room_id+"-content:"+content);
		
		if(access_token==null || uid==null || utype==null 
				|| room_id==null || content==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("") || content.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//房间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.先依据房间ID来获取相应的直播信息
				Live live = this.liveService.getOneLiveInfoByRoomId(i_room_id);
				//如果存在直播信息 
				if(live!=null) {
					Integer owner=live.getOwner();
					String owner_type=live.getOwnerType();
					
					//新增一条直播评论信息
					LiveComment comment=new LiveComment();
					//获取当前时间
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					comment.setVisitor(i_uid);
					comment.setVisitorType(utype);
					comment.setHost(owner);
					comment.setHostType(owner_type);
					comment.setForeignKey(i_room_id);
					comment.setCreateTime(date);
					comment.setOrderCode(time);
					comment.setContent(content);
					comment.setType("comment");
					
					try {
						this.liveCommentService.insertLiveComment(comment);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20073";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20073;
					}
				} else {
					errorCode = "20073";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20073;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/***
	 * 客户端退出直播房间
	 * 
	 */
	@RequestMapping(value = "/room/exit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object roomExit(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-room_id:"+room_id);
		
		if(access_token==null || uid==null || utype==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//房间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//客户退出直播房间时  首先插入一条退出房间记录 
				//然后减少在线人数
				//1.先依据房间ID来获取相应的直播信息
				Live live = this.liveService.getOneLiveInfoByRoomId(i_room_id);
				//如果存在直播信息 
				if(live!=null) {
					//继续判断是否加入该直播房间
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("room_id", i_room_id);
					map.put("uid", i_uid);
					map.put("utype", utype);
					LiveMember member=this.liveMemberService.getLiveMemberInfo(map);
					//直播成员对象
					LiveMember upd_member=null;
					if(member!=null) {
						upd_member=new LiveMember();
						upd_member.setId(member.getId());
						upd_member.setIsDeleted(1);
					}
					Integer owner=live.getOwner();
					String owner_type=live.getOwnerType();
					//2.然后插入一条加入房间的信息
					//获取当前时间
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					LiveDetail detail = new LiveDetail();
					detail.setOwner(owner);
					detail.setOwnerType(owner_type);
					detail.setHost(i_uid);
					detail.setHostType(utype);
					detail.setForeignKey(i_room_id);
					detail.setCreateTime(date);
					detail.setOrderCode(time);
					detail.setLiveOperation("exit");
					detail.setType("member");
					
					//修改直播在线人数
					Live upd_live=new Live();
					upd_live.setId(i_room_id);
					upd_live.setLiveNumber(-1);
					
					try {
						this.liveMemberService.insertLiveDetailAndUpdateLiveMember(upd_member, detail, upd_live);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20072";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20072;
					}
				} else {
					errorCode = "20072";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20072;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	/***
	 * 客户端加入直播房间
	 * 获取播放地址
	 */
	@RequestMapping(value = "/room/join", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object roomJoin(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
	
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-room_id:"+room_id);
		
		//加入房间返回的信息
		LiveJoinBean joinReBean = new LiveJoinBean();
		
		if(access_token==null || uid==null || utype==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(room_id) ) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//房间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.先依据房间ID来获取相应的直播信息
				Live live = this.liveService.getOneLiveInfoByRoomId(i_room_id);
				//如果存在直播信息 
				if(live!=null) {
					//继续判断是否加入该直播房间
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("room_id", i_room_id);
					map.put("uid", i_uid);
					map.put("utype", utype);
					LiveMember member=this.liveMemberService.getLiveMemberInfo(map);
					Integer owner=live.getOwner();
					String owner_type=live.getOwnerType();
					//获取当前时间
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					
					LiveMember upd_member=null;
					//如果该用户尚未加入过该直播房间 则新增一条加入房间记录
					if(member==null) {
						upd_member=new LiveMember();
						upd_member.setOwner(owner);
						upd_member.setOwnerType(owner_type);
						upd_member.setHost(i_uid);
						upd_member.setHostType(utype);
						upd_member.setForeignKey(i_room_id);
						upd_member.setCreateTime(date);
						upd_member.setOrderCode(time);
						upd_member.setType("member");
					}
					//2.然后插入一条加入房间的信息
					LiveDetail detail = new LiveDetail();
					detail.setOwner(owner);
					detail.setOwnerType(owner_type);
					detail.setHost(i_uid);
					detail.setHostType(utype);
					detail.setForeignKey(i_room_id);
					detail.setCreateTime(date);
					detail.setOrderCode(time);
					detail.setLiveOperation("join");
					detail.setType("member");
					
					//修改直播在线人数
					Live upd_live=new Live();
					upd_live.setId(i_room_id);
					upd_live.setLiveNumber(1);
					
					try {
						this.liveMemberService.insertLiveDetailAndUpdateLive(upd_member, detail, upd_live);
						//依据直播ID 主播ID 主播类型
						map.put("owner", owner);
						map.put("owner_type", owner_type);
						joinReBean=this.liveService.getLiveInfoByJoin(map);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20071";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20071;
					}
					
				} else {
					errorCode = "20071";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20071;
				}
			}else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		joinReBean.setError_code(errorCode);
		joinReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(joinReBean));
		return gson.toJson(joinReBean);
	}
	
	/**
	 * 主播端发起直播请求
	 * 获取推流地址
	 */
	@RequestMapping(value = "/publish/url", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publishUrl(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		//以下不是必选参数
		String province=request.getParameter("province");
		String city=request.getParameter("city");
		String title=request.getParameter("title");
		String address=request.getParameter("address");
		String tag=request.getParameter("tag");
		String is_private=request.getParameter("is_private");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+
				"-province:"+province+"-city:"+city+"-title:"+title+"-address:"+address+
				"-tag:"+tag+"-is_private:"+is_private);
		
		String url="";
		//coffee add 0111 
		Integer room_id=0;
		//end
		if(access_token==null || uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				if(i_uid!=0) {
					//1.首先依据用户ID和类型来判断是否生成过推流地址
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					Live live=this.liveService.getOneLiveInfoByUid(map);
					//流名
					String stream="";
					//推流URL
					String publish_url="";
					//rtmp播放地址
					String rtmp_url="";
					//hls播放地址
					String hls_url="";
					//hdl播放地址
					String hdl_url="";
					//封面播放地址
					String snapshot_url="";
					String insert_flag="no";
					if(live!=null) {
						stream = live.getStreamKey();
						publish_url=live.getPublishUrl();
						rtmp_url=live.getRtmpUrl();
						hls_url=live.getHlsUrl();
						hdl_url=live.getHdlUrl();
						snapshot_url=live.getSnapshotUrl();
						url=publish_url;
						//启用流
						try {
							LiveUtil.enableStream(stream);
							insert_flag="yes";
						} catch (PiliException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							errorCode = "20070";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20070;
							insert_flag="no";
						}
					} else {
						//依据用户请求生成推流地址
						try {
							Map<String, String> urlMap=LiveUtil.createLiveUrl();
							stream = urlMap.get("stream");
							publish_url= urlMap.get("publish_url");
							rtmp_url= urlMap.get("rtmp_url");
							hls_url= urlMap.get("hls_url");
							hdl_url= urlMap.get("hdl_url");
							snapshot_url= urlMap.get("snapshot_url");
							url=publish_url;
							insert_flag="yes";
						} catch (PiliException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							errorCode = "20070";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20070;
							insert_flag="no";
						}
					}
					if(insert_flag.equals("yes")) {
						//获取当前时间
						Date date = new Date();
						String time=TimeUtil.getTimeByDate(date);
						Integer i_is_private=0;
						if(is_private!=null) {
							i_is_private=Integer.valueOf(is_private);
						}
						//主播开启直播的记录
						Live ins_live = new Live();
						ins_live.setOwner(i_uid);
						ins_live.setOwnerType(utype);
						ins_live.setLiveProvince(province);
						ins_live.setLiveCity(city);
						ins_live.setLiveTag(tag);
						ins_live.setTitle(title);
						ins_live.setIsPrivate(i_is_private);
						ins_live.setAddress(address);
						ins_live.setCreateTime(date);
						ins_live.setStreamKey(stream);
						ins_live.setPublishUrl(publish_url);
						ins_live.setRtmpUrl(rtmp_url);
						ins_live.setHdlUrl(hdl_url);
						ins_live.setHlsUrl(hls_url);
						ins_live.setSnapshotUrl(snapshot_url);
						ins_live.setLiveNumber(1);
						ins_live.setOrderCode(time);
						ins_live.setStartTime(date);
						//房间成员列表记录
						LiveDetail detail=new LiveDetail();
						detail.setOwner(i_uid);
						detail.setOwnerType(utype);
						detail.setHost(i_uid);
						detail.setHostType(utype);
						detail.setCreateTime(date);
						detail.setOrderCode(time);
						detail.setType("owner");
						
						try {
							room_id=this.liveService.insertLiveAndDetailInfo(ins_live, detail);
							errorCode = "0";
							errorMessage = "ok";
						} catch (Exception e) {
							// TODO: handle exception
							errorCode = "20070";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20070;
						}
					}
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("publish_url", url);
		jsonObject.put("room_id", room_id);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
}

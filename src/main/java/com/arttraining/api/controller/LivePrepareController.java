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
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.beanv2.LiveAuthBean;
import com.arttraining.api.beanv2.LiveReBean;
import com.arttraining.api.beanv2.LiveRoomBean;
import com.arttraining.api.beanv2.LiveTypeList;
import com.arttraining.api.pojo.LiveAuth;
import com.arttraining.api.pojo.LiveAuthWithBLOBs;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.LiveRoomHistory;
import com.arttraining.api.pojo.LiveTimeTable;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.LivePrepareService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/prepare/live")
public class LivePrepareController {
	@Resource
	private TokenService tokenService;
	@Resource
	private LivePrepareService livePrepareService;
	@Resource
	private UserTecService userTecService;
	
	/***
	 * 直播类型列表
	 */
	@RequestMapping(value = "/type/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object typeList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<LiveTypeList> typeList = this.livePrepareService.getLivesTypeList();
		if(typeList.size()>0) {
			errorCode="0";
			errorMessage="ok";
		} else {
			typeList = new ArrayList<LiveTypeList>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("type_list", typeList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/***
	 * 进入认证二级页面
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object auth(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token"); 
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		LiveReBean reBean = new LiveReBean();
		
		if(access_token==null  || uid==null || utype==null) {
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
				//获取教师直播资质以及直播间认证审核状态
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("uid", i_uid);
				map.put("utype", utype);
				//教师直播资质认证信息
				LiveAuth auth = this.livePrepareService.getLiveAuthByUid(i_uid, utype);
				if(auth!=null) {
					reBean.setLive_status(auth.getIsPublish());
				}
				//教师直播间信息
				LiveRoom room=this.livePrepareService.getLiveRoomByUid(i_uid, utype);
				if(room!=null) {
					reBean.setRoom_status(room.getIsPublish());
				}
				errorCode="0";
				errorMessage="ok";
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}
	/**
	 * 设置直播章节课时计划表
	 */
	@RequestMapping(value = "/chapter/set", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object chapterSet(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token"); 
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String timetable_id=request.getParameter("timetable_id");
		String room_id=request.getParameter("room_id");		
		String name=request.getParameter("name");		
		String introduction=request.getParameter("introduction");
		//将直播价格/重播价格设置为一样
		String price=request.getParameter("price");	
		String start_time=request.getParameter("start_time");
		String end_time=request.getParameter("end_time");
		String duration=request.getParameter("duration");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype+"-timetable_id:"+timetable_id+"-room_id:"+room_id
				+"-name:"+name+"-introduction:"+introduction
				+"-price:"+price+"-start_time:"+start_time+"-end_time:"+end_time
				+"-duration:"+duration);
		
		if(access_token==null  || uid==null || utype==null
				|| room_id==null || name==null 
				|| introduction==null || timetable_id==null
				|| start_time==null || end_time==null
				|| price==null || duration==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")
				|| name.equals("") || introduction.equals("")
				|| timetable_id.equals("") || price.equals("")
				|| start_time.equals("") || end_time.equals("")
				|| duration.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) 
				|| !NumberUtil.isInteger(room_id)
				|| !NumberUtil.isInteger(timetable_id)
				|| !NumberUtil.isDouble(price)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课表ID
				Integer i_timetable_id=Integer.valueOf(timetable_id);
				//去空课时名称
				name=name.trim();
				//1.判断是否重复新增课时
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("uid", i_uid);
				map.put("utype", utype);
				map.put("room_id", i_room_id);
				map.put("timetable_id", i_timetable_id);
				map.put("name", name);
				LiveChapterPlan chapter=this.livePrepareService.getChapterPlanByUid(map);
				//如果存在
				if(chapter!=null) {
					errorCode = "20084";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20084;
				} else {
					chapter=new LiveChapterPlan();
					//获取当前时间
					Date date=new Date();
					String time=TimeUtil.getTimeByDate(date);
					chapter.setRoomId(i_room_id);
					chapter.setTimetableId(i_timetable_id);
					chapter.setOwner(i_uid);
					chapter.setOwnerType(utype);
					chapter.setCreateTime(date);
					chapter.setOrderCode(time);
					chapter.setStartTime(TimeUtil.strToDate(start_time));
					chapter.setEndTime(TimeUtil.strToDate(end_time));
					chapter.setName(name);
					chapter.setIntroduction(introduction);
					double d_price=Double.parseDouble(price);
					if(d_price>0) {
						chapter.setIsFree(0);
					} else {
						chapter.setIsFree(1);
					}
					chapter.setLivePrice(d_price);
					chapter.setRecordPrice(d_price);
					chapter.setDuration(duration);
					chapter.setIsPublish(1);
					
					//修改直播间课时数量
//					LiveRoom room=new LiveRoom();
//					room.setId(i_room_id);
//					room.setPreNumber(1);
//					room.setChapterNumber(1);
					
					try {
						//this.livePrepareService.insertOneLiveChapterPlan(chapter,room);
						this.livePrepareService.insertOneLiveChapterPlan(chapter);
						errorCode="0";
						errorMessage="ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20085";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20085;
					}
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		SimpleReBean reBean = new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}
	/***
	 * 设置直播课表
	 */
//	@RequestMapping(value = "/timetable/set", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public @ResponseBody Object timeTableSet(HttpServletRequest request, HttpServletResponse response) {
//		String errorCode = "";
//		String errorMessage = "";
//		
//		//以下是必选参数
//		String access_token=request.getParameter("access_token"); 
//		String uid=request.getParameter("uid");
//		String utype=request.getParameter("utype");
//		String room_id=request.getParameter("room_id");		
//		String name=request.getParameter("name");		
//		String introduction=request.getParameter("introduction");		
//		String major_one=request.getParameter("major_one");		
//		String major_two=request.getParameter("major_two");		
//		String live_type=request.getParameter("live_type");		
//		//以下不是必选参数
//		String price=request.getParameter("price");	
//		
//		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
//				+"-utype:"+utype+"-room_id:"+room_id+"-name:"+name
//				+"-introduction:"+introduction+"-major_one:"+major_one
//				+"-major_two:"+major_two+"-live_type:"+live_type+"-price:"+price);
//		
//		if(access_token==null  || uid==null || utype==null
//				|| room_id==null || name==null 
//				|| introduction==null || major_one==null
//				|| major_two==null || live_type==null) {
//			errorCode = "20032";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
//		} else if(access_token.equals("") || uid.equals("") 
//				|| utype.equals("") || room_id.equals("")
//				|| name.equals("") || introduction.equals("")
//				|| major_one.equals("") || major_two.equals("")
//				|| live_type.equals("")) {
//			errorCode = "20032";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
//		} else if(!NumberUtil.isInteger(uid) 
//				|| !NumberUtil.isInteger(room_id)
//				|| !NumberUtil.isInteger(major_one)
//				|| !NumberUtil.isInteger(major_two)
//				|| !NumberUtil.isInteger(live_type)) {
//			errorCode = "20033";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
//		} else {
//			boolean tokenFlag = tokenService.checkToken(access_token);
//			if (tokenFlag) {
//				//用户ID
//				Integer i_uid=Integer.valueOf(uid);
//				//直播间ID
//				Integer i_room_id=Integer.valueOf(room_id);
//				//去空课表名称
//				name=name.trim();
//				//1.首先判断是否重复创建课表
//				Map<String, Object> map=new HashMap<String, Object>();
//				map.put("uid", i_uid);
//				map.put("utype", utype);
//				map.put("room_id", i_room_id);
//				map.put("name", name);
//				LiveTimeTable timeTable=this.livePrepareService.getLiveTimeTableByUid(map);
//				//如果存在 则说明已经新增过相同名称的课表 则提醒
//				if(timeTable!=null) {
//					errorCode = "20082";
//					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20082;
//				} else {
//					//课表所属专业大类
//					Integer i_major_one=Integer.valueOf(major_one);
//					//课表所属专业二类
//					Integer i_major_two=Integer.valueOf(major_two);
//					//课表所属类型
//					Integer i_live_type=Integer.valueOf(live_type);
//					//获取当前时间
//					Date date=new Date();
//					String time=TimeUtil.getTimeByDate(date);
//					timeTable = new LiveTimeTable();
//					timeTable.setCreateTime(date);
//					timeTable.setOrderCode(time);
//					timeTable.setName(name);
//					timeTable.setMajorOne(i_major_one);
//					timeTable.setMajorTwo(i_major_two);
//					timeTable.setLiveType(i_live_type);
//					timeTable.setIntroduction(introduction);
//					timeTable.setRoomId(i_room_id);
//					timeTable.setOwner(i_uid);
//					timeTable.setOwnerType(utype);
//					timeTable.setIsPublish(1);
//					double d_price=0;
//					if(price!=null && NumberUtil.isDouble(price)) {
//						d_price=Double.parseDouble(price);
//					}
//					if(d_price>0) {
//						timeTable.setIsFree(0);
//					} else {
//						timeTable.setIsFree(1);
//					}
//					timeTable.setPrice(d_price);
//					
//					try {
//						this.livePrepareService.insertOneLiveTimeTable(timeTable);
//						errorCode="0";
//						errorMessage="ok";
//					} catch (Exception e) {
//						// TODO: handle exception
//						errorCode = "20083";
//						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20083;
//					}
//				}				
//			} else {
//				errorCode = "20028";
//				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
//			}
//		}
//		SimpleReBean reBean = new SimpleReBean();
//		reBean.setError_code(errorCode);
//		reBean.setError_msg(errorMessage);
//		
//		Gson gson = new Gson();
//		ServerLog.getLogger().warn(gson.toJson(reBean));
//		return gson.toJson(reBean);
//	}
	/**
	 * 浏览直播间信息
	 */
	@RequestMapping(value = "/room/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object roomShow(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下传递的是必选参数
		String access_token=request.getParameter("access_token"); 
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype);
		
		LiveRoomBean roomBean = new LiveRoomBean();
		if(access_token==null  || uid==null || utype==null) {
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
				Integer i_uid=Integer.valueOf(uid);
				//依据用户ID和类型去查询直播间信息
				LiveRoom room = this.livePrepareService.getLiveRoomByUid(i_uid, utype);
				if(room!=null) {
					roomBean.setRoom_id(room.getId());
					roomBean.setLive_name(room.getLiveName());
					roomBean.setThumbnail(room.getThumbnail());
				} 
				errorCode="0";
				errorMessage="ok";
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		roomBean.setError_code(errorCode);
		roomBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(roomBean));
		return gson.toJson(roomBean);
	}
	/**
	 * 修改直播间信息
	 */
	@RequestMapping(value = "/room/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object roomUpdate(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下传递的是必选参数
		String access_token=request.getParameter("access_token"); 
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		//以下不是必选参数
		String live_name=request.getParameter("live_name"); 
		String thumbnail=request.getParameter("thumbnail"); 
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype
				+"-live_name:"+live_name+"-thumbnail:"+thumbnail);
	
		if(access_token==null  || uid==null || utype==null) {
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
				Integer i_uid=Integer.valueOf(uid);
				
				LiveRoomHistory history=new LiveRoomHistory();
				//获取当前日期
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				history.setLiveName(live_name);
				history.setThumbnail(thumbnail);
				history.setOwner(i_uid);
				history.setOwnerType(utype);
				history.setCreateTime(date);
				history.setOrderCode(time);
				history.setIsPublish(1);
				
				try {
					this.livePrepareService.insertOneLiveRoomHistory(history);
					errorCode="0";
					errorMessage="ok";
				} catch (Exception e) {
					// TODO: handle exception
					errorCode = "20087";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20087;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		SimpleReBean reBean=new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}
	
	/**
	 * 设置直播间信息
	 */
	@RequestMapping(value = "/room/set", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object roomSet(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下传递的是必选参数
		String access_token=request.getParameter("access_token"); 
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String live_name=request.getParameter("live_name"); 
		String thumbnail=request.getParameter("thumbnail"); 
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype
				+"-live_name:"+live_name+"-thumbnail:"+thumbnail);
		
		if(access_token==null  || uid==null || utype==null
				|| live_name==null || thumbnail==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || live_name.equals("")
				|| thumbnail.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else { 
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				Integer i_uid=Integer.valueOf(uid);
				//1.首先去判断教师直播资质是否已经审核通过
				LiveAuthWithBLOBs liveAuth=this.livePrepareService.getLiveAuthByUid(i_uid, utype);
				if(liveAuth!=null) {
					Integer is_publish=liveAuth.getIsPublish();
					//2--直播资质认证审核通过 0--保存 1--提交资质认证
					if(is_publish==2) {
						//继续判断是否设置过直播间
						LiveRoom liveRoom=this.livePrepareService.getLiveRoomByUid(i_uid, utype);
						if(liveRoom==null) {
							//获取当前日期
							Date date = new Date();
							String time = TimeUtil.getTimeByDate(date);
							liveRoom = new LiveRoom();
							liveRoom.setLiveName(live_name);
							liveRoom.setThumbnail(thumbnail);
							liveRoom.setOwner(i_uid);
							liveRoom.setOwnerType(utype);
							liveRoom.setCreateTime(date);
							liveRoom.setOrderCode(time);
							liveRoom.setIsPublish(1);
							this.livePrepareService.insertOneLiveRoom(liveRoom);
							errorCode="0";
							errorMessage="ok";
						} else {
							errorCode = "20086";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20086;
						} 
					} else {
						errorCode = "20081";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20081;
					}
				} else {
					errorCode = "20080";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20080;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		SimpleReBean reBean=new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}
	/***
	 * 浏览直播资格认证信息
	 */
//	@RequestMapping(value = "/auth/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public @ResponseBody Object authShow(HttpServletRequest request, HttpServletResponse response) {
//		String errorCode = "";
//		String errorMessage = "";
//		
//		//以下传递的是必选参数
//		String access_token=request.getParameter("access_token"); 
//		String uid=request.getParameter("uid");
//		String utype=request.getParameter("utype");
//				
//		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
//					+"-utype:"+utype);
//			
//		LiveAuthBean auth=new LiveAuthBean();
//		
//		if(access_token==null  || uid==null || utype==null) {
//			errorCode = "20032";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
//		} else if(access_token.equals("") || uid.equals("") 
//				|| utype.equals("")) {
//			errorCode = "20032";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
//		} else if(!NumberUtil.isInteger(uid)) {
//			errorCode = "20033";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
//		} else { 
//			boolean tokenFlag = tokenService.checkToken(access_token);
//			if (tokenFlag) {
//				Integer i_uid=Integer.valueOf(uid);
//				LiveAuthWithBLOBs liveAuth=this.livePrepareService.getLiveAuthByUid(i_uid, utype);
//				//2.依据用户ID来判断是否具备直播资质
//				if(liveAuth!=null) {
//					//第一步
//					auth.setCity(liveAuth.getCity());
//					auth.setAddress(liveAuth.getAddress());					
//					auth.setWork_place(liveAuth.getWorkPlace());
//					auth.setWork_year(liveAuth.getWorkYear());
//					auth.setBirth(liveAuth.getBirth());
//					auth.setId_number(liveAuth.getIdNumber());
//					//第二步
//					auth.setIntroduction(liveAuth.getIntroduction());
//					auth.setResume(liveAuth.getResume());
//					auth.setWin_price(liveAuth.getWinPrice());
//					//第三步
//					auth.setId_number_pic(liveAuth.getIdNumberPic());
//					auth.setTeaching_certify_pic(liveAuth.getTeachingCertifyPic());
//					auth.setGraduate_certify_pic(liveAuth.getGraduateCertifyPic());
//					auth.setIndustry_certify_pic(liveAuth.getIndustryCertifyPic());
//					auth.setOther_certify_pic(liveAuth.getOtherCertifyPic());
//					
//					auth.setIs_publish(liveAuth.getIsPublish());
//					//end
//					errorCode = "0";
//					errorMessage = "ok";
//				} else {
//					errorCode = "20080";
//					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20080;
//				}
//			} else {
//				errorCode = "20028";
//				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
//			}
//		}
//		auth.setError_code(errorCode);
//		auth.setError_msg(errorMessage);
//		
//		Gson gson=new Gson();
//		ServerLog.getLogger().warn(gson.toJson(auth));
//		return gson.toJson(auth);
//	}
	/***
	 * 直播资格认证
	 * 
	 */
	@RequestMapping(value = "/auth/set", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object authSet(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选操作
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		
		LiveAuthBean auth=new LiveAuthBean();
		
		if(access_token==null  || uid==null || utype==null) {
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
				//传递的认证步骤来判断具体操作
				String auth_step=request.getParameter("auth_step");
				Integer i_uid=Integer.valueOf(uid);
				//1.首先依据用户ID来获取老师用户信息
				UserTech tec = this.userTecService.getOneUserTecById(i_uid);
				String user_mobile="";
				if(tec!=null) {
					user_mobile=tec.getUserMobile();
					//继续判断手机号是否符合要求
					if(PhoneUtil.isMobile(user_mobile)) {
						//如果手机号符合要求 则继续判断
						auth.setName(tec.getName());
						auth.setSex(tec.getSex());
						auth.setTelephone(user_mobile);
						auth.setUid(i_uid);
						auth.setUtype(utype);
						
						LiveAuthWithBLOBs liveAuth=this.livePrepareService.getLiveAuthByUid(i_uid, utype);
					
						//传递的认证步骤==null或者为空 点击直播认证
						if(auth_step==null || auth_step.equals("")) {
							//2.依据用户ID来判断是否具备直播资质
							if(liveAuth!=null) {
								//第一步
								auth.setCity(liveAuth.getCity());
								auth.setAddress(liveAuth.getAddress());					
								auth.setWork_place(liveAuth.getWorkPlace());
								auth.setWork_year(liveAuth.getWorkYear());
								auth.setBirth(liveAuth.getBirth());
								auth.setId_number(liveAuth.getIdNumber());
								//第二步
								auth.setIntroduction(liveAuth.getIntroduction());
								auth.setResume(liveAuth.getResume());
								auth.setWin_price(liveAuth.getWinPrice());
								//第三步
								auth.setId_number_pic(liveAuth.getIdNumberPic());
								auth.setTeaching_certify_pic(liveAuth.getTeachingCertifyPic());
								auth.setGraduate_certify_pic(liveAuth.getGraduateCertifyPic());
								auth.setIndustry_certify_pic(liveAuth.getIndustryCertifyPic());
								auth.setOther_certify_pic(liveAuth.getOtherCertifyPic());
								
								auth.setIs_publish(liveAuth.getIsPublish());
								//end
							}
							errorCode = "0";
							errorMessage = "ok";
						} else if(auth_step.equals("first")) {
							//获取传递的参数
							String city=request.getParameter("city");
							String address=request.getParameter("address");
							String work_place=request.getParameter("work_place");
							String work_year=request.getParameter("work_year");
							String birth=request.getParameter("birth");
							String id_number=request.getParameter("id_number");
							ServerLog.getLogger().warn("city:"+city+"-address:"+address+"-work_place:"+work_place
									+"-work_year:"+work_year+"-birth:"+birth+"-id_number:"+id_number);
							
							//如果未申请过直播资质认证
							if(liveAuth==null) {
								if(birth==null || id_number==null) {
									errorCode = "20032";
									errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
								} else if(birth.equals("") || id_number.equals("")) {
									errorCode = "20032";
									errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
								} else {
									//获取当前日期
									Date date = new Date();
									String time = TimeUtil.getTimeByDate(date);
									liveAuth = new LiveAuthWithBLOBs();
									liveAuth.setCity(city);
									liveAuth.setAddress(address);
									liveAuth.setWorkPlace(work_place);
									liveAuth.setWorkYear(work_year);
									liveAuth.setBirth(birth);
									liveAuth.setIdNumber(id_number);
									liveAuth.setOwner(i_uid);
									liveAuth.setOwnerType(utype);
									liveAuth.setCreateTime(date);
									liveAuth.setOrderCode(time);
										
									this.livePrepareService.insertOneLiveAuth(liveAuth);
								}
							} else {
									liveAuth.setCity(city);
									liveAuth.setAddress(address);
									liveAuth.setWorkPlace(work_place);
									liveAuth.setWorkYear(work_year);
									liveAuth.setBirth(birth);
									liveAuth.setIdNumber(id_number);
									this.livePrepareService.updateOneLiveAuth(liveAuth);
							}
							//第一步
							auth.setCity(liveAuth.getCity());
							auth.setAddress(liveAuth.getAddress());					
							auth.setWork_place(liveAuth.getWorkPlace());
							auth.setWork_year(liveAuth.getWorkYear());
							auth.setBirth(liveAuth.getBirth());
							auth.setId_number(liveAuth.getIdNumber());
							//第二步
							auth.setIntroduction(liveAuth.getIntroduction());
							auth.setResume(liveAuth.getResume());
							auth.setWin_price(liveAuth.getWinPrice());
							//第三步
							auth.setId_number_pic(liveAuth.getIdNumberPic());
							auth.setTeaching_certify_pic(liveAuth.getTeachingCertifyPic());
							auth.setGraduate_certify_pic(liveAuth.getGraduateCertifyPic());
							auth.setIndustry_certify_pic(liveAuth.getIndustryCertifyPic());
							auth.setOther_certify_pic(liveAuth.getOtherCertifyPic());
								
							auth.setIs_publish(liveAuth.getIsPublish());
							//end
							errorCode = "0";
							errorMessage = "ok";
								
						} else if(auth_step.equals("second")) {
							//获取传递的参数
							String introduction=request.getParameter("introduction");
							String resume=request.getParameter("resume");
							String win_price=request.getParameter("win_price");
							
							if(liveAuth!=null) {
								liveAuth.setIntroduction(introduction);
								liveAuth.setResume(resume);
								liveAuth.setWinPrice(win_price);
								
								this.livePrepareService.updateOneLiveAuth(liveAuth);
								//第一步
								auth.setCity(liveAuth.getCity());
								auth.setAddress(liveAuth.getAddress());					
								auth.setWork_place(liveAuth.getWorkPlace());
								auth.setWork_year(liveAuth.getWorkYear());
								auth.setBirth(liveAuth.getBirth());
								auth.setId_number(liveAuth.getIdNumber());
								//第二步
								auth.setIntroduction(liveAuth.getIntroduction());
								auth.setResume(liveAuth.getResume());
								auth.setWin_price(liveAuth.getWinPrice());
								//第三步
								auth.setId_number_pic(liveAuth.getIdNumberPic());
								auth.setTeaching_certify_pic(liveAuth.getTeachingCertifyPic());
								auth.setGraduate_certify_pic(liveAuth.getGraduateCertifyPic());
								auth.setIndustry_certify_pic(liveAuth.getIndustryCertifyPic());
								auth.setOther_certify_pic(liveAuth.getOtherCertifyPic());
								
								auth.setIs_publish(liveAuth.getIsPublish());
								//end
							}
							errorCode = "0";
							errorMessage = "ok";
						} else if(auth_step.equals("third")) {
							//获取传递的参数
							String id_number_pic=request.getParameter("id_number_pic");
							String teaching_certify_pic=request.getParameter("teaching_certify_pic");
							String graduate_certify_pic=request.getParameter("graduate_certify_pic");
							String industry_certify_pic=request.getParameter("industry_certify_pic");
							String other_certify_pic=request.getParameter("other_certify_pic");
							
							//将教师直播资质认证状态修改为1 0--保存 1--提交资质审核 2--审核通过
							if(liveAuth!=null) {
								if(id_number_pic==null || id_number_pic.equals("")) {
									errorCode = "20032";
									errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
								} else {
									liveAuth.setIdNumberPic(id_number_pic);
									liveAuth.setTeachingCertifyPic(teaching_certify_pic);
									liveAuth.setGraduateCertifyPic(graduate_certify_pic);
									liveAuth.setIndustryCertifyPic(industry_certify_pic);
									liveAuth.setOtherCertifyPic(other_certify_pic);
									liveAuth.setIsPublish(1);
									this.livePrepareService.updateOneLiveAuth(liveAuth);
									//第一步
									auth.setCity(liveAuth.getCity());
									auth.setAddress(liveAuth.getAddress());					
									auth.setWork_place(liveAuth.getWorkPlace());
									auth.setWork_year(liveAuth.getWorkYear());
									auth.setBirth(liveAuth.getBirth());
									auth.setId_number(liveAuth.getIdNumber());
									//第二步
									auth.setIntroduction(liveAuth.getIntroduction());
									auth.setResume(liveAuth.getResume());
									auth.setWin_price(liveAuth.getWinPrice());
									//第三步
									auth.setTeaching_certify_pic(liveAuth.getTeachingCertifyPic());
									auth.setGraduate_certify_pic(liveAuth.getGraduateCertifyPic());
									auth.setIndustry_certify_pic(liveAuth.getIndustryCertifyPic());
									auth.setOther_certify_pic(liveAuth.getOtherCertifyPic());
										
									auth.setIs_publish(liveAuth.getIsPublish());
									//end
									errorCode = "0";
									errorMessage = "ok";
								}
							} 	
						}
					} else {//如果不符合手机号要求 则提示错误
						errorCode = "20044";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
					}
				} else {
					errorCode = "20079";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20079;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		auth.setError_code(errorCode);
		auth.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(auth));
		return gson.toJson(auth);
	}
	
}

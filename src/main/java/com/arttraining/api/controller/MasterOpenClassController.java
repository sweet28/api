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
import com.arttraining.api.beanv2.LiveChapterListBean;
import com.arttraining.api.beanv2.LiveTimeTableBean;
import com.arttraining.api.beanv2.MasterOpenClassEnterLiveBean;
import com.arttraining.api.beanv2.MasterOpenClassPublishUrlBean;
import com.arttraining.api.beanv2.MastersLiveCloseBean;
import com.arttraining.api.beanv2.OpenClassEnterLiveBean;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.OpenClassLiveService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.LiveUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;
import com.qiniu.pili.PiliException;


@Controller
@RequestMapping("/masters/live")
public class MasterOpenClassController {
	@Resource
	private TokenService tokenService;
	@Resource
	private OpenClassLiveService openClassLiveService;
	@Resource
	private UserTecService userTecService;
	
	/**
	 * 老师查看课时列表调用的接口
	 */
	@RequestMapping(value = "/chapter/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object chapterList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String timetable_id=request.getParameter("timetable_id");
		String room_id=request.getParameter("room_id");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-timetable_id:"
					+timetable_id+"-room_id"+room_id);
		
		List<LiveChapterListBean> chapter_list = new ArrayList<LiveChapterListBean>();
		
		if(access_token==null || timetable_id==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || timetable_id.equals("") 
				|| room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(timetable_id) ||
				!NumberUtil.isInteger(room_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//课程ID
				Integer i_timetable_id=Integer.valueOf(timetable_id);
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//1.查询课时列表
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("timetable_id", i_timetable_id);
				map.put("room_id", i_room_id);
				//继续获取其他课时列表信息 按照课时开课的顺序
				chapter_list = this.openClassLiveService.getChapterListById(map);
				if(chapter_list.size()>0) {
					errorCode="0";
					errorMessage="ok";
				} else {
					chapter_list = new ArrayList<LiveChapterListBean>();
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("chapter_list", chapter_list);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 老师查看课程列表调用的接口
	 */
	@RequestMapping(value = "/timetable/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object timetableList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
					+"-utype:"+utype+"-room_id"+room_id);
		
		List<LiveTimeTableBean> timetable_list=new ArrayList<LiveTimeTableBean>();
		
		if(access_token==null || uid==null || utype==null
					|| room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
					|| utype.equals("") || room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) ||
				!NumberUtil.isInteger(room_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//1.获取课程列表
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("uid", i_uid);
				map.put("utype", utype);
				map.put("room_id", i_room_id);
				timetable_list=this.openClassLiveService.getLiveTimeTableByUid(map);
				if(timetable_list.size()>0) {
					//循环读取课程列表
					for (LiveTimeTableBean timetable : timetable_list) {
						//课程ID
						Integer i_timetable_id=timetable.getTimetable_id();
						map.put("timetable_id", i_timetable_id);
						//继续获取其他课时列表信息 按照课时开课的顺序
						List<LiveChapterListBean> chapter_list = this.openClassLiveService.getChapterListById(map);
						if(chapter_list.size()>0) {
							//循环读取课时信息
							for (LiveChapterListBean chapter : chapter_list) {
								chapter.setOrder_status(-1);
							}
							timetable.setChapter_list(chapter_list);
						}
					}
					errorCode="0";
					errorMessage="ok";
				} else {
					timetable_list=new ArrayList<LiveTimeTableBean>();
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("timetable_list", timetable_list);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
	}
	
	/**
	 * 老师开启/禁用直播发言调用的接口
	 */
	@RequestMapping(value = "/talk", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object talk(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String chapter_id=request.getParameter("chapter_id");
		String is_talk=request.getParameter("is_talk");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-is_talk:"+is_talk
				+"-chapter_id:"+chapter_id);
		
		if(access_token==null || is_talk==null || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || is_talk.equals("")
				|| chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//修改课时是否禁言的状态
				//启用remark3字段--存放是否禁言该课时直播
				LiveChapterPlan upd_chapter=new LiveChapterPlan();
				upd_chapter.setId(i_chapter_id);
				upd_chapter.setRemarks3(is_talk);
				this.openClassLiveService.updateChapterInfo(upd_chapter);
				errorCode="0";
				errorMessage="ok";
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
	
	/**
	 * 老师关闭课时直播调用的接口
	 */
	@RequestMapping(value = "/close", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object close(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String chapter_id=request.getParameter("chapter_id");
		String room_id=request.getParameter("room_id");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-room_id:"+room_id
				+"-chapter_id:"+chapter_id);
		
		MastersLiveCloseBean closeBean=new MastersLiveCloseBean();
		
		if(access_token==null || room_id==null || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || room_id.equals("")
				|| chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(room_id) ||
				  !NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//房间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//1.首先依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.修改直播间和课时信息
					this.openClassLiveService.updateLiveRoomAndChapterInfo(room, i_chapter_id);
					//3.继续获取老师头像信息和课时信息
					//主播ID和类型
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					UserTech tec=this.userTecService.getOneUserTecById(owner);
					if(tec!=null) {
						closeBean.setOwner(owner);
						closeBean.setOwner_type(owner_type);
						closeBean.setName(tec.getName());
						closeBean.setHead_pic(tec.getHeadPic());
					}
					//4.继续获取课时信息
					LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
					if(chapter!=null) {
						//closeBean.setBrowse_number(chapter.getBuyNumber());
						closeBean.setBrowse_number(chapter.getBrowseNumber());
						closeBean.setDuration(Integer.parseInt(chapter.getDuration()));
						//统计老师收到的赠送礼物数
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", owner);
						map.put("utype", owner_type);
						map.put("chapter_id", chapter.getId());
						Integer gift_number=this.openClassLiveService.getLiveGiftNumber(map);
						closeBean.setGift_number(gift_number);
						//end
					}
					errorCode="0";
					errorMessage="ok";
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		closeBean.setError_code(errorCode);
		closeBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(closeBean));
		return gson.toJson(closeBean);
		
	}
	/**
	 * 老师调整好直播的灯光等其他前奏信息后 点击确定时调用的接口
	 * 
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object confirm(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String chapter_id=request.getParameter("chapter_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype+"-chapter_id"+chapter_id);
		
		if(access_token==null || uid==null || utype==null
				|| chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) ||
				!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				//Integer i_uid=Integer.valueOf(uid);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//判断课时ID的直播状态是否为2
				LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
				if(chapter!=null) {
					//如果是直播结束
					if(chapter.getLiveStatus()==2) {
						LiveRoom upd_room=new LiveRoom();
						upd_room.setId(chapter.getRoomId());
						upd_room.setPreNumber(1);
						upd_room.setPreTime(TimeUtil.getTimeByDate(chapter.getStartTime()));
						this.openClassLiveService.updateOnePreNumByRoomId(upd_room);
					}
				}
				//1.确认开启直播
				LiveChapterPlan upd_chapter=new LiveChapterPlan();
				upd_chapter.setId(i_chapter_id);
				upd_chapter.setLiveStatus(1);
				this.openClassLiveService.updateChapterInfo(upd_chapter);
				errorCode="0";
				errorMessage="ok";
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
	 * 老师开始直播获取推流地址调用的接口
	 * 
	 */
	@RequestMapping(value = "/publish/url", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publishUrl(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String chapter_id=request.getParameter("chapter_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype+"-chapter_id"+chapter_id);
		
		MasterOpenClassPublishUrlBean publishBean = new MasterOpenClassPublishUrlBean();
		
		if(access_token==null || uid==null || utype==null
				|| chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) ||
				!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//依据主播ID和类型去查询直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomByUid(i_uid, utype);
				if(room!=null) {
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
					//是否修改课时信息标识
					boolean chapter_flag=false;
					//获取原来的推流地址
					boolean old_url=false;
					//2.继续判断直播间的课时状态 主播ID和类型
					Integer i_room_id=room.getId();
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("room_id", i_room_id);
					//3.获取直播信息
					OpenClassEnterLiveBean enterBean = this.openClassLiveService.getLiveRoomInfoById(map);
					if(enterBean!=null) {
						publishBean.setOwner(i_uid);
						publishBean.setOwner_type(utype);
						publishBean.setName(publishBean.getName());
						publishBean.setHead_pic(enterBean.getHead_pic());
						publishBean.setFollow_number(enterBean.getFollow_number());
						publishBean.setLike_number(enterBean.getLike_number());
						publishBean.setChapter_number(enterBean.getChapter_number());
					}
					map.put("live_status", 1);
					LiveChapterPlan chapter=this.openClassLiveService.getChapterInfoByOwner(map);
					if(chapter!=null) {
						old_url=true;
						stream = chapter.getStreamKey();
						publish_url=chapter.getPublishUrl();
						rtmp_url=chapter.getRtmpUrl();
						hls_url=chapter.getHlsUrl();
						hdl_url=chapter.getHdlUrl();
						snapshot_url=chapter.getSnapshotUrl();
					} else {
						map.put("live_status", 2);
						LiveChapterPlan chapter2=this.openClassLiveService.getChapterInfoByOwner(map);
						if(chapter2!=null) {
							//获取直播开始时间和结束时间
							Date start=chapter2.getStartTime();
							Date end=chapter2.getEndTime();
							//获取当前时间
							Date current=new Date();
							//如果当前时间在直播开始和结束时间之内 则允许再次开播
							if(current.getTime()>=start.getTime() 
									&& current.getTime()<=end.getTime()) {
								old_url=true;
								stream = chapter2.getStreamKey();
								publish_url=chapter2.getPublishUrl();
								rtmp_url=chapter2.getRtmpUrl();
								hls_url=chapter2.getHlsUrl();
								hdl_url=chapter2.getHdlUrl();
								snapshot_url=chapter2.getSnapshotUrl();
							} 
						}
					}
					if(old_url) {
						//启用流
						try {
							LiveUtil.enableStream(stream);
							publishBean.setPublish_url(publish_url);
							errorCode="0";
							errorMessage="ok";
						} catch (PiliException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							errorCode = "20070";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20070;
						}
					} else {
						try {
							Map<String, String> urlMap = LiveUtil.createLiveUrl();
							stream = urlMap.get("stream");
							publish_url= urlMap.get("publish_url");
							rtmp_url= urlMap.get("rtmp_url");
							hls_url= urlMap.get("hls_url");
							hdl_url= urlMap.get("hdl_url");
							snapshot_url= urlMap.get("snapshot_url");
							chapter_flag=true;
							publishBean.setPublish_url(publish_url);
						} catch (PiliException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							errorCode = "20070";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20070;
						}
					}
					if(chapter_flag) {
						//启用remark1备用字段--开启直播时间
						//获取当前时间
						Date date = new Date();
						String time=TimeUtil.getTimeByDate(date);
						//修改课时信息
						LiveChapterPlan upd_chapter=new LiveChapterPlan();
						upd_chapter.setId(i_chapter_id);
						upd_chapter.setRemarks1(time);
						upd_chapter.setPublishUrl(publish_url);
						upd_chapter.setRtmpUrl(rtmp_url);
						upd_chapter.setHdlUrl(hdl_url);
						upd_chapter.setHlsUrl(hls_url);
						upd_chapter.setSnapshotUrl(snapshot_url);
						upd_chapter.setStreamKey(stream);
						
						this.openClassLiveService.updateChapterInfo(upd_chapter);
						errorCode="0";
						errorMessage="ok";
					}
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			}  else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		publishBean.setError_code(errorCode);
		publishBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(publishBean));
		return gson.toJson(publishBean);
	}
	/***
	 * 老师点击直播按钮调用的接口,用于返回直播状态
	 */
	@RequestMapping(value = "/status", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object status(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		
		//直播状态
		Integer live_status=-1;
		//直播间ID
		Integer i_room_id=0;
		
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
				//依据主播ID和类型去查询直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomByUid(i_uid, utype);
				if(room!=null) {
					//2.继续判断直播间的课时状态 主播ID和类型
					i_room_id=room.getId();
					//3.获取直播间信息
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("room_id", i_room_id);
					//4.如果存在预告课时 则直接查询课时信息
					map.put("live_status", 2);
					LiveChapterPlan chapter2=this.openClassLiveService.getChapterInfoByOwner(map);
					if(chapter2!=null) {
						//获取直播开始时间和结束时间
						Date start=chapter2.getStartTime();
						Date end=chapter2.getEndTime();
						//获取当前时间
						Date current=new Date();
						//如果当前时间在直播开始和结束时间之内 则允许再次开播
						if(current.getTime()>=start.getTime() 
								&& current.getTime()<=end.getTime()) {
							live_status=1;
						} else {
							live_status=2;
						}
					}
					map.put("live_status", 0);
					LiveChapterPlan chapter0=this.openClassLiveService.getChapterInfoByOwner(map);
					if(chapter0!=null) {
						live_status=0;
					}
					map.put("live_status", 1);
					LiveChapterPlan chapter1=this.openClassLiveService.getChapterInfoByOwner(map);
					if(chapter1!=null) {
						live_status=1;
					} 
					errorCode="0";
					errorMessage="ok";
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("live_status", live_status);
		jsonObject.put("room_id", i_room_id);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/***
	 * 老师点击直播按钮时调用的接口
	 * 
	 */
	@RequestMapping(value = "/enter", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object enter(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		
		MasterOpenClassEnterLiveBean masterEnterBean = new MasterOpenClassEnterLiveBean();
		
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
				//依据主播ID和类型去查询直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomByUid(i_uid, utype);
				if(room!=null) {
					//2.继续判断直播间的课时状态 主播ID和类型
					Integer i_room_id=room.getId();
					//3.获取直播间信息
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("room_id", i_room_id);
					
					masterEnterBean.setRoom_id(i_room_id);
					//父类强制类型转换成子类 begin
					OpenClassEnterLiveBean enterBean= this.openClassLiveService.getLiveRoomInfoById(map);
					masterEnterBean.setOwner(enterBean.getOwner());
					masterEnterBean.setOwner_type(enterBean.getOwner_type());
					masterEnterBean.setName(enterBean.getName());
					masterEnterBean.setHead_pic(ImageUtil.parsePicPath(enterBean.getHead_pic(), 5));
					masterEnterBean.setLike_number(enterBean.getLike_number());
					masterEnterBean.setFollow_number(enterBean.getFollow_number());
					masterEnterBean.setChapter_number(enterBean.getChapter_number());
					masterEnterBean.setPre_time(enterBean.getPre_time());
					masterEnterBean.setCurr_time(enterBean.getCurr_time());
					//end
					Integer live_status=-1;
					Boolean flag=false;
					map.put("live_status", 2);
					LiveChapterPlan chapter2=this.openClassLiveService.getChapterInfoByOwner(map);
					if(chapter2!=null) {
						//获取直播开始时间和结束时间
						Date start=chapter2.getStartTime();
						Date end=chapter2.getEndTime();
						//获取当前时间
						Date current=new Date();
						//System.out.println(current.getTime()+"=="+
								//start.getTime()+"=="+end.getTime());
						//如果当前时间在直播开始和结束时间之内 则允许再次开播
						if(current.getTime()>=start.getTime() 
								&& current.getTime()<=end.getTime()) {
							live_status=1;
							masterEnterBean.setChapter_id(chapter2.getId());
							masterEnterBean.setChapter_name(chapter2.getName());
							masterEnterBean.setIs_talk(chapter2.getRemarks3());
							masterEnterBean.setPre_time(TimeUtil.getTimeByDate(chapter2.getStartTime()));
						} else {
							flag=true;
						}
					} else {
						flag=true;
					}
					if(flag) {
						//如果存在正在直播课时 则直接查询课时信息
						map.put("live_status", 1);
						LiveChapterPlan chapter1=this.openClassLiveService.getChapterInfoByOwner(map);
						if(chapter1!=null) {
							live_status=1;
							masterEnterBean.setChapter_id(chapter1.getId());
							masterEnterBean.setChapter_name(chapter1.getName());
							masterEnterBean.setIs_talk(chapter1.getRemarks3());
						} else {
							//4.如果存在预告课时 则直接查询课时信息
							map.put("live_status", 0);
							LiveChapterPlan chapter0=this.openClassLiveService.getChapterInfoByOwner(map);
							if(chapter0!=null) {
								live_status=0;
								masterEnterBean.setChapter_id(chapter0.getId());
								masterEnterBean.setChapter_name(chapter0.getName());
								masterEnterBean.setIs_talk(chapter0.getRemarks3());
							} else {
								live_status=2;
							}
						}
					}
					masterEnterBean.setLive_status(live_status);
					//5.查询课时列表
					//继续获取其他课时列表信息 按照课时开课的顺序
//					List<LiveChapterListBean> chapter_list=this.openClassLiveService.getChapterListById(map);
//					if(chapter_list.size()>0) {
//						masterEnterBean.setChapter_list(chapter_list);
//					}
					errorCode="0";
					errorMessage="ok";
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		masterEnterBean.setError_code(errorCode);
		masterEnterBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(masterEnterBean));
		
		return gson.toJson(masterEnterBean);
	}
}

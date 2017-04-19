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
import com.arttraining.api.beanv2.LiveChapterListBean;
import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.beanv2.LiveGiftListBean;
import com.arttraining.api.beanv2.LiveHistoryBean;
import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.beanv2.LiveTimeTableBean;
import com.arttraining.api.beanv2.LiveTypeList;
import com.arttraining.api.beanv2.OpenClassBeingLiveBean;
import com.arttraining.api.beanv2.OpenClassEnterLiveBean;
import com.arttraining.api.beanv2.OpenClassFinishLiveBean;
import com.arttraining.api.beanv2.OpenClassLiveListBean;
import com.arttraining.api.beanv2.OpenClassLiveListReBean;
import com.arttraining.api.beanv2.OpenClassWaitLiveBean;
import com.arttraining.api.beanv2.WatchHistoryBean;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.Wallet;
import com.arttraining.api.service.impl.OpenClassLiveService;
import com.arttraining.api.service.impl.OrderCourseService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.WalletService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/open/class")
public class OpenClassController {
	@Resource
	private TokenService tokenService;
	@Resource
	private OpenClassLiveService openClassLiveService;
	@Resource
	private OrderCourseService orderCourseService;
	@Resource
	private WalletService walletService;
	
	
	/**
	 * 购买直播课程调用的接口
	 */
	@RequestMapping(value = "/buy/chapter", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object buyChapter(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");
		String buy_type=request.getParameter("buy_type");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-chapter_id:"+chapter_id+"-room_id:"+room_id+
				"-buy_type:"+buy_type);
		
		if(access_token==null || uid==null || utype==null 
				|| room_id==null || chapter_id==null || buy_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("") 	
				|| room_id.equals("") || buy_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) 
				|| !NumberUtil.isInteger(chapter_id) 
				|| !NumberUtil.isInteger(room_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.依据课时ID来查询课时信息
					LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
					if(chapter!=null) {
						//课时费用
						Double chapter_price=0.0;
						if(buy_type.equals("live")) {
							chapter_price=chapter.getLivePrice();
						} else if(buy_type.equals("record")) {
							chapter_price=chapter.getRecordPrice();
						}
						Wallet wallet=this.walletService.getCloudMoneyByUid(i_uid, utype);
						if(wallet!=null) {
							//当前用户的云币
							Double cloud_money=wallet.getCloudMoney();
							if(chapter_price.doubleValue()<=cloud_money.doubleValue()) {
								//如果用户的云币足够支付课时的价格 则扣除相应的用户云币信息
								this.walletService.updateCloudAndDetailInfoByConsume(i_uid, utype, cloud_money, chapter_price);
								errorCode="0";
								errorMessage="ok";
							} else {
								errorCode = "20093";
								errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20093;
							}
						}
					} 
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
		jsonObject.put("data","");
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 首页新增直播列表接口
	 */
	@RequestMapping(value = "/live/home", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveHome(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		OpenClassLiveListReBean liveBean = new OpenClassLiveListReBean();
		
		//分页所用数据
		Integer limit=ConfigUtil.HOMELIVE_PAGESIZE;
		
		//首先去查询指定大小的直播间信息 按照order_code降序排列
		List<OpenClassLiveListBean> liveList= this.openClassLiveService.getRoomLiveListByHome(limit);
		int liveSize=liveList.size();
		
		if(liveSize==0) {
			liveList=new ArrayList<OpenClassLiveListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		} else {
			if(liveSize%2!=0) {//奇数直播大小
				liveList.remove(liveSize-1);
			} 
			Map<String, Object> map = new HashMap<String, Object>();
			//查询预告直播状态和直播课时名称
			for (OpenClassLiveListBean live : liveList) {
				Integer owner=live.getOwner();
				String owner_type=live.getOwner_type();
				Integer i_room_id=live.getRoom_id();
				String pre_time=live.getPre_time();
				
				map.put("uid", owner);
				map.put("utype", owner_type);
				map.put("room_id", i_room_id);
				map.put("pre_time", pre_time);
				
				LiveChapterPlan chapter=this.openClassLiveService.getChapterPlanByPreTime(map);
				if(chapter!=null) {
					live.setLive_status(chapter.getLiveStatus());
					live.setChapter_name(chapter.getName());
					live.setChapter_id(chapter.getId());
				}
			}
			liveBean.setOpenclass_list(liveList);
			errorCode="0";
			errorMessage="ok";
		}
		liveBean.setError_code(errorCode);
		liveBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(liveBean));
		return gson.toJson(liveBean);
	}
	
	/**
	 * 用于获取所有的直播礼物列表
	 */
	@RequestMapping(value = "/gift/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object giftList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<LiveGiftListBean> giftList=new ArrayList<LiveGiftListBean>();
		
		//获取直播礼物列表
		giftList = this.openClassLiveService.getLiveGiftList();
		if(giftList.size()>0) {
			errorCode="0";
			errorMessage="ok";
		} else {
			giftList=new ArrayList<LiveGiftListBean>();
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("gift_list", giftList);
			
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
		
	}
	
	/**
	 * 直播期间赠送礼物时调用的接口
	 */
	@RequestMapping(value = "/give/gift", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object giveGift(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");
		String gift_id=request.getParameter("gift_id");
		String number=request.getParameter("number");
						
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
						"-utype:"+utype+"-chapter_id:"+chapter_id+"-gift_id:"+gift_id
						+"-number:"+number+"-room_id:"+room_id);
		if(access_token==null || uid==null || utype==null || room_id==null
				|| chapter_id==null || gift_id==null || number==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("") 
				|| gift_id.equals("")
				|| room_id.equals("") || number.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) 
				|| !NumberUtil.isInteger(chapter_id) 
				|| !NumberUtil.isInteger(room_id)
				|| !NumberUtil.isInteger(gift_id)
				|| !NumberUtil.isInteger(number)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//赠送礼物ID
				//Integer i_gift_id=Integer.valueOf(gift_id);
				//赠送礼物数量
				Integer gift_num=Integer.valueOf(number);
				//1.依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
							
					//新增一条直播评论信息
					LiveComment comment=new LiveComment();
					//获取当前时间
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					comment.setVisitor(i_uid);
					comment.setVisitorType(utype);
					comment.setHost(owner);
					comment.setHostType(owner_type);
					comment.setForeignKey(i_chapter_id);
					comment.setCreateTime(date);
					comment.setOrderCode(time);
					comment.setContent(gift_id);
					comment.setType("comment");
					comment.setBuyNumber(gift_num);
					comment.setRemarks("gift");
					
				try {
						this.openClassLiveService.insertLiveComment(comment);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20073";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20073;
					}
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
				
			ServerLog.getLogger().warn(jsonObject.toString());
			return jsonObject;
	}
	
	/**
	 * 爱好者端获取直播是否禁言状态
	 */
	@RequestMapping(value = "/live/talk", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveTalk(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String chapter_id=request.getParameter("chapter_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-chapter_id"+chapter_id);
		
		String is_talk="";
		
		if(access_token==null || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//获取课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//依据课时ID去查询相应的
				LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
				if(chapter!=null) {
					is_talk=chapter.getRemarks3();
					errorCode = "0";
					errorMessage = "ok";
				} else {
					errorCode = "20089";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20089;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("is_talk",is_talk);
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
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					//1.获取课程列表
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", owner);
					map.put("utype", owner_type);
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
								for (LiveChapterListBean liveChapter : chapter_list) {
									Integer id=liveChapter.getChapter_id();
									boolean flag=this.orderCourseService.getIsBuyChapterById(i_uid, id);
									if(flag) {
										liveChapter.setOrder_status(1);
									} else {
										liveChapter.setOrder_status(0);
									}
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
		jsonObject.put("timetable_list", timetable_list);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
	}
	
	/**
	 * 爱好者端退出直播调用的接口
	 */
	@RequestMapping(value = "/exit/live", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object exitLive(HttpServletRequest request, HttpServletResponse response) {
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
				//1.首先依据直播间ID来获取直播信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//主播ID和主播类型
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					//获取当前时间 begin
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					//end
					LiveDetail detail = new LiveDetail();
					detail.setOwner(owner);
					detail.setOwnerType(owner_type);
					detail.setHost(i_uid);
					detail.setHostType(utype);
					detail.setForeignKey(i_room_id);
					detail.setCreateTime(date);
					detail.setOrderCode(time);
					detail.setLiveOperation("exit");
					
					this.openClassLiveService.insertLiveDetail(detail);
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
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	/**
	 * 直播期间发表评论接口
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
		String chapter_id=request.getParameter("chapter_id");
		String content=request.getParameter("content");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-chapter_id:"+chapter_id+"-content:"+content
				+"-room_id:"+room_id);
		if(access_token==null || uid==null || utype==null 
				|| chapter_id==null || content==null || room_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("") || content.equals("")
				|| room_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(chapter_id) 
				|| !NumberUtil.isInteger(room_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					
					//新增一条直播评论信息
					LiveComment comment=new LiveComment();
					//获取当前时间
					Date date = new Date();
					String time=TimeUtil.getTimeByDate(date);
					comment.setVisitor(i_uid);
					comment.setVisitorType(utype);
					comment.setHost(owner);
					comment.setHostType(owner_type);
					comment.setForeignKey(i_chapter_id);
					comment.setCreateTime(date);
					comment.setOrderCode(time);
					comment.setContent(content);
					comment.setType("comment");
					comment.setRemarks("text");
					
					try {
						this.openClassLiveService.insertLiveComment(comment);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20073";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20073;
					}
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
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/**
	 * 查询直播间学员列表接口
	 */
	@RequestMapping(value = "/member/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object memberList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String room_id=request.getParameter("room_id");
				
		//以下不是必选参数
		String self=request.getParameter("self");
		//coffee add 0110
		String name=request.getParameter("name");
		//end
				
		ServerLog.getLogger().warn("access_token:"+access_token
				+"-room_id:"+room_id+"-self:"+self+"-name:"+name);
		
		List<LiveMemberBean> member_list=new ArrayList<LiveMemberBean>();
		
		Integer offset=-1;
		Integer limit=ConfigUtil.MEMBER_PAGESIZE;
		
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
					//获取成员列表信息(头像 姓名)
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("room_id", i_room_id);
					map.put("offset", offset);
					map.put("limit", limit);
					map.put("name", name);
					//然后获取其他人信息
					member_list=this.openClassLiveService.getLiveMemberByRoomId(map);
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
	/**
	 * 查询直播评论列表接口
	 */
	@RequestMapping(value = "/comment/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object commentList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String chapter_id=request.getParameter("chapter_id");
		
		//以下不是必选参数 分页时所需
		String self=request.getParameter("self");
		Integer offset=-1;
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-chapter_id"+chapter_id+"-self:"+self);
		
		List<LiveCommentBean>  comment_list=new ArrayList<LiveCommentBean>();
		
		if(access_token==null || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				if(self==null || self.equals("")) {
					offset=-1;
				} else if(!NumberUtil.isInteger(self)) {
					offset=-10;
				} else {
					offset=Integer.valueOf(self);
				}
				if(offset==-10) {
					errorCode = "20033";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
				} else {
					//课时ID
					Integer i_chapter_id=Integer.valueOf(chapter_id);
					//评论列表大小
					Integer limit=ConfigUtil.COMMENT_PAGESIZE;
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("chapter_id", i_chapter_id);
					map.put("offset", offset);
					map.put("limit", limit);
					if(offset==-1) {
						//获取当前时间
						//结束时间
						Date endDate=new Date();
						String end=TimeUtil.getTimeByDate(endDate);
						long currtime=endDate.getTime();
						currtime=currtime-5000;
						//开始时间
						Date startDate=new Date(currtime);
						String start=TimeUtil.getTimeByDate(startDate);
						map.put("start_time", start);
						map.put("end_time", end);
					}
					//获取直播课时的评论列表信息
					comment_list=this.openClassLiveService.getLiveCommentByRoomId(map);
					if(comment_list.size()>0) {
						//coffee add 0208 循环读取评论列表 如果是赠送礼物 则去查询礼物pic
						for (LiveCommentBean comment : comment_list) {
							String comm_type=comment.getType();
							String gift_pic="";
							String gift_name="";
							if(comm_type.equals("gift")) {
								Integer id=Integer.valueOf(comment.getContent());
								LiveGift gift=this.openClassLiveService.getGiftInfoById(id);
								if(gift!=null) {
									gift_pic=gift.getStorePath();
									gift_name=gift.getName();
								}
								//gift_pic=this.openClassLiveService.getGiftPicById(id);
							}
							comment.setGift_pic(gift_pic);
							comment.setGift_name(gift_name);
						}
						//end
						errorCode="0";
						errorMessage="ok";
					} else {
						comment_list=new ArrayList<LiveCommentBean>();
						errorCode = "20007";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
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
		jsonObject.put("comment_list", comment_list);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
	}
	/***
	 * 直播类型列表
	 */
	@RequestMapping(value = "/type/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object typeList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<LiveTypeList> typeList = this.openClassLiveService.getLivesTypeList();
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
	
	/**
	 * 爱好者端点击直播结束的课时显示接口
	 */
	@RequestMapping(value = "/finish/live", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object finishLive(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");	
						
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype
							+"-room_id:"+room_id);
		
		OpenClassFinishLiveBean finishBean=new OpenClassFinishLiveBean();
		if(access_token==null  || uid==null || utype==null
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
				//1.一进直播间 默认关注和点赞
				//1.先依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				
				if(room!=null) {
					//2.继续判断直播间的课时状态 主播ID和类型
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					try {
						this.openClassLiveService.insertFollowLikeAndUpdateRoom(room, i_uid, utype);
						//3.获取直播间信息
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", owner);
						map.put("utype", owner_type);
						map.put("room_id", i_room_id);
						//父类强制类型转换成子类 begin
						OpenClassEnterLiveBean enterBean= this.openClassLiveService.getLiveRoomInfoById(map);
						finishBean.setOwner(enterBean.getOwner());
						finishBean.setOwner_type(enterBean.getOwner_type());
						finishBean.setName(enterBean.getName());
						finishBean.setHead_pic(ImageUtil.parsePicPath(enterBean.getHead_pic(), 5));
						finishBean.setLike_number(enterBean.getLike_number());
						finishBean.setFollow_number(enterBean.getFollow_number());
						finishBean.setChapter_number(enterBean.getChapter_number());
						finishBean.setPre_time(enterBean.getPre_time());
						finishBean.setCurr_time(enterBean.getCurr_time());
						//end
						//4.继续查询课时列表
						List<LiveChapterListBean> chapter_list=this.openClassLiveService.getChapterListById(map);
						if(chapter_list.size()>0) {
							//循环读取课时信息
							for (LiveChapterListBean liveChapter : chapter_list) {
								Integer id=liveChapter.getChapter_id();
								boolean flag=this.orderCourseService.getIsBuyChapterById(i_uid, id);
								if(flag) {
									liveChapter.setOrder_status(1);
								} else {
									liveChapter.setOrder_status(0);
								}
							}
							finishBean.setChapter_list(chapter_list);
						}
						errorCode="0";
						errorMessage="ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20091";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20091;
					}
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		finishBean.setError_code(errorCode);
		finishBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(finishBean));
		return gson.toJson(finishBean);
	}
	/**
	 * 爱好者端点击正在直播的课时显示接口
	 */
	@RequestMapping(value = "/being/live", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object beingLive(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");		
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype
					+"-room_id:"+room_id+"-chapter_id:"+chapter_id);
		
		OpenClassBeingLiveBean beingBean=new OpenClassBeingLiveBean();
		if(access_token==null  || uid==null || utype==null
				|| room_id==null || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")
				|| chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || 
				!NumberUtil.isInteger(room_id) ||
				!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//1.一进直播间 默认关注和点赞
				//1.先依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.继续判断直播间的课时状态 主播ID和类型
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					try {
						//System.out.println("=====00000");
						this.openClassLiveService.insertFollowLikeAndUpdateRoom(room, i_uid, utype);
						//System.out.println("=====11111");
						//3.获取直播间信息
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", owner);
						map.put("utype", owner_type);
						map.put("room_id", i_room_id);
						//父类强制类型转换成子类 begin
						OpenClassEnterLiveBean enterBean= this.openClassLiveService.getLiveRoomInfoById(map);
						beingBean.setOwner(enterBean.getOwner());
						beingBean.setOwner_type(enterBean.getOwner_type());
						beingBean.setName(enterBean.getName());
						beingBean.setHead_pic(ImageUtil.parsePicPath(enterBean.getHead_pic(), 5));
						beingBean.setLike_number(enterBean.getLike_number());
						beingBean.setFollow_number(enterBean.getFollow_number());
						beingBean.setChapter_number(enterBean.getChapter_number());
						beingBean.setPre_time(enterBean.getPre_time());
						beingBean.setCurr_time(enterBean.getCurr_time());
						//coffee add 0321
						beingBean.setLive_name(room.getLiveName());
						//end
						//System.out.println("=====2222");
						//4.依据课时ID 查询相应的课时信息
						LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
						if(chapter!=null) {
							beingBean.setPlay_url(chapter.getRtmpUrl());
							beingBean.setSnapshot_url(chapter.getSnapshotUrl());
							beingBean.setChapter_name(chapter.getName());
							beingBean.setIs_talk(chapter.getRemarks3());
							//coffee add 0314
							beingBean.setLive_price(chapter.getLivePrice());
							beingBean.setRecord_price(chapter.getRecordPrice());
							beingBean.setIs_free(chapter.getIsFree());
							//System.out.println("=====33333333");
							boolean is_buy=this.orderCourseService.getIsBuyChapterById(i_uid, i_chapter_id);
							//System.out.println("====="+is_buy);
							if(is_buy) {
								beingBean.setOrder_status(1);
							} else 
								beingBean.setOrder_status(0);
							//System.out.println("=====6666666");
							//coffee add 0321 
							beingBean.setIntroduction(chapter.getIntroduction());
							//end
						
							//coffee add 0120 增加直播课时的浏览量
							LiveChapterPlan upd_chapter=new LiveChapterPlan();
							upd_chapter.setId(i_chapter_id);
							upd_chapter.setBrowseNumber(1);
							this.openClassLiveService.updateChapterInfo(upd_chapter);
							//end
							//System.out.println("=====4444");
						}
						errorCode="0";
						errorMessage="ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20091";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20091;
					}
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		beingBean.setError_code(errorCode);
		beingBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(beingBean));
		return gson.toJson(beingBean);
	}
	/**
	 * 爱好者端点击未开始直播的课时显示
	 */
	@RequestMapping(value = "/wait/live", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object waitLive(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");		
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype
				+"-room_id:"+room_id+"-chapter_id:"+chapter_id);
		
		OpenClassWaitLiveBean waitBean=new OpenClassWaitLiveBean();
		
		if(access_token==null  || uid==null || utype==null
				|| room_id==null || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")
				|| chapter_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || 
				!NumberUtil.isInteger(room_id) ||
				!NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//1.一进直播间 默认关注和点赞
				//1.先依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.继续判断直播间的课时状态 主播ID和类型
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					try {
						this.openClassLiveService.insertFollowLikeAndUpdateRoom(room, i_uid, utype);
						//3.获取直播间信息
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", owner);
						map.put("utype", owner_type);
						map.put("room_id", i_room_id);
						//父类强制类型转换成子类 begin
						OpenClassEnterLiveBean enterBean= this.openClassLiveService.getLiveRoomInfoById(map);
						waitBean.setOwner(enterBean.getOwner());
						waitBean.setOwner_type(enterBean.getOwner_type());
						waitBean.setName(enterBean.getName());
						waitBean.setHead_pic(ImageUtil.parsePicPath(enterBean.getHead_pic(), 5));
						waitBean.setLike_number(enterBean.getLike_number());
						waitBean.setFollow_number(enterBean.getFollow_number());
						waitBean.setChapter_number(enterBean.getChapter_number());
						waitBean.setPre_time(enterBean.getPre_time());
						waitBean.setCurr_time(enterBean.getCurr_time());
						//end
						//4.依据课时ID 查询相应的课时信息
						LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
						if(chapter!=null) {
							waitBean.setChapter_id(i_chapter_id);
							waitBean.setChapter_name(chapter.getName());
							//coffee add 0314
							waitBean.setLive_price(chapter.getLivePrice());
							waitBean.setRecord_price(chapter.getRecordPrice());
							waitBean.setIs_free(chapter.getIsFree());
							boolean is_buy=this.orderCourseService.getIsBuyChapterById(i_uid, i_chapter_id);
							if(is_buy) {
								waitBean.setOrder_status(1);
							} else 
								waitBean.setOrder_status(0);
							//end
							map.put("chapter_id", i_chapter_id);
							//继续获取其他课时列表信息 按照课时开课的顺序
							List<LiveChapterListBean> chapter_list=this.openClassLiveService.getChapterListById(map);
							if(chapter_list.size()>0) {
								//循环读取课时信息
								for (LiveChapterListBean liveChapter : chapter_list) {
									Integer id=liveChapter.getChapter_id();
									boolean flag=this.orderCourseService.getIsBuyChapterById(i_uid, id);
									if(flag) {
										liveChapter.setOrder_status(1);
									} else {
										liveChapter.setOrder_status(0);
									}
								}
								waitBean.setChapter_list(chapter_list);
							}
						}
						errorCode="0";
						errorMessage="ok";
					} catch (Exception e) {
						// TODO: handle exception
						errorCode = "20091";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20091;
					}
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		waitBean.setError_code(errorCode);
		waitBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(waitBean));
		return gson.toJson(waitBean);
	}
	
	/***
	 * 爱好者端点击某个直播间进入的接口
	 */
	@RequestMapping(value = "/enter/live", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object enterLive(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-room_id:"+room_id
				+"-uid:"+uid+"-utype:"+utype);
		
		Integer live_status=-1;
		Integer chapter_id=0;
		
		if(access_token==null  || room_id==null 
				|| uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || room_id.equals("")
				|| uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(room_id)
				|| !NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//1.先依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.继续判断直播间的课时状态 主播ID和类型
					Integer owner=room.getOwner();
					String owner_type=room.getOwnerType();
					String pre_time=room.getPreTime();
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", owner);
					map.put("utype", owner_type);
					map.put("room_id", i_room_id);
					map.put("pre_time", pre_time);
					
					LiveChapterPlan chapter=this.openClassLiveService.getChapterPlanByPreTime(map);
					if(chapter!=null) {
						chapter_id=chapter.getId();
						live_status=chapter.getLiveStatus();
						errorCode="0";
						errorMessage="ok";
					} else {
						errorCode = "20089";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20089;
					}
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
		jsonObject.put("chapter_id", chapter_id);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/***
	 * 爱好者端直播列表
	 */
	@RequestMapping(value = "/live/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String major_one=request.getParameter("major_one");
		String major_two=request.getParameter("major_two");
		String live_type=request.getParameter("live_type");
		String self=request.getParameter("self");
		String live_status=request.getParameter("live_status");
		//coffee add 0331 
		//预告课时、正在直播课时分页当前页码
		String pre_page=request.getParameter("pre_page");
		String finish_page=request.getParameter("finish_page");
		String page_limit=request.getParameter("page_limit");
		//end
		
		
		ServerLog.getLogger().warn("major_one:"+major_one+"-major_two:"+major_two
				+"-live_type:"+live_type+"-self:"+self+"-live_status:"+live_status
				+"-pre_page:"+pre_page+"-finish_page:"+finish_page
				+"-page_limit:"+page_limit);
		
		OpenClassLiveListReBean liveBean = new OpenClassLiveListReBean();
		//分页所用数据
		Integer limit=ConfigUtil.PAGESIZE;
		Integer offset=-1;
		
		Integer i_major_one=0;
		Integer i_major_two=0;
		Integer i_live_type=0;
		//定义一个已完结的数量
		Integer finish_limit=0;
		Integer i_pre_page=1;
		Integer i_finish_page=0;
//		if(page==null || page.equals("")) {
//			errorCode = "20032";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
//		} else if(!NumberUtil.isInteger(page)) {
//			errorCode = "20033";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
//		} else {
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else {
				offset=Integer.valueOf(self);
			}
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				if(major_one!=null && NumberUtil.isInteger(major_one)) {
					i_major_one=Integer.valueOf(major_one);
				}
				if(major_two!=null && NumberUtil.isInteger(major_two)) {
					i_major_two=Integer.valueOf(major_two);
				}
				if(live_type!=null && NumberUtil.isInteger(live_type)) {
					i_live_type=Integer.valueOf(live_type);
				}
				//直播列表
				List<OpenClassLiveListBean> liveList=new ArrayList<OpenClassLiveListBean>();
				//end
				Integer status=0;
				if(live_status!=null && NumberUtil.isInteger(live_status)) {
					status=Integer.valueOf(live_status);
				}
				//coffee add 0331 预告或正在直播课时当前页码
				
				if(pre_page!=null && NumberUtil.isInteger(pre_page)) {
					i_pre_page=Integer.valueOf(pre_page);
				}
				//直播完结的当前页码
				if(finish_page!=null && NumberUtil.isInteger(finish_page)) {
					i_finish_page=Integer.valueOf(finish_page);
				}
				//end
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("offset", offset);
				map.put("limit", limit);
				map.put("major_one", i_major_one);
				map.put("major_two", i_major_two);
				map.put("live_type", i_live_type);

				if(status==0 || status==1) {
					//coffee add 0331
					
					map.put("pre_page", (i_pre_page-1)*limit);
					//end
					//首先去分页查询直播间是否存在有尚未直播 且按照预告时间先后升序排序
					List<OpenClassLiveListBean> preList = this.openClassLiveService.getRoomLiveListByPre(map);
					Integer preSize=preList.size();
					//如果有预告片
					if(preSize>0) {
						//查询预告直播状态和直播课时名称
						for (OpenClassLiveListBean live : preList) {
							Integer owner=live.getOwner();
							String owner_type=live.getOwner_type();
							Integer i_room_id=live.getRoom_id();
							String pre_time=live.getPre_time();
							
							map.put("uid", owner);
							map.put("utype", owner_type);
							map.put("room_id", i_room_id);
							map.put("pre_time", pre_time);
							
							LiveChapterPlan chapter=this.openClassLiveService.getChapterPlanByPreTime(map);
							if(chapter!=null) {
								live.setLive_status(chapter.getLiveStatus());
								live.setChapter_name(chapter.getName());
								live.setChapter_id(chapter.getId());
							}
						}
						//继续判断是否小于10 如果小于10 则将已经完结凑成10大小
						if(preSize<10) {
							//coffee add 0331
							i_finish_page=1;
							map.put("finish_page",0);
							//end
							finish_limit=limit-preSize;
							map.put("offset", -1);
							map.put("limit", finish_limit);
							List<OpenClassLiveListBean> finishList=this.openClassLiveService.getRoomLiveListByFinish(map);
							liveList.addAll(preList);
							if(finishList.size()>0) {
								liveList.addAll(finishList);
							}
						} else {
							liveList.addAll(preList);
						}
					} else {
						//coffee add 0331
						i_finish_page=1;
						map.put("finish_page", 0);
						map.put("limit", limit);
						//end
						List<OpenClassLiveListBean> finishList=this.openClassLiveService.getRoomLiveListByFinish(map);
						if(finishList.size()>0) {
							liveList.addAll(finishList);
						}
					}
				} else {
					//coffee add 0331
					Integer i_page_limit=0;
					if(page_limit!=null && NumberUtil.isInteger(page_limit)) {
						i_page_limit=Integer.valueOf(page_limit);
					}
					map.put("limit", limit);
					//如果等于0
					i_page_limit=i_page_limit+(i_finish_page-1)*limit;
					map.put("finish_page",i_page_limit);
					//end
					List<OpenClassLiveListBean> finishList=this.openClassLiveService.getRoomLiveListByFinish(map);
					if(finishList.size()>0) {
						liveList.addAll(finishList);
					}
				}
				if(liveList.size()>0) {
					//继续去判断课时直播状态
					liveBean.setOpenclass_list(liveList);
					errorCode="0";
					errorMessage="ok";
				} else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
//		}
		liveBean.setError_code(errorCode);
		liveBean.setError_msg(errorMessage);
		liveBean.setPre_page(i_pre_page);
		liveBean.setFinish_page(i_finish_page);
		liveBean.setPage_limit(finish_limit);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(liveBean));
		return gson.toJson(liveBean);
	}
	/***
	 * 爱好者端直播列表版本2
	 */
	@RequestMapping(value = "/live/list_v2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveLisV2(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String major_one=request.getParameter("major_one");
		String major_two=request.getParameter("major_two");
		String live_type=request.getParameter("live_type");
		String self=request.getParameter("self");
		String page=request.getParameter("page");
		//end
		
		ServerLog.getLogger().warn("major_one:"+major_one+"-major_two:"+major_two
				+"-live_type:"+live_type+"-self:"+self+"-page:"+page);
		
		OpenClassLiveListReBean liveBean = new OpenClassLiveListReBean();
		//分页所用数据
		Integer limit=ConfigUtil.PAGESIZE;
		//Integer limit=2;
		Integer i_major_one=0;
		Integer i_major_two=0;
		Integer i_live_type=0;
		//定义一个已完结的数量
		Integer i_page=1;
		if(page!=null && NumberUtil.isInteger(page)) {
			if(!page.equals("0")) {
				i_page=Integer.valueOf(page);
			}
		} 
		
		if(major_one!=null && NumberUtil.isInteger(major_one)) {
			i_major_one=Integer.valueOf(major_one);
		}
		if(major_two!=null && NumberUtil.isInteger(major_two)) {
			i_major_two=Integer.valueOf(major_two);
		}
		if(live_type!=null && NumberUtil.isInteger(live_type)) {
			i_live_type=Integer.valueOf(live_type);
		}
		//end
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("page", (i_page-1)*limit);
		map.put("major_one", i_major_one);
		map.put("major_two", i_major_two);
		map.put("live_type", i_live_type);
		
		//首先去分页查询直播间是否存在有尚未直播 且按照预告时间先后升序排序
		List<OpenClassLiveListBean> liveList = this.openClassLiveService.getRoomLiveListByPreV2(map);
		//如果有预告片
		if(liveList.size()>0) {
			//查询预告直播状态和直播课时名称
			for (OpenClassLiveListBean live : liveList) {
				Integer owner=live.getOwner();
				String owner_type=live.getOwner_type();
				Integer i_room_id=live.getRoom_id();
				String pre_time=live.getPre_time();
							
				map.put("uid", owner);
				map.put("utype", owner_type);
				map.put("room_id", i_room_id);
				map.put("pre_time", pre_time);
							
				LiveChapterPlan chapter=this.openClassLiveService.getChapterPlanByPreTime(map);
				if(chapter!=null) {
					live.setLive_status(chapter.getLiveStatus());
					live.setChapter_name(chapter.getName());
					live.setChapter_id(chapter.getId());
				}
			}	
			liveBean.setOpenclass_list(liveList);	
			errorCode="0";
			errorMessage="ok";
		} else {
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		liveBean.setError_code(errorCode);
		liveBean.setError_msg(errorMessage);
		liveBean.setPre_page(0);
		liveBean.setFinish_page(0);
		liveBean.setPage_limit(0);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(liveBean));
		return gson.toJson(liveBean);
	}
	/**
	 * 爱好者新增直播回放列表接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/live/history", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveHistory(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String major_one=request.getParameter("major_one");
		String major_two=request.getParameter("major_two");
		String live_type=request.getParameter("live_type");
		String self=request.getParameter("self");
		String page=request.getParameter("page");
		//end
				
		ServerLog.getLogger().warn("-major_one:"+major_one+"-major_two:"+major_two
				+"-live_type:"+live_type+"-self:"+self+"-page:"+page);
		
		List<LiveHistoryBean> historyList=new ArrayList<LiveHistoryBean>();
		
		//分页所用数据
		Integer limit=ConfigUtil.PAGESIZE;
		//Integer limit=2;
		Integer i_major_one=0;
		Integer i_major_two=0;
		Integer i_live_type=0;
		//定义一个已完结的数量
		Integer i_page=1;
		
		if(page!=null && NumberUtil.isInteger(page)) {
			if(!page.equals("0")) {
				i_page=Integer.valueOf(page);
			}
		} 	
		if(major_one!=null && NumberUtil.isInteger(major_one)) {
			i_major_one=Integer.valueOf(major_one);
		}
		if(major_two!=null && NumberUtil.isInteger(major_two)) {
			i_major_two=Integer.valueOf(major_two);
		}
		if(live_type!=null && NumberUtil.isInteger(live_type)) {
			i_live_type=Integer.valueOf(live_type);
		}
		//end
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("page", (i_page-1)*limit);
		map.put("major_one", i_major_one);
		map.put("major_two", i_major_two);
		map.put("live_type", i_live_type);		
			
		historyList=this.openClassLiveService.getLiveHistoryChapterList(map);
		if(historyList.size()>0) {
			errorCode="0";
			errorMessage="ok";
		} else {
			errorCode = "20007";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", historyList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	@RequestMapping(value = "/watch/history", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object watchHistory(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下是必选参数
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String access_token=request.getParameter("access_token");
		String chapter_id=request.getParameter("chapter_id");
		
		ServerLog.getLogger().warn("-uid:"+uid+"-utype:"+utype
				+"-access_token:"+access_token+"-chapter_id:"+chapter_id);
		
		WatchHistoryBean watch=new WatchHistoryBean();
		
		if(uid==null || utype==null || access_token==null
				|| chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")
				|| utype.equals("") || chapter_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)
				|| !NumberUtil.isInteger(chapter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
				if(chapter!=null) {
					watch.setIs_free(chapter.getIsFree());
					watch.setRecord_url(chapter.getSdUrl());
					boolean flag=this.orderCourseService.getIsBuyChapterById(i_uid, i_chapter_id);
					if(flag) {
						watch.setOrder_status(1);
					} else {
						watch.setOrder_status(0);
					}
					errorCode="0";
					errorMessage="ok";
				} else {
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
		jsonObject.put("data", watch);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

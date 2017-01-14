package com.arttraining.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.beanv2.LiveAuthBean;
import com.arttraining.api.beanv2.LiveRoomBean;
import com.arttraining.api.pojo.LiveAuthWithBLOBs;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.LivePrepareService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserTecService;
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
	 * 设置直播课表
	 */
	@RequestMapping(value = "/timetable/set", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object timeTableSet(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token"); 
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");		
		String name=request.getParameter("name");		
		String introduction=request.getParameter("introduction");		
		String major_one=request.getParameter("major_one");		
		String major_two=request.getParameter("major_two");		
		String live_type=request.getParameter("live_type");		
		//以下不是必选参数
		String price=request.getParameter("price");	
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype+"-room_id:"+room_id+"-name:"+name
				+"-introduction:"+introduction+"-major_one:"+major_one
				+"-major_two:"+major_two+"-live_type:"+live_type+"-price:"+price);
		
		if(access_token==null  || uid==null || utype==null
				|| room_id==null || name==null 
				|| introduction==null || major_one==null
				|| major_two==null || live_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || room_id.equals("")
				|| name.equals("") || introduction.equals("")
				|| major_one.equals("") || major_two.equals("")
				|| live_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		
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
		
		LiveRoomBean room = new LiveRoomBean();
		
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
						Integer room_id=0;
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
							room_id=this.livePrepareService.insertOneLiveRoom(liveRoom);
						} else {
							liveRoom.setLiveName(live_name);
							liveRoom.setThumbnail(thumbnail);
							liveRoom.setIsPublish(1);
							this.livePrepareService.updateOneLiveRoom(liveRoom);
							room_id=liveRoom.getId();
						}
						room.setRoom_id(room_id);
						room.setLive_name(live_name);
						room.setThumbnail(thumbnail);
						room.setIs_publish(liveRoom.getIsPublish());
						
						errorCode="0";
						errorMessage="ok";
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
		room.setError_code(errorCode);
		room.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(room));
		return gson.toJson(room);
	}
	
	/***
	 * 直播资格认证
	 * 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object auth(HttpServletRequest request, HttpServletResponse response) {
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
							
							if(birth==null || id_number==null) {
								errorCode = "20032";
								errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
							} else if(birth.equals("") || id_number.equals("")) {
								errorCode = "20032";
								errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
							} else {
								//如果未申请过直播资质认证
								if(liveAuth==null) {
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
							}
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
							
							if(id_number_pic==null || id_number_pic.equals("")) {
								errorCode = "20032";
								errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
							} else {
								//将教师直播资质认证状态修改为1 0--保存 1--提交资质审核 2--审核通过
								if(liveAuth!=null) {
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

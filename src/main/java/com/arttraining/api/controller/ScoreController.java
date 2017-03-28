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
import com.arttraining.api.beanv2.ScoreDetailBean;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.Score;
import com.arttraining.api.service.impl.OpenClassLiveService;
import com.arttraining.api.service.impl.ScoreService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;

@Controller
@RequestMapping("/score")
public class ScoreController {
	@Resource
	private TokenService tokenService;
	@Resource
	private ScoreService scoreService;
	@Resource
	private OpenClassLiveService openClassLiveService;
	
	/**
	 * 直播礼物消费积分接口
	 * 
	 */
	@RequestMapping(value = "/live/consume", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveConsume(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下3个参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String gift_id = request.getParameter("gift_id");
		String number = request.getParameter("number");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype+"-gift_id:"+gift_id+"-number:"+number
				+"-room_id:"+room_id+"-chapter_id:"+chapter_id);
		
		if(access_token==null || uid==null || utype==null 
				|| gift_id==null || number==null
				|| room_id==null || chapter_id==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| gift_id.equals("") || number.equals("")
				|| chapter_id.equals("") || room_id.equals("") ) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)
				|| !NumberUtil.isInteger(gift_id)
				|| !NumberUtil.isInteger(number)
				|| !NumberUtil.isInteger(chapter_id) 
				|| !NumberUtil.isInteger(room_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//礼物ID
				Integer i_gift_id=Integer.valueOf(gift_id);
				//礼物数量
				Integer num=Integer.valueOf(number);
				Integer gift_score=0;
				//首先依据礼物ID查询相应的礼物积分信息
				LiveGift gift=this.scoreService.getGiftInfoById(i_gift_id);
				if(gift!=null) {
					gift_score=gift.getForeignKey();
				}
				//直播礼物所需消费的积分数
				gift_score=gift_score*num;
				//然后依据用户ID和类型查询积分信息
				Integer total_score=0;
				Score s=this.scoreService.getScoreInfoByUid(i_uid, utype);
				if(s!=null) {
					total_score=s.getScore();
				}
				if(total_score.intValue()<gift_score.intValue()) {
					errorCode="20092";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20092;
				} else {
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
						comment.setBuyNumber(num);
						comment.setRemarks("gift");
						
					try {
							this.openClassLiveService.insertLiveComment(comment);
							//coffee add 0301 直播礼物消费积分
							this.scoreService.updateScoreAndDetailInfoByUid(i_uid, utype, total_score, gift_score);
							//end
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
				}
				
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
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
	 * 查询积分详情列表接口
	 * 
	 */
	@RequestMapping(value = "/detail/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object detailQuery(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
	
		//以下3个参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		//以下参数不是必选参数 分页时用到的参数
		String self = request.getParameter("self");
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
				+"-utype:"+utype+"-self:"+self);
		
		List<ScoreDetailBean> detaliList=new ArrayList<ScoreDetailBean>();
		
		if(access_token==null || uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") ) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//继续判断分页参数
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else {
				offset=Integer.valueOf(self);
			}
			
			if(offset==-10) {
				errorCode="20032";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else {
				// todo:判断token是否有效
				boolean tokenFlag = this.tokenService.checkToken(access_token);
				if (tokenFlag) {
					Integer i_uid=Integer.valueOf(uid);
					//依据用户ID和类型查询相应的积分详情列表信息
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("offset", offset);
					map.put("limit", limit);
					detaliList=this.scoreService.getScoreListByUid(map);
					if(detaliList.size()>0) {
						errorCode="0";
						errorMessage="ok";
					} else {
						detaliList=new ArrayList<ScoreDetailBean>();
						errorCode="20007";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
				} else {
					errorCode="20028";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",detaliList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 新增查询积分接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下3个参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		
		Integer score=0;
		
		if(access_token==null || uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") ) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//依据用户ID和类型查询相应的积分信息
				Integer i_uid=Integer.valueOf(uid);
				Score s=this.scoreService.getScoreInfoByUid(i_uid, utype);
				if(s!=null) {
					score=s.getScore();
				}
				errorCode="0";
				errorMessage="ok";
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",score);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
}

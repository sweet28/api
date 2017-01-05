package com.arttraining.api.controller;

import java.util.ArrayList;
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
import com.arttraining.api.bean.MsgListReBean;
import com.arttraining.api.bean.MsgListBean;
import com.arttraining.api.bean.MsgUserBean;
import com.arttraining.api.pojo.MessagePush;
import com.arttraining.api.service.impl.MessagePushService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/message")
public class MessageController {
		@Resource
		private MessagePushService messagePushService;
		@Resource
		private TokenService tokenService;
		
		//查看更多消息列表信息
		@RequestMapping(value = "/list/more", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		public @ResponseBody Object listMore(HttpServletRequest request, HttpServletResponse response) {
			String errorCode = "";
			String errorMessage = "";
			
			//以下是必选参数
			String access_token = request.getParameter("access_token");
			String uid = request.getParameter("uid");
			String utype=request.getParameter("utype");
			//以下是不可选参数
			String self=request.getParameter("self");
			ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
					"-utype:"+utype+"-self:"+self);
			
			Integer limit=ConfigUtil.MSG_PAGESIZE;
			Integer offset=-1;
			MsgListReBean msgReBean = new MsgListReBean();
			
			if(access_token==null || uid==null || utype==null) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(access_token.equals("") || uid.trim().equals("")
					 || utype.trim().equals("")) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(!NumberUtil.isInteger(uid)) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
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
					boolean tokenFlag = this.tokenService.checkToken(access_token);
					if (tokenFlag) {
						//用户ID
						Integer i_uid=Integer.valueOf(uid);
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", i_uid);
						map.put("utype", utype);
						map.put("limit", limit);
						map.put("offset", offset);
						List<MessagePush> pushList=this.messagePushService.getMoreMsgListByUid(map);
						if(pushList.size()>0) {
							List<MsgListBean> msg_list = new ArrayList<MsgListBean>();
							//循环读取消息列表
							for (MessagePush push : pushList) {
								MsgListBean msg = new MsgListBean();
								
								String time=TimeUtil.getTimeByDate(push.getCreateTime());
								msg.setMsg_time(time);
								msg.setMsg_id(push.getId());
								msg.setMsg_content(push.getMsgContent());
								msg.setMsg_type(push.getMsgType());
								//评论人/点赞人...
								Integer visitor=push.getVisitor();
								String visitor_type=push.getVisitorType();
								MsgUserBean visitor_user = this.messagePushService.getMsgUserInfoByUid(visitor, visitor_type);
								msg.setUid(visitor);
								msg.setUtype(visitor_type);
								msg.setName(visitor_user.getName());
								msg.setHead_pic(visitor_user.getHead_pic());
								//被回复人/类型
								Integer b_uid=push.getOwnerId();
								String b_utype=push.getOwnerType();
								MsgUserBean b_user = this.messagePushService.getMsgUserInfoByUid(b_uid, b_utype);
								msg.setB_uid(b_uid);
								msg.setB_utype(b_utype);
								msg.setB_name(b_user.getName());
								msg.setB_head_pic(b_user.getHead_pic());
								
								//动态类型信息 ID/封面/类型
								Integer status_id=push.getStatusId();
								String status_type=push.getStatusType();
								String status_pic=push.getStatusPic();
								status_pic=ImageUtil.parseStatusThumbnail(status_pic, status_type);
								msg.setStatus_id(status_id);
								msg.setStatus_type(status_type);
								msg.setStatus_pic(status_pic);
								msg.setStatus_content(push.getAttachment());
								
								msg_list.add(msg);
							}
							msgReBean.setMsg_list(msg_list);
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
			}
			msgReBean.setError_code(errorCode);
			msgReBean.setError_msg(errorMessage);
			
			Gson gson = new Gson();
			ServerLog.getLogger().warn(gson.toJson(msgReBean));
			return gson.toJson(msgReBean);
		}
		
		//获取消息推送列表
		@RequestMapping(value = "/push/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		public @ResponseBody Object pushList(HttpServletRequest request, HttpServletResponse response) {
			String errorCode = "";
			String errorMessage = "";
			
			//以下是必选参数
			String access_token = request.getParameter("access_token");
			String uid = request.getParameter("uid");
			String utype=request.getParameter("utype");
			//以下是不可选参数
			String self=request.getParameter("self");
			
			ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
					"-utype:"+utype+"-self:"+self);
			
			Integer limit=ConfigUtil.MSG_PAGESIZE;
			Integer offset=-1;
			
			MsgListReBean msgReBean = new MsgListReBean();
			if(access_token==null || uid==null || utype==null) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(access_token.equals("") || uid.trim().equals("")
					 || utype.trim().equals("")) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(!NumberUtil.isInteger(uid)) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
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
					boolean tokenFlag = this.tokenService.checkToken(access_token);
					if (tokenFlag) {
						//1.先去查询uid/utype对应的所有未读消息列表
						Integer i_uid=Integer.valueOf(uid);
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", i_uid);
						map.put("utype", utype);
						map.put("limit", limit);
						map.put("offset", offset);
						System.out.println("111111");
						List<MessagePush> pushList=this.messagePushService.getMoreMsgListByUid(map);
						if(pushList.size()>0) {
							System.out.println("222222");
							List<MsgListBean> msg_list = new ArrayList<MsgListBean>();
							//循环读取消息列表
							for (MessagePush push : pushList) {
								MsgListBean msg = new MsgListBean();
								
								String time=TimeUtil.getTimeByDate(push.getCreateTime());
								msg.setMsg_time(time);
								msg.setMsg_id(push.getId());
								msg.setMsg_content(push.getMsgContent());
								msg.setMsg_type(push.getMsgType());
								//评论人/点赞人...
								Integer visitor=push.getVisitor();
								String visitor_type=push.getVisitorType();
								MsgUserBean visitor_user = this.messagePushService.getMsgUserInfoByUid(visitor, visitor_type);
								msg.setUid(visitor);
								msg.setUtype(visitor_type);
								msg.setName(visitor_user.getName());
								msg.setHead_pic(visitor_user.getHead_pic());
								//被回复人/类型
								Integer b_uid=push.getOwnerId();
								String b_utype=push.getOwnerType();
								MsgUserBean b_user = this.messagePushService.getMsgUserInfoByUid(b_uid, b_utype);
								msg.setB_uid(b_uid);
								msg.setB_utype(b_utype);
								msg.setB_name(b_user.getName());
								msg.setB_head_pic(b_user.getHead_pic());
								
								//动态类型信息 ID/封面/类型
								Integer status_id=push.getStatusId();
								String status_type=push.getStatusType();
								String status_pic=push.getStatusPic();
								status_pic=ImageUtil.parseStatusThumbnail(status_pic, status_type);
								msg.setStatus_id(status_id);
								msg.setStatus_type(status_type);
								msg.setStatus_pic(status_pic);
								msg.setStatus_content(push.getAttachment());
								
								msg_list.add(msg);
							}
							System.out.println("33333");
							msgReBean.setMsg_list(msg_list);
							if(offset==-1) {
								//将该用户所有未读的消息设置为已读 begin
								MessagePush upd_msg = new MessagePush();
								upd_msg.setOwnerId(i_uid);
								upd_msg.setOwnerType(utype);
								upd_msg.setIsRead(1);
								this.messagePushService.updateAllMsgReadStatusByUid(upd_msg);
								//end
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
			}
			msgReBean.setError_code(errorCode);
			msgReBean.setError_msg(errorMessage);
			
			Gson gson = new Gson();
			ServerLog.getLogger().warn(gson.toJson(msgReBean));
			return gson.toJson(msgReBean);
		}
		
		// 标记该用户所有的推送消息已读
		@RequestMapping(value = "/read/all", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		public @ResponseBody Object readAll(HttpServletRequest request, HttpServletResponse response) {
			String errorCode = "";
			String errorMessage = "";
				
			//以下是必选参数
			String access_token = request.getParameter("access_token");
			String uid = request.getParameter("uid");
			String utype=request.getParameter("utype");
			
			ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
			
			if(access_token==null || uid==null || utype==null) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(access_token.equals("") || uid.trim().equals("")
					 || utype.trim().equals("")) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(!NumberUtil.isInteger(uid)) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				boolean tokenFlag = this.tokenService.checkToken(access_token);
				if (tokenFlag) {
					//用户ID
					Integer i_uid=Integer.valueOf(uid);
					MessagePush msg = new MessagePush();
					msg.setOwnerId(i_uid);
					msg.setOwnerType(utype);
					msg.setIsRead(1);
					this.messagePushService.updateAllMsgReadStatusByUid(msg);
					errorCode = "0";
					errorMessage = "ok";
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
		
		// 标记推送消息已读
		@RequestMapping(value = "/read/one", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		public @ResponseBody Object readOne(HttpServletRequest request, HttpServletResponse response) {
			String errorCode = "";
			String errorMessage = "";
			
			//以下是必选参数
			String access_token = request.getParameter("access_token");
			String msg_id = request.getParameter("msg_id");
			
			ServerLog.getLogger().warn("access_token:"+access_token+"-msg_id:"+msg_id);
			
			if(access_token==null || msg_id==null) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(access_token.equals("") || msg_id.trim().equals("")) {
				errorCode = "20032";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else if(!NumberUtil.isInteger(msg_id)) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				boolean tokenFlag = this.tokenService.checkToken(access_token);
				if (tokenFlag) {
					//消息推送ID
					Integer i_msg_id=Integer.valueOf(msg_id);
					MessagePush msg = new MessagePush();
					msg.setId(i_msg_id);
					msg.setIsRead(1);
					this.messagePushService.updateOneMessagePush(msg);
					errorCode = "0";
					errorMessage = "ok";
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

}

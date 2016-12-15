package com.arttraining.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.pojo.CommentRead;
import com.arttraining.api.pojo.WorksTecComment;
import com.arttraining.api.service.impl.TecCommentReadService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;

@Controller
@RequestMapping("/teccomment")
public class TecCommentReadController {
	@Resource
	private TecCommentReadService tecCommentReadService;
	@Resource
	private TokenService tokenService;
	
	/**
	 * 收看/听老师点评信息时调用的接口
	 * 传递的参数:access_token--验证口令
	 * uid--用户ID utype--用户类型
	 * comm_id--点评ID host_id--点评人ID host_type--点评人类型
	 */
	@RequestMapping(value = "/read", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object read(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String comm_id=request.getParameter("comm_id");
		String host_id=request.getParameter("host_id");
		String host_type=request.getParameter("host_type");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+
				"-comm_id:"+comm_id+"-host_id:"+host_id);
		
		if(access_token==null || uid==null || utype==null 
				|| comm_id==null || host_id==null || host_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| comm_id.equals("") || host_id.equals("") || host_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(comm_id)
				 || !NumberUtil.isInteger(host_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			// todo:判断token是否有效
			//boolean tokenFlag = TokenUtil.checkToken(access_token);
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//点评ID
				Integer i_comm_id=Integer.valueOf(comm_id);
				//点评人ID
				Integer i_host_id=Integer.valueOf(host_id);
				//1.首先判断是否重复对一个点评进行收听或者收看 如果重复 只修改点评的收听/看数量 
				//否则新增收听/看记录的同时 修改点评的收听/看数量 
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("uid", i_uid);
				map.put("utype", utype);
				map.put("comm_id", i_comm_id);
				CommentRead read= this.tecCommentReadService.getCommentReadByComId(map);
				CommentRead upd_read=null;
				//是否重复收听/收看
				if(read==null) {
					upd_read=new CommentRead();
					//当前时间/日期
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					upd_read.setForeignKey(i_comm_id);
					upd_read.setCreateTime(date);
					upd_read.setOrderCode(time);
					upd_read.setVisitor(i_uid);
					upd_read.setVisitorType(utype);
					upd_read.setHost(i_host_id);
					upd_read.setHostType(host_type);
				}
				WorksTecComment comment=new WorksTecComment();
				comment.setId(i_comm_id);
				comment.setListenNum(1);
				
				try {
					errorCode = "0";
					errorMessage = "ok";
					this.tecCommentReadService.insertReadAndTecComment(upd_read, comment);
				} catch (Exception e) {
					errorCode = "20064";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20064;
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
}

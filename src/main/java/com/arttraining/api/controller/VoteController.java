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
import com.arttraining.api.pojo.ActivityVote;
import com.arttraining.api.pojo.VoteRecord;
import com.arttraining.api.service.impl.VoteRecordService;
import com.arttraining.api.service.impl.VoteService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;

@Controller
@RequestMapping("/vote")
public class VoteController {
	@Resource
	private VoteService voteService;
	@Resource
	private VoteRecordService voteRecordService;
	
	/***
	 * 投票时新增票数
	 * act_vote_id--活动ID
	 * voter_id--投票人ID
	 * voter_type--投票人类型
	 * 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String callbackparam=request.getParameter("callbackparam");
		String act_vote_id=request.getParameter("act_vote_id");
		String voter_id=request.getParameter("voter_id");
		String voter_type=request.getParameter("voter_type");
		
		ServerLog.getLogger().warn("act_vote_id:"+act_vote_id+"-voter_id:"+voter_id+"-voter_type:"+voter_type);
		
		int popular_num=0;
		
		if(act_vote_id==null || voter_id==null || voter_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(act_vote_id.equals("") || voter_id.equals("") 
				|| voter_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(act_vote_id)
				|| !NumberUtil.isInteger(voter_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			Integer vote_id=Integer.valueOf(act_vote_id);
			Integer uid=Integer.valueOf(voter_id);
			
			//依据vote_id获取选手信息
			ActivityVote vote=this.voteService.getOneVoteById(vote_id);
			//人气数
			popular_num = vote.getPopularNum();
			String act_id=vote.getActId()+"";
			String act_type=vote.getActType();
			
			boolean vote_flag=false;
			//首先去投票记录表中去查找此人是否在当天已经投票过
			Date date = new Date();
			String time=TimeUtil.getTimeByDate(date);
			//传递参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("utype", voter_type);
			map.put("act_id", act_id);
			map.put("act_type", act_type);
			
			VoteRecord record=this.voteRecordService.getVoteRecordByUid(map);
			if(record!=null) {
				Date vote_date=record.getCreateTime();
				String vote_time=TimeUtil.getTimeByDate(vote_date);
				String sub_time=time.substring(0, 9);
				String sub_vote_time=vote_time.substring(0,9);
				//当天已投票过
				if(sub_time.equals(sub_vote_time)) {
					errorCode = "20066";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20066;
				} else {
					vote_flag=true;
				}
			} 
			else {
				vote_flag=true;
			}
			if(vote_flag) {
				//投票记录
				VoteRecord insert_vote=new VoteRecord();
				insert_vote.setCreateTime(date);
				insert_vote.setOrderCode(time);
				insert_vote.setForeignKey(vote_id);
				insert_vote.setVoterId(uid);
				insert_vote.setVoterType(voter_type);
				insert_vote.setRemarks(act_id);
				insert_vote.setAttachment(act_type);
				//修改投票数量
				ActivityVote upd_vote=new ActivityVote();
				upd_vote.setId(vote.getId());
				upd_vote.setPopularNum(1);
				
				try {
					this.voteRecordService.insertVoteRecordAndNumber(insert_vote, upd_vote);
					//投票数+1
					popular_num+=1;
					errorCode = "0";
					errorMessage = "ok";
				} catch (Exception e) {
					// TODO: handle exception
					errorCode = "20066";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20066;
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("popular_num", popular_num);
		jsonObject.put("vote_id", act_vote_id);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		String jsonResult=callbackparam+"("+jsonObject.toString()+")";
		return jsonResult;	
	}
	/***
	 * 用于获取投票列表信息
	 * 传递的参数:act_id--活动ID
	 * act_type--活动类型
	 * 
	 */
	@RequestMapping(value = "/act/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object actList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String callbackparam=request.getParameter("callbackparam");
		String act_id=request.getParameter("act_id");
		String act_type=request.getParameter("act_type");
		//以下不是必选参数
		String voter_id=request.getParameter("voter_id");
		String voter_type=request.getParameter("voter_type");
		
		ServerLog.getLogger().warn("act_id:"+act_id+"-act_type:"+act_type
				+"-voter_id:"+voter_id+"-voter_type:"+voter_type);
		
		List<ActivityVote> voteList = new ArrayList<ActivityVote>();
		
		if(act_id==null || act_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(act_id.equals("") || act_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(act_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			Integer i_act_id=Integer.valueOf(act_id);
			//去查询投票列表信息
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("act_id", i_act_id);
			map.put("act_type", act_type);
			voteList = this.voteService.getVoteListByActId(map);
			if(voteList.size()>0) {
				if(voter_id!=null && !voter_id.equals("") && !voter_id.equals("0")) {
					//获取当前日期
					Date curr_date = new Date();
					String curr_time=TimeUtil.getTimeByDate(curr_date);
					//System.out.println("44444"+curr_time);
					//循环读取
					for (ActivityVote vote : voteList) {
						map.put("uid", voter_id);
						map.put("utype", voter_type);
						map.put("vote_id", vote.getId());
						
						//System.out.println("2222");
						
						VoteRecord record=this.voteRecordService.getVoteRecordByUid(map);
						if(record!=null) {
							Date vote_date=record.getCreateTime();
							String vote_time=TimeUtil.getTimeByDate(vote_date);
							String sub_time=curr_time.substring(0, 9);
							String sub_vote_time=vote_time.substring(0,9);
							//当天已投票过
							if(sub_time.equals(sub_vote_time)) {
								vote.setRemarks("vote");
							}
						}
					}
				}	
				errorCode="0";
				errorMessage="ok";
			} else {
				voteList = new ArrayList<ActivityVote>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("vote_list", voteList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		String jsonResult=callbackparam+"("+jsonObject.toString()+")";
		return jsonResult;
	}
}

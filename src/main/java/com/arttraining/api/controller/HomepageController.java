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
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageAdvertiseBean;
import com.arttraining.api.bean.HomePageAttBean;
import com.arttraining.api.bean.HomePageTecCommentBean;
import com.arttraining.api.bean.HomePageWorkBean;
import com.arttraining.api.service.impl.AdvertiseService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.api.service.impl.WorksTecCommentService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TokenUtil;

@Controller
@RequestMapping("/homepage")
public class HomepageController {
	@Resource
	private WorksService worksService;
	@Resource
	private WorksTecCommentService worksTecCommentService;
	@Resource
	private AdvertiseService advertiseService;
	
	/***
	 * 获取首页作品列表接口
	 * 
	 */
	@RequestMapping(value = "/public_timeline/work", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object publicTimelineWork(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下参数不是必选参数
		String access_token = request.getParameter("access_token");
		if(access_token!=null && !access_token.equals("")) {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				TokenUtil.delayTokenDeadline(access_token);
			}
		}
		//定义一个首页列表对象
		List<Object> statusesList = new ArrayList<Object>();
		//分页时 最后一个位置ID(当前首页作品的ID)
		String self = request.getParameter("self");
				
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
				
		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype+"-self:"+self);
				
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
			//如果用户id 默认查询uid=0 即尚未登录的用户
			if(uid==null || uid.equals("") || !NumberUtil.isInteger(uid)) {
				uid="0";
			}
			if(utype==null) {
				utype="";
			}
			//用户ID
			Integer i_uid = Integer.valueOf(uid);
			//填充作品详情信息
			Map<String, Object> work_map = new HashMap<String, Object>();  
			work_map.put("limit", limit);  
			work_map.put("offset", offset);
			List<HomePageWorkBean> worksList = this.worksService.getWorkListByPublic(work_map);
			//System.out.println("1111==="+worksList.size());
			if(worksList.size()==0) {
				worksList = new ArrayList<HomePageWorkBean>();
			}
			else {
				for (HomePageWorkBean work : worksList) {
					Integer s_id = work.getStus_id();
					//System.out.println("2222==="+s_id);
					//判断是否点赞或点评--(传递当前用户的ID和类型)
					Map<String, Object> map = new HashMap<String, Object>();  
					map.put("s_id", s_id);  
					map.put("u_id", i_uid);
					map.put("u_type", utype);
					
					HomeLikeOrCommentBean isExistLike = this.worksService.getIsLikeOrCommentOrAtt(map);   
					work.setIs_like((String)map.get("is_like"));
					work.setIs_comment((String)map.get("is_comment"));
					if(isExistLike!=null) {
						String att_type = isExistLike.getAtt_type();
						//System.out.println("3333==="+att_type);
						if(att_type!=null && !att_type.equals("")) {
							Integer att_id = isExistLike.getAtt_id();
							String duration= isExistLike.getDuration();
							String thumbnail=isExistLike.getThumbnail();
							String store_path=isExistLike.getStore_path();
							List<HomePageAttBean> attList = ImageUtil.parseAttPathByType(att_id, att_type, duration, thumbnail, store_path,6);
							work.setAtt(attList);
						}
				   }
				   //在这里需要依据作品ID 去获取老师点评信息
				   List<HomePageTecCommentBean> tec_comment_list=this.worksTecCommentService.getTecCommentByWorkId(s_id);
				   if(tec_comment_list.size()>0) {
					   work.setTec_comment_list(tec_comment_list);
				   }
				   statusesList.add(work);
			   }
			  //获取广告信息
			HomePageAdvertiseBean ad = this.advertiseService.getOneAdByHomepage();	
			boolean ad_flag=false;
			if(ad!=null) {
				ad_flag=true;
			}
			if(statusesList.size()>0) {
				if(statusesList.size()>5) {
					if(ad_flag) {
						statusesList.add(5, ad);
					} else {
						statusesList.add(ad);
					}
				}
				errorCode="0";
				errorMessage="ok";
			} else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
	  }
	 JSONObject jsonObject = new JSONObject();
	 jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
	 jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
	 jsonObject.put("statuses", statusesList);
			
	 ServerLog.getLogger().warn(jsonObject.toString());
			
	 return jsonObject;
	}
}

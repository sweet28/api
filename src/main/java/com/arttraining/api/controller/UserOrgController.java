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
import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.OrgShowBean;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.service.impl.FollowService;
import com.arttraining.api.service.impl.UserOrgService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;


@Controller
@RequestMapping("/org")
public class UserOrgController {
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private FollowService followService;
	
	/**
	 * 获取机构列表
	 * 传递的参数:
	 * self--当前位置id
	 * city	false	string	地域城市
	 * province	false	string	省份
	 * type	false	string	类别
	 * 
	 * **/
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String self = request.getParameter("self");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String type = request.getParameter("type");
		
		ServerLog.getLogger().warn("self:"+self+"-type:"+type+
				"-city:"+city+"-province:"+province);
		
		List<OrgListBean> orgList  = new ArrayList<OrgListBean>();

		Integer offset = -1;
		Integer limit = ConfigUtil.PAGESIZE;
		if(self==null || self.equals("")) {
			offset = -1;
		}
		else if(!NumberUtil.isInteger(self)) {
			offset = -10;
		}
		else {
			offset = Integer.valueOf(self);
		}
		
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("city", city);
			map.put("province", province);
			map.put("type", type);
			map.put("offset", offset);
			map.put("limit", limit);
			
			orgList = this.userOrgService.getOrgListPrimaryKey(map);
			if(orgList.size() > 0) {
				errorCode = "0";
				errorMessage = "ok";
			}
			else {
				orgList  = new ArrayList<OrgListBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("org", orgList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 根据机构ID获取机构详情信息
	 * 传递的参数:org_id--机构ID
	 * 
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String org_id = request.getParameter("org_id");
		//以下是可选参数
		String login_id = request.getParameter("login_id");
		String login_type = request.getParameter("login_type");
		
		String is_follow="";
		
		ServerLog.getLogger().warn("org_id:"+org_id+"-login_type:"+login_type+"-login_id:"+login_id);
		
		OrgShowBean orgShow = new OrgShowBean();
		
		if(org_id==null || org_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(org_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			Integer i_login_id = 0;
			if(login_id==null || login_type==null || login_id.equals("") || login_type.equals("")) {
				i_login_id = 0;
				login_type="";
			} else if(!NumberUtil.isInteger(login_id)) {
				i_login_id = 0;
			} else
				i_login_id=Integer.valueOf(login_id);
			
			//INT机构ID
			Integer i_org_id = Integer.valueOf(org_id);
			//依据关注类型的不同 查询不同的表
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "org");
			map.put("id", i_org_id);
			map.put("uid", i_login_id);
			map.put("utype", login_type);
			//首先判断登录是否重复对名师/机构/爱好者用户关注
			Follow isExist=this.followService.getIsExistFollow(map);
			if(isExist!=null) {
				is_follow="yes";
			}
			else
				is_follow="no";
			
			orgShow = this.userOrgService.getOneOrgByOrgShow(i_org_id);
			if(orgShow==null) {
				orgShow = new OrgShowBean();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				orgShow.setIs_follow(is_follow);
				errorCode = "0";
				errorMessage = "ok";
			}
		}
		orgShow.setError_code(errorCode);
		orgShow.setError_msg(errorMessage);
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(orgShow));
		return gson.toJson(orgShow);
	}
	
}

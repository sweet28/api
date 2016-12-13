package com.arttraining.api.controller;

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

import com.arttraining.api.bean.GroupListBean;
import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.SearchGroupReBean;
import com.arttraining.api.bean.SearchOrgReBean;
import com.arttraining.api.bean.SearchTecReBean;
import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.service.impl.GroupService;
import com.arttraining.api.service.impl.UserOrgService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private UserTecService userTecService;
	@Resource
	private GroupService groupService;
	
	/***
	 * 根据关键字搜索机构
	 * 传递的参数:key (机构名称、城市)
	 * self--分页
	 */
	@RequestMapping(value = "/org", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object org(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下不是必选参数
		String key = request.getParameter("key");
		//以下用于分页
		String self=request.getParameter("self");
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		
		ServerLog.getLogger().warn("key:"+key+"-self:"+self);
		
		SearchOrgReBean orgReBean = new SearchOrgReBean();
		
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
			//依据关键字搜索机构列表信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", key);
			map.put("offset", offset);
			map.put("limit", limit);
			List<OrgListBean> orgList = this.userOrgService.getOrgListBySearch(map);
			if(orgList.size()>0) {
				orgReBean.setOrg(orgList);
				errorCode = "0";
				errorMessage = "ok";
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		orgReBean.setError_code(errorCode);
		orgReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(orgReBean));
		return gson.toJson(orgReBean);
	}
	
	/***
	 * 根据关键字搜索教师
	 * 传递的参数:key (名师名称、城市、学校)
	 * self--分页
	 */
	@RequestMapping(value = "/tec", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object tec(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下不是必选参数
		String key = request.getParameter("key");
		String spec = request.getParameter("spec");
		//以下用于分页
		String self=request.getParameter("self");
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		
		//coffee add 1208
		String identity = request.getParameter("identity");
		Integer role=null;
		if(identity!=null) {
			if(identity.equals("ms")) {
				role=0;
			} else if(identity.equals("zj")) {
				role=1;
			} else if(identity.equals("iartschool")) {
				role=2;
			} else if(identity.equals("dr")) {
				role=3;
			}
		}
		//end
		ServerLog.getLogger().warn("key:"+key+"-self:"+self+"-spec:"+spec+"-identity:"+identity);
		
		SearchTecReBean tecReBean = new SearchTecReBean();
		
//		if(spec==null || spec.equals("")) {
//			errorCode = "20032";
//			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
//		}
//		else {
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
				//依据关键字搜索机构列表信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", key);
				map.put("offset", offset);
				map.put("limit", limit);
				map.put("spec", spec);
				map.put("identity", role);
				
				List<TecherListBean> tecList = this.userTecService.getTecherListBySearch(map);
				if(tecList.size()>0) {
					tecReBean.setTec(tecList);
					errorCode = "0";
					errorMessage = "ok";
				}
				else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		//}
		tecReBean.setError_code(errorCode);
		tecReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(tecReBean));
		return gson.toJson(tecReBean);
	}
	/***
	 * 根据关键字搜索小组传递的参数:key (小组名称)
	 * self--分页
	 */
	@RequestMapping(value = "/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object group(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下不是必选参数
		String key = request.getParameter("key");
		//以下用于分页
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("key:"+key+"-self:"+self);
		
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		
		SearchGroupReBean groupReBean = new SearchGroupReBean();
		
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
			//依据关键字搜索机构列表信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", key);
			map.put("offset", offset);
			map.put("limit", limit);
			List<GroupListBean> groupList = this.groupService.getGroupListBySearch(map);
			if(groupList.size()>0) {
				groupReBean.setGroups(groupList);
				errorCode = "0";
				errorMessage = "ok";
			}
			else {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		groupReBean.setError_code(errorCode);
		groupReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(groupReBean));
		return gson.toJson(groupReBean);
	}
}

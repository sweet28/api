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
import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.bean.TecherShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.FollowService;
import com.arttraining.api.service.impl.UserOrgService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/techer")
public class TecherController {
	@Resource
	private UserTecService userTecService;
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private FollowService followService;
	
	/**
	 * 获取名师列表
	 * 默认显示10条记录
	 * 传递的参数是当前位置的ID
	 * self
	 * college--院校 spec--专业 city--城市
	 * provinces --身份
	 * 
	 * **/
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String self=request.getParameter("self");
		String college=request.getParameter("college");
		String spec=request.getParameter("spec");
		String city=request.getParameter("city");
		String provinces = request.getParameter("provinces");
		//coffee add 1207
		String identity=request.getParameter("identity");
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
		List<TecherListBean> teacherList = new ArrayList<TecherListBean>();
		ServerLog.getLogger().warn("self:"+self+"-college:"+college+"-spec:"+spec+
				"-city:"+city+"-provinces:"+provinces+"-identity:"+identity+"-role:"+role);
		
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		//self--当前位置ID 在这里指的是名师ID 
		if(self==null || self.equals("")) {
			offset=-1;
		}
		else //如果传递的self不是数值字符串  返回-10
		if(!NumberUtil.isInteger(self)){
			offset=-10;
		}
		else {
			offset=Integer.valueOf(self);
		}
		
		if(offset==-10) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			//传递多个参数 查询名师信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("spec", spec);
			map.put("city", city);
			map.put("college", college);
			map.put("provinces", provinces);
			map.put("offset", offset);
			map.put("limit", limit);
			map.put("identity", role);
			
			teacherList = this.userTecService.getTecherListBySelective(map);
			if(teacherList.size()>0) {
				errorCode="0";
				errorMessage="ok";
			}
			else {
				teacherList = new ArrayList<TecherListBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;	
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("tec", teacherList);
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	/**
	 * 首页的获取名师列表接口，每次只返回两个名师
	 * 传递的参数:
	 * self--当前位置ID(类似于页码--必选参数)
	 * 
	 * **/
	@RequestMapping(value = "/list/index", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listIndex(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<TecherListBean> teacherList = new ArrayList<TecherListBean>();
		
		String self=request.getParameter("self");
		ServerLog.getLogger().warn("self:"+self);
		
		Integer offset=-1;
		Integer limit = ConfigUtil.HOMEPAGE_PAGESIZE;
		if(self==null || self.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else //如果传递的self不是数值字符串  返回-10
		if(!NumberUtil.isInteger(self)){
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			//在这里查询总的名师个数
			Integer tecNum = this.userTecService.countTecherNumer();
			Integer page=tecNum/limit;
			offset=Integer.valueOf(self)-1;
			//如果当前页>=名师数量
			offset = (offset+page)%page*limit;
			
			teacherList = this.userTecService.getTecherListIndexBySelective(offset,limit);
			if(teacherList.size()>0) {
				errorCode = "0";
				errorMessage = "ok";	
			}
			else {
				teacherList = new ArrayList<TecherListBean>();
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;	
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("tec", teacherList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/**
	 * 根据名师ID获取名师信息
	 * 传递的参数:
	 * tec_id--名师ID
	 * 
	 * **/
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		TecherShowBean tecShow = new TecherShowBean();
		
		String tec_id = request.getParameter("tec_id");
		//以下是可选参数
		String login_id = request.getParameter("login_id");
		String login_type = request.getParameter("login_type");
				
		String is_follow="";
				
		ServerLog.getLogger().warn("tec_id:"+tec_id+"-login_type:"+login_type+"-login_id:"+login_id);
		
		if(tec_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(tec_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else
		if(!NumberUtil.isInteger(tec_id)) {
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
			
			Integer i_tec_id = Integer.valueOf(tec_id);
			try {
			//在这里先更新名师浏览量
			UserTech bro_tec= new UserTech();
			bro_tec.setId(i_tec_id);
			bro_tec.setBrowseNum(Random.randomCommonInt());
			this.userTecService.updateTecNumber(bro_tec);
			
			//依据关注类型的不同 查询不同的表
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "tec");
			map.put("id", i_tec_id);
			map.put("uid", i_login_id);
			map.put("utype", login_type);
			//首先判断登录是否重复对名师/机构/爱好者用户关注
			Follow isExist=this.followService.getIsExistFollow(map);
			if(isExist!=null) {
				is_follow="yes";
			}
			else
				is_follow="no";

			//依据名师tec_id--名师ID来查询相应的名师信息
			UserTech userTec = this.userTecService.getOneUserTecById(i_tec_id);
			if(userTec==null) {
				errorCode = "20007";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;		
			}
			else {
				errorCode = "0";
				errorMessage = "ok";		
				
				//查询到相应的名师详情,判断是否有所属的机构
				Integer i_org_id = userTec.getOrgId();
				//如果所属机构不存在,则无需进行一次机构查询
				TecherShowOrgBean org=null;
				if(i_org_id!=null && i_org_id!=0) {
					org = this.userOrgService.getOneOrgByTecShow(i_org_id);
					tecShow.setOrg(org);
				}
				tecShow.setTec_id(userTec.getId());
				tecShow.setName(userTec.getName());
				tecShow.setPic(userTec.getHeadPic());
				tecShow.setComment(userTec.getCommentNum());
				tecShow.setFans_num(userTec.getFansNum());
				tecShow.setAuth(userTec.getAuthentication());
				tecShow.setBrowse_num(userTec.getBrowseNum());
				tecShow.setCity(userTec.getCityName());
				tecShow.setCollege(userTec.getSchoolName());
				tecShow.setAss_pay(userTec.getAssPay());
				tecShow.setTitle(userTec.getTitle());
				tecShow.setSpecialty(userTec.getSpecialtyName());
				tecShow.setIntroduction(userTec.getIntroduction());
				tecShow.setIs_follow(is_follow);
				tecShow.setBg_pic(userTec.getAttachment());
				int role=userTec.getRole();
				switch (role) {
				case 0:
					tecShow.setIdentity("ms");
					break;
				case 1:
					tecShow.setIdentity("zj");
					break;
				case 2:
					tecShow.setIdentity("iartschool");
					break;
				case 3:
					tecShow.setIdentity("dr");
					break;
				default:
					break;
				}
			 }
			} catch (Exception e) {
				errorCode="20054";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20054;
			}
		}
		tecShow.setError_code(errorCode);
		tecShow.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(tecShow));
		return gson.toJson(tecShow);
	}

}

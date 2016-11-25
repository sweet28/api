package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.MasterShowBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.UserOrgService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/masters")
public class MasterController {
	@Resource
	private UserTecService userTecService;
	@Resource
	private UserOrgService userOrgService;
	
	/***
	 * 根据名师ID获取名师详情
	 * 传递的参数:login_id--名师ID
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String login_id=request.getParameter("login_id");
		
		ServerLog.getLogger().warn("login_id:"+login_id);
		//返回的数据对象
		MasterShowBean masterBean = new MasterShowBean();
		
		if(login_id==null || login_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(login_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//名师ID
			Integer i_tec_id=Integer.valueOf(login_id);
			//依据名师ID去查询相应的名师信息
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
					masterBean.setOrg(org);
				}
				masterBean.setTec_id(userTec.getId());
				masterBean.setName(userTec.getName());
				masterBean.setPic(userTec.getHeadPic());
				masterBean.setComment(userTec.getCommentNum());
				masterBean.setFans_num(userTec.getFansNum());
				masterBean.setAuth(userTec.getAuthentication());
				masterBean.setBrowse_num(userTec.getBrowseNum());
				masterBean.setCity(userTec.getCityName());
				masterBean.setCollege(userTec.getSchoolName());
				masterBean.setAss_pay(userTec.getAssPay());
				masterBean.setTitle(userTec.getTitle());
				masterBean.setSpecialty(userTec.getSpecialtyName());
				masterBean.setIntroduction(userTec.getIntroduction());
				masterBean.setSex(userTec.getSex());
			}
		}
		masterBean.setError_code(errorCode);
		masterBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(masterBean));
		return gson.toJson(masterBean);
		
	}
	
	/***
	 * 根据用户ID设置用户信息
	 * 必须参数 access_token uid
	 * access_token--验证 uid--用户id name--用户名
	 * sex--性别 city_id-- 城市id city--城市 
	 * identity_id--身份ID identity--身份
	 * school_id--院校ID  school 
	 * org_id--机构ID org--机构
	 * specialty_id 专业ID specialty--专业 
	 * mobile 名师联系电话 pic--名师头像  email--邮箱
	 * title--职称 ass_pay--测评费用
	 */
	@RequestMapping(value = "/set_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object setInfo(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		//以下参数是可选参数
		String name=request.getParameter("name");
		String pic=request.getParameter("pic");
		String sex=request.getParameter("sex");
		String city_id=request.getParameter("city_id");
		String city=request.getParameter("city");
		String identity_id=request.getParameter("identity_id");
		String identity=request.getParameter("identity");
		String school_id=request.getParameter("school_id");
		String school=request.getParameter("school");
		String org_id=request.getParameter("org_id");
		String org=request.getParameter("org");
		String specialty_id=request.getParameter("specialty_id");
		String specialty=request.getParameter("specialty");
		String introduction=request.getParameter("introduction");
		String ass_pay=request.getParameter("ass_pay");
		String title=request.getParameter("title");
		String email=request.getParameter("email");
		String mobile=request.getParameter("mobile");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-name:"+name+"-sex:"+sex+"-city_id:"+city_id+"-city:"+city+
				"-identity_id:"+identity_id+"-identity:"+identity+
				"-school_id:"+school_id+"-school:"+school+"-org_id:"+org_id+"-org:"+org+
				"-specialty_id:"+specialty_id+"-specialty:"+specialty+
				"-title:"+title+"-mobile:"+mobile+"-email:"+email+"-introduction:"+introduction+
				"-pic:"+pic+"-ass_pay:"+ass_pay);
		
		if(access_token==null || uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;		
		} else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//登录用户ID
				Integer i_uid = Integer.valueOf(uid);
				Integer i_city_id=null;
				Integer i_identity_id=null;
				Integer i_school_id=null;
				Integer i_org_id=null;
				Integer i_specialty_id=null;
				Double d_ass_pay=null;
				
				if(city_id!=null && !city_id.equals("") && NumberUtil.isInteger(city_id)) {
					i_city_id=Integer.valueOf(city_id);
				}
				if(identity_id!=null && !identity_id.equals("") && NumberUtil.isInteger(identity_id)) {
					i_identity_id=Integer.valueOf(identity_id);
				}
				if(school_id!=null && !school_id.equals("") && NumberUtil.isInteger(school_id)) {
					i_school_id=Integer.valueOf(school_id);
				}
				if(org_id!=null && !org_id.equals("") && NumberUtil.isInteger(org_id)) {
					i_org_id=Integer.valueOf(org_id);
				}
				if(specialty_id!=null && !specialty_id.equals("") && NumberUtil.isInteger(specialty_id)) {
					i_specialty_id=Integer.valueOf(specialty_id);
				}
				if(ass_pay!=null && !ass_pay.equals("") && NumberUtil.isDouble(ass_pay)) {
					d_ass_pay=Double.valueOf(ass_pay);
				}
				UserTech tec= new UserTech();
				tec.setId(i_uid);
				tec.setName(name);
				tec.setSex(sex);
				tec.setCityId(i_city_id);
				tec.setCityName(city);
				tec.setIdentityId(i_identity_id);
				tec.setIdentityName(identity);
				tec.setSchoolId(i_school_id);
				tec.setSchoolName(school);
				tec.setOrgId(i_org_id);
				tec.setOrgName(org);
				tec.setSpecialtyId(i_specialty_id);
				tec.setSpecialtyName(specialty);
				tec.setEmail(email);
				tec.setUserMobile(mobile);
				tec.setTitle(title);
				tec.setAssPay(d_ass_pay);
				tec.setHeadPic(pic);
				tec.setIntroduction(introduction);
				
				try {
					this.userTecService.updateMasterInfoByPrimaryKeySelective(tec);
					errorCode = "0";
					errorMessage = "ok";
				} catch (Exception e) {
					errorCode = "20036";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
				}
			}
			
		}
		SimpleReBean reBean = new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		
		return gson.toJson(reBean);
	}
	

}

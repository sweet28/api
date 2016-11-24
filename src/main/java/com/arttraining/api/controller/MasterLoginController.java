package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.MasterLoginBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.UserOrgService;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login")
public class MasterLoginController {
	@Resource
	private UserTecService userTecService;
	@Resource
	private UserOrgService userOrgService;
	
	/***
	 * 根据名师账号密码登录APP
	 * 传递的参数:
	 * name--用户名 pwd--密码
	 * 
	 */
	@RequestMapping(value = "/master/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterLogin(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个参数是必选参数
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		
		ServerLog.getLogger().warn("name:"+name+"-pwd:"+pwd);
		//创建返回结果的对象
		MasterLoginBean masterLoginBean = new MasterLoginBean();
		if(name==null || pwd==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(name.equals("") || pwd.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			//判断登录的账号是否存在
			UserTech tec = this.userTecService.getMasterInfoByName(name);
			if(tec==null) {
				errorCode = "20022";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;	
			} else {
				//如果账号存在 则继续判断密码是否输入正确
				String pwdData=tec.getPwd();
				
				if (!MD5.check(
						MD5.encodeString(pwd + ConfigUtil.MD5_PWD_STR)
								+ ConfigUtil.MD5_PWD_STR, pwdData)) {
					errorCode = "20023";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20023;
				} else {
					//生成token
					String access_token = TokenUtil.generateToken(name);
					errorCode = "0";
					errorMessage = "ok";
					
					//查询到相应的名师详情,判断是否有所属的机构
					Integer i_org_id = tec.getOrgId();
					//如果所属机构不存在,则无需进行一次机构查询
					TecherShowOrgBean org=null;
					if(i_org_id!=null && i_org_id!=0) {
						org = this.userOrgService.getOneOrgByTecShow(i_org_id);
						masterLoginBean.setOrg(org);
					}
					masterLoginBean.setAccess_token(access_token);
					masterLoginBean.setAss_pay(tec.getAssPay());
					masterLoginBean.setAuth(tec.getAuthentication());
					masterLoginBean.setBrowse_num(tec.getBrowseNum());
					masterLoginBean.setCity(tec.getCityName());
					masterLoginBean.setCollege(tec.getSchoolName());
					masterLoginBean.setComment(tec.getCommentNum());
					masterLoginBean.setEmail(tec.getEmail());
					masterLoginBean.setFans_num(tec.getFansNum());
					masterLoginBean.setIdentity(tec.getIdentityName());
					masterLoginBean.setIntroduction(tec.getIntroduction());
					masterLoginBean.setMobile(tec.getUserMobile());
					masterLoginBean.setName(tec.getName());
					masterLoginBean.setPic(tec.getHeadPic());
					masterLoginBean.setRank(tec.getRank());
					masterLoginBean.setScore(tec.getScore());
					masterLoginBean.setSpecialty(tec.getSpecialtyName());
					masterLoginBean.setTitle(tec.getTitle());
					masterLoginBean.setUser_code(tec.getUserCode());
				}
			}
		}
		masterLoginBean.setError_code(errorCode);
		masterLoginBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(masterLoginBean));
		return gson.toJson(masterLoginBean);
	}
	
	/***
	 * 根据用户账号密码退出APP
	 * 传递的参数:
	 * access_token--验证token
	 * 
	 */
	@RequestMapping(value = "/master/exit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterExit(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个参数是必选参数
		String access_token=request.getParameter("access_token");
		
		ServerLog.getLogger().warn("access_token:"+access_token);
		if(access_token==null || access_token.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(TokenUtil.deleteToken(access_token)) {
			errorCode = "0";
			errorMessage = "ok";
		} else {
			errorCode = "20056";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20056;
		}
		SimpleReBean reBean = new SimpleReBean();
		reBean.setError_code(errorCode);
		reBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(reBean));
		return gson.toJson(reBean);
	}

}

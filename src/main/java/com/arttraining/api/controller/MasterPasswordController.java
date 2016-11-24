package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.impl.UserTecService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/forgot_pwd")
public class MasterPasswordController {
	@Resource
	private UserTecService userTecService;
	
	/***
	 * 找回名师密码接口
	 * 传递的参数:mobile--手机号码 new_pwd--修改密码
	 * 
	 */
	@RequestMapping(value = "/master/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterCreate(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个参数是必选参数
		String mobile=request.getParameter("mobile");
		String new_pwd=request.getParameter("new_pwd");
		
		ServerLog.getLogger().warn("mobile:"+mobile+"-new_pwd:"+new_pwd);
		
		if(mobile==null || new_pwd==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(mobile.equals("") || new_pwd.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			//名师更新密码
			UserTech tec = new UserTech();
			tec.setUserMobile(mobile);
			new_pwd = MD5.encodeString(MD5.encodeString(new_pwd
					+ ConfigUtil.MD5_PWD_STR)
					+ ConfigUtil.MD5_PWD_STR);
			tec.setPwd(new_pwd);
			try {
				this.userTecService.updateMasterInfoBySelective(tec);
				errorCode = "0";
				errorMessage = "ok";
				
			}catch(Exception e) {
				errorCode = "20036";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20036;
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

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
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/register")
public class MasterRegisterController {
	@Resource
	private UserTecService userTecService;
	
	/***
	 * 判断手机号是否已经注册
	 * 传递的参数:mobile--手机号码
	 */
	@RequestMapping(value = "/master/is_reg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterIsReg(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String mobile=request.getParameter("mobile");
		ServerLog.getLogger().warn("mobile:"+mobile);
		
		if(mobile==null || mobile.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			//判断手机号码是否存在于数据库中
			UserTech tec = this.userTecService.getMasterInfoByName(mobile);
			if(tec==null) {
				errorCode = "0";
				errorMessage = "ok";
			} else {
				errorCode = "20024";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20024;
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

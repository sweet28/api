package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.InviteCode;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.InviteCodeService;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/invite_code")
public class InviteCodeController {
	@Resource
	private InviteCodeService invCodeService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createInviteCode(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String mobile = "";
		String inviteType = "";
		
		mobile = request.getParameter("mobile");
		inviteType = request.getParameter("invite_type");
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);

		return gson.toJson(simReBean);
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object checkInviteCode(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String inviteCode = "";
		
		inviteCode = request.getParameter("invite_code");
		System.out.println("进入邀请码验证："+inviteCode+"----"+TimeUtil.getTimeStamp());
		if(inviteCode.equals("") || inviteCode == null){
			System.out.println(2);
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			System.out.println("invite_type null "+TimeUtil.getTimeStamp());
		}else{
			System.out.println(4);
			InviteCode invCode = new InviteCode();
			invCode = this.invCodeService.selectByCode(inviteCode);
			if(invCode == null || invCode.equals("")){
				errorCode = "20045";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20045;
			}else{
				errorCode = "0";
				errorMsg = "ok";
				
//				invCode.setIsUsed(1);
//				invCode.setUseTime(TimeUtil.getTimeStamp());
//				this.invCodeService.update(invCode);
				
				//增加生成优惠券的方法
			}
		}
		
		simReBean.setError_code(errorCode);
		simReBean.setError_msg(errorMsg);
		System.out.println(gson.toJson(simReBean)+"-"+TimeUtil.getTimeStamp());

		return gson.toJson(simReBean);
	}

	public static void main(String[] args) {

	}

}

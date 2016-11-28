package com.arttraining.api.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.InviteCode;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.InviteCodeService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/invite_code")
public class InviteCodeController {
	@Resource
	private InviteCodeService invCodeService;
	@Resource
	private UserStuService userStuService;
	
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
	/****
	 * 判断邀请码是否有效
	 * 传递的参数:invite_code--输入的邀请码
	 * uid--爱好者用户ID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object checkInviteCode(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String inviteCode = "";
		String uid="";
		String access_token="";
		
		inviteCode = request.getParameter("invite_code");
		uid=request.getParameter("uid");
		access_token=request.getParameter("access_token");
		ServerLog.getLogger().warn("access_token:"+access_token+"-inviteCode:"+inviteCode+"-uid:"+uid);
		
		//System.out.println("进入邀请码验证："+inviteCode+"----"+TimeUtil.getTimeStamp());
		if(inviteCode == null || uid == null || access_token==null){
			//System.out.println(2);
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			//System.out.println("invite_type null "+TimeUtil.getTimeStamp());
		} else if(inviteCode.equals("") || uid.equals("") || access_token.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else{
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
			//System.out.println(4);
			//用户ID
			Integer i_uid=Integer.valueOf(uid);
			//判断邀请码/兑换码是否存在
			InviteCode invCode = new InviteCode();
			invCode = this.invCodeService.selectByCode(inviteCode);
			if(invCode == null || invCode.equals("")){
				errorCode = "20060";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20060;
			} else{
					//如果邀请码或者兑换码存在的话 则需要为该用户生成一张优惠券的方法
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					//增加生成优惠券的方法
					//1.首先依据用户ID去获取用户信息
					UserStu userStu = this.userStuService.getUserStuById(i_uid);
					if(userStu!=null) {
						//兑换邀请码的人是谁
						InviteCode update_invCode = new InviteCode();
						update_invCode.setVisitorId(i_uid);
						update_invCode.setVisitorName(userStu.getName());
						update_invCode.setVisitorMoblie(userStu.getUserMobile());
						update_invCode.setVisitorType("stu");
						//标记邀请码已使用
						update_invCode.setIsUsed(1);
						update_invCode.setUseTime(Timestamp.valueOf(time));
						update_invCode.setId(invCode.getId());
						//生成优惠券
						Coupon coupon = new Coupon();
						coupon.setCreateTime(Timestamp.valueOf(time));
						coupon.setOrderCode(time);
						coupon.setExpiryDate(invCode.getExpiryDate());
						coupon.setFaceValue(invCode.getFaceValue());
						coupon.setFaceValueType(invCode.getFaceValueType());
						coupon.setName(invCode.getRemarks());
						coupon.setDescribel(invCode.getDescribel());
						coupon.setUserId(i_uid);
						coupon.setUserType("stu");
						String type=invCode.getInviteType();
						if(type!=null && NumberUtil.isInteger(type)) {
							coupon.setType(Integer.valueOf(type));
						}
						try {
							this.invCodeService.update(update_invCode, coupon);
							errorCode = "0";
							errorMsg = "ok";	
						} catch (Exception e) {
							errorCode = "20059";
							errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20059;
						}
					} else {
						errorCode = "20022";
						errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20022;
					}
			  	  }
				} else {
					errorCode = "20028";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			//System.out.println(gson.toJson(simReBean)+"-"+TimeUtil.getTimeStamp());
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			return gson.toJson(simReBean);
	}

	public static void main(String[] args) {

	}

}

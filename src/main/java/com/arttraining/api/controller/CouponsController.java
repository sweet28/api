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

import com.arttraining.api.bean.CouponsListBean;
import com.arttraining.api.bean.CouponsListReBean;
import com.arttraining.api.service.impl.CouponService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/coupons")
public class CouponsController {
	@Resource
	private CouponService couponService;
	
	/***
	 * 获取优惠券信息列表
	 * 传递的参数:access_token--验证
	 * uid --用户ID
	 * utype --用户类型
	 * self--分页 位置ID
	 * type--优惠券类型
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		//以下3个必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		
		//以下1个可选参数
		String self = request.getParameter("self");
		String type = request.getParameter("type");
		
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		
		CouponsListReBean couponsReBean = new CouponsListReBean();
		
		if(access_token==null || uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;	
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;	
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = TokenUtil.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid = Integer.valueOf(uid);
				
				//self--当前位置ID 在这里指的是优惠券ID 
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
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("offset", offset);
					map.put("limit", limit);
					map.put("type", type);
					
					List<CouponsListBean> coupons = this.couponService.getCouponListByUid(map);
					if(coupons.size()>0) {
						couponsReBean.setCoupons(coupons);
						errorCode = "0";
						errorMessage = "ok";
					}
					else {
						errorCode = "20007";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
				}
			}
			else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		couponsReBean.setError_code(errorCode);
		couponsReBean.setError_msg(errorMessage);
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(couponsReBean));
		return gson.toJson(couponsReBean);
	}

}

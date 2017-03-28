package com.arttraining.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.beanv2.PaymentAssessmentBean;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.impl.AssessmentService;
import com.arttraining.api.service.impl.CouponService;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.api.service.impl.OrdersServiceV3;
import com.arttraining.api.service.impl.PaymentSystemService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksAttchmentService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;

@Controller
@RequestMapping("/orders_v3")
public class OrdersControllerV3 {
	@Resource
	private OrdersService ordersService;
	@Resource
	private UserStuService userStuService;
	@Resource
	private WorksService workService;
	@Resource
	private AssessmentService assessmentService;
	@Resource
	private CouponService couponService;
	@Resource
	private TokenService tokenService;
	@Resource
	private WorksAttchmentService worksAttchmentService;
	@Resource
	private PaymentSystemService paymentSystemService;
	@Resource
	private OrdersServiceV3 ordersServiceV3;
	/**
	 * 用于其他支付方式支付开小灶
	 */
	@RequestMapping(value = "/buy/assessment/other", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object assessmentOther(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";

		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String is_check=request.getParameter("is_check");
		String order_id=request.getParameter("order_id");
		String order_number=request.getParameter("order_number");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"
				+uid+"-utype"+utype+"-is_check:"+is_check
				+"-order_id:"+order_id+"-order_number:"+order_number);
		
		PaymentAssessmentBean assPay=new PaymentAssessmentBean();
		
		if(access_token==null || uid==null || utype==null
				|| is_check==null || order_id==null
				|| order_number==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")
				|| utype.equals("") || is_check.equals("")
				|| order_id.equals("") || order_number.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) 
				|| !NumberUtil.isInteger(order_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//订单ID
				Integer i_order_id=Integer.valueOf(order_id);
				//继续判断是否勾选云币抵扣
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("order_id", i_order_id);
				map.put("order_number", order_number);
				map.put("is_check", is_check);
				map.put("uid", i_uid);
				map.put("utype", utype);
				
				assPay=this.paymentSystemService.updateAssessmentOrderInfoByOther(map);
				errorCode="0";
				errorMessage="ok";
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", assPay);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	//end
	@RequestMapping(value = "/update/assessment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object updateAssessment(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		String errorCode = "";
		String errorMsg = "";
		String accessToken = "";
		String uid = "";
		String orderNum = "";
		String payType = "";
		String attachment = "";
		String attrType = "";
		String attrLong = "";
		String isPay = "";
		String thumbnail = "";
		
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		orderNum = request.getParameter("order_number");
		payType = request.getParameter("pay_type");
		attachment = request.getParameter("attachment");
		attrType = request.getParameter("attr_type");
		attrLong = request.getParameter("attr_long");
		isPay = request.getParameter("is_pay");
		thumbnail = request.getParameter("thumbnail");
		//coffee add
		String coupon_id=request.getParameter("coupon_id");
		Integer i_coupon_id=0;
		Coupon coupon=null;
		//end
		
		ServerLog.getLogger().warn("uid:"+uid+"-token:"+accessToken+"-orderNum:"+orderNum+
				"-thumbnail:"+thumbnail+"-isPay:"+isPay+"-attrLong:"+attrLong+
				"-attrType:"+attrType+"-attachment:"+attachment+"-payType:"+payType);

		if(accessToken == null || uid == null || orderNum == null){
			System.out.println("进入订单更新2："+TimeUtil.getTimeStamp());
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (accessToken.equals("") || uid.equals("") || orderNum.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			//coffee begin
			// todo:判断token是否有效
			//boolean tokenFlag = TokenUtil.checkToken(accessToken);
			boolean tokenFlag = this.tokenService.checkToken(accessToken);
			if (tokenFlag) {
				System.out.println("进入订单更新3："+TimeUtil.getTimeStamp());
				//1.依据订单号获取订单信息
				Order order = this.ordersServiceV3.selectByOrderNumber(orderNum);
				if(order != null){
					System.out.println("进入订单更新4："+TimeUtil.getTimeStamp());
					//2.更新订单信息和测评信息
					Assessments ass = new Assessments();
					if(isPay != null && !("").equals(isPay)){
						Integer pay_status=Integer.parseInt(isPay);
						//订单状态
						order.setStatus(pay_status);
						//判断支付状态是否是已支付状态
						if(pay_status==ConfigUtil.STATUS_1){
							//支付时间
							order.setPayTime(TimeUtil.getTimeStamp());
							//支付类型(微信支付还是支付宝支付)
							if(payType != null && !("").equals(payType)){
								order.setPayType(payType);
							}
							//测评时间
							ass.setPayTime(TimeUtil.getTimeStamp());
							//测评状态 判断是否支付
							ass.setIsPay(1);
							//coffee add 新增修改优惠券ID为使用的状态
							if(coupon_id!=null && NumberUtil.isInteger(coupon_id)) {
								Date date = new Date();
								String time = TimeUtil.getTimeByDate(date);
								coupon = new Coupon();
								i_coupon_id=Integer.valueOf(coupon_id);
								coupon.setId(i_coupon_id);
								coupon.setIsUsed(1);
								coupon.setRemarks(time);
							}
							//end
							if(attachment != null && !("").equals(attachment)){
								ass.setStatus(ConfigUtil.STATUS_4);
							}else{
								ass.setStatus(ConfigUtil.STATUS_3);
							}
						}
					}
					//3.更新作品附件信息
					WorksAttchment workAtt = new WorksAttchment();
					//coffee add 更新登录用户作品数
					UserStu user = null;
					//end
					if(attachment != null && !("").equals(attachment)){
						workAtt.setStorePath(attachment);
						Integer i_uid=Integer.valueOf(uid);
						user=new UserStu();
						user.setId(i_uid);
						user.setWorkNum(1);
					}
					if(attrLong != null && !("").equals(attrLong)){
						workAtt.setDuration(attrLong);
					}
					if(attrType != null && !("").equals(attrType)){
						workAtt.setType(attrType);
					}
					if(thumbnail != null && !("").equals(thumbnail.trim())){
						workAtt.setThumbnail(thumbnail);
					}
				
					System.out.println(attrType+"_---------"+attachment +"-----------------"+thumbnail);
					try {
						this.ordersServiceV3.updateAndUpdateWorkAssAtt(coupon,order, workAtt, ass,user);
						System.out.println("进入订单更新5："+TimeUtil.getTimeStamp());
						errorCode = "0";
						errorMsg = "ok";
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("进入订单更新6："+TimeUtil.getTimeStamp());
						errorCode = "20032";
						errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
					}
				}else{
					System.out.println("进入订单更新7："+TimeUtil.getTimeStamp());
					errorCode = "20032";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
				}
			} else {
				errorCode = "20028";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
			//end
		}
		jo.put("error_code", errorCode);
		jo.put("error_msg", errorMsg);
		jo.put("uid", 0);
		jo.put("user_code", "");
		jo.put("name", "");
		ServerLog.getLogger().warn(jo.toString());
		return jo;
	}

}

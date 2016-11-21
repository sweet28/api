package com.arttraining.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.AssessmentListBean;
import com.arttraining.api.bean.AssessmentListReBean;
import com.arttraining.api.bean.OrderListMyBean;
import com.arttraining.api.bean.OrderListMyReBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	@Resource
	private OrdersService ordersService;
	@Resource
	private UserStuService userStuService;
	@Resource
	private WorksService workService;
	
	/***
	 * 获取用户订单信息列表
	 * @param request access_token--验证 uid--用户ID
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list_my", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listMy(HttpServletRequest request, HttpServletResponse response){
		String errorCode = "";
		String errorMsg = "";
		String access_token = "";
		String uid="";
		//以下两个是必选参数
		access_token = request.getParameter("access_token");
		uid = request.getParameter("uid");
		
		String status=request.getParameter("status");
		String self = request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-status:"+status);
		
		Integer limit=ConfigUtil.PAGESIZE;
		Integer offset=-1;
		
		OrderListMyReBean orderMyBean = new OrderListMyReBean();
		
		if(access_token == null || uid==null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
				Integer i_status=null;
				if(status==null || status.equals("") || !NumberUtil.isInteger(status)) {
					i_status=null;
				}
				else 
					i_status = Integer.valueOf(status);
				//用户ID 
				Integer i_uid = Integer.valueOf(uid);
				//依据用户ID去查询相应的订单列表
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uid", i_uid);
				map.put("offset", offset);
				map.put("limit",limit);
				map.put("status", i_status);
				List<OrderListMyBean> orderList = this.ordersService.getMyListOrderByUid(map);
				if(orderList.size()>0) {
					//循环读取list 依次查询作品信息
					for (OrderListMyBean order : orderList) {
						Integer type = order.getOrder_type();
						Integer order_id = order.getOrder_id();
						String order_number=order.getOrder_number();
						map.put("type", type);
						map.put("order_id", order_id);
						map.put("order_number", order_number);
						//获取作品相关的信息
						OrderWorkBean work = this.ordersService.getWorkInfoByListMy(map);
						if(work!=null) {
							order.setWork_id(work.getWork_id());
							order.setWork_title(work.getWork_title());
							order.setWork_pic(work.getWork_pic());
						}
					}
					orderMyBean.setOrders(orderList);
					errorCode = "0";
					errorMsg = "ok";
				}
				else {
					errorCode = "20007";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
		  }
		}
		orderMyBean.setError_code(errorCode);
		orderMyBean.setError_msg(errorMsg);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(orderMyBean));
		return gson.toJson(orderMyBean);
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object show(HttpServletRequest request, HttpServletResponse response){
		
		String errorCode = "";
		String errorMsg = "";
	
		//以下4个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String order_id = request.getParameter("order_id");
		String order_type = request.getParameter("order_type");
	
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-order_id:"+order_id+"-order_type:"+order_type);
		
		AssessmentListReBean assReBean = new AssessmentListReBean();
		
		if(access_token == null || uid==null || order_id==null || order_type==null){
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (access_token.equals("") || uid.equals("") || order_id.equals("") || order_type.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(order_id)) {
			errorCode = "20033";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//用户ID和订单ID
			Integer i_uid = Integer.valueOf(uid);
			Integer i_order_id=Integer.valueOf(order_id);
			//查询某个订单ID对应的测评列表信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_id", i_order_id);
			map.put("uid", i_uid);
			assReBean = this.ordersService.getAssListByShow(map);
			if(assReBean==null) {
				assReBean = new AssessmentListReBean();
				errorCode = "20007";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
			else {
				List<AssessmentListBean> assessments=assReBean.getAssessments();
				//判断测评信息是否为空
				if(assessments.size()>0) {
					//循环读取测评信息 从而名师头像
					for (AssessmentListBean ass : assessments) {
						Integer tid = ass.getTec_id();
						String pic = this.ordersService.getTecPicById(tid);
						ass.setTec_pic(pic);
					}
					assReBean.setAssessments(assessments);
					errorCode = "0";
					errorMsg = "ok";
				}
				else {
					errorCode = "20007";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
			
		}
		assReBean.setError_code(errorCode);
		assReBean.setError_msg(errorMsg);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(assReBean));
		return gson.toJson(assReBean);
	}
	
	@RequestMapping(value = "/create/assessment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createAssessment(HttpServletRequest request, HttpServletResponse response){
		SimpleReBean simReBean = new SimpleReBean();
		Gson gson = new Gson();
		String errorCode = "";
		String errorMsg = "";
		String account = "";
		
		String accessToken = "";
		String uid = "";
		String assType = "";
		double totalPay = 0;
		String totalStr = "";
		double couponPay = 0;
		String couponStr = "";
		double finalPay = 0;
		String finalStr = "";
		JSONArray teaArr = new JSONArray();
		String teaStr = "";
		String title = "";
		
		String content = "";
		String attachment = "";
		String attType = "";

		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		assType = request.getParameter("ass_type");
		totalStr = request.getParameter("total_pay");
		couponStr = request.getParameter("coupon_pay");
		finalStr = request.getParameter("final");
		title = request.getParameter("title");
		teaStr = request.getParameter("teacher_list"); 
		
		content = request.getParameter("content");
		attachment = request.getParameter("attachment");
		attType = request.getParameter("attr_type");
		
		System.out.println("uid:"+uid+"-token:"+accessToken+"-type:"+assType+"-total:"+totalStr+"-coupon:"+couponStr+"-final:"+finalStr+"-tec:"+teaArr.equals("[]")+"-title:"+title+"-进入订单创建："+account+TimeUtil.getTimeStamp()+request.toString());
		
		if(accessToken == null || ("").equals(accessToken.trim())){
			errorCode = "00000000000000";
			errorMsg = "token为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(uid == null || ("").equals(uid.trim())){
			errorCode = "00000000000000";
			errorMsg = "uid为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建1-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(assType == null || ("").equals(assType.trim())){
			errorCode = "00000000000000";
			errorMsg = "订单类型为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建2-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(totalStr == null || ("").equals(totalStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "订单总费用为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建3-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(couponStr == null || ("").equals(couponStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "优惠券抵扣金额为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建4-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(finalStr == null || ("").equals(finalStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "订单实付金额为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建5-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(title == null || ("").equals(title.trim())){
			errorCode = "00000000000000";
			errorMsg = "作品名称为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);	
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建6-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(teaStr == null || ("").equals(teaStr.trim()) || ("[]").equals(teaStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "测评名师列表为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建7-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}
		
		TokenUtil.delayTokenDeadline(accessToken);//token延时
		
		totalPay = Double.parseDouble(totalStr);
		couponPay = Double.parseDouble(couponStr);
		finalPay = Double.parseDouble(finalStr);
		try {
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建77-"+gson.toJson(simReBean));
//			teaArr = JSONObject.parseObject(teaStr)
//					.getJSONArray("teacher_list");
			teaArr = JSON.parseArray(teaStr);
			if(teaArr.size() < 0){
				System.out.println(TimeUtil.getTimeStamp() + "-订单创建777-"
						+ gson.toJson(simReBean));
				errorCode = "00000000000000";
				errorMsg = "测评名师列表为空";

				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);
				System.out.println(TimeUtil.getTimeStamp() + "-订单创建79-"
						+ gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}
		} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
			System.out.println(TimeUtil.getTimeStamp() + "-订单创建78-"
					+ gson.toJson(simReBean));
			errorCode = "00000000000000";
			errorMsg = "测评名师列表为空";

			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp() + "-订单创建79-"
					+ gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}

		Order order = new Order();
		order.setUserId(uid);
		IdWorker idWorker = new IdWorker(0, 0);
		String orderNum = idWorker.nextId() + "";
		//创建时间
		Date date = new Date();
		String time = TimeUtil.getTimeByDate(date);
		
		order.setCodeNumber(orderNum);
		order.setType(0);
		order.setCouponPay(couponPay);
		order.setFinalPay(finalPay);
		order.setMoney(totalPay);
		//新增订单创建时间 create_time order_code 和订单商品数量
		order.setCreateTime(Timestamp.valueOf(time));
		order.setOrderCode(time);
		order.setOrderDetailNum(teaArr.size());
		
		UserStu userStu = new UserStu();
		userStu = this.userStuService.getUserStuById(Integer.parseInt(uid));
		
		List<Assessments> assList = new ArrayList<Assessments>();
		for(int i = 0; i < teaArr.size(); i++){
			JSONObject jo = new JSONObject();
			jo = teaArr.getJSONObject(i);
			
			Assessments ass = new Assessments();
			ass.setAssType(assType);
			ass.setCreateTime(Timestamp.valueOf(time));
			ass.setOrderNumber(orderNum);
			ass.setStuId(Integer.parseInt(uid));
			ass.setStuName(userStu.getName());
			ass.setStatus(3);
			ass.setTecId(jo.getIntValue("tec_id"));
			ass.setTecName(jo.getString("tec_name"));
			ass.setCodes(idWorker.nextId() + "");
			//新增order_code
			ass.setOrderCode(time);
			assList.add(ass);
		}
		
		Works work = new Works();
		work.setOwner(Integer.parseInt(uid));
		work.setOwnerType("stu");
		work.setArtType(assType);
		work.setCreateTime(Timestamp.valueOf(time));
		work.setTitle(title);
		if(content != null && ("").equals(content.trim())){
			work.setContent(content);
		}
		work.setAssessmentsCode(orderNum);
		
		WorksAttchment workAtt = new WorksAttchment();
		
		int result = this.ordersService.insertAndUpdateWorkAssAtt(order, work, assList, workAtt);
		
		if (result == 1) {
			errorCode = "0";
			errorMsg = "ok";
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
			jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMsg);
			jsonObject.put("order_number", orderNum);
			jsonObject.put("create_time", time);
			
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建8-"+jsonObject);
			return jsonObject;
		} else {
			errorCode = "000000000000";
			errorMsg = "测评订单创建失败";//ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			System.out.println(TimeUtil.getTimeStamp()+"-订单创建9-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}
	}
	
	@RequestMapping(value = "/update/assessment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object updateAssessment(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		Gson gson = new Gson();
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

		System.out.println("进入订单更新000："+TimeUtil.getTimeStamp());
		
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		orderNum = request.getParameter("order_number");
		payType = request.getParameter("pay_type");
		attachment = request.getParameter("attachment");
		attrType = request.getParameter("attr_type");
		attrLong = request.getParameter("attr_long");
		isPay = request.getParameter("is_pay");
		thumbnail = request.getParameter("thumbnail");
		
		System.out.println("进入订单更新："+TimeUtil.getTimeStamp());
		
		if(accessToken == null || uid == null || orderNum == null){
			System.out.println("进入订单更新2："+TimeUtil.getTimeStamp());
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if (accessToken.equals("") || uid.equals("") || orderNum.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			System.out.println("进入订单更新3："+TimeUtil.getTimeStamp());
			Order order = this.ordersService.selectByOrderNumber(orderNum);
			if(order != null){
				System.out.println("进入订单更新4："+TimeUtil.getTimeStamp());
				order.setPayTime(TimeUtil.getTimeStamp());
				order.setPayType(payType);
				order.setStatus(4);
				
				WorksAttchment workAtt = new WorksAttchment();
				workAtt.setStorePath(attachment);
				workAtt.setDuration(attrLong);
				workAtt.setType(attrType);
				if(thumbnail != null || ("").equals(thumbnail.trim())){
					workAtt.setThumbnail(thumbnail);
				}
				
				int result = this.ordersService.updateAndUpdateWorkAssAtt(order, workAtt);
				if(result == 1){
					System.out.println("进入订单更新5："+TimeUtil.getTimeStamp());
					errorCode = "0";
					errorMsg = "ok";
				}else{
					System.out.println("进入订单更新6："+TimeUtil.getTimeStamp());
					errorCode = "20032";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
				}
			}else{
				System.out.println("进入订单更新7："+TimeUtil.getTimeStamp());
				errorCode = "20032";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			}
		}
		
		jo.put("error_code", errorCode);
		jo.put("error_msg", errorMsg);
		jo.put("uid", 0);
		jo.put("user_code", "");
		jo.put("name", "");

		System.out.println(TimeUtil.getTimeStamp()+"-update订单结束-"+jo);
		
		return jo;
	}

}

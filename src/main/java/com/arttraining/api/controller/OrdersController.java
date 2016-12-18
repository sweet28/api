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
import com.arttraining.api.bean.AssTecListBean;
import com.arttraining.api.bean.AssessmentListBean;
import com.arttraining.api.bean.AssessmentListReBean;
import com.arttraining.api.bean.OrderListMyBean;
import com.arttraining.api.bean.OrderListMyReBean;
import com.arttraining.api.bean.OrderRemainTimeBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.impl.AssessmentService;
import com.arttraining.api.service.impl.CouponService;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WorksAttchmentService;
import com.arttraining.api.service.impl.WorksService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
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
	@Resource
	private AssessmentService assessmentService;
	@Resource
	private CouponService couponService;
	@Resource
	private TokenService tokenService;
	@Resource
	private WorksAttchmentService worksAttchmentService;
	
	/***
	 * 用于获取订单剩余时间
	 * 传递的参数:order_id订单ID
	 * order_number 订单编号
	 */
	@RequestMapping(value = "/remaining/time", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object remainingTime(HttpServletRequest request, HttpServletResponse response){
		String errorCode = "";
		String errorMsg = "";
		
		//以下参数是必选参数
		String order_id=request.getParameter("order_id");
		String order_number=request.getParameter("order_number");
		
		ServerLog.getLogger().warn("order_id:"+order_id+"-order_number:"+order_number);
		
		OrderRemainTimeBean remain=new OrderRemainTimeBean();
		
		if(order_id==null || order_number==null) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(order_id.equals("") || order_number.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(order_id)) {
			errorCode = "20033";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//订单ID
			Integer i_order_id=Integer.valueOf(order_id);
			//依据订单ID去获取订单详情
			Order order=this.ordersService.selectByOrderNumber(order_number);
			if(order!=null) {
				//订单状态
				Integer order_status=order.getStatus();
				//如果订单状态为0 表示还处于待支付的状态 继续判断是否超过交易时间 
				if(order_status==0) {
					//如果订单状态为0 判断支付时间是否已经过期 如果过期 则更改相应的代码
					//如果已经错过支付时间 则关闭交易 如果有涉及到优惠券
					Date currentDate = new Date();
					Date payDate= order.getActiveTime();
					long diff=TimeUtil.isOverTime(payDate, currentDate);
					//当前时间减去有效时间 如果<0 表示支付超时
					if(diff<0) {
						//如果支付超时 关闭交易 将订单状态改为2 
						//依据优惠券的type来执行的处理
						Integer coupon_id=order.getCouponId();
						Integer coupon_type=order.getCouponType();
						Order upd_order=new Order();
						upd_order.setId(i_order_id);
						upd_order.setStatus(2);
						//coffee add 1215 新增取消交易时间
						upd_order.setCancelTime(currentDate);
						//end
						//则用户未选择优惠券抵用券
						Map<String, Object> map=new HashMap<String, Object>();
						if(coupon_id>0) {
							map.put("coupon_id", coupon_id);
							map.put("coupon_type", coupon_type);
						} 
						try {
							this.ordersService.updateOrderAndCoupon(upd_order, map, coupon_id);
						} catch (Exception e) {
							System.out.println("2222===");
						}
					} else {
						//如果支付未超时 则直接返回有效时间 和剩余支付时间
						Integer remain_time=Integer.valueOf(String.valueOf(diff));
						remain.setRemaining_time(remain_time);
					}
					errorCode = "0";
					errorMsg = "ok";
				} else {
					errorCode = "20065";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20065;
				}
			}
		}
		
		remain.setError_code(errorCode);
		remain.setError_msg(errorMsg);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(remain));
		return gson.toJson(remain);
	}
	/***
	 * 订单取消接口
	 * 传递的参数:access_token--验证
	 * uid--用户ID
	 * order_id--订单ID
	 * order_number--订单编号
	 * 
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object cancel(HttpServletRequest request, HttpServletResponse response){
		String errorCode = "";
		String errorMsg = "";
		//以下是必选参数
		String access_token = request.getParameter("access_token");
		//String uid = request.getParameter("uid"); 
		String order_id = request.getParameter("order_id"); 
		String order_number = request.getParameter("order_number"); 
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-order_id:"+order_id+
				"-order_number:"+order_number);
		if(access_token==null || order_id==null || order_number==null) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || order_id.equals("") || order_number.equals("")) {
			errorCode = "20032";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(order_id)) {
			errorCode = "20033";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//用户ID
				//Integer i_uid=Integer.valueOf(uid);
				//订单ID
				Integer i_order_id=Integer.valueOf(order_id);
				//依据订单ID去查找订单信息
				Order order=this.ordersService.selectByOrderNumber(order_number);
				if(order!=null) {
					Integer status=order.getStatus();
					Integer coupon_id=order.getCouponId();
					Integer coupon_type=order.getCouponType();
					//订单状态为0 待支付
					if(status==0) {
						Order upd_order=new Order();
						upd_order.setId(i_order_id);
						upd_order.setStatus(2);
						Date time =new Date();
						upd_order.setCancelTime(time);
						//判断是否恢复优惠券信息
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("order_id", i_order_id);
						map.put("coupon_id", coupon_id);
						map.put("coupon_type", coupon_type);
						try {
							this.ordersService.updateOrderAndCoupon(upd_order, map, coupon_id);
							errorCode = "0";
							errorMsg = "ok";
						} catch (Exception e) {
							errorCode = "20063";
							errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20063;
						}
					}
				} else {
					errorCode = "20063";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20063;
				}
			} else {
				errorCode = "20028";
				errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMsg);
		jsonObject.put("uid", 0);
		jsonObject.put("user_code","");
		jsonObject.put("name", "");
		
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
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
				boolean tokenFlag = tokenService.checkToken(access_token);
				if (tokenFlag) {
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
							//coffee add 1129 判断订单状态是否为1
							Integer order_status=order.getOrder_status();
							System.out.println("fffff==="+order_status);
							if(order_status==1) {
								//如果状态为1 表示订单已支付状态 然后我去查看测评状态
								Integer[] ass_status={ConfigUtil.STATUS_3,ConfigUtil.STATUS_4,ConfigUtil.STATUS_5};
								for (Integer ass : ass_status) {
									map.put("ass_status",ass);
									Integer ass_num= this.assessmentService.getAssStatusByOrderId(map);
									if(ass_num>0) {
										//如果存在测评数量 则更改订单状态
										order.setOrder_status(ass);
										if(ass==ConfigUtil.STATUS_5) {
											order.setAss_num(ass_num);
										}
										break;
									}
								}
							} else if(order_status==0) {
								//如果订单状态为0 判断支付时间是否已经过期 如果过期 则更改相应的代码
								//如果已经错过支付时间 则关闭交易 如果有涉及到优惠券
								Date currentDate = new Date();
								System.out.println("hhhhh1==="+currentDate.getTime());
								System.out.println("hhhhh22==="+order.getActive_time());
								Date payDate= TimeUtil.strToDate(order.getActive_time());
								System.out.println("hhhhh2==="+payDate.getTime());
								long diff=TimeUtil.isOverTime(payDate, currentDate);
								System.out.println("1111===");
								//当前时间减去有效时间 如果>0 表示支付超时
								if(diff<0) {
									//如果支付超时 关闭交易 将订单状态改为2 
									//依据优惠券的type来执行的处理
									order.setOrder_status(2);
									Integer coupon_id=order.getCoupon_id();
									Integer coupon_type=order.getCoupon_type();
									Order upd_order=new Order();
									upd_order.setId(order_id);
									upd_order.setStatus(2);
									upd_order.setCancelTime(currentDate);
									//System.out.println("2222===");
									//则用户未选择优惠券抵用券
									if(coupon_id>0) {
										map.put("coupon_id", coupon_id);
										map.put("coupon_type", coupon_type);
									} 
									try {
										this.ordersService.updateOrderAndCoupon(upd_order, map, coupon_id);
										System.out.println("333===");
									} catch (Exception e) {
										System.out.println("444===");
									}
								} else {
									//如果支付未超时 则直接返回有效时间 和剩余支付时间
									Integer remain=Integer.valueOf(String.valueOf(diff));
									order.setRemaining_time(remain);
									System.out.println("555===");
								}
								System.out.println("==="+diff);	
							}
							//coffee add 1208 状态为1的订单 表示已支付 去查询名师测评详情列表
							System.out.println("=========");	
							List<AssTecListBean> ass_tec_list=this.assessmentService.getAssTecListByOrderId(map);
							System.out.println("==="+ass_tec_list.size());	
							order.setAss_tec_list(ass_tec_list);
							//end
							//获取作品相关的信息
							OrderWorkBean work = this.ordersService.getWorkInfoByListMy(map);
							if(work!=null) {
								Integer work_id=work.getWork_id();
								String work_title=work.getWork_title();
								String work_pic=work.getWork_pic();
								order.setWork_id(work_id);
								order.setWork_title(work_title);
								if(work_pic==null || work_pic.equals("")) {
									//String work_attr=this.ordersService.getWorkAttById(work_id);
									WorksAttchment attr=this.worksAttchmentService.getOneAttByWorkId(work_id);
									if(attr!=null) {
										if(attr.getType().equals("pic")) {
											work_pic=ImageUtil.parseWorkPicPath(attr.getStorePath());
										}
									}
								} 
								order.setWork_pic(work_pic);
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
				} else {
					errorCode = "20028";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
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
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
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
						map.put("order_number", assReBean.getOrder_number());
						//coffee add 1129 判断订单状态是否为1
						Integer order_status=assReBean.getOrder_status();
						if(order_status==1) {
							//如果状态为1 表示订单已支付状态 然后我去查看测评状态
							Integer[] ass_status={ConfigUtil.STATUS_3,ConfigUtil.STATUS_4,ConfigUtil.STATUS_5};
							for (Integer ass : ass_status) {
								map.put("ass_status",ass);
								Integer ass_num= this.assessmentService.getAssStatusByOrderId(map);
								if(ass_num>0) {
									//如果存在测评数量 则更改订单状态
									assReBean.setOrder_status(ass);
									if(ass==ConfigUtil.STATUS_5) {
										assReBean.setAss_num(ass_num);
									}
									break;
								}
							}
						}			
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
				} else {
					errorCode = "20028";
					errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
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
		//String account = "";
		
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
		//String attachment = "";
		//String attType = "";
		//以下参数是必选参数
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		assType = request.getParameter("ass_type");
		totalStr = request.getParameter("total_pay");
		couponStr = request.getParameter("coupon_pay");
		finalStr = request.getParameter("final");
		title = request.getParameter("title");
		teaStr = request.getParameter("teacher_list"); 
		content = request.getParameter("content");
		//以下不是必选参数
		//attachment = request.getParameter("attachment");
		//attType = request.getParameter("attr_type");
		//coffee add
		String coupon_id=request.getParameter("coupon_id");
		String coupon_type=request.getParameter("coupon_type");
		Integer i_coupon_id=null;
		Integer i_coupon_type=null;
		//end
		
		ServerLog.getLogger().warn("uid:"+uid+"-token:"+accessToken+"-type:"+assType+"-total:"+totalStr+"-coupon:"+couponStr+"-final:"+finalStr+"-tec:"+teaArr+
				"-title:"+title+"-content:"+content+"-coupon_id:"+coupon_id+
				"-coupon_type:"+coupon_type);
		//System.out.println("uid:"+uid+"-token:"+accessToken+"-type:"+assType+"-total:"+totalStr+"-coupon:"+couponStr+"-final:"+finalStr+"-tec:"+teaArr.equals("[]")+"-title:"+title+"----content:---"+content+"-进入订单创建："+account+TimeUtil.getTimeStamp()+request.toString());
		
		if(accessToken == null || ("").equals(accessToken.trim())){
			errorCode = "00000000000000";
			errorMsg = "token为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(uid == null || ("").equals(uid.trim())){
			errorCode = "00000000000000";
			errorMsg = "uid为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建1-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(assType == null || ("").equals(assType.trim())){
			errorCode = "00000000000000";
			errorMsg = "订单类型为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建2-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(totalStr == null || ("").equals(totalStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "订单总费用为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建3-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(couponStr == null || ("").equals(couponStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "优惠券抵扣金额为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建4-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(finalStr == null || ("").equals(finalStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "订单实付金额为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建5-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(title == null || ("").equals(title.trim())){
			errorCode = "00000000000000";
			errorMsg = "作品名称为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建6-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}else if(teaStr == null || ("").equals(teaStr.trim()) || ("[]").equals(teaStr.trim())){
			errorCode = "00000000000000";
			errorMsg = "测评名师列表为空";
			
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			//System.out.println(TimeUtil.getTimeStamp()+"-订单创建7-"+gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}
		//coffee add
		// todo:判断token是否有效
		//boolean tokenFlag = TokenUtil.checkToken(accessToken);
		boolean tokenFlag = tokenService.checkToken(accessToken);
		if (tokenFlag) {
			//TokenUtil.delayTokenDeadline(accessToken);//token延时
			totalPay = Double.parseDouble(totalStr);
			couponPay = Double.parseDouble(couponStr);
			finalPay = Double.parseDouble(finalStr);
			try {
					System.out.println(TimeUtil.getTimeStamp()+"-订单创建77-"+gson.toJson(simReBean));
					teaArr = JSON.parseArray(teaStr);
					if(teaArr.size() <= 0){
						System.out.println(TimeUtil.getTimeStamp() + "-订单创建777-"+ gson.toJson(simReBean));
						errorCode = "00000000000000";
						errorMsg = "测评名师列表为空";
			
						simReBean.setError_code(errorCode);
						simReBean.setError_msg(errorMsg);
						ServerLog.getLogger().warn(gson.toJson(simReBean));
						//System.out.println(TimeUtil.getTimeStamp() + "-订单创建79-"+ gson.toJson(simReBean));
						return gson.toJson(simReBean);
					}
				} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
					System.out.println(TimeUtil.getTimeStamp() + "-订单创建78-"+ gson.toJson(simReBean));
					errorCode = "00000000000000";
					errorMsg = "测评名师列表为空";
		
					simReBean.setError_code(errorCode);
					simReBean.setError_msg(errorMsg);
					ServerLog.getLogger().warn(gson.toJson(simReBean));
					//System.out.println(TimeUtil.getTimeStamp() + "-订单创建79-"+ gson.toJson(simReBean));
					return gson.toJson(simReBean);
				}
			//1.先新增订单信息
			Order order = new Order();
			order.setUserId(uid);
			IdWorker idWorker = new IdWorker(0, 0);
			String orderNum = idWorker.nextId() + "";
			//创建时间
			Date date = new Date();
			String time = TimeUtil.getTimeByDate(date);
			
			order.setCodeNumber(orderNum);//订单号
			order.setType(0);//订单类型 0--测评 1--课程
			order.setCouponPay(couponPay);//优惠金额
			order.setFinalPay(finalPay);//实际金额
			order.setMoney(totalPay);//总金额
			//新增订单创建时间 create_time order_code 和订单商品数量
			order.setCreateTime(Timestamp.valueOf(time));
			order.setOrderCode(time);
			order.setOrderDetailNum(teaArr.size());
			order.setStatus(ConfigUtil.STATUS_0);
			Integer pay_time=ConfigUtil.PAY_TIME;
			order.setActiveTime(TimeUtil.getTimeByMinute(pay_time));
			
			if(coupon_id!=null && NumberUtil.isInteger(coupon_id)) {
				i_coupon_id=Integer.valueOf(coupon_id);
			}
			if(coupon_type!=null && NumberUtil.isInteger(coupon_type)) {
				i_coupon_type=Integer.valueOf(coupon_type);
			}
			order.setCouponId(i_coupon_id);
			order.setCouponType(i_coupon_type);
			
			//2.新增爱好者用户的作品数量
			UserStu userStu = new UserStu();
			userStu = this.userStuService.getUserStuById(Integer.parseInt(uid));
			//3.新增订单对应的测评列表信息
			List<Assessments> assList = new ArrayList<Assessments>();
			for(int i = 0; i < teaArr.size(); i++){
				JSONObject jo = new JSONObject();
				jo = teaArr.getJSONObject(i);
				//每一个测评信息
				Assessments ass = new Assessments();
				ass.setAssType(assType);
				ass.setCreateTime(Timestamp.valueOf(time));
				ass.setOrderNumber(orderNum);
				ass.setStuId(Integer.parseInt(uid));
				ass.setStuName(userStu.getName());
				ass.setStatus(ConfigUtil.STATUS_0);
				ass.setTecId(jo.getIntValue("tec_id"));
				//ass.setTecName(jo.getString("tec_name"));
				ass.setTecName(jo.getString("name"));
				ass.setCodes(idWorker.nextId() + "");
				//新增order_code
				ass.setOrderCode(time);
				assList.add(ass);
			}
			//4.新增作品信息
			Works work = new Works();
			work.setOwner(Integer.parseInt(uid));
			work.setOwnerType("stu");
			work.setArtType(assType);
			work.setCreateTime(Timestamp.valueOf(time));
			work.setOrderCode(time);
			work.setTitle(title);
			if(content != null && !("").equals(content.trim())){
				work.setContent(content);
			}
			//作品对应的订单号
			work.setAssessmentsCode(orderNum);
			//5.新增作品附件信息
			WorksAttchment workAtt = new WorksAttchment();
			
			try {
				int orderId=this.ordersService.insertAndUpdateWorkAssAtt(order, work, assList, workAtt);
				errorCode = "0";
				errorMsg = "ok";
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
				jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMsg);
				jsonObject.put("order_number", orderNum);
				jsonObject.put("create_time", time);
				jsonObject.put("order_id", orderId);
				ServerLog.getLogger().warn(jsonObject.toString());
				//System.out.println(TimeUtil.getTimeStamp()+"-订单创建8-"+jsonObject);
				return jsonObject;
			} catch (Exception e) {
				// TODO: handle exception
				errorCode = "000000000000";
				errorMsg = "测评订单创建失败";//ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
				
				simReBean.setError_code(errorCode);
				simReBean.setError_msg(errorMsg);
				ServerLog.getLogger().warn(gson.toJson(simReBean));
				//System.out.println(TimeUtil.getTimeStamp()+"-订单创建9-"+gson.toJson(simReBean));
				return gson.toJson(simReBean);
			}
		} else {
			errorCode = "20028";
			errorMsg = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			simReBean.setError_code(errorCode);
			simReBean.setError_msg(errorMsg);
			ServerLog.getLogger().warn(gson.toJson(simReBean));
			return gson.toJson(simReBean);
		}
		//end
	}
	
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

		
		//System.out.println("进入订单更新000："+TimeUtil.getTimeStamp());
		
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
		
		//System.out.println(thumbnail+"-进入订单更新："+TimeUtil.getTimeStamp()+"-"+attachment);
		
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
			boolean tokenFlag = tokenService.checkToken(accessToken);
			if (tokenFlag) {
				System.out.println("进入订单更新3："+TimeUtil.getTimeStamp());
				//1.依据订单号获取订单信息
				Order order = this.ordersService.selectByOrderNumber(orderNum);
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
							order.setPayType(payType);
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
						this.ordersService.updateAndUpdateWorkAssAtt(coupon,order, workAtt, ass,user);
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
		//System.out.println(TimeUtil.getTimeStamp()+"-update订单结束-"+jo);
		return jo;
	}

}

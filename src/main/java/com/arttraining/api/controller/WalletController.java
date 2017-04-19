package com.arttraining.api.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.beanv2.CloudHelpRechargeBean;
import com.arttraining.api.beanv2.CloudMoneyDetailBean;
import com.arttraining.api.beanv2.CloudTranformMoneyBean;
import com.arttraining.api.beanv2.WalletOrderBean;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.Wallet;
import com.arttraining.api.pojo.WalletOrder;
import com.arttraining.api.pojo.WalletValueTransform;
import com.arttraining.api.service.impl.OpenClassLiveService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.UserStuService;
import com.arttraining.api.service.impl.WalletService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;

@Controller
@RequestMapping("/wallet/cloud")
public class WalletController {
	@Resource
	private WalletService walletService;
	@Resource
	private TokenService tokenService;
	@Resource
	private OpenClassLiveService openClassLiveService;
	@Resource
	private UserStuService userStuService;
	
	/**
	 * 帮好友充值云币
	 */
	@RequestMapping(value = "/help/recharge", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object helpRecharge(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		String telephone=request.getParameter("telephone");
		
		ServerLog.getLogger().warn("telephone:"+telephone);
		
		List<CloudHelpRechargeBean> helpBean=new ArrayList<CloudHelpRechargeBean>();
		
		if(telephone==null || telephone.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!PhoneUtil.isMobile(telephone)) {
			errorCode="20044";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20044;
		} else {
			//依据传递的电话号码来查询相应的用户信息
			helpBean=this.userStuService.getHelpUserByMobile(telephone);
			if(helpBean.size()>0) {
				errorCode="0";
				errorMessage="ok";
			} else {
				helpBean=new ArrayList<CloudHelpRechargeBean>();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",helpBean);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 直播赠送礼物的接口(付费礼物)
	 */
	@RequestMapping(value = "/live/consume", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object liveConsume(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下3个参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
		String gift_id = request.getParameter("gift_id");
		String number = request.getParameter("number");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
						+"-utype:"+utype+"-gift_id:"+gift_id+"-number:"+number
						+"-room_id:"+room_id+"-chapter_id:"+chapter_id);
				
		if(access_token==null || uid==null || utype==null 
					|| gift_id==null || number==null
					|| room_id==null || chapter_id==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
			|| gift_id.equals("") || number.equals("")
			|| chapter_id.equals("") || room_id.equals("") ) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)
			|| !NumberUtil.isInteger(gift_id)
			|| !NumberUtil.isInteger(number)
			|| !NumberUtil.isInteger(chapter_id) 
			|| !NumberUtil.isInteger(room_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//礼物ID
				Integer i_gift_id=Integer.valueOf(gift_id);
				//礼物数量
				Integer num=Integer.valueOf(number);
				Double gift_cloud=0.0;
				//首先依据礼物ID查询相应的礼物积分信息
				LiveGift gift= this.openClassLiveService.getGiftInfoById(i_gift_id);
				if(gift!=null) {
					gift_cloud=gift.getPrice();
				}
				//直播礼物所需消费的云币
				gift_cloud=gift_cloud*num;
				//然后依据用户ID和类型查询云币信息
				Double total_cloud=0.0;
				Wallet wallet=this.walletService.getCloudMoneyByUid(i_uid, utype);
				if(wallet!=null) {
					total_cloud=wallet.getCloudMoney();
				}
				if(total_cloud.doubleValue()<gift_cloud.doubleValue()) {
						errorCode="20093";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20093;
				} else {
					//1.依据直播间ID来获取直播间信息
					LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
					if(room!=null) {
						Integer owner=room.getOwner();
						String owner_type=room.getOwnerType();
										
						//新增一条直播评论信息
						LiveComment comment=new LiveComment();
						//获取当前时间
						Date date = new Date();
						String time=TimeUtil.getTimeByDate(date);
						comment.setVisitor(i_uid);
						comment.setVisitorType(utype);
						comment.setHost(owner);
						comment.setHostType(owner_type);
						comment.setForeignKey(i_chapter_id);
						comment.setCreateTime(date);
						comment.setOrderCode(time);
						comment.setContent(gift_id);
						comment.setType("comment");
						comment.setBuyNumber(num);
						comment.setRemarks("gift");
								
					try {
							this.openClassLiveService.insertLiveComment(comment);
							//coffee add 0301 直播礼物消费云币
							this.walletService.updateCloudAndDetailInfoByUid(i_uid, utype, total_cloud, gift_cloud);
							//end
							errorCode = "0";
							errorMessage = "ok";
						} catch (Exception e) {
							// TODO: handle exception
							errorCode = "20073";
							errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20073;
						}
					} else {
						errorCode = "20088";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
					}
				}
						
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data","");
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 查询云币详情列表接口
	 */
	@RequestMapping(value = "/detail/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object detailQuery(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下3个参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
				
		//以下参数不是必选参数 分页时用到的参数
		String self = request.getParameter("self");
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
						
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid
					+"-utype:"+utype+"-self:"+self);
		
		List<CloudMoneyDetailBean> detaliList=new ArrayList<CloudMoneyDetailBean>();
		
		if(access_token==null || uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") ) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//继续判断分页参数
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else {
				offset=Integer.valueOf(self);
			}
			
			if(offset==-10) {
				errorCode="20032";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
			} else {
				// todo:判断token是否有效
				boolean tokenFlag = this.tokenService.checkToken(access_token);
				if (tokenFlag) {
					Integer i_uid=Integer.valueOf(uid);
					//依据用户ID和类型查询相应的钱包云币详情列表信息
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("offset", offset);
					map.put("limit", limit);
					detaliList = this.walletService.getCloudListByUid(map);
					if(detaliList.size()>0) {
						errorCode="0";
						errorMessage="ok";
					} else {
						detaliList=new ArrayList<CloudMoneyDetailBean>();
						errorCode="20007";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
					
				}  else {
					errorCode="20028";
					errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",detaliList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 查询云币接口
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下3个参数是必选参数
		String access_token=request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String utype = request.getParameter("utype");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		
		Double cloud_money=0.0;
		if(access_token==null || uid==null || utype==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") ) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//依据用户ID和类型查询相应的云币信息
				Integer i_uid=Integer.valueOf(uid);
				Wallet wallet=this.walletService.getCloudMoneyByUid(i_uid, utype);
				if(wallet!=null) {
					cloud_money=wallet.getCloudMoney();
					cloud_money=NumberUtil.formatDouble1(cloud_money);
				}
				errorCode="0";
				errorMessage="ok";
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",cloud_money);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/***
	 * 云币充值的接口
	 */
	@RequestMapping(value = "/update/order", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object updateOrder(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String orderId=request.getParameter("order_id");
		String orderNum = request.getParameter("order_number");
		String payType = request.getParameter("pay_type");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-orderId:"+orderId+"-orderNum:"+orderNum+"-payType:"+payType);
		
		Double cloud_money=0.0;
		
		if(access_token==null || orderId==null 
				|| orderNum==null || payType==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || payType.equals("") 
				|| orderId.equals("") || orderNum.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(orderId)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//订单ID
				Integer i_order_id=Integer.valueOf(orderId);
				//用户ID
				//Integer i_uid=Integer.valueOf(uid);
				Integer i_uid=0;
				
				WalletOrder order=this.walletService.getOneWalletInfoById(i_order_id, orderNum);
				if(order!=null) {
					cloud_money=order.getBydouble1();
					//coffee add 0407
					i_uid=Integer.valueOf(order.getUserId());
					//end
					//获取当前时间
					Date date=new Date();
					//修改订单状态信息
					WalletOrder upd_order=new WalletOrder();
					upd_order.setPayTime(date);
					upd_order.setPayType(payType);
					upd_order.setStatus(ConfigUtil.STATUS_1);
					upd_order.setId(i_order_id);
					upd_order.setUserId(order.getUserId());
					upd_order.setByint2(order.getByint2());
					
					//修改钱包信息
					Wallet upd_wallet=new Wallet();
					upd_wallet.setUserId(i_uid);
					upd_wallet.setUserType("stu");
					//bydouble1--指的是云币
					upd_wallet.setCloudMoney(cloud_money);
					
					try{
						this.walletService.updateCloudMoneyByRecharge(upd_order, upd_wallet);
						errorCode = "0";
						errorMessage = "ok";
					} catch (Exception e) {
						errorCode = "000000000000";
						errorMessage = "订单创建失败";
					}
				}
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",cloud_money);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 云币充值的接口
	 * 
	 */
	@RequestMapping(value = "/create/order1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createOrder1(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String transform_id = request.getParameter("transform_id");
		String totalStr = request.getParameter("total_pay");
		String couponStr = request.getParameter("coupon_pay");
		String finalStr = request.getParameter("final_pay");
		String cloud_money = request.getParameter("cloud_money");
		
		ServerLog.getLogger().warn("uid:"+uid+"-access_token:"+access_token+
				"-total_pay:"+totalStr+"-coupon_pay:"+couponStr+"-final:"+finalStr+
				"-transform_id"+transform_id+"-cloud_money:"+cloud_money);
		
		WalletOrderBean wallet=new WalletOrderBean();
		
		if(access_token==null || uid==null || totalStr==null 
				|| finalStr==null || transform_id==null || cloud_money==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || transform_id.equals("") 
				|| totalStr.equals("") || finalStr.equals("") || cloud_money.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)
				|| !NumberUtil.isInteger(transform_id)
				|| !NumberUtil.isDouble(totalStr)
				|| !NumberUtil.isDouble(finalStr)
				|| !NumberUtil.isDouble(cloud_money)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//价值转换ID
				Integer tid=Integer.valueOf(transform_id);
				
				//coffee add 
				
				Double totalPay=Double.valueOf(totalStr);
				Double finalPay=Double.valueOf(finalStr);
				Double couponPay=0.0;
				Double icloud=Double.valueOf(cloud_money);
				if(couponStr!=null && NumberUtil.isDouble(couponStr)) {
					couponPay=Double.valueOf(couponStr);
				}
				//新增充值订单信息
				WalletOrder order=new WalletOrder();
				order.setUserId(uid);
				IdWorker idWorker = new IdWorker(0, 0);
				String orderNum = idWorker.nextId() + "";
				// 创建时间
				Date date = new Date();
				String time = TimeUtil.getTimeByDate(date);
				order.setCodeNumber(orderNum);
				order.setCouponPay(couponPay);//优惠金额
				order.setFinalPay(finalPay);// 实际金额
				order.setMoney(totalPay);// 总金额
				order.setCreateTime(date);
				order.setOrderCode(time);
				order.setStatus(ConfigUtil.STATUS_0);//订单支付状态
				//Integer pay_time = ConfigUtil.PAY_TIME;//设置支付时间
				//order.setActiveTime(TimeUtil.getTimeByMinute(pay_time));
				order.setByint1(tid);
				order.setBydouble1(icloud);
				try {
					int orderId=this.walletService.insertCloudMoneyByRecharge(order);
					errorCode = "0";
					errorMessage = "ok";
					wallet.setOrder_id(orderId);
					wallet.setOrder_number(orderNum);
					wallet.setCreate_time(time);
				} catch (Exception e) {
					errorCode = "000000000000";
					errorMessage = "订单创建失败";
				}
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",wallet);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	/**
	 * 云币充值的接口
	 * 
	 */
	@RequestMapping(value = "/create/order", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object createOrder(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String transform_id = request.getParameter("transform_id");
		//coffee add 0407 
		String charge_uid = request.getParameter("charge_uid");
		//end
		
		
		ServerLog.getLogger().warn("uid:"+uid+"-access_token:"+access_token+
				"-transform_id:"+transform_id+"-charge_uid:"+charge_uid);
		
		WalletOrderBean wallet=new WalletOrderBean();
		
		if(access_token==null || uid==null || transform_id==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || transform_id.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid)
				|| !NumberUtil.isInteger(transform_id)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			// todo:判断token是否有效
			boolean tokenFlag = this.tokenService.checkToken(access_token);
			if (tokenFlag) {
				//价值转换ID
				Integer tid=Integer.valueOf(transform_id);
				//coffee add 0303 begin 依据价值转换ID查询相应的价值转换信息
				WalletValueTransform value=this.walletService.getWalletValueInfoById(tid);
				Double totalPay=0.0;
				Double finalPay=0.0;
				Double couponPay=0.0;
				Double icloud=0.0;
				if(value!=null) {
					totalPay=value.getMoney();
					icloud=value.getCloudMoney();
					finalPay=value.getMoney();
					//新增充值订单信息
					WalletOrder order=new WalletOrder();
					
					IdWorker idWorker = new IdWorker(0, 0);
					String orderNum = idWorker.nextId() + "";
					// 创建时间
					Date date = new Date();
					String time = TimeUtil.getTimeByDate(date);
					order.setCodeNumber(orderNum);
					order.setCouponPay(couponPay);//优惠金额
					order.setFinalPay(finalPay);// 实际金额
					order.setMoney(totalPay);// 总金额
					order.setCreateTime(date);
					order.setOrderCode(time);
					order.setStatus(ConfigUtil.STATUS_0);//订单支付状态
					//Integer pay_time = ConfigUtil.PAY_TIME;//设置支付时间
					//order.setActiveTime(TimeUtil.getTimeByMinute(pay_time));
					order.setByint1(tid);
					order.setBydouble1(icloud);
					//coffee add 0407
					if(charge_uid!=null && NumberUtil.isInteger(charge_uid)) {
						order.setUserId(charge_uid);
					} else {
						order.setUserId(uid);
					}
					order.setByint2(Integer.valueOf(uid));
					//end
					try {
						int orderId=this.walletService.insertCloudMoneyByRecharge(order);
						errorCode = "0";
						errorMessage = "ok";
						wallet.setOrder_id(orderId);
						wallet.setOrder_number(orderNum);
						wallet.setCreate_time(time);
					} catch (Exception e) {
						errorCode = "000000000000";
						errorMessage = "订单创建失败";
					}
				} else {
					errorCode = "000000000000";
					errorMessage = "订单创建失败";
				}
			} else {
				errorCode="20028";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",wallet);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 云币和钱价值转换列表信息的接口
	 * 
	 */
	@RequestMapping(value = "/tranform/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object tranformList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		List<CloudTranformMoneyBean> tranformList=this.walletService.getWalletMoneyList();
		if(tranformList.size()>0) {
			errorCode="0";
			errorMessage="ok";
		} else {
			tranformList=new ArrayList<CloudTranformMoneyBean>();
			errorCode="20007";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",tranformList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
		
	}
}

package com.arttraining.api.controller;

import java.util.ArrayList;
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
import com.arttraining.api.beanv2.LiveChapterOrderListBean;
import com.arttraining.api.beanv2.PaymentSystemOrderBean;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.Wallet;
import com.arttraining.api.pojo.WalletSystem;
import com.arttraining.api.service.impl.OpenClassLiveService;
import com.arttraining.api.service.impl.PaymentSystemService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.api.service.impl.WalletService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
@RequestMapping("/payment/")
public class PaymentSystemController {
	@Resource
	private TokenService tokenService;
	@Resource
	private OpenClassLiveService openClassLiveService;
	@Resource
	private WalletService walletService;
	@Resource
	private PaymentSystemService paymentService;
	
	/**
	 * 用于查看我的直播课程列表
	 * */
	@RequestMapping(value = "/chapter/order/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object orderList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		//以下不是必选参数
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+
				"-uid:"+uid+"-utype:"+utype+"-self:"+self);
		//用于分页时的参数
		Integer limit=ConfigUtil.PAGESIZE;
		Integer offset=-1;
		
		List<LiveChapterOrderListBean> orderList=new ArrayList<LiveChapterOrderListBean>();
				
		if(access_token==null || uid==null || utype==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//继续判断分页
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else {
				offset=Integer.valueOf(self);
			}
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				//继续判断token是否失效
				boolean tokenFlag = tokenService.checkToken(access_token);
				if (tokenFlag) {
					//用户ID
					Integer i_uid=Integer.valueOf(uid);
					//继续判断用户购买课时订单列表信息
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", i_uid);
					map.put("utype", utype);
					map.put("offset", offset);
					map.put("limit", limit);
					orderList=this.paymentService.getLiveChapterOrderListByUid(map);
					if(orderList.size()>0) {
						errorCode="0";
						errorMessage="ok";
					} else {
						orderList=new ArrayList<LiveChapterOrderListBean>(); 
						errorCode = "20007";
						errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
				} else {
					errorCode = "20028";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",orderList);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 直播课时其他支付方式购买成功调用的接口
	 */
	@RequestMapping(value = "/buy/chapter/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object chapterUpdate(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String order_number=request.getParameter("order_number");
		String pay_type=request.getParameter("pay_type");
		
		ServerLog.getLogger().warn("access_token:"+access_token+
				"-order_number:"+order_number+"-pay_type:"+pay_type);
		
		if(access_token==null || order_number==null || pay_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || order_number.equals("")
				|| pay_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//首先去订单表去查询是否存在订单
				WalletSystem payment=this.paymentService.getWalletSystemInfoByOrderNumber(order_number);
				if(payment!=null) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("order_number", order_number);
					map.put("pay_type", pay_type);
					map.put("order_id", payment.getId());
					map.put("chapter_id", payment.getByint1());
					
					this.paymentService.updatePaymentSystemChapterInfoByOther(map);
					errorCode="0";
					errorMessage="ok";
				} else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
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
	 * 用于其他支付方式购买直播课时
	 */
	@RequestMapping(value = "/buy/chapter/other", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object chapterOther(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");
		String buy_type=request.getParameter("buy_type");
		String is_check=request.getParameter("is_check");
				
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-chapter_id:"+chapter_id+"-room_id:"+room_id+
				"-buy_type:"+buy_type+"-is_check:"+is_check);
		
		PaymentSystemOrderBean order=new PaymentSystemOrderBean();
		
		if(access_token==null || uid==null || utype==null 
				|| room_id==null || chapter_id==null 
				|| buy_type==null || is_check==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("") 	
				|| room_id.equals("") || buy_type.equals("")
				|| is_check.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) 
				|| !NumberUtil.isInteger(chapter_id) 
				|| !NumberUtil.isInteger(room_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.依据课时ID来查询课时信息
					LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
					if(chapter!=null) {
						//课时费用 传递的购买类型(live--直播购买 record--重播购买)
						Double chapter_price=0.0;
						if(buy_type.equals("live")) {
							chapter_price=chapter.getLivePrice();
						} else if(buy_type.equals("record")) {
							chapter_price=chapter.getRecordPrice();
						}
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("uid", i_uid);
						map.put("utype", utype);
						map.put("chapter_id", i_chapter_id);
						map.put("chapter_name", chapter.getName());
						map.put("room_id", i_room_id);
						map.put("consume_way","购买直播课时");
						map.put("chapter_price",chapter_price);
						map.put("is_check",is_check);
						
						order=this.paymentService.updatePaymentLiveChapterByOther(map);
						errorCode="0";
						errorMessage="ok";
					}
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
						
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data",order);
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}
	
	/**
	 * 用于云币支付购买直播课时
	 */
	@RequestMapping(value = "/buy/chapter/clound", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object chapterClound(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String room_id=request.getParameter("room_id");
		String chapter_id=request.getParameter("chapter_id");
		String buy_type=request.getParameter("buy_type");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+
				"-utype:"+utype+"-chapter_id:"+chapter_id+"-room_id:"+room_id+
				"-buy_type:"+buy_type);
		
		if(access_token==null || uid==null || utype==null 
				|| room_id==null || chapter_id==null || buy_type==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") 
				|| utype.equals("") || chapter_id.equals("") 	
				|| room_id.equals("") || buy_type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) 
				|| !NumberUtil.isInteger(chapter_id) 
				|| !NumberUtil.isInteger(room_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			boolean tokenFlag = tokenService.checkToken(access_token);
			if (tokenFlag) {
				//直播间ID
				Integer i_room_id=Integer.valueOf(room_id);
				//课时ID
				Integer i_chapter_id=Integer.valueOf(chapter_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.依据直播间ID来获取直播间信息
				LiveRoom room=this.openClassLiveService.getLiveRoomById(i_room_id);
				if(room!=null) {
					//2.依据课时ID来查询课时信息
					LiveChapterPlan chapter=this.openClassLiveService.getChapterPlan(i_chapter_id);
					if(chapter!=null) {
						//课时费用
						Double chapter_price=0.0;
						if(buy_type.equals("live")) {
							chapter_price=chapter.getLivePrice();
						} else if(buy_type.equals("record")) {
							chapter_price=chapter.getRecordPrice();
						}
						Wallet wallet=this.walletService.getCloudMoneyByUid(i_uid, utype);
						if(wallet!=null) {
							//当前用户的云币
							Double cloud_money=wallet.getCloudMoney();
							if(chapter_price.doubleValue()<=cloud_money.doubleValue()) {
								Map<String, Object> map=new HashMap<String, Object>();
								map.put("uid", i_uid);
								map.put("utype", utype);
								map.put("total_cloud", cloud_money);
								map.put("consume_cloud", chapter_price);
								map.put("chapter_id", i_chapter_id);
								map.put("chapter_name", chapter.getName());
								map.put("room_id", i_room_id);
								map.put("consume_way","购买直播课时");
								
								this.paymentService.updatePaymentLiveChapterByCloud(map);
								errorCode="0";
								errorMessage="ok";
							} else {
								errorCode = "20093";
								errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20093;
							}
						}
					}
				} else {
					errorCode = "20088";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20088;
				}
						
			} else {
				errorCode = "20028";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20028;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data","");
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;
	}

}

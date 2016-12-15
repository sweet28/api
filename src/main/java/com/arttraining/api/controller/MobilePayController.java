package com.arttraining.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.arttraining.commons.util.pay.AliPay.AlipayConfig;
import com.arttraining.commons.util.pay.AliPay.AlipayCore;
import com.arttraining.commons.util.pay.AliPay.RSA;
import com.arttraining.commons.util.pay.WXPay.HttpRequest;
import com.arttraining.commons.util.pay.WXPay.RequestHandler;
import com.arttraining.commons.util.pay.WXPay.XMLUtil;
import com.arttraining.commons.util.pay.utils.MD5;
import com.arttraining.commons.util.pay.utils.MessageModel;
import com.google.gson.Gson;

@Controller
@RequestMapping("/pay/mobile")
/**
 * 请求支付
 *
 */
public class MobilePayController {
	@Resource
	private OrdersService ordersService;
	@Resource
	private TokenService tokenService;

	private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	private static final String PAY_METHOD_ALI = "alipay";
	private static final String PAY_METHOD_WX = "wxpay";
	private static final String PAY_SOURCE_IOS = "ios";
	private static final String PAY_SOURCE_ANDROID = "android";
	//todo:wx
	private static final String PAY_WX_APP_ID = "wx7d6ed84ec930fb37";
	private static final String PAY_WX_MCH_ID = "1415449402";
	

	/**
	 * 请求支付
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/do_pay", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doPay(HttpServletRequest request, HttpServletResponse response, MessageModel model) {
		String accessToken = request.getParameter("access_token");
		String uid = request.getParameter("uid");
		String order_id = request.getParameter("order_number");
		String pay_method = request.getParameter("pay_method");//支付方式：wxpay||alipay
		String pay_source = request.getParameter("pay_source");//支付来源：ios||android
		String show_url = request.getParameter("show_url");//show_url 是只在h5支付的时候，必须传的参数，是用户支付过程中取消支付所返回的页面
		
		System.out.println(TimeUtil.getTimeStamp()+"-进入支付：order_no:"+order_id+"-accessToken:"+accessToken+"-method:"+pay_method+"-source:"+pay_source+"-show_url:"+show_url);
//		order_id = "802121234509876234";
//		pay_method = PAY_METHOD_WX;
//		pay_source = PAY_SOURCE_ANDROID;
//		accessToken = "test_token";
//		uid = "0";
		//System.out.println(TokenUtil.checkToken(accessToken));
		if (order_id == null || pay_method == null || pay_source == null
				|| accessToken == null || uid == null || ("").equals(order_id)
				|| ("").equals(pay_method) || ("").equals(pay_method)
				|| ("").equals(accessToken) || ("").equals(uid)) {
			SimpleReBean simReBean = new SimpleReBean();
			Gson gson = new Gson();

			simReBean.setError_code("20032");
			simReBean.setError_msg(ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
			System.out.println(TimeUtil.getTimeStamp() + "-支付1-"
					+ gson.toJson(simReBean));
			return gson.toJson(simReBean);
		} //else if(TokenUtil.checkToken(accessToken)){
		else if(tokenService.checkToken(accessToken)){
			TokenUtil.delayTokenDeadline(accessToken);//token延时
			
			String product_name="云互艺评测";//订单名称
			String order_price="";//订单金额(ali)
			String order_price_wx="";//订单金额(wx)
			try {
				Order order = this.ordersService.selectByOrderNumber(order_id);
				order_price = order.getFinalPay().toString();
				int int_order_price = (int)(Double.parseDouble(order_price)*100);//微信金额 以分为单位
				order_price_wx =  Integer.toString(int_order_price);
				
				// double demo_price = 0.1;//从数据库查询出的订单价格 demo:0.1元
				// int int_order_price = (int)(demo_price*100);
				// order_price_wx = Integer.toString(int_order_price);
			} catch (Exception e) {
				model.setError_code("20032");
				model.setError_msg(ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
				e.printStackTrace();
			}
			if((PAY_METHOD_WX).equals(pay_method)){
				// 初始化 RequestHandler
				RequestHandler reqHandler = new RequestHandler(request, response);
				// 拼接参数
				Random random = new Random();
				String noncestr = MD5.GetMD5Code(String.valueOf(random.nextInt(10000)));// 生成随机字符串
				String timestamp = Long.toString(System.currentTimeMillis() / 1000);// 生成1970年到现在的秒数

				reqHandler.setParameter("appid", PAY_WX_APP_ID);
				reqHandler.setParameter("mch_id", PAY_WX_MCH_ID);// 商户号
				reqHandler.setParameter("nonce_str", noncestr);// 随机字符串
				reqHandler.setParameter("body", product_name);// 商品描述
				reqHandler.setParameter("attach", pay_source);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据::: 支付来源 ios/android
				reqHandler.setParameter("out_trade_no", order_id);// 商家订单号
				reqHandler.setParameter("total_fee", order_price_wx);// 商品金额,以分为单位
				reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());// 用户的公网IP
				//reqHandler.setParameter("notify_url", "http://118.178.136.110/api/mobile/wx/pay");//微信异步通知地址，WXPay.java118.178.136.110
				reqHandler.setParameter("notify_url", "http://127.0.0.1:8080/api/mobile/wx/pay");//微信异步通知地址，WXPay.java118.178.136.110
				reqHandler.setParameter("trade_type", "APP");
				String requestUrl = reqHandler.getRequestURL(pay_source);
				// 发起统一下单
				String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				String result = HttpRequest.sendPost(orderUrl, requestUrl);
				System.out.println("1111===="+result);
				Map<String, String> orderMap = XMLUtil.doXMLParse(result);
				System.out.println("2222===="+orderMap.get("prepay_id"));
				
				String prepay_id = orderMap.get("prepay_id");// 预支付ID
				// 调起支付所需参数
				SortedMap<String, String> getPayMap = new TreeMap<String, String>();
				getPayMap.put("appid", PAY_WX_APP_ID);
				getPayMap.put("partnerid", PAY_WX_MCH_ID);// 商户号
				getPayMap.put("prepayid", prepay_id);// 预支付ID
				getPayMap.put("package", "Sign=WXPay");// 扩展字段，固定填写
				getPayMap.put("noncestr", noncestr); //随机字符串
				getPayMap.put("timestamp", timestamp);// 时间戳
				String sign = reqHandler.createSign(getPayMap, pay_source); //签名
				getPayMap.put("sign", sign);
				model.setModel(getPayMap);
				model.setError_code("0");
				model.setError_msg("ok");
			} else if ((PAY_METHOD_ALI).equals(pay_method)) {
				Map<String, String> getPayMap = new HashMap<String, String>();
				getPayMap.put("service", AlipayConfig.h5service);
				getPayMap.put("partner", AlipayConfig.partner);
				getPayMap.put("_input_charset", AlipayConfig.input_charset);
				getPayMap.put("notify_url", AlipayConfig.notify_url);//notify_url是异步通知，作为正式结果去操作数据库
				getPayMap.put("out_trade_no", order_id);
				getPayMap.put("subject", product_name);
				getPayMap.put("payment_type", "1");
				getPayMap.put("seller_id", AlipayConfig.seller_id);
				getPayMap.put("total_fee", order_price);
				getPayMap.put("body", product_name);
				if (!("").equals(show_url) && null != show_url) {
					getPayMap.put("return_url", AlipayConfig.return_url);//return_url是同步通知，给用户展示支付成功失败，但不作为正式数据保存
					getPayMap.put("show_url", show_url);
					getPayMap.put("app_pay", "Y");
				}
				getPayMap = AlipayCore.paraFilter(getPayMap);
				String content = null;
				if (!("").equals(show_url) && null != show_url) {
					content = AlipayCore.createLinkString(getPayMap);//h5支付：参数=参数值
				} else {
					content = AlipayCore.createLinkStringPay(getPayMap);//app支付：参数="参数值"
				}
				String sign = RSA.sign(content, AlipayConfig.private_key, AlipayConfig.input_charset);
				if (("").equals(show_url) || null == show_url) {//app支付的sign签名，需要URLEncoder
					try {
						sign = java.net.URLEncoder.encode(sign, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					content = content + "&sign=\"" + sign + "\"&sign_type=\"" + AlipayConfig.sign_type + "\"";
				} else {
					getPayMap.put("sign", sign);
					getPayMap.put("sign_type", AlipayConfig.sign_type);
					List<String> keys = new ArrayList<String>(getPayMap.keySet());
					StringBuffer sbHtml = new StringBuffer();
					sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"post\">");

					for (int i = 0; i < keys.size(); i++) {
						String name = (String) keys.get(i);
						String value = (String) getPayMap.get(name);
						sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
					}

					// submit按钮控件请不要含有name属性
					sbHtml.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
					sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
					content = sbHtml.toString();
				}
				model.setModel(content);
				model.setError_code("0");
				model.setError_msg("ok");
			}
		}else{
			model.setError_code("20028");
			model.setError_msg(ErrorCodeConfigUtil.ERROR_MSG_ZH_20028);
		}
		
		
		System.out.println(TimeUtil.getTimeStamp() + "-支付接口结束-" + JSON.toJSONString(model));
		return JSON.toJSONString(model);
	}

}

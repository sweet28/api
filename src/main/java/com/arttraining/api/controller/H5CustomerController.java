package com.arttraining.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.arttraining.commons.util.pay.WXPay.HttpRequest;
import com.arttraining.commons.util.pay.WXPay.RequestHandler;
import com.arttraining.commons.util.pay.WXPay.XMLUtil;
import com.arttraining.commons.util.pay.utils.MD5;

@Controller
@RequestMapping("/h5")
/**
 * h5 页面跳转
 * @author shr
 *
 */
public class H5CustomerController {

	/**
	 * 进入登录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		return "pay";
	}

	/**
	 * 订单生成demo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String addOrder(HttpServletRequest request, Model m) {
		m.addAttribute("code","1");
		m.addAttribute("model","DD00123");
		return JSON.toJSONString(m);
	}
	/**
	 * 进入支付成功画面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/payOK.htm",method=RequestMethod.GET)
	public String payOK(HttpServletRequest request,Model m) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("total_money", request.getParameter("total_money"));
		map.put("order_time", request.getParameter("order_time"));
		map.put("order_id", request.getParameter("order_id"));
		map.put("pay_method", request.getParameter("pay_method"));
		m.addAttribute("order",map);
		return "payOK";
	}

	/**
	 * 进入支付失败画面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payErr.htm", method = RequestMethod.GET)
	public String payErr(HttpServletRequest request, Model m) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total_money", request.getParameter("total_money"));
		map.put("order_id", request.getParameter("order_id"));
		m.addAttribute("order", map);
		return "payErr";
	}

	/**
	 * 支付宝网页支付return_url处理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Alipay.htm",method=RequestMethod.GET)
	public String Alipay(HttpServletRequest request,Model m) {
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		try {
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易金额
			String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
			// 通知时间
			String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("total_money", total_fee);
			map.put("order_id", out_trade_no);
			map.put("order_time", notify_time);
			map.put("pay_method", "zfb");
			m.addAttribute("order",map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {// 验证成功
			return "payOK";
		} else {
			return "payErr";
		}
	}
	
	/**
	 * 微信登陆回调
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/WXLogin.htm",method=RequestMethod.GET)
	public String WXLogin(HttpServletRequest request,Model m) {
		String code=request.getParameter("code");
		String url_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param_access_token = "appid=APPID&secret=SECRET&code="+code+"&grant_type=authorization_code";
		Map<String,Object> map_access_token = HttpRequest.sendGet(url_access_token, param_access_token);
		if(map_access_token.containsKey("errcode")){
			m.addAttribute("wx_error",map_access_token);
			return "pay";
		}
		String _access_token = map_access_token.get("access_token").toString();
		String _openid = map_access_token.get("openid").toString();
		String url_userinfo = "https://api.weixin.qq.com/sns/userinfo";
		String patam_userinfo = "access_token="+_access_token+"&openid="+_openid+"&lang=zh_CN";
		Map<String,Object> map_userinfo = HttpRequest.sendGet(url_userinfo, patam_userinfo);
		if(map_userinfo.containsKey("errcode")){
			m.addAttribute("wx_error",map_userinfo);
			return "pay";
		}
		String nickname = map_userinfo.get("nickname").toString();
		String sex = map_userinfo.get("sex").toString();
		String headimgurl = map_userinfo.get("headimgurl").toString();
		String unionid = map_userinfo.get("unionid").toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nickName", nickname);
		params.put("sex", sex);
		params.put("head_portrait", headimgurl);
		params.put("wx_id", unionid);
		m.addAttribute("userinfo",params);
		return "pay";
	}
	
	/**
	 * 微信支付回调
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/WXPay.htm",method=RequestMethod.GET)
	public String WXPay(HttpServletRequest request,HttpServletResponse response,Model m) {
		Map<String, Object> map = new HashMap<String,Object>();
		String code = request.getParameter("code");
		String order_id = request.getParameter("state");//订单号
		Random random = new Random();
		String noncestr = MD5.GetMD5Code(String.valueOf(random.nextInt(10000)));//生成随机字符串
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);//生成1970年到现在的秒数
		//获取openID
		String url_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param_access_token = "appid=APPID&secret=SECRET&code="+code+"&grant_type=authorization_code";
		Map<String,Object> map_access_token = HttpRequest.sendGet(url_access_token, param_access_token);
		if(map_access_token.containsKey("errcode")){
			m.addAttribute("wx_error",map_access_token);
			return "pay";
		}
		String openid = map_access_token.get("openid").toString();
		//商品
		String product_name="订单名称";//订单名称
		String order_price="";//订单金额
		map.put("id", order_id);
		try {
			order_price = "1";//demo，从数据库得到订单价格，1元
			int int_order_price = (int)(Double.parseDouble(order_price)*100);//微信金额 以分为单位
			order_price =  Integer.toString(int_order_price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//初始化 RequestHandler
		RequestHandler reqHandler = new RequestHandler(request, response);
		//拼接参数
		reqHandler.setParameter("appid","");
		reqHandler.setParameter("mch_id", "");//商户号
		reqHandler.setParameter("nonce_str", noncestr);//随机字符串
		reqHandler.setParameter("body", product_name);//商品描述
		reqHandler.setParameter("attach", "gzh");// 支付来源-公众号
		reqHandler.setParameter("out_trade_no", order_id);//商家订单号
		reqHandler.setParameter("total_fee", order_price);//商品金额,以分为单位
		reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());//用户的公网IP
		reqHandler.setParameter("notify_url", "http://www.你们的网址.com/mobile/wx/pay");
		reqHandler.setParameter("trade_type", "JSAPI");
		reqHandler.setParameter("openid", openid);
		String requestUrl = reqHandler.getRequestURL("gzh");
		//发起统一下单
		String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String result= HttpRequest.sendPost(orderUrl, requestUrl);
		Map<String, String> orderMap = XMLUtil.doXMLParse(result);
		String prepay_id = orderMap.get("prepay_id");//预支付ID
		//网页调起支付所需参数
		SortedMap<String,String> params = new TreeMap<String,String>();
		params.put("appId", "");
		params.put("timeStamp",timestamp);
		params.put("nonceStr", noncestr);
		params.put("package", "prepay_id="+prepay_id);
		params.put("signType", "MD5");
		String paySign =  reqHandler.createSign(params,"gzh");
		params.put("paySign", paySign);
		params.put("order_id", order_id);
		params.put("total_money", String.valueOf(Double.parseDouble(order_price)/100));
		m.addAttribute("WCPayRequest",params);
		return "wxpay";
	}

}

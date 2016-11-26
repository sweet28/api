package com.arttraining.api.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arttraining.api.pojo.Order;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.commons.util.pay.WXPay.RequestHandler;

@Controller
@RequestMapping("mobile/wx")
public class WXPay {
	@Resource
	private OrdersService ordersService;

	@RequestMapping("/pay")
	public void wxpay(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> pay = new HashMap<String, Object>();
		SortedMap<String, String> map = new TreeMap<String, String>();
		Map<String, Object> Apply = new HashMap<String, Object>();
		String res = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
		String resSign = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[签名错误]]></return_msg>" + "</xml>";
		String resFee = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[价格不符]]></return_msg>" + "</xml>";
		try {
			InputStream inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 初始化 RequestHandler
			RequestHandler reqHandler = new RequestHandler(request, response);
			String sign = reqHandler.createSign(map, map.get("attach"));
			// 验证签名
			if ((sign).equals(map.get("sign"))) {
				// 查询订单中的价格
				String order_number = map.get("out_trade_no");
				//查询数据库操作
				Order order = this.ordersService.selectByOrderNumber(order_number);
				String order_price = order.getFinalPay().toString();
//				String order_price = "1";//demo，数据库中保存价格1元
				//微信需要使用单位为   分   的数据，1元=100
				int int_order_price = (int) (Double.parseDouble(order_price) * 100);
				// 取返回的价格
				String cash_fee = map.get("cash_fee").toString();
				Integer num = Integer.valueOf(cash_fee);
				int int_num = num.intValue();
				if (int_order_price == int_num) {
					//价格比对正确，继续操作
					//正式完成订单已支付的相关操作

					// 暂放在订单更新接口中操作，为了安全，后面要调整到此处异步操作
					
					inputStream.close();
					response.getWriter().println(res);
				} else {
					inputStream.close();
					response.getWriter().println(resFee);
				}
			} else {
				inputStream.close();
				response.getWriter().println(resSign);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//	/* 此方法微信支付回调 */
//	@RequestMapping(value = "/wechat/notify")
//	@ResponseBody
//	public Object donateNofityForWeChat(HttpServletRequest request,
//			HttpServletResponse response, Model m) {
//		String returnValue = "<xml><return_code><![CDATA[STATUS]]></return_code><return_msg><![CDATA[MESSAGE]]></return_msg></xml>";
//		try {
//			Map<String, Object> params = new HashMap<String, Object>();
//			Map<String, Object> pay = new HashMap<String, Object>();
//			Map<String, String> result = parseXml(request.getInputStream());
//			// 获取支付编号对象
//			// SnsDonate donate =
//			// this.donateService.querySnsDonateById(result.get("out_trade_no"));
//			Map<String, Object> donate = mobileService
//					.queryCustomerPayById(result.get("out_trade_no"));
//			if (donate == null) {
//				// 直接返回失败
//				return returnValue.replace("STATUS", "FAIL").replace("MESSAGE",
//						"订单不存在");
//			}
//			// if(!"1".equals(donate.getPayStatus())){//微信会多次调用该接口进行状态更新，所以判断处理一下
//			// 后续做签名认证
//			if ("SUCCESS".equals(result.get("result_code"))) {// 成功
//				/*
//				 * donate.setPayId(result.get("transaction_id"));
//				 * donate.setPayReturnContent(JsonUtils.obj2json(result));
//				 * donate.setPayReturnTime(result.get("time_end"));
//				 * donate.setPayType("0"); donate.setPayStatus("1");
//				 * donate.setDonateStatus("02");
//				 */
//				params.put("id", result.get("out_trade_no"));
//				params.put("status", "dsh");
//				String str = result.get("time_end").toString();
//				String pay_time = str.substring(0, 4) + "-"
//						+ str.substring(4, 6) + "-" + str.substring(6, 8) + " "
//						+ str.substring(8, 10) + ":" + str.substring(10, 12)
//						+ ":" + str.substring(12, 14);
//				String cash_fee = result.get("cash_fee").toString();
//				Integer num = Integer.valueOf(cash_fee);
//				int yuan = num / 100;
//				int jiao = num % 100 / 10;
//				int fen = num % 100 % 10;
//				String money = "" + yuan + "." + jiao + fen;
//				pay.put("id", UUIDUtil.getUUID());
//				pay.put("order_id", result.get("out_trade_no"));
//				pay.put("pay_time", pay_time);
//				pay.put("total_money", money);
//			}/*
//			 * else{ donate.setPayStatus("0"); }
//			 */
//			mobileService.updateOrderStatus(params);
//			mobileService.insertCustomrPay(pay);
//			// donateService.updateSnsDonate(donate);
//			// }
//
//			return returnValue.replace("STATUS", "SUCCESS").replace("MESSAGE",
//					"ok");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return returnValue.replace("STATUS", "FAIL").replace("MESSAGE", "处理失败");
//	}
//
//	public static Map<String, String> parseXml(InputStream inputStream)
//			throws Exception {
//		// 将解析结果存储在HashMap中
//		Map<String, String> map = new HashMap<String, String>();
//
//		// 读取输入流
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(inputStream);
//		// 得到xml根元素
//		Element root = document.getRootElement();
//		// 得到根元素的所有子节点
//		List<Element> elementList = root.elements();
//
//		// 遍历所有子节点
//		for (Element e : elementList) {
//			map.put(e.getName(), e.getText());
//		}
//
//		return map;
//	}
}

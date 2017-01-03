package com.arttraining.commons.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;


public class JPushClientUtilV2 {
		//coffee add 1215 极光推送应用密钥
		//在极光注册上传应用的 appKey 和 masterSecret
	    //必填,例如0e14cc079e593485ecdbbc94
	    //必填,每个应用都对应一个masterSecret
		//1.云互艺爱好者端 appKey/masterSecret
		private static final String AHZ_APPKEY ="0e14cc079e593485ecdbbc94";
		private static final String AHZ_MASTERSECRET = "fbfc1983b6848108d6652f3d";
		//2.云互艺名师端  appKey/masterSecret
//		private static final String MS_APPKEY ="175d8ea025b468b82598ab90";
//		private static final String MS_MASTERSECRET = "86d1f423ce59274f12601619";
		private static final String MS_APPKEY ="722ef0657ea474313d7612da";
		private static final String MS_MASTERSECRET = "428456b07d5b834f3117f77d";
		//3.云互艺达人端  appKey/masterSecret
		private static final String DR_APPKEY ="722ef0657ea474313d7612da";
		private static final String DR_MASTERSECRET = "428456b07d5b834f3117f77d";
		//end
		//推送消息的默认值--TITLE
		private static final String TITLE="云互艺";
		private static JPushClient jpush = null;
		
		private static final String ALERT_KEY="alert";
		private static final String MSG_KEY="msg";
		/*
		 * 保存离线的时长。秒为单位。最多支持10天(864000秒)。
		 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
		 * 此参数不设置则表示默认，默认为保存1天的离线消息(86400秒)
		 */
//		private static long timeToLive =  60 * 60 * 24;  
//		public static void main(String[] args) {
//			jpush = new JPushClient(MS_MASTERSECRET,MS_APPKEY,3);
//			//生成推送的内容，这里我们先测试全部推送  
//	        PushPayload payload=buildPushObject_all_all_alert("云互艺测试");
//	      
//	        try {
//	            PushResult result = jpush.sendPush(payload);
//	            System.out.println("Got result - " + result);
//
//	        } catch (APIConnectionException e) {
//	            // Connection error, should retry later
//	        	System.out.println("Connection error, should retry later");
//
//	        } catch (APIRequestException e) {
//	            // Should review the error, and fix the request
//	        	System.out.println("Should review the error, and fix the request");
//	        	System.out.println("HTTP Status: " + e.getStatus());
//	        	System.out.println("Error Code: " + e.getErrorCode());
//	        	System.out.println("Error Message: " + e.getErrorMessage());
//	        }
//		}
		//封装额外的JSon数据--用于alert消息内容消息推送
		public static String enclose_push_extra_json_dataV2(String type,String value) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("type", type);
			jsonObject.put("value", value);
			return jsonObject.toString();
		}
		//coffee add 1230 新增封装extra_value为json数据--用于msg消息内容推送
		public static String enclose_extra_value_jsonV2(Map<String, Object> map) {
			JSONObject jsonObject=new JSONObject();
			Iterator entries = map.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry entry = (Map.Entry) entries.next();
			    String key = (String)entry.getKey();  
			    Object value = entry.getValue();  
			    jsonObject.put(key, value);
			} 
			return jsonObject.toString();
		}
		//end
		//封装Map对象--推送别名的消息方法
		public static void enclose_push_data_aliasV2(String user_type,String push_type,
				String alias,String alert, String content,
				String content_type,String extra_value,String alert_extra) {
			Map<String, Object> push_map=new HashMap<String, Object>();
			push_map.put("user_type", user_type);
			push_map.put("push_type", push_type);
			push_map.put("alias",alias );
			push_map.put("alert",alert);
			push_map.put("extra_value",extra_value);
			push_map.put("content",content);
			push_map.put("content_type",content_type);
			push_map.put("alert_extra",alert_extra);
			push_all_alias_alert_msgV2(push_map);
		}
		
		//进行推送的关键在于构建一个 PushPayload对象
		//方法一:推送自定义消息msg和用户手机消息框内容
		public static void push_all_alias_alert_msgV2(Map<String, Object> map) {
			//用户类型
			String user_type=(String)map.get("user_type");
			if(user_type.equals("stu")) {
				jpush = new JPushClient(AHZ_MASTERSECRET,AHZ_APPKEY,3);
			} else if(user_type.equals("tec")) {
				jpush = new JPushClient(MS_MASTERSECRET,MS_APPKEY,3);
			}
			//判断推送的内容(1.只推送alert 2.只推送msg 3.alert/msg均推送)
			String push_type=(String)map.get("push_type");
			//生成推送的内容，这里我们先测试全部推送  
	        PushPayload payload=null;
	        if(push_type.equals("alert")) {
	        	payload=buildPushObject_all_alias_alertV2(map);
	        } else if(push_type.equals("msg")) {
	        	payload=buildPushObject_all_alias_msgV2(map);
	        } else if(push_type.equals("alert_msg")) {
	        	payload=buildPushObject_all_alias_alert_msgV2(map);
	        }
	        try {
	            PushResult result = jpush.sendPush(payload);
	            System.out.println("Got result - " + result);

	        } catch (APIConnectionException e) {
	            // Connection error, should retry later
	        	System.out.println("Connection error, should retry later");

	        } catch (APIRequestException e) {
	            // Should review the error, and fix the request
	        	System.out.println("Should review the error, and fix the request");
	        	System.out.println("HTTP Status: " + e.getStatus());
	        	System.out.println("Error Code: " + e.getErrorCode());
	        	System.out.println("Error Message: " + e.getErrorMessage());
	        }
		}
		/***
		 * notification--alert(消息通知栏的内容)
		 * android平台: title--消息通知栏的标题
		 * ios平台:不提供title参数,默认显示应用名称
		 * Extra--key键值  value--可以是json数据
		 * @return
		 */
		//2017-01-02 
		//1.向指定的用户推送相应的消息:别名 默认是用户ID
		public static PushPayload buildPushObject_all_alias_alert_msgV2(Map<String, Object> map) { 
			String alias=(String)map.get("alias");
			String alert=(String)map.get("alert");
			String extra_value=(String)map.get("extra_value");
			String content=(String)map.get("content");
			String content_type=(String)map.get("content_type");
			//add
			String alert_extra=(String)map.get("alert_extra");
			
			return PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					//Audience设置为all,说明采用广播方式推送,所有用户都可以接收到
					.setNotification(Notification.newBuilder()
									.setAlert(alert)
									.addPlatformNotification(
											AndroidNotification.newBuilder()
													.setTitle(TITLE)
													.addExtra(ALERT_KEY, alert_extra)
													.build())
									.addPlatformNotification(
											IosNotification.newBuilder()
											.addExtra(ALERT_KEY, alert_extra)
											.build())
									.build())
					.setMessage(Message.newBuilder()
								.setMsgContent(content)
								.addExtra(MSG_KEY,extra_value)
								.setContentType(content_type).build())
								.build();
	   }  
	   //2017-01-02
	   //2.向指定的别名用户只推送自定义消息
		public static PushPayload buildPushObject_all_alias_msgV2(Map<String, Object> map) { 
			String alias=(String)map.get("alias");
			String extra_value=(String)map.get("extra_value");
			String content=(String)map.get("content");
			String content_type=(String)map.get("content_type");
			
			return PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					.setMessage(Message.newBuilder()
								.setMsgContent(content)
								.addExtra(MSG_KEY,extra_value)
								.setContentType(content_type).build())
								.build();
	   } 
		//2017-01-02
	   //3.向指定的别名用户只推送内容为 ALERT的通知
		public static PushPayload buildPushObject_all_alias_alertV2(Map<String, Object> map) {
			String alias=(String)map.get("alias");
			String alert=(String)map.get("alert");
			String alert_extra=(String)map.get("alert_extra");
			
			return PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					//Audience设置为all,说明采用广播方式推送,所有用户都可以接收到
					.setNotification(Notification.newBuilder()
									.setAlert(alert)
									.addPlatformNotification(
											AndroidNotification.newBuilder()
													.setTitle(TITLE)
													.addExtra(ALERT_KEY, alert_extra)
													.build())
									.addPlatformNotification(
											IosNotification.newBuilder()
											.addExtra(ALERT_KEY, alert_extra)
											.build())
									.build())
									.build();
		}
	
}

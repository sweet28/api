package com.arttraining.commons.util;

import java.util.HashMap;
import java.util.Map;

import com.qiniu.pili.Client;
import com.qiniu.pili.Config;
import com.qiniu.pili.Hub;
import com.qiniu.pili.PiliException;
import com.qiniu.pili.Stream;

public class LiveUtil {
	/**
	 * 随机生成一个不重复的流名
	 * 当前毫秒数+随机生成的4位数
	 */
	public static String randomStreamKey() {
		String key=ConfigUtil.QINIU_LIVE_STREAM_PREFIX;
		key+=System.currentTimeMillis()+Random.randomCommonStr(4);
		return key;
	}
	/**
	 * 保存某一条流的直播数据
	 * @throws PiliException 
	 */
	public static String saveLiveStream(String key,long start,long end) throws PiliException {
		//直播空间名
		String hubName=ConfigUtil.QINIU_LIVE_HUBNAME;
						
		Config.APIHost = ConfigUtil.QINIU_API_HOST;
		//初始化client
		Client cli = new Client(ConfigUtil.QINIU_LIVE_AK,ConfigUtil.QINIU_LIVE_SK);
		//初始化Hub直播空间
		Hub hub = cli.newHub(hubName);
		Stream stream = hub.get(key); 
		//保存直播回放
		//String fname=stream.save(start, end);
		String fname=stream.save(0,0);
		return fname;		
	}
	/***
	 * 启用某一条流
	 * @throws PiliException 
	 */
	public static void enableStream(String key) throws PiliException {
		//直播空间名
		String hubName=ConfigUtil.QINIU_LIVE_HUBNAME;
				
		Config.APIHost = ConfigUtil.QINIU_API_HOST;
		//初始化client
		Client cli = new Client(ConfigUtil.QINIU_LIVE_AK,ConfigUtil.QINIU_LIVE_SK);
		//初始化Hub直播空间
		Hub hub = cli.newHub(hubName);
		Stream stream = hub.get(key); 
		stream.enable();
	}
	/***
	 * 生成推流地址和直播地址
	 * @throws PiliException 
	 * 
	 */
	public static Map<String, String> createLiveUrl() throws PiliException {
		//将生成的推流地址和直播地址 流名存放在map对象中
		Map<String, String> urlMap=new HashMap<String, String>();
		
		//直播空间名
		String hubName=ConfigUtil.QINIU_LIVE_HUBNAME;
		
		Config.APIHost = ConfigUtil.QINIU_API_HOST;
	    //初始化client
	    Client cli = new Client(ConfigUtil.QINIU_LIVE_AK,ConfigUtil.QINIU_LIVE_SK);
	    //初始化Hub 直播空间
	    Hub hub = cli.newHub(hubName);
	    //获取一个不重复的流名
	    String key=randomStreamKey();
	    //创建一个不重复的房间 依据流名
	    //Stream stream = hub.create(key);
	    hub.create(key);
	    
	    // RTMP推流地址
	    String publish_url = cli.RTMPPublishURL(ConfigUtil.LIVE_RTMP_PUBLISH_URL, hubName, key, ConfigUtil.QINIU_LIVE_EXPIRES);
      	//RTMP直播地址
	    String rtmp_url = cli.RTMPPlayURL(ConfigUtil.LIVE_RTMP_PLAY_URL, hubName, key);
	    //HLS直播地址
        String hls_url = cli.HLSPlayURL(ConfigUtil.LIVE_HLS_PLAY_URL, hubName, key);
        //HDL直播地址
        String hdl_url = cli.HDLPlayURL(ConfigUtil.LIVE_HDL_PLAY_URL, hubName, key);
        // 截图直播地址
        String snapshot_url = cli.SnapshotPlayURL(ConfigUtil.LIVE_SNAPSHOT_PLAY_URL, hubName, key);
        
        urlMap.put("stream", key);
        urlMap.put("publish_url", publish_url);
        urlMap.put("rtmp_url", rtmp_url);
        urlMap.put("hls_url", hls_url);
        urlMap.put("hdl_url", hdl_url);
        urlMap.put("snapshot_url", snapshot_url);
        
        return urlMap;
	}
}

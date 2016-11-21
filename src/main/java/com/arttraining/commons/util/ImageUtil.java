package com.arttraining.commons.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.HomePageAttBean;

public class ImageUtil {
	// 封装一个方法用于解析json数据 然后将其拆解
	public static String parseAttPath(String store_path) {
		String path = "";
		//去除首尾空格字符串
		store_path=store_path.trim();
		// 首先判断是否是Json
		JSONArray jsonArray = JSONArray.parseArray(store_path);
		for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
			JSONObject jsonObject = (JSONObject) iterator.next();
			path = jsonObject.getString("store_path");
		}
		return path;
	}

	// 封装一个方法用于解析json数据 然后将其拆解
	public static String parseQiNiuPath(String store_path) {
		String path = "";
		// 首先判断是否是Json
		JSONArray jsonArray = JSONArray.parseArray(store_path);
		for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
			JSONObject jsonObject = (JSONObject) iterator.next();
			path = ConfigUtil.QINIU_BUCKET_COM_URL + "/"
					+ jsonObject.getString("store_path");
		}

		return path;
	}
	
	public static String parsePicPath(String store_path){
		String result = "";
		
		if(!"".equals(store_path) && null != store_path){
			result = ConfigUtil.QINIU_BUCKET_COM_URL+"/" + store_path;
		}else{
			result = store_path;
		}
		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

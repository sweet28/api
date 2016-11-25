package com.arttraining.commons.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.HomePageAttBean;

public class ImageUtil {
	//封装一个方法用于解析json数据 然后将其拆解
	public static List<HomePageAttBean> parseAttPathByType(Integer att_id,String att_type,
				String duration,String thumbnail,String store_path,Integer type) {
		List<HomePageAttBean> attList = new ArrayList<HomePageAttBean>();
		HomePageAttBean h = null;
			 
		String path="";
		//首先判断是否是Json
		JSONArray jsonArray = JSONArray.parseArray(store_path);
		for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
		        JSONObject jsonObject=(JSONObject)iterator.next();
		        h = new HomePageAttBean();
		        h.setAtt_id(att_id);
		        h.setAtt_type(att_type);
		          
		        h.setDuration(duration);
		        path = jsonObject.getString("store_path");
		        path=ImageUtil.parsePicPath(path, type);
		        h.setStore_path(path);
		        thumbnail=ImageUtil.parsePicPath(thumbnail, type);
		        h.setThumbnail(thumbnail);
		        attList.add(h);
		    }
		return attList;
	}
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
	public static String parseQiNiuPath(String store_path,Integer type) {
		String path = "";
		String pre_path="";
		switch (type) {
		case 0:
			pre_path=ConfigUtil.QINIU_BUCKET_COM_URL;
			break;
		case 1:
			pre_path=ConfigUtil.QINIU_BUCKET_BBS_COM_URL;
			break;
		case 2:
			pre_path=ConfigUtil.QINIU_BUCKET_COURSE_COM_URL;
			break;
		case 3:
			pre_path=ConfigUtil.QINIU_BUCKET_G_STUS_COM_URL;
			break;
		case 4:
			pre_path=ConfigUtil.QINIU_BUCKET_INFO_COM_URL;
			break;
		case 5:
			pre_path=ConfigUtil.QINIU_BUCKET_STU_ORG_TEC_COM_URL;
			break;
		case 6:
			pre_path=ConfigUtil.QINIU_BUCKET_WORKS_COM_URL;
			break;
		default:
			break;
		}
		
		// 首先判断是否是Json
		JSONArray jsonArray = JSONArray.parseArray(store_path);
		for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
			JSONObject jsonObject = (JSONObject) iterator.next();
			path = pre_path + "/"
					+ jsonObject.getString("store_path");
		}

		return path;
	}
	
	public static String parsePicPath(String store_path,Integer type){
		String result = "";
		
		if(null != store_path && !"".equals(store_path.trim())){
			String pre_path="";
			switch (type) {
			case 0:
				pre_path=ConfigUtil.QINIU_BUCKET_COM_URL;
				break;
			case 1:
				pre_path=ConfigUtil.QINIU_BUCKET_BBS_COM_URL;
				break;
			case 2:
				pre_path=ConfigUtil.QINIU_BUCKET_COURSE_COM_URL;
				break;
			case 3:
				pre_path=ConfigUtil.QINIU_BUCKET_G_STUS_COM_URL;
				break;
			case 4:
				pre_path=ConfigUtil.QINIU_BUCKET_INFO_COM_URL;
				break;
			case 5:
				pre_path=ConfigUtil.QINIU_BUCKET_STU_ORG_TEC_COM_URL;
				break;
			case 6:
				pre_path=ConfigUtil.QINIU_BUCKET_WORKS_COM_URL;
				break;
			default:
				break;
			}
			result = pre_path+"/" + store_path;
		}else{
			result = store_path;
		}
		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

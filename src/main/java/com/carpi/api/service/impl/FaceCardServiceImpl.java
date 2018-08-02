package com.carpi.api.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.FaceIdentityUtil;
import com.arttraining.commons.util.JsonResult;
import com.carpi.api.service.FaceCardService;

@Service
public class FaceCardServiceImpl implements FaceCardService {

	// 身份证识别
	@Override
	public JsonResult card(String img_url) throws Exception {
		String url = "https://api-cn.faceplusplus.com/cardpp/v1/ocridcard";
		HashMap<String, String> map = new HashMap<>();
		map.put("api_key", "8qq0MDKgcfMwOiw7E27tvZ08D6LbErhP");
		map.put("api_secret", "xJIXFiZV011fBLQVexoS1S1QUYkxLaIz");
		map.put("image_url", img_url);
		map.put("legality",String.valueOf(1));
		String backResult = new String(FaceIdentityUtil.post(url, map), "UTF-8");
		System.out.println("身份证数据："+backResult);
		//转换为JSon对象
		JSONObject jo = JSON.parseObject(backResult);
		//取card   json数组
		JSONArray cards = jo.getJSONArray("cards");
		
		//遍历json数组
		for(int i = 0;i<cards.size();i++){
			JSONObject legality = cards.getJSONObject(i).getJSONObject("legality");
			double IDPhoto = legality.getDouble("ID Photo");
			System.out.println("准确性："+IDPhoto);
			
			if (IDPhoto >= 0.9) {
			   return JsonResult.ok(cards.getJSONObject(i));
			}
			return JsonResult.build(500, "请上传清晰身份证图片");
		}
		return JsonResult.build(500, "请上传清晰身份证图片");
	}
}

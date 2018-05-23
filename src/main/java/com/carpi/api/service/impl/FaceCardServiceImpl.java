package com.carpi.api.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.arttraining.commons.util.FaceIdentityUtil;
import com.carpi.api.service.FaceCardService;

@Service
public class FaceCardServiceImpl implements FaceCardService {

	// 身份证识别
	@Override
	public String card(String img_url) throws Exception {
		String url = "https://api-cn.faceplusplus.com/cardpp/v1/ocridcard";
		HashMap<String, String> map = new HashMap<>();
		map.put("api_key", "8qq0MDKgcfMwOiw7E27tvZ08D6LbErhP");
		map.put("api_secret", "xJIXFiZV011fBLQVexoS1S1QUYkxLaIz");
		map.put("image_url", img_url);
		String backResult = new String(FaceIdentityUtil.post(url, map), "UTF-8");
		return backResult;
	}

}

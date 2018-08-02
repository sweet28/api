package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;

public interface FaceCardService {

	//身份证识别
	public JsonResult card(String imgUrl) throws Exception;
}

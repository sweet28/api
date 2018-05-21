package com.carpi.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ServerLog;
import com.carpi.api.service.FaceCardService;
import com.carpi.api.service.PictureService;
import com.qiniu.util.Auth;


@Controller
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@Autowired
	private FaceCardService faceCardService;
	
	@RequestMapping(value = "/pic/upload", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map pictureUpload(MultipartFile file0) {
		return  pictureService.uploadPicture(file0);
	}
	
	//身份证识别
	@RequestMapping(value = "/cpa/facecard", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String facecard(String imgUrl) throws Exception{
		return faceCardService.card(imgUrl);
	}
	
	
	@RequestMapping(value = "/cpa/qiniu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getQiniuToken(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("银子来了");
		String QNToken = "";
		String errorCode = "";
		String errorMessage = "";
		String buketType = "2";
		
		String buketPath = "";
		switch (Integer.parseInt(buketType)) {
		case 0:
			buketPath = ConfigUtil.QINIU_BUCKET;
			break;
		case 1:
			buketPath = ConfigUtil.QINIU_BUCKET_BBS;
			break;
		case 2:
			buketPath = ConfigUtil.QINIU_BUCKET_COURSE;
			break;
		case 3:
			buketPath = ConfigUtil.QINIU_BUCKET_G_STUS;
			break;
		case 4:
			buketPath = ConfigUtil.QINIU_BUCKET_INFO;
			break;
		case 5:
			buketPath = ConfigUtil.QINIU_BUCKET_STU_ORG_TEC;
			break;
		case 6:
			buketPath = ConfigUtil.QINIU_BUCKET_WORKS;
			break;
		case 7:
			buketPath = ConfigUtil.QINIU_BUCKET_LIVE_COM_URL;
			break;
		default:
			break;
		}

		Auth auth = Auth.create(ConfigUtil.QINIU_AK, ConfigUtil.QINIU_SK);
		QNToken = auth.uploadToken(buketPath, null, ConfigUtil.QINIU_EXPIRES, ConfigUtil.QINIU_POLICY);
		if (QNToken.equals("") || QNToken == null) {
			errorCode = "20031";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20031;
			QNToken = "";
		} else {
			errorCode = "0";
			errorMessage = "ok";
		}
		System.out.println("银子来了2");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put(ConfigUtil.PARAMETER_QINIU_TOKEN, QNToken);
		//System.out.println("银子来了3："+jsonObject.toJSONString());
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;

	}
}

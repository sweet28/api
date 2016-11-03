package com.arttraining.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.TokenUtil;
import com.qiniu.util.Auth;

@Controller
@RequestMapping("/upload")
public class UploadController {
	// 依据用户uid查询爱好者用户信息
	@RequestMapping(value = "/get_token/qiniu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getQiniuToken(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("银子来了");
		String accessToken = "";
		String uid = "";
		String QNToken = "";
		String errorCode = "";
		String errorMessage = "";
		
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		if(accessToken == null || uid == null){
			errorCode = "20043";
			errorMessage = "数据参数为空，请检查网络是否畅通";
		}else{
			if(accessToken.equals("") || uid.equals("")){
				errorCode = "20043";
				errorMessage = "数据参数为空，请检查网络是否畅通";
			}else{
				// todo:判断token是否有效
				boolean tokenFlag = TokenUtil.checkToken(accessToken);
				if (tokenFlag) {
					Auth auth = Auth.create(ConfigUtil.QINIU_AK,
							ConfigUtil.QINIU_SK);
					QNToken = auth.uploadToken(ConfigUtil.QINIU_BUCKET, null,
							ConfigUtil.QINIU_EXPIRES, ConfigUtil.QINIU_POLICY);
					if (QNToken.equals("") || QNToken == null) {
						errorCode = "20042";
						errorMessage = "获取上传口令失败";
						QNToken = "";
					} else {
						errorCode = "0";
						errorMessage = "ok";
					}
					System.out.println("银子来了2");
				} else {
					errorCode = "20039";
					errorMessage = "登录失效，请重新登录";
				}
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put(ConfigUtil.PARAMETER_QINIU_TOKEN, QNToken);
		System.out.println("银子来了3："+jsonObject.toJSONString());
		return jsonObject;

	}
	
	@RequestMapping(value = "/create/qiniu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object getQiniuUploadUrl(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("康康来了A");
		String accessToken = "";
		String uid = "";
		String QNToken = "";
		String attType = "";
		String attUrl = "";
		String errorCode = "";
		String errorMessage = "";
		
		accessToken = request.getParameter("access_token");
		uid = request.getParameter("uid");
		if(accessToken == null || uid == null){
			errorCode = "20043";
			errorMessage = "数据参数为空，请检查网络是否畅通";
		}else{
			if(accessToken.equals("") || uid.equals("")){
				errorCode = "20043";
				errorMessage = "数据参数为空，请检查网络是否畅通";
			}else{
				// todo:判断token是否有效
				boolean tokenFlag = TokenUtil.checkToken(accessToken);
				if (tokenFlag) {

					System.out.println(attType + ":type:::url:" + attUrl);
					System.out.println("灰灰来了B");
				} else {
					errorCode = "20039";
					errorMessage = "登录失效，请重新登录";
				}
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put(ConfigUtil.PARAMETER_UID, uid);
		jsonObject.put(ConfigUtil.PARAMETER_USER_CODE, "");
		jsonObject.put(ConfigUtil.PARAMETER_NAME, "");
		System.out.println("银子来了C："+jsonObject.toJSONString());
		return jsonObject;

	}

}

package com.carpi.api.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.carpi.api.service.PictureService;

@Controller
public class PictureController2 {

	@Autowired
	private PictureService pictureService;

	/*
	 * @RequestMapping(value = "/pic/upload2", method = RequestMethod.POST,produces
	 * = "application/json;charset=UTF-8")
	 * 
	 * @ResponseBody public Map pictureUpload(MultipartFile file0) { return
	 * pictureService.uploadPicture(file0); }
	 */

	@RequestMapping(value = "/pic/upload2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map pushErrorData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 转型为MultipartHttpRequest(重点的所在)这个就是上面ajax中提到如果直接访问此接口而不加"/"，此转型就会报错
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("errPic"); // 对应 jquery $("#imageFile").get(0).files[index]
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != file && !file.isEmpty()) {
			return pictureService.uploadPicture(file);
		}
		return null;
	}
}

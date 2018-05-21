package com.carpi.api.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	//图片上传
	Map uploadPicture(MultipartFile file0);

}

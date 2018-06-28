package com.carpi.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arttraining.commons.util.FtpUtil;
import com.arttraining.commons.util.IDUtils;
import com.carpi.api.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	// FTP的ip地址
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;

	// FTP的ip地址
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;

	// FTP账号
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;

	// FTP密码
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;

	// FTP保存图片的路径
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;

	// 图片服务器的基础url
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	// 图片上传
	@Override
	public Map uploadPicture(MultipartFile file0) {

		Map resultMap = new HashMap<>();
		
		if(file0 == null){
			resultMap.put("error", 1);
			resultMap.put("message", "图片获取失败，请重新上传图片");
			
			return resultMap;
		}
		
		try {
			// 生成一个新的文件名
			// 去原始文件名
			String oldName = file0.getOriginalFilename();
			// 生成新的文件名
			// UUID.randomUUID();
			String newName = IDUtils.genImageName();
			// 新的文件名
			String namem = oldName.substring(oldName.lastIndexOf("."));// 取旧的文件名的后缀
			if (!namem.equals(".jpg") && !namem.equals(".png") && !namem.equals(".jpeg")) {
				resultMap.put("error", 11);
				resultMap.put("message", "仅支持jpg和png和jpeg格式图片");
				return resultMap;
			}
			newName = newName + namem;

			// 图片上传 filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, file0.getInputStream());
			// 返回结果
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);

			return resultMap;
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}

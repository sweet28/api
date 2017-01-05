package com.arttraining.commons.util;

import java.io.File;
import java.io.InputStream;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class SignleUploadPresenter {
	private UploadManager uploadManager;
	//上传到七牛后保存的文件名
    String key;
    //上传文件的路径
    String filePath;
	//coffee add 0104
	private String serverPath="D:/";
	//end
   
	//将从网络抓取到的图片上传至七牛云存储空间
	public String getUpToken(Integer buketType) {
		String buketPath = "";
		String QNToken="";
		
		if(ConfigUtil.CODE_TYPE.equals(ConfigUtil.CODE_TYPE_DEV)){
			buketType = 0;
		}
		
		switch (buketType) {
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
		default:
			break;
		}
		Auth auth = Auth.create(ConfigUtil.QINIU_AK,
				ConfigUtil.QINIU_SK);
		QNToken = auth.uploadToken(buketPath, null,
				ConfigUtil.QINIU_EXPIRES, ConfigUtil.QINIU_POLICY);
		
		return QNToken;
	}
	public void upload() {
	    try {
	         //调用put方法上传 D:/11.png
	         Response res = uploadManager.put(filePath, key, getUpToken(5));
	         //打印返回的信息
	         System.out.println(res.bodyString());
	       } catch (QiniuException e) {
	            Response r = e.response;
	            // 请求失败时打印的异常的信息
	            System.out.println(r.toString());
	            try {
	                //响应的文本信息
	                System.out.println(r.bodyString());
	            } catch (QiniuException e1) {
	                //ignore
	            }
	        }
	 }
	//从网络上下载图片 然后上传至七牛云空间
	public void downloadIconToQiNiuYun(String iconurl,String fileName) {
		//创建上传对象
		this.uploadManager = new UploadManager();	
		//上传到七牛云空间的文件名
		key=fileName;
		//key=System.currentTimeMillis()+Random.randomCommonStr(6)+".png";
		//上传文件的路径
		filePath=serverPath+key;
		File file = new File(filePath);
	    //1.先从网络中下载图片下来到本地 
	    InputStream inputStream = HttpURLConnectionUtil
	                .getInputStreamByGet(iconurl);
	    HttpURLConnectionUtil.saveData(inputStream, file);
	    //2.然后再从本地上传至七牛云
	    upload();
	    //3.最后删除本地图片文件
	    if(file.exists()) {
	    	file.delete();
	    }
	}
	
}

package com.arttraining.commons.util;

import com.qiniu.util.StringMap;

public class ConfigUtil {
	//todo:七牛云密钥
	public static String QINIU_AK = "4NLPjCaLLjYCXYUQ-Jl5iW5Ceknmux9iDRDtQ2vX";
	public static String QINIU_SK = "ZxF06uDuQ_ZQVKsNSQykLN1YwWYrC1lFTunBUFf6";
	public static String QINIU_BUCKET = "yipei-2016";//空间名
	public static String QINIU_KEY = null;
	public static long QINIU_EXPIRES = 3600;//有效时长，单位秒
	public static boolean STRICT = true; 
	public static StringMap QINIU_POLICY = null;
	public static String QINIU_COM_URL = "oflkt0ank.bkt.clouddn.com";
	
	public static String PARAMETER_ERROR_CODE = "error_code";
	public static String PARAMETER_ERROR_MSG = "error_msg";
	public static String PARAMETER_QINIU_TOKEN = "qiniu_token";

}

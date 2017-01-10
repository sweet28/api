package com.arttraining.commons.util;

import com.qiniu.util.StringMap;

public class ConfigUtil {
	//coffee:七牛云直播密钥
	public static String QINIU_LIVE_AK="ZqROaKfnfjygnaWWfYjPUkqvrSdlAHOrJgnz0SDu";
	public static String QINIU_LIVE_SK="LvxeWmiHaXfqCP3AwXO_GWkKEPC6fyA0_xL6186W";
	//七牛云直播空间
	public static String QINIU_LIVE_HUBNAME= "yhy-live";
	//七牛云直播API访问域名
	public static String QINIU_API_HOST="pili.qiniuapi.com";
	//流前缀名
	public static String QINIU_LIVE_STREAM_PREFIX="yhy";
	//生成推流地址的有效时长 单位秒
	public static int QINIU_LIVE_EXPIRES=3600;
	//生成推流地址域名
	public static String LIVE_RTMP_PUBLISH_URL="pili-publish.artforyou.cn";
	//生成RTMP直播地址域名
	public static String LIVE_RTMP_PLAY_URL="pili-live-rtmp.artforyou.cn";
	//生成HLS直播地址域名
	public static String LIVE_HLS_PLAY_URL="pili-live-hls.artforyou.cn";
	//生成HDL直播地址域名
	public static String LIVE_HDL_PLAY_URL="pili-live-hdl.artforyou.cn";
	//生成截图封面直播地址域名
	public static String LIVE_SNAPSHOT_PLAY_URL="pili-live-snapshot.artforyou.cn";
	//设置直播开启间隔
	public static long LIVE_INTERVAL=1;
	//end
	
	// todo:七牛云密钥
	public static String QINIU_AK = "4NLPjCaLLjYCXYUQ-Jl5iW5Ceknmux9iDRDtQ2vX";
	public static String QINIU_SK = "ZxF06uDuQ_ZQVKsNSQykLN1YwWYrC1lFTunBUFf6";
	public static String QINIU_KEY = null;
	public static long QINIU_EXPIRES = 86400;// 有效时长，单位秒
	public static boolean STRICT = true;
	public static StringMap QINIU_POLICY = null;
	
	public static String CODE_TYPE = "dev";//测试存储环境
	public static String CODE_TYPE_DEV = "dev";//测试存储环境
	public static String CODE_TYPE_ONLINE = "online";//正式存储环境
	//--0
	public static String QINIU_BUCKET = "yipei-2016";// 空间名
	public static String QINIU_BUCKET_COM_URL = "http://oflkt0ank.bkt.clouddn.com";
	//todo:帖子存储及访问--1
	public static String QINIU_BUCKET_BBS = "artforyou-bbs";// 空间名
	public static String QINIU_BUCKET_BBS_COM_URL = "http://oh0vdeauy.bkt.clouddn.com";
	//todo:课程存储及访问--2
	public static String QINIU_BUCKET_COURSE = "artforyou-course";// 空间名
	public static String QINIU_BUCKET_COURSE_COM_URL = "http://oh0ucnjvb.bkt.clouddn.com";
	//todo:小组存储及访问--3
	public static String QINIU_BUCKET_G_STUS = "artforyou-g-stus";// 空间名
	public static String QINIU_BUCKET_G_STUS_COM_URL = "http://oh0vfhtgh.bkt.clouddn.com";
	//todo:活动、广告等存储及访问--4
	public static String QINIU_BUCKET_INFO = "artforyou-info";// 空间名
	public static String QINIU_BUCKET_INFO_COM_URL = "http://oh0uyp4iw.bkt.clouddn.com";
	//todo:爱好者、名师、机构头像、图片存储及访问--5
	public static String QINIU_BUCKET_STU_ORG_TEC = "artforyou-stu-org-tec";// 空间名
	public static String QINIU_BUCKET_STU_ORG_TEC_COM_URL = "http://oh0uhvgvb.bkt.clouddn.com";
	//todo:作品存储及访问--6
	public static String QINIU_BUCKET_WORKS = "artforyou-works";// 空间名
	public static String QINIU_BUCKET_WORKS_COM_URL = "http://oh0u47h9e.bkt.clouddn.com";
	
	public static String PARAMETER_ERROR_CODE = "error_code";
	public static String PARAMETER_ERROR_MSG = "error_msg";
	public static String PARAMETER_QINIU_TOKEN = "qiniu_token";
	public static String PARAMETER_UID = "uid";
	public static String PARAMETER_USER_CODE = "user_code";
	public static String PARAMETER_NAME = "name";

	// todo:token配置
	public static int EXPIRE_TIME = 7 * 24 * 60 * 60;// 单位秒
	// todo:pwd md5字符
	public static String MD5_PWD_STR = "YUNHUYI_YZL_@)!^";
	// todo:sms验证码长度
	public static int ALIDAYU_SMS_CHECK_CODE_LENGTH = 4;
	public static String ALIDAYU_SMS_URL = "http://gw.api.taobao.com/router/rest";
	public static String ALIDAYU_SMS_APPKEY = "23523438";
	public static String ALIDAYU_SMS_SECRET = "221e92217b31a02c2a5620dc80f15d74";
	public static String ALIDAYU_SMS_TYPE = "normal";
	public static String ALIDAYU_SMS_EXTEND = "";
	public static String ALIDAYU_SMS_PRODUCT = "云互艺";
	public static String ALIDAYU_SMS_FREE_SIGN_NAME_REG = "注册验证";
	public static String ALIDAYU_SMS_FREE_SIGN_NAME_LOGIN = "登录验证";
	public static String ALIDAYU_SMS_FREE_SIGN_NAME_CHANGE = "变更验证";
	public static String ALIDAYU_SMS_FREE_SIGN_NAME_IDENTITY = "身份验证";
	public static String ALIDAYU_SMS_FREE_SIGN_NAME_ACTIVITY = "活动验证";
	public static String ALIDAYU_SMS_TEMPLATE_CODE_IDENTITY = "SMS_11625303";// 身份验证验证码模板ID
	public static String ALIDAYU_SMS_TEMPLATE_CODE_LOGIN = "SMS_11625301";// 登录确认验证码模板ID
	public static String ALIDAYU_SMS_TEMPLATE_CODE_LOGIN_REEOR = "SMS_11625300";// 登录异常验证码模板ID
	public static String ALIDAYU_SMS_TEMPLATE_CODE_REG = "SMS_11625299";// 注册验证码模板ID
	public static String ALIDAYU_SMS_TEMPLATE_CODE_ACTIVITY = "SMS_11625298";// 活动确认验证码模板ID
	public static String ALIDAYU_SMS_TEMPLATE_CODE_CHANGE_PWD = "SMS_11625297";// 修改密码验证码模板ID
	public static String ALIDAYU_SMS_TEMPLATE_CODE_CHANGE_INFO = "SMS_11625296";// 信息变更验证码ID
	public static int ALIDAYU_SMS_SEND_INTERVAL = 60;// 发送验证码的间隔
	public static int ALIDAYU_SMS_EXPIRE_TIME = 5;// 发送的验证码有效时间，分钟

	public static String ALIDAYU_SMS_CODE_TYPE_REG = "reg_code";// 发送注册类型的验证码
	public static String ALIDAYU_SMS_CODE_TYPE_LOGIN = "login_code";// 发送登录验证码
	public static String ALIDAYU_SMS_CODE_TYPE_CHANGE = "change_code";// 发送变更身份的验证码
	public static String ALIDAYU_SMS_CODE_TYPE_IDENTITY = "identity_code";// 发送身份验证的验证码6
	public static String ALIDAYU_SMS_CODE_TYPE_ACTIVITY = "activity_code";// 发送活动验证的验证码

	// 分页时 默认显示记录条数
	public static int PAGESIZE = 10;
	// 广告 默认显示的记录条数
	public static int ADVERTISE_PAGESIZE = 2;
	// 首页名师 默认显示记录条数
	public static int HOMEPAGE_PAGESIZE = 2;
	// 首页作品 默认显示2条记录数
	public static int HOMEWORK_PAGESIZE = 6;

	// 帖子/小组动态名师点评显示的记录数
	public static int DIANPING_PAGESIZE = 1;
	//定义2015/1/1时间戳
	public static long DEFINE_TIMESTAMP=1420041600;
	
	//生成随机数的最大和最小值
	public static int RANDOM_MAXVALUE=30;
	public static int RANDOM_MINVALUE=5;
	
	//订单和测评状态
	public static int STATUS_0=0;//待支付
	public static int STATUS_1=1;//已支付
	public static int STATUS_2=2;//取消交易
	public static int STATUS_3=3;//作品待上传
	public static int STATUS_4=4;//作品待测评
	public static int STATUS_5=5;//已测评
	
	//忘记密码 修改密码 注册时差 验证码验证成功有效期
	public static long VERIFY_TIME=120; 
	//设置支付时间在30分钟以内
	public static int PAY_TIME=30;
	
	//设置已读消息列表分页大小
	public static int MSG_PAGESIZE=20;
	//设置艺培头条/资讯分页大小
	public static int INFO_PAGESIZE=30;
	
	//设置直播成员列表大小
	public static int MEMBER_PAGESIZE=15;
	//设置直播评论列表大小
	public static int COMMENT_PAGESIZE=5;
	
	/*
	 * 邀请码/会员号类型前置标记
	 */
	public static String USER_FLAG_STU = "@";
	public static String USER_FLAG_TEC = "#";
	public static String USER_FLAG_ORG = "$";
	
}

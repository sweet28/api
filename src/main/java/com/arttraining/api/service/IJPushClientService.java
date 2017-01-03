package com.arttraining.api.service;

import java.util.Map;

public interface IJPushClientService {
	//点赞帖子status/作品work/小组动态g_stus 封装json数据
	//id--被点赞信息ID
	//public String insertMsgPushByLike(String type,Integer status_id,String status_type,Integer uid,String utype);
	public String insertMsgPushByLike(String type, Map<String,Object> param);
	
	//关注爱好者/老师/机构 封装json数据
	//public String insertMsgPushByFollow(String type,String follow_type,Integer fan_id,String fan_type,Integer uid,String utype);
	public String insertMsgPushByFollow(String type, Map<String,Object> param);
	//发布帖子/动态 封装json数据
	public String insertMsgPushByPublishBBS(String type,Integer uid,String utype);
	
	//老师点评/回复作品 封装json数据
	public String insertMsgPushByTecCommentAndReply(String type,Integer work_id,Integer uid,String utype,Integer tec_id);
	
	//评论/回复帖子/小组动态/作品 封装json数据
	public String insertMsgPushByByCommentAndReply(String type, Map<String, Object> param);
	
	//下订单时提醒老师进行点评
	public String inserMsgPushByWorkOrder(String type,String user_type,Map<String, Object> param);
	//封装一个推送的方法
	public void encloseMsgPush(String user_type,Integer user_id,String type,Map<String,Object> param);
}

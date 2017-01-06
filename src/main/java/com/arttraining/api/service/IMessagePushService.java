package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.MsgUserBean;
import com.arttraining.api.pojo.MessagePush;

public interface IMessagePushService {
	//标记消息列表的某个消息已读
	public int updateOneMessagePush(MessagePush msg);
	 //coffee add 1230 标记指定用户的消息为已读 
    int updateAllMsgReadStatusByUid(MessagePush msg);
    
    //coffee add 0102 查询当前用户的消息列表 message/push/list接口调用
    List<MessagePush> getUnreadMsgListByUid(Integer owner,String owner_type);
    //coffee add 0102 依据用户ID和用户类型来查询相应的用户信息
    MsgUserBean getMsgUserInfoByUid(Integer uid,String utype);
    
    //coffee add 0102 查看更多消息列表信息 message/list/more接口调用
    List<MessagePush> getMoreMsgListByUid(Map<String, Object> map);
    
    //coffee 1230  查询当前用户未读的推送消息数
    int getUnreadMsgByUid(Integer owner,String owner_type);
}

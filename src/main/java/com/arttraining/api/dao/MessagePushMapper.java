package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.MsgUserBean;
import com.arttraining.api.pojo.MessagePush;

public interface MessagePushMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessagePush record);

    int insertSelective(MessagePush record);

    MessagePush selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessagePush record);

    int updateByPrimaryKey(MessagePush record);
    //coffee add 1230 标记指定用户的消息为已读 
    int updateMsgReadStatusByUid(MessagePush record);
    //coffee 1230  查询当前用户未读的推送消息数
    int selectUnreadMsgByUid(@Param("owner") Integer owner,
    		@Param("owner_type") String owner_type);
    
    //coffee add 0102 查询当前用户的消息列表 message/push/list接口调用
    List<MessagePush> selectUnreadMsgListByUid(@Param("owner") Integer owner,
    		@Param("owner_type") String owner_type);
    //coffee add 0102 依据用户ID和用户类型来查询相应的用户信息
    MsgUserBean selectMsgUserInfoByUid(@Param("uid") Integer uid,
    		@Param("utype") String utype);
    //coffee add 0102 查看更多消息列表信息 message/list/more接口调用
    List<MessagePush> selectMoreMsgListByUid(Map<String, Object> map);
}
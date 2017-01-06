package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.MsgUserBean;
import com.arttraining.api.dao.MessagePushMapper;
import com.arttraining.api.pojo.MessagePush;
import com.arttraining.api.service.IMessagePushService;

@Service("messagePushService")
public class MessagePushService implements IMessagePushService {
	@Resource
	private MessagePushMapper msgDao;
	
	@Override
	public int updateOneMessagePush(MessagePush msg) {
		// TODO Auto-generated method stub
		return this.msgDao.updateByPrimaryKeySelective(msg);
	}

	@Override
	public int updateAllMsgReadStatusByUid(MessagePush msg) {
		// TODO Auto-generated method stub
		return this.msgDao.updateMsgReadStatusByUid(msg);
	}

	@Override
	public List<MessagePush> getUnreadMsgListByUid(Integer owner,
			String owner_type) {
		// TODO Auto-generated method stub
		return this.msgDao.selectUnreadMsgListByUid(owner, owner_type);
	}

	@Override
	public MsgUserBean getMsgUserInfoByUid(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.msgDao.selectMsgUserInfoByUid(uid, utype);
	}

	@Override
	public List<MessagePush> getMoreMsgListByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.msgDao.selectMoreMsgListByUid(map);
	}

	@Override
	public int getUnreadMsgByUid(Integer owner, String owner_type) {
		// TODO Auto-generated method stub
		return this.msgDao.selectUnreadMsgByUid(owner, owner_type);
	}

}

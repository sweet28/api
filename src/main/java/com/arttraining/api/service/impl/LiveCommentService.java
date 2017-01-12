package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.dao.LiveCommentMapper;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.service.ILiveCommentService;

@Service("liveCommentService")
public class LiveCommentService implements ILiveCommentService {
	@Resource
	private LiveCommentMapper commentDao;
	
	@Override
	public int insertLiveComment(LiveComment comment) {
		// TODO Auto-generated method stub
		return this.commentDao.insertSelective(comment);
	}

	@Override
	public List<LiveCommentBean> getLiveCommentByRoomId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.commentDao.selectLiveCommentByRoomId(map);
	}

}

package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.LiveLikeMapper;
import com.arttraining.api.dao.LiveMapper;
import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveLike;
import com.arttraining.api.service.ILiveLikeService;

@Service("liveLikeService")
public class LiveLikeService implements ILiveLikeService {
	@Resource
	private LiveLikeMapper likeDao;
	@Resource
	private LiveMapper liveDao;
	
	@Override
	public void insertLiveLikeAndUpdateLive(LiveLike like, Live live) {
		// TODO Auto-generated method stub
		//1.新增直播点赞信息
		this.likeDao.insertSelective(like);
		//2.修改主播点赞数
		this.liveDao.updateByPrimaryKeySelective(live);
	}

}

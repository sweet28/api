package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.LiveJoinBean;
import com.arttraining.api.dao.LiveDetailMapper;
import com.arttraining.api.dao.LiveMapper;
import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.service.ILiveService;

@Service("liveService")
public class LiveService implements ILiveService {
	@Resource
	private LiveMapper liveDao;
	@Resource
	private LiveDetailMapper detailDao;

	@Override
	public int insertLiveAndDetailInfo(Live record, LiveDetail detail) {
		// TODO Auto-generated method stub
		//先插入直播信息
		this.liveDao.insertOneLiveInfo(record);
		Integer id=record.getId();
		//然后插入直播详情信息
		detail.setForeignKey(id);
		this.detailDao.insertSelective(detail);
		return id;
	}

	@Override
	public Live getOneLiveInfoByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.liveDao.selectLiveInfoByUid(map);
	}

	@Override
	public Live getOneLiveInfoByRoomId(Integer id) {
		// TODO Auto-generated method stub
		return this.liveDao.selectByPrimaryKey(id);
	}

	@Override
	public LiveJoinBean getLiveInfoByJoin(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.liveDao.selectLiveInfoByJoin(map);
	}

	@Override
	public int updateLiveInfo(Live record) {
		// TODO Auto-generated method stub
		return this.liveDao.updateByPrimaryKeySelective(record);
	}
	
	
}

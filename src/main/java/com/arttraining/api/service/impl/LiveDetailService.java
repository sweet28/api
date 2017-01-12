package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.LiveDetailMapper;
import com.arttraining.api.service.ILiveDetailService;

@Service("liveDetailService")
public class LiveDetailService implements ILiveDetailService{
	@Resource
	private LiveDetailMapper detailDao;
	
	@Override
	public int getLiveNumberByRoomId(Integer room_id) {
		// TODO Auto-generated method stub
		return this.detailDao.selectLiveNumberByRoomId(room_id);
	}

}

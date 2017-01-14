package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.LiveAuthMapper;
import com.arttraining.api.dao.LiveRoomMapper;
import com.arttraining.api.dao.LiveTimeTableMapper;
import com.arttraining.api.pojo.LiveAuthWithBLOBs;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.LiveTimeTable;
import com.arttraining.api.service.ILivePrepareService;

@Service("livePrepareService")
public class LivePrepareService implements ILivePrepareService {
	@Resource
	private LiveAuthMapper authDao;
	@Resource
	private LiveRoomMapper roomDao;
	@Resource
	private LiveTimeTableMapper timeDao;

	@Override
	public LiveAuthWithBLOBs getLiveAuthByUid(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.authDao.selectLiveAuthByUid(uid, utype);
	}

	@Override
	public int insertOneLiveAuth(LiveAuthWithBLOBs auth) {
		// TODO Auto-generated method stub
		return this.authDao.insertSelective(auth);
	}

	@Override
	public int updateOneLiveAuth(LiveAuthWithBLOBs auth) {
		// TODO Auto-generated method stub
		return this.authDao.updateByPrimaryKeySelective(auth);
	}

	@Override
	public LiveRoom getLiveRoomByUid(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.roomDao.selectLiveRoomByUid(uid, utype);
	}

	@Override
	public int insertOneLiveRoom(LiveRoom room) {
		// TODO Auto-generated method stub
		this.roomDao.insertLiveRoom(room);
		int id=room.getId();
		return id;
	}

	@Override
	public int updateOneLiveRoom(LiveRoom room) {
		// TODO Auto-generated method stub
		return this.roomDao.updateByPrimaryKeySelective(room);
	}

	@Override
	public LiveTimeTable getLiveTimeTableByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.timeDao.selectLiveTimeTableByUid(map);
	}

}

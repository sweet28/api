package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.LiveTypeList;
import com.arttraining.api.dao.LiveAuthMapper;
import com.arttraining.api.dao.LiveChapterPlanMapper;
import com.arttraining.api.dao.LiveRoomHistoryMapper;
import com.arttraining.api.dao.LiveRoomMapper;
import com.arttraining.api.dao.LiveTimeTableMapper;
import com.arttraining.api.dao.LiveTypeMapper;
import com.arttraining.api.pojo.LiveAuthWithBLOBs;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.LiveRoomHistory;
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
	@Resource
	private LiveChapterPlanMapper chapterDao;
	@Resource
	private LiveRoomHistoryMapper historyDao;
	@Resource
	private LiveTypeMapper typeDao;

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
	public int insertOneLiveTimeTable(LiveTimeTable timeTable) {
		// TODO Auto-generated method stub
		return this.timeDao.insertSelective(timeTable);
	}

	@Override
	public LiveChapterPlan getChapterPlanByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.chapterDao.selectChapterPlanByUid(map);
	}

	@Override
	public int insertOneLiveChapterPlan(LiveChapterPlan chapter) {
		// TODO Auto-generated method stub
		//1.先新增章节课时
		this.chapterDao.insertSelective(chapter);
		//2.然后修改直播间课时数
		//this.roomDao.updateByPrimaryKeySelective(room);
		return 0;
	}

	@Override
	public int insertOneLiveRoomHistory(LiveRoomHistory history) {
		// TODO Auto-generated method stub
		return this.historyDao.insertSelective(history);
	}

	@Override
	public List<LiveTypeList> getLivesTypeList() {
		// TODO Auto-generated method stub
		return this.typeDao.selectLivesTypeList();
	}
}

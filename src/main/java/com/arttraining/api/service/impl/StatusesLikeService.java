package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.StatusesLikeMapper;
import com.arttraining.api.dao.StatusesMapper;
import com.arttraining.api.pojo.StatusesLike;
import com.arttraining.api.service.IStatusesLikeService;

@Service("statusesLikeService")
public class StatusesLikeService implements IStatusesLikeService {
	@Resource
	private StatusesLikeMapper statusesLikeDao;
	@Resource
	private StatusesMapper statusesDao;
	
	@Override
	public StatusesLike selectStatusesLikeByUidAndFid(Integer fid, Integer uid) {
		// TODO Auto-generated method stub
		return this.statusesLikeDao.selectSelectiveByUidAndFid(fid, uid);
	}

	@Override
	public int insertStatusesLikeSelective(StatusesLike record) {
		// TODO Auto-generated method stub
		return this.statusesLikeDao.insertSelective(record);
	}

	@Override
	public int updateStatusesLikeSelective(StatusesLike record) {
		// TODO Auto-generated method stub
		return this.statusesLikeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public void insertAndUpdateStatus(StatusesLike record, Integer id) {
		// TODO Auto-generated method stub
		this.statusesLikeDao.insertSelective(record);
		this.statusesDao.updateStatusLikeNumByPrimaryKey(id);
	}

}

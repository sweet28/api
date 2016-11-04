package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.WorksLikeMapper;
import com.arttraining.api.pojo.WorksLike;
import com.arttraining.api.service.IWorksLikeService;

@Service("worksLikeService")
public class WorksLikeService implements IWorksLikeService {
	@Resource
	private WorksLikeMapper workLikeDao;
	
	@Override
	public WorksLike selectWorksLikeByUidAndFid(Integer fid, Integer uid) {
		// TODO Auto-generated method stub
		return this.workLikeDao.selectSelectiveByUidAndFid(fid, uid);
	}

	@Override
	public int insertWorksLikeSelective(WorksLike record) {
		// TODO Auto-generated method stub
		return this.workLikeDao.insertSelective(record);
	}

	@Override
	public int updateWorksLikeSelective(WorksLike record) {
		// TODO Auto-generated method stub
		return this.workLikeDao.updateByPrimaryKeySelective(record);
	}

}

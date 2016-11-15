package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.RecommendMapper;
import com.arttraining.api.pojo.Recommend;
import com.arttraining.api.service.IRecommendService;

@Service("recommendService")
public class RecommendService implements IRecommendService {
	@Resource
	private RecommendMapper recommendDao;
	
	@Override
	public int insertOneRecommend(Recommend record) {
		// TODO Auto-generated method stub
		return this.recommendDao.insertSelective(record);
	}

}

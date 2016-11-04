package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.service.IWorksService;

@Service("worksService")
public class WorksService implements IWorksService {
	@Resource
	private WorksMapper worksDao;
	@Override
	public Works getWorksById(Integer id) {
		// TODO Auto-generated method stub
		return this.worksDao.selectByPrimaryKey(id);
	}

}

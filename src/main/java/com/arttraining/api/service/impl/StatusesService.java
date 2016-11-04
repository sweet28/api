package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.StatusesMapper;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.service.IStatusesService;

@Service("statusesService")
public class StatusesService implements IStatusesService {
	@Resource
	private StatusesMapper statusesDao;
	
	@Override
	public Statuses getStatusesById(Integer id) {
		// TODO Auto-generated method stub
		return this.statusesDao.selectByPrimaryKey(id);
	}

}

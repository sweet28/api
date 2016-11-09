package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.StatusesForwardMapper;
import com.arttraining.api.dao.StatusesMapper;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesForward;
import com.arttraining.api.service.IStatusesForwardService;

@Service("statusesForwardService")
public class StatusesForwardService implements IStatusesForwardService {
	@Resource
	private StatusesMapper statusDao;
	@Resource
	private StatusesForwardMapper statusForwardDao;
	
	@Override
	public void insertOneStatusForward(StatusesForward statusForward,
			Statuses status) {
		//先插入一条动态信息
		this.statusDao.insertOneStatusSelective(status);
		int id = status.getId();
		statusForward.setForeignKey(id);
		this.statusForwardDao.insertSelective(statusForward);
	}

}

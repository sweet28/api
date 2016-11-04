package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.BBSMapper;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.service.IBBSService;

@Service("bbsService")
public class BBSService implements IBBSService {
	@Resource
	private BBSMapper bbsDao;

	@Override
	public BBS getBBSById(Integer id) {
		// TODO Auto-generated method stub
		return this.bbsDao.selectByPrimaryKey(id);
	}

}

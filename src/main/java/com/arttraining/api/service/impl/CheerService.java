package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.VoteCheerMapper;
import com.arttraining.api.pojo.VoteCheer;
import com.arttraining.api.service.ICheerService;

@Service("cheerService")
public class CheerService implements ICheerService {
	@Resource
	private VoteCheerMapper cheerDao;
	
	@Override
	public List<VoteCheer> getCheerListByActId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.cheerDao.selectCheerListByActId(map);
	}

}

package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.IdentityMapper;
import com.arttraining.api.service.IIdentityService;

@Service("identityService")
public class IdentityService implements IIdentityService {
	@Resource
	private IdentityMapper identityDao;

	@Override
	public String getNameById(Integer identityId) {
		// TODO Auto-generated method stub
		return this.identityDao.selectNameByPrimaryKey(identityId);
	}
	
}

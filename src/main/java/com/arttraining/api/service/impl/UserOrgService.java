package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.UserOrgMapper;
import com.arttraining.api.service.IUserOrgService;

@Service("userOrgService")
public class UserOrgService implements IUserOrgService {
	@Resource
	private UserOrgMapper userOrgDao;

	@Override
	public String getNameById(Integer userOrgId) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectNameByPrimaryKey(userOrgId);
	}

}

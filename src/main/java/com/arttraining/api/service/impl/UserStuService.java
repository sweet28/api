package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IUserStuService;

@Service("userStuService")
public class UserStuService implements IUserStuService{
	@Resource
	private UserStuMapper userStuDao;

	@Override
	public UserStu getUserStuById(int userStuId) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectByPrimaryKey(userStuId);
	}
	
}

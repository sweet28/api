package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IUserStuService;

// @Service("") service名称可选,之所以写service名的原因是为了方便外部调用
@Service("userStuService")
public class UserStuService implements IUserStuService{
	@Resource
	private UserStuMapper userStuDao;

	@Override
	public UserStu getUserStuById(Integer userStuId) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectByPrimaryKey(userStuId);
	}

	@Override
	public Integer updateUserStuBySelective(UserStu record) {
		// TODO Auto-generated method stub
		return this.userStuDao.updateByPrimaryKeySelective(record);
	}
	
}

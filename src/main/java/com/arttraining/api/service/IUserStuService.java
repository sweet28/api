package com.arttraining.api.service;

import com.arttraining.api.pojo.UserStu;

public interface IUserStuService {
	//依据用户ID查询相应的爱好者用户信息
	public UserStu getUserStuById(Integer userStuId);
	//传递参数修改相应的爱好者用户信息
	public Integer updateUserStuBySelective(UserStu record);

}

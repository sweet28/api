package com.arttraining.api.dao;

import com.arttraining.api.pojo.UserStu;

public interface UserStuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserStu record);

    int insertSelective(UserStu record);

    UserStu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStu record);

    int updateByPrimaryKey(UserStu record);
}
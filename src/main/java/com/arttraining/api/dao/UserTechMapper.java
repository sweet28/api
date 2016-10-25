package com.arttraining.api.dao;

import com.arttraining.api.pojo.UserTech;

public interface UserTechMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTech record);

    int insertSelective(UserTech record);

    UserTech selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTech record);

    int updateByPrimaryKey(UserTech record);
}
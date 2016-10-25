package com.arttraining.api.dao;

import com.arttraining.api.pojo.Statuses;

public interface StatusesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Statuses record);

    int insertSelective(Statuses record);

    Statuses selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Statuses record);

    int updateByPrimaryKey(Statuses record);
}
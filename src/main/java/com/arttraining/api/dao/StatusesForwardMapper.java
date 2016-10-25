package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesForward;

public interface StatusesForwardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesForward record);

    int insertSelective(StatusesForward record);

    StatusesForward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesForward record);

    int updateByPrimaryKey(StatusesForward record);
}
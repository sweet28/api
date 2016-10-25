package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesRead;

public interface StatusesReadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesRead record);

    int insertSelective(StatusesRead record);

    StatusesRead selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesRead record);

    int updateByPrimaryKey(StatusesRead record);
}
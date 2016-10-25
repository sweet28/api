package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesShare;

public interface StatusesShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesShare record);

    int insertSelective(StatusesShare record);

    StatusesShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesShare record);

    int updateByPrimaryKey(StatusesShare record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSRead;

public interface BBSReadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSRead record);

    int insertSelective(BBSRead record);

    BBSRead selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSRead record);

    int updateByPrimaryKey(BBSRead record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSShare;

public interface BBSShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSShare record);

    int insertSelective(BBSShare record);

    BBSShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSShare record);

    int updateByPrimaryKey(BBSShare record);
}
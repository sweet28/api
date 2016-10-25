package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSForward;

public interface BBSForwardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSForward record);

    int insertSelective(BBSForward record);

    BBSForward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSForward record);

    int updateByPrimaryKey(BBSForward record);
}
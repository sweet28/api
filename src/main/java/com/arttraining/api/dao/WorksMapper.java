package com.arttraining.api.dao;

import com.arttraining.api.pojo.Works;

public interface WorksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Works record);

    int insertSelective(Works record);

    Works selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Works record);

    int updateByPrimaryKey(Works record);
}
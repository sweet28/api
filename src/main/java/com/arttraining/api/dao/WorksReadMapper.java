package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksRead;

public interface WorksReadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksRead record);

    int insertSelective(WorksRead record);

    WorksRead selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksRead record);

    int updateByPrimaryKey(WorksRead record);
}
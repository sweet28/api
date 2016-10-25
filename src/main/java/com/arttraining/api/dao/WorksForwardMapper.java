package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksForward;

public interface WorksForwardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksForward record);

    int insertSelective(WorksForward record);

    WorksForward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksForward record);

    int updateByPrimaryKey(WorksForward record);
}
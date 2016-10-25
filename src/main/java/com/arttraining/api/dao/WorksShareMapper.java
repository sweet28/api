package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksShare;

public interface WorksShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksShare record);

    int insertSelective(WorksShare record);

    WorksShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksShare record);

    int updateByPrimaryKey(WorksShare record);
}
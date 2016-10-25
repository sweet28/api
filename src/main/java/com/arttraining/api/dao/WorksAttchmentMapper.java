package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksAttchment;

public interface WorksAttchmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksAttchment record);

    int insertSelective(WorksAttchment record);

    WorksAttchment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksAttchment record);

    int updateByPrimaryKey(WorksAttchment record);
}
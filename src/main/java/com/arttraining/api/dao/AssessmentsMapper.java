package com.arttraining.api.dao;

import com.arttraining.api.pojo.Assessments;

public interface AssessmentsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assessments record);

    int insertSelective(Assessments record);

    Assessments selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assessments record);

    int updateByPrimaryKey(Assessments record);
}
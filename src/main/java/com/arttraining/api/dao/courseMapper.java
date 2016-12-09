package com.arttraining.api.dao;

import com.arttraining.api.pojo.course;

public interface courseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(course record);

    int insertSelective(course record);

    course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(course record);

    int updateByPrimaryKey(course record);
}
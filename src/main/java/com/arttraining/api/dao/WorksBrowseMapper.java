package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksBrowse;

public interface WorksBrowseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksBrowse record);

    int insertSelective(WorksBrowse record);

    WorksBrowse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksBrowse record);

    int updateByPrimaryKey(WorksBrowse record);
}
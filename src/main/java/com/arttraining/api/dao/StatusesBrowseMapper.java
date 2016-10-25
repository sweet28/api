package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesBrowse;

public interface StatusesBrowseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesBrowse record);

    int insertSelective(StatusesBrowse record);

    StatusesBrowse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesBrowse record);

    int updateByPrimaryKey(StatusesBrowse record);
}
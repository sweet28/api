package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSBrowse;

public interface BBSBrowseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSBrowse record);

    int insertSelective(BBSBrowse record);

    BBSBrowse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSBrowse record);

    int updateByPrimaryKey(BBSBrowse record);
}
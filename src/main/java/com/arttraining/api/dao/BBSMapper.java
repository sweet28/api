package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBS;

public interface BBSMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBS record);

    int insertSelective(BBS record);

    BBS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBS record);

    int updateByPrimaryKey(BBS record);
}
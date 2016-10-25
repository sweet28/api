package com.arttraining.api.dao;

import com.arttraining.api.pojo.Institutions;

public interface InstitutionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Institutions record);

    int insertSelective(Institutions record);

    Institutions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Institutions record);

    int updateByPrimaryKey(Institutions record);
}
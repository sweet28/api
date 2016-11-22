package com.arttraining.api.dao;

import com.arttraining.api.pojo.InstitutionsConditions;

public interface InstitutionsConditionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InstitutionsConditions record);

    int insertSelective(InstitutionsConditions record);

    InstitutionsConditions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InstitutionsConditions record);

    int updateByPrimaryKey(InstitutionsConditions record);
}
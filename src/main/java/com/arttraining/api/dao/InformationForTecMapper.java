package com.arttraining.api.dao;

import com.arttraining.api.pojo.InformationForTec;

public interface InformationForTecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InformationForTec record);

    int insertSelective(InformationForTec record);

    InformationForTec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InformationForTec record);

    int updateByPrimaryKey(InformationForTec record);
}
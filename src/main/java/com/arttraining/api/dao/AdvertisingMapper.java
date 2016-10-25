package com.arttraining.api.dao;

import com.arttraining.api.pojo.Advertising;

public interface AdvertisingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertising record);

    int insertSelective(Advertising record);

    Advertising selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertising record);

    int updateByPrimaryKey(Advertising record);
}
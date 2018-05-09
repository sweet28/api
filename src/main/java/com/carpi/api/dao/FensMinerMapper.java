package com.carpi.api.dao;

import com.carpi.api.pojo.FensMiner;

public interface FensMinerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensMiner record);

    int insertSelective(FensMiner record);

    FensMiner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensMiner record);

    int updateByPrimaryKey(FensMiner record);
}
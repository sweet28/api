package com.carpi.api.dao;

import com.carpi.api.pojo.bMiner;

public interface bMinerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(bMiner record);

    int insertSelective(bMiner record);

    bMiner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(bMiner record);

    int updateByPrimaryKey(bMiner record);
}
package com.carpi.api.dao;

import com.carpi.api.pojo.Aminer;

public interface AminerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Aminer record);

    int insertSelective(Aminer record);

    Aminer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Aminer record);

    int updateByPrimaryKey(Aminer record);
}
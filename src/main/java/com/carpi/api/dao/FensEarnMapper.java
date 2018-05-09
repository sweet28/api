package com.carpi.api.dao;

import com.carpi.api.pojo.FensEarn;

public interface FensEarnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensEarn record);

    int insertSelective(FensEarn record);

    FensEarn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensEarn record);

    int updateByPrimaryKey(FensEarn record);
}
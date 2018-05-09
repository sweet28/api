package com.carpi.api.dao;

import com.carpi.api.pojo.FensTransaction;

public interface FensTransactionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensTransaction record);

    int insertSelective(FensTransaction record);

    FensTransaction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensTransaction record);

    int updateByPrimaryKey(FensTransaction record);
}
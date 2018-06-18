package com.carpi.api.dao;

import com.carpi.api.pojo.TiquJine;

public interface TiquJineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TiquJine record);

    int insertSelective(TiquJine record);

    TiquJine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TiquJine record);

    int updateByPrimaryKey(TiquJine record);
}
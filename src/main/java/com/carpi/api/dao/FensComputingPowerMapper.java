package com.carpi.api.dao;

import com.carpi.api.pojo.FensComputingPower;

public interface FensComputingPowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensComputingPower record);

    int insertSelective(FensComputingPower record);

    FensComputingPower selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensComputingPower record);

    int updateByPrimaryKey(FensComputingPower record);
}
package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FensComputingPower;

public interface FensComputingPowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensComputingPower record);

    int insertSelective(FensComputingPower record);

    FensComputingPower selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensComputingPower record);

    int updateByPrimaryKey(FensComputingPower record);
    
    //粉丝算力明细
    List<FensComputingPower> selectAll(@Param("fensUserId")Integer fensUserId);
    
    //粉丝算力求和
    Double sum(@Param("fensUserId")Integer fensUserId);
    
}
package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FensEarn;

public interface FensEarnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensEarn record);

    int insertSelective(FensEarn record);

    FensEarn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensEarn record);

    int updateByPrimaryKey(FensEarn record);
    
    //粉丝收益列表
    List<FensEarn> selectFensEarn(@Param("fensUserId") Integer fensUserId);
    
}
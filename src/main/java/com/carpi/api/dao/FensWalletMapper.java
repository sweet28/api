package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FensWallet;

public interface FensWalletMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensWallet record);

    int insertSelective(FensWallet record);

    FensWallet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensWallet record);

    int updateByPrimaryKey(FensWallet record);
    
    //根据粉丝Id查询钱包列表
    List<FensWallet> selectAll(@Param("fensUserId") Integer fensUserId);
}
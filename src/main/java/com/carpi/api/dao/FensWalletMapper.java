package com.carpi.api.dao;

import com.carpi.api.pojo.FensWallet;

public interface FensWalletMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensWallet record);

    int insertSelective(FensWallet record);

    FensWallet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensWallet record);

    int updateByPrimaryKey(FensWallet record);
}
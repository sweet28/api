package com.arttraining.api.dao;

import com.arttraining.api.pojo.WalletSystem;

public interface WalletSystemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WalletSystem record);

    int insertSelective(WalletSystem record);

    WalletSystem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WalletSystem record);

    int updateByPrimaryKey(WalletSystem record);
    
    //coffee add 0322 依据订单编号查询订单信息
    WalletSystem selectWalletSystemByOrderNumber(String order_number);
}
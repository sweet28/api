package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.WalletOrder;

public interface WalletOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WalletOrder record);

    int insertSelective(WalletOrder record);

    WalletOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WalletOrder record);

    int updateByPrimaryKey(WalletOrder record);
    //依据订单ID和订单编号来查询相应的订单信息
    WalletOrder selectOrderInfoById(@Param("order_id") Integer order_id,
    		@Param("order_number") String order_number);
    //coffee add 0302 依据订单编号查询相应的订单信息
    WalletOrder selectOrderInfoByNumber(String order_number);
}
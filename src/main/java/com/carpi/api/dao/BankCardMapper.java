package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.BankCard;

public interface BankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BankCard record);

    int insertSelective(BankCard record);

    BankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankCard record);

    int updateByPrimaryKey(BankCard record);
    
    //根据粉丝Id查询银行卡列表
    List<BankCard> selectAll(@Param("fensUserId") Integer fensUserId);
    
    //查询是否存在该银行卡
    BankCard selectCard(@Param("cardNumber") String cardNumber);
    
    //查询支付宝或者微信
    BankCard selectPay(BankCard bankCard);
    
    //查询该支付宝或者微信是否已存在
    BankCard weChatAipay(BankCard bankCard);
    
    //查询支付账户信息
    List<BankCard> selectZh(@Param("fensUserId") Integer fensUserId);
}
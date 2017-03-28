package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.beanv2.CloudTranformMoneyBean;
import com.arttraining.api.pojo.WalletValueTransform;

public interface WalletValueTransformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WalletValueTransform record);

    int insertSelective(WalletValueTransform record);

    WalletValueTransform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WalletValueTransform record);

    int updateByPrimaryKey(WalletValueTransform record);
    //coffee add 0302 查询云币和售价转换的列表信息
    List<CloudTranformMoneyBean> selectWalletMoneyList();
}
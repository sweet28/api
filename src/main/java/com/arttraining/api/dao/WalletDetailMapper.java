package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.CloudMoneyDetailBean;
import com.arttraining.api.pojo.WalletDetail;

public interface WalletDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WalletDetail record);

    int insertSelective(WalletDetail record);

    WalletDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WalletDetail record);

    int updateByPrimaryKey(WalletDetail record);
    //coffee add 0301 依据用户ID和类型查询云币消费详情列表信息 
    List<CloudMoneyDetailBean> selectCloudListByUid(Map<String, Object> map);
}
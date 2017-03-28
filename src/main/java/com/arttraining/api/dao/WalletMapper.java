package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.Wallet;

public interface WalletMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);
    //coffee add 0302 修改云币信息
    int updateCloudMoneyByUid(Wallet record);
    //coffee add 0302 依据用户ID和类型查询云币信息 
    Wallet selectCloudMoneyByUid(@Param("uid") Integer uid,
    		@Param("utype") String utype);
}
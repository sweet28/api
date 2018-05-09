package com.carpi.api.dao;

import com.carpi.api.pojo.FensUserInfo;

public interface FensUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensUserInfo record);

    int insertSelective(FensUserInfo record);

    FensUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensUserInfo record);

    int updateByPrimaryKey(FensUserInfo record);
}
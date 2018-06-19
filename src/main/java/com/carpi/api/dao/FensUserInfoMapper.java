package com.carpi.api.dao;

import com.carpi.api.pojo.FensUserInfo;

public interface FensUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensUserInfo record);

    int insertSelective(FensUserInfo record);

    FensUserInfo selectByPrimaryKey(Integer id);
    
    FensUserInfo selectByFensUserId(Integer fensUserId);

    int updateByPrimaryKeySelective(FensUserInfo record);
    
    int updateByPrimaryKeySelectiveByFensUserId(FensUserInfo record);

    int updateByPrimaryKey(FensUserInfo record);
    
    int selectTotalChildFensOne(Integer fensUserId);

    int selectTotalChildFensTwo(Integer fensUserId);
}
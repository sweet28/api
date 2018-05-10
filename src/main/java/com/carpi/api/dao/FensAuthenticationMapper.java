package com.carpi.api.dao;

import com.carpi.api.pojo.FensAuthentication;

public interface FensAuthenticationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensAuthentication record);

    int insertSelective(FensAuthentication record);

    FensAuthentication selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensAuthentication record);

    int updateByPrimaryKey(FensAuthentication record);
    
    //根据身份证查询信息，看看是否被绑定
    FensAuthentication selectCardInfo(String cardNumber);
}
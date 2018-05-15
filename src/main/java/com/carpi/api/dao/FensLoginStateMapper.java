package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.FensLoginState;

public interface FensLoginStateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensLoginState record);

    int insertSelective(FensLoginState record);

    FensLoginState selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensLoginState record);

    int updateByPrimaryKey(FensLoginState record);
    
    //登入状态列表
    List<FensLoginState> selectAll();
}
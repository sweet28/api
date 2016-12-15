package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.pojo.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);
    
    //获取某个token 依据用户ID和类型
    Token selectOneTokenInfo(Map<String, Object> map);
}
package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.pojo.ThirdLogin;

public interface ThirdLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThirdLogin record);

    int insertSelective(ThirdLogin record);

    ThirdLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdLogin record);

    int updateByPrimaryKey(ThirdLogin record);
    //coffee add 0104 依据登录方式和uid 判断是否登录过
    ThirdLogin selectLoginInfoByUid(Map<String, Object> map);
}
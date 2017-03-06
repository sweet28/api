package com.arttraining.api.dao;

import com.arttraining.api.pojo.AppVersion;

public interface AppVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);
    //app版本更新--version/update接口调用
    AppVersion selectOneVersionInfo(String app_type);
}
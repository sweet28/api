package com.arttraining.api.dao;

import com.arttraining.api.pojo.AppException;

public interface AppExceptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppException record);

    int insertSelective(AppException record);

    AppException selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppException record);

    int updateByPrimaryKeyWithBLOBs(AppException record);

    int updateByPrimaryKey(AppException record);
}
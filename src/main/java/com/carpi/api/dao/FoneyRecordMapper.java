package com.carpi.api.dao;

import com.carpi.api.pojo.FoneyRecord;

public interface FoneyRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FoneyRecord record);

    int insertSelective(FoneyRecord record);

    FoneyRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FoneyRecord record);

    int updateByPrimaryKey(FoneyRecord record);
}
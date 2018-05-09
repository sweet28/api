package com.carpi.api.dao;

import com.carpi.api.pojo.AminerRecord;

public interface AminerRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AminerRecord record);

    int insertSelective(AminerRecord record);

    AminerRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AminerRecord record);

    int updateByPrimaryKey(AminerRecord record);
}
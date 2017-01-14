package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveType;

public interface LiveTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveType record);

    int insertSelective(LiveType record);

    LiveType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveType record);

    int updateByPrimaryKey(LiveType record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveAuth;
import com.arttraining.api.pojo.LiveAuthWithBLOBs;

public interface LiveAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveAuthWithBLOBs record);

    int insertSelective(LiveAuthWithBLOBs record);

    LiveAuthWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveAuthWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LiveAuthWithBLOBs record);

    int updateByPrimaryKey(LiveAuth record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveTimeTable;

public interface LiveTimeTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveTimeTable record);

    int insertSelective(LiveTimeTable record);

    LiveTimeTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveTimeTable record);

    int updateByPrimaryKeyWithBLOBs(LiveTimeTable record);

    int updateByPrimaryKey(LiveTimeTable record);
}
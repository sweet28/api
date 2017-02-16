package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveRoomHistory;

public interface LiveRoomHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveRoomHistory record);

    int insertSelective(LiveRoomHistory record);

    LiveRoomHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveRoomHistory record);

    int updateByPrimaryKey(LiveRoomHistory record);
}
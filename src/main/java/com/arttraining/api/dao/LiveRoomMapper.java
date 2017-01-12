package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveRoom;

public interface LiveRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveRoom record);

    int insertSelective(LiveRoom record);

    LiveRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveRoom record);

    int updateByPrimaryKey(LiveRoom record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveDetail;

public interface LiveDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveDetail record);

    int insertSelective(LiveDetail record);

    LiveDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveDetail record);

    int updateByPrimaryKey(LiveDetail record);
    //coffee 0110 获取直播观看人次 live/stop接口调用 
    int selectLiveNumberByRoomId(Integer room_id);
}
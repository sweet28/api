package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.LiveRoom;

public interface LiveRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveRoom record);

    int insertSelective(LiveRoom record);

    LiveRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveRoom record);

    int updateByPrimaryKey(LiveRoom record);
    
    //coffee add 0113 判断教师是否设置过直播间
    LiveRoom selectLiveRoomByUid(@Param("uid") Integer uid,
    		@Param("utype") String utype);
    //coffee add 0113 新增直播间信息
    int insertLiveRoom(LiveRoom record);
}
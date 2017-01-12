package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.pojo.LiveMember;

public interface LiveMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveMember record);

    int insertSelective(LiveMember record);

    LiveMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveMember record);

    int updateByPrimaryKey(LiveMember record);
    
    //coffee 0111 加入直播房间时 判断是否已经加入过直播房间 
    //如果加入过 则不需要加入到直播房间中,但是需要加入到直播痕迹表中
    LiveMember selectLiveMemberInfoByRoomId(Map<String, Object> map);
    
    //获取直播成员列表信息 live/member/list接口调用
    List<LiveMemberBean> selectLiveMemberByRoomId(Map<String, Object> map);
    //coffee add 获取主播基本信息 live/member/list接口调用
    LiveMemberBean selectLiveOwnerByRoomId(Map<String, Object> map);
}
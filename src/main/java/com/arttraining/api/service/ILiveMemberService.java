package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.pojo.LiveMember;

public interface ILiveMemberService {
	//加入房间的同时修改直播在线数量
	int insertLiveDetailAndUpdateLive(LiveMember member,LiveDetail detail,Live live);
	//coffee 0111 加入直播房间时 判断是否已经加入过直播房间 
    //如果加入过 则不需要加入到直播房间中,但是需要加入到直播痕迹表中
    LiveMember getLiveMemberInfo(Map<String, Object> map);
    //退出房间的同时修改直播成员信息
    int insertLiveDetailAndUpdateLiveMember(LiveMember member,LiveDetail detail,Live live);
    
	//获取直播成员列表信息 live/member/list接口调用
    List<LiveMemberBean> getLiveMemberByRoomId(Map<String, Object> map);
    //coffee add 获取主播基本信息 live/member/list接口调用
    LiveMemberBean getLiveOwnerByRoomId(Map<String, Object> map);
 
    
}

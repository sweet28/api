package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.pojo.LiveAuthWithBLOBs;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.LiveTimeTable;

public interface ILivePrepareService {
	//教师直播资质认证调用方法
	//coffee add 0112 依据用户ID和类型来判断是否进行了直播资质认证
    LiveAuthWithBLOBs getLiveAuthByUid(Integer uid,String utype);
    //coffee add 0113 新增一条教师直播资格认证信息
    int insertOneLiveAuth(LiveAuthWithBLOBs auth);
    //coffee add 0113 修改一条教师直播资格认证信息
    int updateOneLiveAuth(LiveAuthWithBLOBs auth);
    
    //教师直播间认证调用方法
    //coffee add 0113 判断教师是否设置过直播间
    LiveRoom getLiveRoomByUid(Integer uid,String utype);
    //coffee add 0113 新增一条直播间认证信息
    int insertOneLiveRoom(LiveRoom room);
    //coffee add 0113 修改一条直播间认证信息
    int updateOneLiveRoom(LiveRoom room);
    
    //课表设置调用方法
    //coffee add 0113 判断是否设置过相同课表名称
    LiveTimeTable getLiveTimeTableByUid(Map<String, Object> map);
}

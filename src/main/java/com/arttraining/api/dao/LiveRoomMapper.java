package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.beanv2.OpenClassEnterLiveBean;
import com.arttraining.api.beanv2.OpenClassLiveListBean;
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
    //coffee add 0116 直播列表open/class/live/list接口调用
    List<OpenClassLiveListBean> selectRoomLiveListByPre(Map<String, Object> map);
    List<OpenClassLiveListBean> selectRoomLiveListByFinish(Map<String, Object> map);
    
    //coffee add 0117 依据直播间ID来查询对应的直播信息 
    OpenClassEnterLiveBean selectLiveRoomInfoById(Map<String, Object> map);
    //coffee add 0216 修改直播预告课时数
    int updatePreNumByRoomId(LiveRoom record);
    
    //coffee add 0217 首页新增直播列表接口 
    List<OpenClassLiveListBean> selectRoomLiveListByHome(Integer limit);
}
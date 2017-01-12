package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.beanv2.LiveJoinBean;
import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveDetail;

public interface ILiveService {
	/***
	 * 开启直播时 新增一条开启直播的信息
	 */
    int insertLiveAndDetailInfo(Live record,LiveDetail detail);
    //coffee add 0107 依据用户ID和类型来获取直播列表信息
    Live getOneLiveInfoByUid(Map<String, Object> map);
    //依据房间ID/直播ID来获取详细的直播信息
    Live getOneLiveInfoByRoomId(Integer id);
    //coffee add 0107 依据用户ID和类型 以及直播ID来获取主播相关信息
    LiveJoinBean getLiveInfoByJoin(Map<String, Object> map);
    //coffee add 0110 修改某一条主播房间信息
    int updateLiveInfo(Live record);
}

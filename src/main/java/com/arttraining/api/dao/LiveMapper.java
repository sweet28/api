package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.beanv2.LiveJoinBean;
import com.arttraining.api.pojo.Live;

public interface LiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Live record);

    int insertSelective(Live record);

    Live selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Live record);

    int updateByPrimaryKey(Live record);
    
    //coffee add 0107 主播开启直播时调用的方法
    int insertOneLiveInfo(Live record);
    //coffee add 0107 依据用户ID和类型来获取直播列表信息
    Live selectLiveInfoByUid(Map<String, Object> map);
    //coffee add 0107 依据用户ID和类型 以及直播ID来获取主播相关信息
    LiveJoinBean selectLiveInfoByJoin(Map<String, Object> map);
}
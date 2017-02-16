package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveChapterListBean;
import com.arttraining.api.pojo.LiveChapterPlan;

public interface LiveChapterPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveChapterPlan record);

    int insertSelective(LiveChapterPlan record);

    LiveChapterPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveChapterPlan record);

    int updateByPrimaryKeyWithBLOBs(LiveChapterPlan record);

    int updateByPrimaryKey(LiveChapterPlan record);
    
    //coffee add 0114 判断是否重复新增课时信息
    LiveChapterPlan selectChapterPlanByUid(Map<String, Object> map);
    //coffee add 0117 房间ID 主播ID和类型去查询信息
    LiveChapterPlan selectChapterPlanByPreTime(Map<String, Object> map);
    
    //coffee add 0119 按照课时开始时间升序排列获取所有课时信息 
    List<LiveChapterListBean> selectChapterListById(Map<String, Object> map);
    //coffee add 0119 依据主播ID和类型判断是否存在预告课时信息
    LiveChapterPlan selectChapterInfoByOwner(Map<String, Object> map);
}
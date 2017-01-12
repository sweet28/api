package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveChapterPlan;

public interface LiveChapterPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveChapterPlan record);

    int insertSelective(LiveChapterPlan record);

    LiveChapterPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveChapterPlan record);

    int updateByPrimaryKeyWithBLOBs(LiveChapterPlan record);

    int updateByPrimaryKey(LiveChapterPlan record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.ScoreRecord;

public interface ScoreRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScoreRecord record);

    int insertSelective(ScoreRecord record);

    ScoreRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScoreRecord record);

    int updateByPrimaryKey(ScoreRecord record);
    
    //依据订单编号查询相应的积分记录表
    ScoreRecord selectScoreRecordByOrderId(Integer order_id);
}
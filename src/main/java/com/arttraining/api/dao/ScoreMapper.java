package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.Score;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);
    
    // coffee add 0228 依据用户ID和类型来查询积分信息
 	Score selectScoreByUid(@Param("uid") Integer uid,
 			@Param("utype") String utype);
 	//coffee add 0301 直播礼物消费积分
 	int updateScoreByUid(Score record);
 	//end
}
package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.ScoreDetailBean;
import com.arttraining.api.pojo.ScoreDetail;

public interface ScoreDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScoreDetail record);

    int insertSelective(ScoreDetail record);

    ScoreDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScoreDetail record);

    int updateByPrimaryKey(ScoreDetail record);
    //coffee add 0301 依据用户ID和类型查询积分消费详情列表信息
    List<ScoreDetailBean> selectScoreListByUid(Map<String, Object> map);
}
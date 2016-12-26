package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.pojo.ActivityVote;

public interface ActivityVoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityVote record);

    int insertSelective(ActivityVote record);

    ActivityVote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityVote record);

    int updateByPrimaryKeyWithBLOBs(ActivityVote record);

    int updateByPrimaryKey(ActivityVote record);
    
    //用于获取投票列表信息 vote/act/list接口调用 
    List<ActivityVote> selectVoteListByActId(Map<String, Object> map);
}
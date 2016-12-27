package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.pojo.VoteRecord;

public interface VoteRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VoteRecord record);

    int insertSelective(VoteRecord record);

    VoteRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteRecord record);

    int updateByPrimaryKey(VoteRecord record);
    //去投票记录表中去查找是否已经投票 取最新的一条投票记录
    VoteRecord selectVoteRecordByUid(Map<String, Object> map);
}
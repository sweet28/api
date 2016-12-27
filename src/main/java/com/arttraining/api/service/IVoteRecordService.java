package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.pojo.ActivityVote;
import com.arttraining.api.pojo.VoteRecord;

public interface IVoteRecordService {
	//去投票记录表中去查找是否已经投票 取最新的一条投票记录
	VoteRecord getVoteRecordByUid(Map<String, Object> map);
	//新增投票记录
	int insertOneVoteRecord(VoteRecord record);
	//新增投票记录的同时要更新投票数
	int insertVoteRecordAndNumber(VoteRecord record,ActivityVote vote);
}

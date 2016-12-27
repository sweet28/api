package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.ActivityVoteMapper;
import com.arttraining.api.dao.VoteRecordMapper;
import com.arttraining.api.pojo.ActivityVote;
import com.arttraining.api.pojo.VoteRecord;
import com.arttraining.api.service.IVoteRecordService;

@Service("voteRecordService")
public class VoteRecordService implements IVoteRecordService {
	@Resource
	private VoteRecordMapper voteRecordDao;
	@Resource
	private ActivityVoteMapper voteDao;

	@Override
	public VoteRecord getVoteRecordByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.voteRecordDao.selectVoteRecordByUid(map);
	}

	@Override
	public int insertOneVoteRecord(VoteRecord record) {
		// TODO Auto-generated method stub
		return this.voteRecordDao.insertSelective(record);
	}

	@Override
	public int insertVoteRecordAndNumber(VoteRecord record, ActivityVote vote) {
		// TODO Auto-generated method stub
		//1.先更新票数
		this.voteDao.updateByPrimaryKeySelective(vote);
		//2.然后新增投票记录
		this.voteRecordDao.insertSelective(record);
		return 0;
	}

}

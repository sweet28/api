package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.ActivityVoteMapper;
import com.arttraining.api.pojo.ActivityVote;
import com.arttraining.api.service.IVoteService;

@Service("voteService")
public class VoteService implements IVoteService {
	@Resource
	private ActivityVoteMapper voteDao;

	@Override
	public List<ActivityVote> getVoteListByActId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.voteDao.selectVoteListByActId(map);
	}

	@Override
	public ActivityVote getOneVoteById(Integer id) {
		// TODO Auto-generated method stub
		return this.voteDao.selectByPrimaryKey(id);
	}

}

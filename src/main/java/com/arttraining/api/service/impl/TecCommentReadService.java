package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.CommentReadMapper;
import com.arttraining.api.dao.WorksTecCommentMapper;
import com.arttraining.api.pojo.CommentRead;
import com.arttraining.api.pojo.WorksTecComment;
import com.arttraining.api.service.ITecCommentReadService;

@Service("tecCommentReadService")
public class TecCommentReadService implements ITecCommentReadService {
	@Resource
	private CommentReadMapper commentReadDao;
	@Resource
	private WorksTecCommentMapper worksTecCommentDao;
	
	@Override
	public void insertReadAndTecComment(CommentRead read,
			WorksTecComment comment) {
		// TODO Auto-generated method stub
		if(read!=null) {
			this.commentReadDao.insertSelective(read);
		}
		this.worksTecCommentDao.updateByPrimaryKeySelective(comment);
	}

	@Override
	public CommentRead getCommentReadByComId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.commentReadDao.selectCommentReadByComId(map);
	}

}

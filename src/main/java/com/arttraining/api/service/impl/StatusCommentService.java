package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.StatusesCommentMapper;
import com.arttraining.api.dao.StatusesMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.StatusesComment;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IStatusCommentService;

@Service("statusCommentService")
public class StatusCommentService implements IStatusCommentService {
	@Resource
	private StatusesCommentMapper statusCommentDao;
	@Resource
	private StatusesMapper statusDao;
	@Resource
	private UserStuMapper userStuDao;

	@Override
	public List<CommentsVisitorBean> getStatusCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.statusCommentDao.selectStatusCommentByShow(fid, limit);
	}

	@Override
	public List<CommentsVisitorBean> getStatusCommentByList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.statusCommentDao.selectStatusCommentByList(map);
	}

	@Override
	public void insertAndUpdateStatusComment(StatusesComment statusComment,
			Integer id, UserStu user) {
		// TODO Auto-generated method stub
		this.statusCommentDao.insertSelective(statusComment);
		this.statusDao.updateStatusCommNumByPrimaryKey(id);
		if(user!=null) {
			this.userStuDao.updateNumberBySelective(user);
		}
	}
	
}

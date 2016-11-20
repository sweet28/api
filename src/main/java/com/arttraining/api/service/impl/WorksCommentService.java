package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.WorksCommentMapper;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.WorksComment;
import com.arttraining.api.service.IWorksCommentService;


@Service("worksCommentService")
public class WorksCommentService implements IWorksCommentService{
	@Resource
	private WorksCommentMapper worksCommentDao;
	@Resource
	private WorksMapper worksDao;
	@Resource
	private UserStuMapper userStuDao;
	
	@Override
	public List<CommentsVisitorBean> getWorkCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.worksCommentDao.selectWorkCommentByShow(fid, limit);
	}

	@Override
	public List<CommentsVisitorBean> getWorkCommentByList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.worksCommentDao.selectWorkCommentByList(map);
	}

	@Override
	public void insertAndUpdateWorkComment(WorksComment workComment, Integer id,UserStu user) {
		// TODO Auto-generated method stub
		this.worksCommentDao.insertSelective(workComment);
		this.worksDao.updateWorkCommNumByPrimaryKey(id);
		if(user!=null) {
			this.userStuDao.updateNumberBySelective(user);
		}
	}

}

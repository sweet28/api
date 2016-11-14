package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.WorksCommentMapper;
import com.arttraining.api.service.IWorksCommentService;


@Service("worksCommentService")
public class WorksCommentService implements IWorksCommentService{
	@Resource
	private WorksCommentMapper worksCommentDao;
	
	@Override
	public List<CommentsVisitorBean> getWorkCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.worksCommentDao.selectWorkCommentByShow(fid, limit);
	}

}

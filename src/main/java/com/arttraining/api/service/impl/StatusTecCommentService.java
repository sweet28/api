package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.StatusesTecCommentMapper;
import com.arttraining.api.pojo.StatusesTecComment;
import com.arttraining.api.service.IStatusTecCommentService;

@Service("statusTecCommentService")
public class StatusTecCommentService implements IStatusTecCommentService{
	@Resource
	private StatusesTecCommentMapper statusTecCommentDao;
	
	@Override
	public List<StatusesTecComment> getStatusTecCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.statusTecCommentDao.selectStatusTecCommentByShow(fid, limit);
	}

	@Override
	public CommentsVisitorBean getVisitorOrHostInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.statusTecCommentDao.selectVisitorOrHostInfo(map);
	}
	
}

package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.StatusesCommentMapper;
import com.arttraining.api.service.IStatusCommentService;

@Service("statusCommentService")
public class StatusCommentService implements IStatusCommentService {
	@Resource
	private StatusesCommentMapper statusCommentDao;

	@Override
	public CommentsVisitorBean getVisitorOrHostInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.statusCommentDao.selectVisitorOrHostInfo(map);
	}

	@Override
	public List<CommentsVisitorBean> getStatusCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.statusCommentDao.selectStatusCommentByShow(fid, limit);
	}
	
}

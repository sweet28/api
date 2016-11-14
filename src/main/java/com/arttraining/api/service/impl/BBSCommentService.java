package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.BBSCommentMapper;
import com.arttraining.api.service.IBBSCommentService;

@Service("bbsCommentService")
public class BBSCommentService implements IBBSCommentService {
	@Resource
	private BBSCommentMapper bbsCommentDao;

	@Override
	public List<CommentsVisitorBean> getBBSCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.bbsCommentDao.selectBBSCommentByShow(fid, limit);
	}

}

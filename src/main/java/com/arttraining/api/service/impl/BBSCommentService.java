package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.BBSCommentMapper;
import com.arttraining.api.dao.BBSMapper;
import com.arttraining.api.pojo.BBSComment;
import com.arttraining.api.service.IBBSCommentService;

@Service("bbsCommentService")
public class BBSCommentService implements IBBSCommentService {
	@Resource
	private BBSCommentMapper bbsCommentDao;
	@Resource
	private BBSMapper bbsDao;

	@Override
	public List<CommentsVisitorBean> getBBSCommentByShow(Integer fid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.bbsCommentDao.selectBBSCommentByShow(fid, limit);
	}

	@Override
	public List<CommentsVisitorBean> getBBSCommentByList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.bbsCommentDao.selectBBSCommentByList(map);
	}

	@Override
	public void insertAndUpdateBBSComment(BBSComment bbsComment, Integer id) {
		// TODO Auto-generated method stub
		this.bbsCommentDao.insertSelective(bbsComment);
		this.bbsDao.updateBBSCommNumByPrimaryKey(id);
	}

}

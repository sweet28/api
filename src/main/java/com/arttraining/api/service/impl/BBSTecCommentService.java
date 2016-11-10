package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.dao.BBSTecCommentMapper;
import com.arttraining.api.pojo.BBSTecComment;
import com.arttraining.api.service.IBBSTecCommentService;

@Service("bbsTecCommentService")
public class BBSTecCommentService implements IBBSTecCommentService {
	@Resource
	private BBSTecCommentMapper bbsTecCommentDao;
	
	@Override
	public List<BBSTecComment> getBBSTecCommentByShow(Integer fid, Integer limit) {
		// TODO Auto-generated method stub
		return this.bbsTecCommentDao.selectBBSTecCommentByShow(fid, limit);
	}

	@Override
	public CommentsVisitorBean getVisitorOrHostInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.bbsTecCommentDao.selectVisitorOrHostInfo(map);
	}

}

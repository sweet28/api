package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.WorkCommentTecInfoBean;
import com.arttraining.api.bean.WorkTecCommentBean;
import com.arttraining.api.dao.WorksTecCommentMapper;
import com.arttraining.api.service.IWorksTecCommentService;

@Service("worksTecCommentService")
public class WorksTecCommentService implements IWorksTecCommentService {
	@Resource
	private WorksTecCommentMapper worksTecCommentDao;
	
	@Override
	public List<WorkCommentTecInfoBean> getUserInfoByWorkShow(Integer fid) {
		// TODO Auto-generated method stub
		return this.worksTecCommentDao.selectUserInfoByWorkShow(fid);
	}

	@Override
	public List<WorkTecCommentBean> getTecCommentByWorkShow(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.worksTecCommentDao.selectTecCommentByWorkShow(map);
	}

}

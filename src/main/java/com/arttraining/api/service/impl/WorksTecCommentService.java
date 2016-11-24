package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.WorkCommentTecInfoBean;
import com.arttraining.api.bean.WorkTecCommentBean;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.dao.WorksTecCommentMapper;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksTecComment;
import com.arttraining.api.service.IWorksTecCommentService;

@Service("worksTecCommentService")
public class WorksTecCommentService implements IWorksTecCommentService {
	@Resource
	private WorksTecCommentMapper worksTecCommentDao;
	@Resource
	private WorksMapper worksDao;
	
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

	@Override
	public void insertTecCommentAndUpdateNum(WorksTecComment comment, Works work) {
		// TODO Auto-generated method stub
		//先插入名师点评信息
		this.worksTecCommentDao.insertSelective(comment);
		//然后更新作品点评数
		this.worksDao.updateNumberBySelective(work);
	}

	@Override
	public int insertOneTecComment(WorksTecComment comment) {
		// TODO Auto-generated method stub
		return this.worksTecCommentDao.insertSelective(comment);
	}

	@Override
	public WorksTecComment getTecCommentByMaster(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.worksTecCommentDao.selectTecCommentByMaster(map);
	}

}

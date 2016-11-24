package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.AssessmentsMapper;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.service.IAssessmentsService;

@Service("AssessmentsService")
public class AssessmentsService implements IAssessmentsService{
	@Resource
	private AssessmentsMapper assDao;

	@Override
	public int insert(Assessments assessments) {
		// TODO Auto-generated method stub
		return this.assDao.insertSelective(assessments);
	}

	@Override
	public int update(Assessments assessments) {
		// TODO Auto-generated method stub
		return this.assDao.updateByPrimaryKeySelective(assessments);
	}

}

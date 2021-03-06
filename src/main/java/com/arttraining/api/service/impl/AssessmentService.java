package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.AssTecListBean;
import com.arttraining.api.bean.MasterAssessmentBean;
import com.arttraining.api.dao.AssessmentsMapper;
import com.arttraining.api.service.IAssessmentService;
@Service("assessmentService")
public class AssessmentService implements IAssessmentService {
	@Resource
	private AssessmentsMapper assessmentDao;

	@Override
	public List<MasterAssessmentBean> getAssessmentNoListByMaster(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.assessmentDao.selectAssessmentNoListByMaster(map);
	}

	@Override
	public List<MasterAssessmentBean> getAssessmentYesListByMaster(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.assessmentDao.selectAssessmentYesListByMaster(map);
	}

	@Override
	public int getAssNumByMaster(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.assessmentDao.selectAssNumByMaster(map);
	}

	@Override
	public int getAssStatusByOrderId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.assessmentDao.selectAssStatusByOrderId(map);
	}

	@Override
	public int getAssIdByMaster(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.assessmentDao.selectAssIdByMaster(map);
	}

	@Override
	public List<AssTecListBean> getAssTecListByOrderId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.assessmentDao.selectAssTecListByOrderId(map);
	}

	@Override
	public int updateOneWorkAttrByOrderId(Integer order_id) {
		// TODO Auto-generated method stub
		return this.assessmentDao.updateWorkAttrByOrderId(order_id);
	}

}

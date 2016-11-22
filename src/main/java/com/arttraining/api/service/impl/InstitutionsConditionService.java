package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.InstitutionConditionBean;
import com.arttraining.api.dao.InstitutionsConditionsMapper;
import com.arttraining.api.service.IInstitutionsConditionService;

@Service("institutionsConditionService")
public class InstitutionsConditionService implements IInstitutionsConditionService {
	@Resource
	private InstitutionsConditionsMapper institutionConditionDao;
	@Override
	public List<InstitutionConditionBean> getConditionList() {
		// TODO Auto-generated method stub
		return this.institutionConditionDao.selectConditionList();
	}

}

package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.InstitutionsListBean;
import com.arttraining.api.bean.InstitutionsShowBean;
import com.arttraining.api.dao.InstitutionsMapper;
import com.arttraining.api.service.IInstitutionService;

@Service("institutionService")
public class InstitutionService implements IInstitutionService {
	@Resource
	private InstitutionsMapper institutionDao;
	
	@Override
	public List<InstitutionsListBean> getInstitutionsList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.institutionDao.selectInstitutionsList(map);
	}

	@Override
	public InstitutionsShowBean getInstitutionsShow(Integer id) {
		// TODO Auto-generated method stub
		return this.institutionDao.selectInstitutionsShow(id);
	}

}

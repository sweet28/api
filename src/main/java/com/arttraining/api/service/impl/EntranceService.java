package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv3.EntranceAdmissionBean;
import com.arttraining.api.beanv3.EntranceCategoryBean;
import com.arttraining.api.beanv3.EntranceCollegeBean;
import com.arttraining.api.beanv3.EntranceLineBean;
import com.arttraining.api.beanv3.EntranceProvinceBean;
import com.arttraining.api.dao.AdmissionScoreMapper;
import com.arttraining.api.dao.EntranceAnalyseMapper;
import com.arttraining.api.dao.EntranceCategoryMapper;
import com.arttraining.api.dao.EntranceProvinceMapper;
import com.arttraining.api.dao.QualificationLineMapper;
import com.arttraining.api.service.IEntranceService;

@Service("entranceService")
public class EntranceService implements IEntranceService {
	@Resource
	private AdmissionScoreMapper batchDao;
	@Resource
	private QualificationLineMapper lineDao;
	@Resource
	private EntranceProvinceMapper provinceDao;
	@Resource
	private EntranceCategoryMapper categoryDao;
	@Resource
	private EntranceAnalyseMapper collegeDao;
	
	@Override
	public List<EntranceAdmissionBean> getBatchScoreList(String province) {
		// TODO Auto-generated method stub
		return this.batchDao.selectBatchScoreList(province);
	}

	@Override
	public List<EntranceLineBean> getLineList(String province) {
		// TODO Auto-generated method stub
		return this.lineDao.selectLineList(province);
	}

	@Override
	public List<EntranceProvinceBean> getProvinceList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.provinceDao.selectProvinceList(map);
	}

	@Override
	public List<EntranceCategoryBean> getCategoryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.categoryDao.selectCategoryList(map);
	}

	@Override
	public List<EntranceCollegeBean> getCollegeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.collegeDao.selectCollegeList(map);
	}

}

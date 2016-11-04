package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.dao.InformationMapper;
import com.arttraining.api.service.IInformationService;

@Service("informationService")
public class InformationService implements IInformationService {
	@Resource
	private InformationMapper informationDao;

	@Override
	public List<InformationListBean> getInformationList() {
		// TODO Auto-generated method stub
		return this.informationDao.selectInformationList();
	}

	@Override
	public InformationShowBean getOneInformation(Integer id) {
		// TODO Auto-generated method stub
		return this.informationDao.selectOneInformation(id);
	}

}

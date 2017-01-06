package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.bean.MasterInfoListBean;
import com.arttraining.api.dao.InformationMapper;
import com.arttraining.api.pojo.Information;
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

	@Override
	public List<MasterInfoListBean> getInfoListByHomePage() {
		// TODO Auto-generated method stub
		return this.informationDao.selectInfoListByHomePage();
	}

	@Override
	public List<InformationListBean> getInformationListByType(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.informationDao.selectInformationListByType(map);
	}

	@Override
	public int updateOneInformation(Information info) {
		// TODO Auto-generated method stub
		return this.informationDao.updateByPrimaryKeySelective(info);
	}

}

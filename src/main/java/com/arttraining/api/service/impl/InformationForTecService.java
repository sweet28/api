package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.MasterInfoListBean;
import com.arttraining.api.bean.MasterInfoShowBean;
import com.arttraining.api.dao.InformationForTecMapper;
import com.arttraining.api.service.IInformationForTecService;

@Service("informationForTecService")
public class InformationForTecService implements IInformationForTecService {
	@Resource
	private InformationForTecMapper informationForTecDao;
	@Override
	public List<MasterInfoListBean> getInfoListByMaster(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.informationForTecDao.selectInfoListByMaster(map);
	}
	@Override
	public MasterInfoShowBean getInfoShowByMaster(Integer id) {
		// TODO Auto-generated method stub
		return this.informationForTecDao.selectInfoShowByMaster(id);
	}

}

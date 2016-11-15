package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.MajorListBean;
import com.arttraining.api.dao.MajorMapper;
import com.arttraining.api.service.IMajorService;

@Service("majorService")
public class MajorService implements IMajorService {
	@Resource
	private MajorMapper majorDao;
	
	@Override
	public List<MajorListBean> getOneLevelMajorByList() {
		// TODO Auto-generated method stub
		return this.majorDao.selectOneLevelMajorByList();
	}

}

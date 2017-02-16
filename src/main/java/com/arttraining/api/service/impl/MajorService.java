package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.MajorLevelListBean;
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

	@Override
	public MajorLevelListBean getMajorNodeById(Integer id) {
		// TODO Auto-generated method stub
		return this.majorDao.selectMajorNodeById(id);
	}

	@Override
	public List<MajorLevelListBean> getMajorNodeByFid(Integer father_id) {
		// TODO Auto-generated method stub
		return this.majorDao.selectMajorNodeByFid(father_id);
	}

	@Override
	public List<Integer> getAllOneLevelMajor() {
		// TODO Auto-generated method stub
		return this.majorDao.selectAllOneLevelMajor();
	}

	@Override
	public List<MajorListBean> getTwoLevelMajorByList(Integer father_id) {
		// TODO Auto-generated method stub
		return this.majorDao.selectTwoLevelMajorByList(father_id);
	}

}

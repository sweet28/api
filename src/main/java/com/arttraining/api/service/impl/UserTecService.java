package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.dao.UserTechMapper;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.IUserTecService;

@Service("userTecService")
public class UserTecService implements IUserTecService {
	@Resource
	private UserTechMapper userTecDao;
	
	@Override
	public UserTech selectOneUserTecById(Integer id) {
		// TODO Auto-generated method stub
		return this.userTecDao.selectByPrimaryKey(id);
	}

	@Override
	public List<TecherListBean> getTecherListBySelective(String spec,String city, String college, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.userTecDao.selectTecherListBySelective(spec, city, college, offset, limit);
	}

	@Override
	public List<TecherListBean> getTecherListIndexBySelective(Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.userTecDao.selectTecherListIndexBySelective(offset,limit);
	}

	@Override
	public int countTecherNumer() {
		// TODO Auto-generated method stub
		return this.userTecDao.selectTecherNumer();
	}

}

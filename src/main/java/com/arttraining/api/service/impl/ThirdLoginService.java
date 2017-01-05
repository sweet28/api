package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.ThirdLoginMapper;
import com.arttraining.api.pojo.ThirdLogin;
import com.arttraining.api.service.IThirdLoginService;

@Service("thirdLoginService")
public class ThirdLoginService implements IThirdLoginService {
	@Resource
	private ThirdLoginMapper thirdDao;

	@Override
	public ThirdLogin getLoginInfoByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.thirdDao.selectLoginInfoByUid(map);
	}

	@Override
	public int insertOneThirdInfo(ThirdLogin third) {
		// TODO Auto-generated method stub
		return this.thirdDao.insertSelective(third);
	}

}

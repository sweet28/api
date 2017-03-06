package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.AppVersionMapper;
import com.arttraining.api.pojo.AppVersion;
import com.arttraining.api.service.IAppVersionService;

@Service("appVersionService")
public class AppVersionService implements IAppVersionService {
	@Resource
	private AppVersionMapper appVersionDao;

	@Override
	public AppVersion getOneVersionInfo(String app_type) {
		// TODO Auto-generated method stub
		return this.appVersionDao.selectOneVersionInfo(app_type);
	}

}

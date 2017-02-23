package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.AppExceptionMapper;
import com.arttraining.api.pojo.AppException;
import com.arttraining.api.service.IExceptionService;

@Service("exceptionService")
public class ExceptionService implements IExceptionService {
	@Resource
	private AppExceptionMapper exceptionDao;

	@Override
	public int insertExceptionInfo(AppException exception) {
		// TODO Auto-generated method stub
		return this.exceptionDao.insertSelective(exception);
	}

}

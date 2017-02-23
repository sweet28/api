package com.arttraining.api.service;

import com.arttraining.api.pojo.AppException;

public interface IExceptionService {
	//新增异常信息
	public int insertExceptionInfo(AppException exception);
}

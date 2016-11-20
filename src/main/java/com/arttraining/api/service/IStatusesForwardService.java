package com.arttraining.api.service;

import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesAttachment;
import com.arttraining.api.pojo.StatusesForward;

public interface IStatusesForwardService {
	//转发一条动态信息时 执行的方法
	void insertOneStatusForward(StatusesForward statusForward,Statuses status,StatusesAttachment statusAttr);
}

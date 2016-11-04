package com.arttraining.api.service;

import com.arttraining.api.pojo.Statuses;

public interface IStatusesService {
	//依据传递的id获取相应的作品信息
	Statuses getStatusesById(Integer id);
}

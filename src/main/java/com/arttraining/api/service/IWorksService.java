package com.arttraining.api.service;

import com.arttraining.api.pojo.Works;

public interface IWorksService {
	//依据传递的id获取相应的作品信息
	Works getWorksById(Integer id);

}

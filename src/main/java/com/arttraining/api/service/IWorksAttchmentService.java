package com.arttraining.api.service;

import com.arttraining.api.pojo.WorksAttchment;

public interface IWorksAttchmentService {
	//根据用户ID获取名师测评详情 --assessments/master/show接口调用
    WorksAttchment getOneAttByWorkId(Integer id);
}

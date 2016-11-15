package com.arttraining.api.service;

import com.arttraining.api.pojo.Recommend;

public interface IRecommendService {
	//发送建议反馈时 插入建议反馈表--recommend/create接口调用
	int insertOneRecommend(Recommend record);
}

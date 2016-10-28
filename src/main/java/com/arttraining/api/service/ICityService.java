package com.arttraining.api.service;

public interface ICityService {
	//依据城市ID来查询对应的城市地域名称
	public String getNameById(Integer cityId);
}

package com.arttraining.api.service;

public interface IIdentityService {
	//依据用户身份ID来查询相应的身份名称
	public String getNameById(Integer identityId);
}

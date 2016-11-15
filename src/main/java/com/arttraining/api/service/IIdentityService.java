package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.IdentityListBean;

public interface IIdentityService {
	 //获取身份列表--identity/list接口调用
    List<IdentityListBean> getIdentityList();
}

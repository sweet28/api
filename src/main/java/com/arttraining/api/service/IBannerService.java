package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.BannerListBean;
import com.arttraining.api.bean.BannerShowBean;

public interface IBannerService {
	//查询轮播信息列表
	List<BannerListBean> getBannerList();
	//依据广告ID查询轮播详情
	BannerShowBean getOneBanner(Integer id);
}

package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.BannerListBean;
import com.arttraining.api.bean.BannerShowBean;
import com.arttraining.api.dao.BannerMapper;
import com.arttraining.api.service.IBannerService;

@Service("bannerService")
public class BannerService implements IBannerService {
	@Resource
	private BannerMapper bannerDao;
	
	@Override
	public List<BannerListBean> getBannerList() {
		// TODO Auto-generated method stub
		return this.bannerDao.selectBannerList();
	}

	@Override
	public BannerShowBean getOneBanner(Integer id) {
		// TODO Auto-generated method stub
		return this.bannerDao.selectOneBanner(id);
	}

}

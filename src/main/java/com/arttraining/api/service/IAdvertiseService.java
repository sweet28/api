package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.AdvertiseListBean;
import com.arttraining.api.bean.AdvertiseShowBean;
import com.arttraining.api.bean.HomePageAdvertiseBean;

public interface IAdvertiseService {
	//获取广告列表详情 --advertising/list
    List<AdvertiseListBean> getAdList();
    //依据广告ID查询相关的广告详情 --advertising/show
    AdvertiseShowBean getAdShowByPrimaryKey(Integer id);
    //获取首页最新的一条广告信息--statuses/public_timeline/bbs接口调用
    HomePageAdvertiseBean getOneAdByHomepage();
}

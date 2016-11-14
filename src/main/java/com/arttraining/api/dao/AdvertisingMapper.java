package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.AdvertiseListBean;
import com.arttraining.api.bean.AdvertiseShowBean;
import com.arttraining.api.bean.HomePageAdvertiseBean;
import com.arttraining.api.pojo.Advertising;

public interface AdvertisingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertising record);

    int insertSelective(Advertising record);

    Advertising selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertising record);

    int updateByPrimaryKey(Advertising record);
    
    //获取广告列表详情 --advertising/list接口调用
    List<AdvertiseListBean> selectAdList();
    //依据广告ID查询相关的广告详情 --advertising/show接口调用
    AdvertiseShowBean selectAdShowByPrimaryKey(Integer id);
    //获取首页最新的一条广告信息 --statuses/public_timeline/bbs接口调用
    HomePageAdvertiseBean selectOneAdByHomepage();
}
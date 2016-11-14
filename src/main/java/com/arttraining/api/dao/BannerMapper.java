package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.BannerListBean;
import com.arttraining.api.bean.BannerShowBean;
import com.arttraining.api.pojo.Banner;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);
    
    //获取轮播信息列表--banner/list接口调用
    List<BannerListBean> selectBannerList();
    //依据广告ID查询某一个轮播信息--banner/show接口调用
    BannerShowBean selectOneBanner(Integer id);
}
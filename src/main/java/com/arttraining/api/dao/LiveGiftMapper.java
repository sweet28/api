package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.beanv2.LiveGiftListBean;
import com.arttraining.api.pojo.LiveGift;

public interface LiveGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveGift record);

    int insertSelective(LiveGift record);

    LiveGift selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveGift record);

    int updateByPrimaryKey(LiveGift record);
    
    //coffee add 0208 获取直播间的礼物列表
    List<LiveGiftListBean> selectLiveGiftList();
    //coffee add 0208 依据礼物ID来获取礼物图片
    String selectGiftPicById(Integer id);
    //coffee add 0209 依据礼物ID来获取礼物信息
    LiveGift selectGiftInfoById(Integer id);
}
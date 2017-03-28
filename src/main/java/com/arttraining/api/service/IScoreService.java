package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.ScoreDetailBean;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.Score;

public interface IScoreService {
	// coffee add 0228 依据用户ID和类型来查询积分信息
	Score getScoreInfoByUid(Integer uid,String utype);
	//coffee add 0301 依据用户ID和类型查询积分消费详情列表信息
    List<ScoreDetailBean> getScoreListByUid(Map<String, Object> map);
    
    //coffee add 0209 依据礼物ID来获取礼物信息
    LiveGift getGiftInfoById(Integer id);
    
    //coffee add 0301 登录记录用户的积分信息
    int recordUserScoreInfoByLogin(Integer uid,String utype);
    //coffee add 0301 注册记录用户的积分信息
    int recordUserScoreInfoByRegister(Integer uid,String utype);
    
    //coffee add 0301 直播礼物消费积分
   	int updateScoreAndDetailInfoByUid(Integer uid,String utype,Integer score,Integer gift_score);
}

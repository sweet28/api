package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.pojo.LiveLike;

public interface LiveLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveLike record);

    int insertSelective(LiveLike record);

    LiveLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveLike record);

    int updateByPrimaryKey(LiveLike record);
    
    //coffee add 0118 判断登录用户是否重复进入直播间进行点赞
    LiveLike selectIsExistLike(Map<String, Object> map);
}
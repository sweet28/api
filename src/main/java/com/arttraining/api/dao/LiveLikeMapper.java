package com.arttraining.api.dao;

import com.arttraining.api.pojo.LiveLike;

public interface LiveLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveLike record);

    int insertSelective(LiveLike record);

    LiveLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveLike record);

    int updateByPrimaryKey(LiveLike record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesLike;

public interface StatusesLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesLike record);

    int insertSelective(StatusesLike record);

    StatusesLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesLike record);

    int updateByPrimaryKey(StatusesLike record);
}
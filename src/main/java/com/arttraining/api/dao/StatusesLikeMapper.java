package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.StatusesLike;

public interface StatusesLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesLike record);

    int insertSelective(StatusesLike record);

    StatusesLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesLike record);

    int updateByPrimaryKey(StatusesLike record);
    //判断用户uid是否重复对小组动态进行点赞
    StatusesLike selectSelectiveByUidAndFid(@Param("fid") Integer fid,@Param("uid") Integer uid);
}
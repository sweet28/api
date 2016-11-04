package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.BBSLike;

public interface BBSLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSLike record);

    int insertSelective(BBSLike record);

    BBSLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSLike record);

    int updateByPrimaryKey(BBSLike record);
    //判断用户uid是否重复对帖子进行点赞
    BBSLike selectSelectiveByUidAndFid(@Param("fid") Integer fid,@Param("uid") Integer uid);
}
package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.WorksLike;

public interface WorksLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksLike record);

    int insertSelective(WorksLike record);

    WorksLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksLike record);

    int updateByPrimaryKey(WorksLike record);
    //判断用户uid是否对测评作品进行点赞过
    WorksLike selectSelectiveByUidAndFid(@Param("fid") Integer fid,@Param("uid") Integer uid);
}
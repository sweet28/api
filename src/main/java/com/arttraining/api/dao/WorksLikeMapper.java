package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksLike;

public interface WorksLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksLike record);

    int insertSelective(WorksLike record);

    WorksLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksLike record);

    int updateByPrimaryKey(WorksLike record);
}
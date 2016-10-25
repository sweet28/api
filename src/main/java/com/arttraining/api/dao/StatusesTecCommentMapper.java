package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesTecComment;

public interface StatusesTecCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesTecComment record);

    int insertSelective(StatusesTecComment record);

    StatusesTecComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesTecComment record);

    int updateByPrimaryKey(StatusesTecComment record);
}
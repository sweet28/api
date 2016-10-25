package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesComment;

public interface StatusesCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesComment record);

    int insertSelective(StatusesComment record);

    StatusesComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesComment record);

    int updateByPrimaryKey(StatusesComment record);
}
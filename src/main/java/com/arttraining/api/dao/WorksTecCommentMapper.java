package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksTecComment;

public interface WorksTecCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksTecComment record);

    int insertSelective(WorksTecComment record);

    WorksTecComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksTecComment record);

    int updateByPrimaryKey(WorksTecComment record);
}
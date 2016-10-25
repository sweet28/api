package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksComment;

public interface WorksCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksComment record);

    int insertSelective(WorksComment record);

    WorksComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksComment record);

    int updateByPrimaryKey(WorksComment record);
}
package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSTecComment;

public interface BBSTecCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSTecComment record);

    int insertSelective(BBSTecComment record);

    BBSTecComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSTecComment record);

    int updateByPrimaryKey(BBSTecComment record);
}
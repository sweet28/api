package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSComment;

public interface BBSCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSComment record);

    int insertSelective(BBSComment record);

    BBSComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSComment record);

    int updateByPrimaryKey(BBSComment record);
}
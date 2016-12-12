package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.pojo.CommentRead;

public interface CommentReadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentRead record);

    int insertSelective(CommentRead record);

    CommentRead selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentRead record);

    int updateByPrimaryKey(CommentRead record);
    //判断是否重复对老师点评信息进行收听/看--teccomment/read接口调用
    CommentRead selectCommentReadByComId(Map<String, Object> map);
}
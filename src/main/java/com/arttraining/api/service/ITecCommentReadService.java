package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.pojo.CommentRead;
import com.arttraining.api.pojo.WorksTecComment;

public interface ITecCommentReadService {
	//修改老师点评的收听/看数量   新增收听/看记录
	void insertReadAndTecComment(CommentRead read,WorksTecComment comment);
	//判断是否重复对老师点评信息进行收听/看--teccomment/read接口调用
    CommentRead getCommentReadByComId(Map<String, Object> map);
}

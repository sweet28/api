package com.arttraining.api.service;

import java.util.List;


import java.util.Map;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.WorksComment;

public interface IWorksCommentService {
	//查询作品相关的评论回复列表信息--statuses/show/work接口调用
    List<CommentsVisitorBean> getWorkCommentByShow(Integer fid,Integer limit);
    //查询帖子相关的评论回复列表信息--comment/list/work接口调用
    List<CommentsVisitorBean> getWorkCommentByList(Map<String, Object> map);
    //评论作品同时更新评论数--comment/create/work接口调用
    void insertAndUpdateWorkComment(WorksComment workComment, Integer id);
}

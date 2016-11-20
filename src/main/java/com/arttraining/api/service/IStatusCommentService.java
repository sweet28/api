package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.StatusesComment;
import com.arttraining.api.pojo.UserStu;

public interface IStatusCommentService {
	//查询小组动态相关的名师点评评论回复列表信息--statuses/show/g_stus接口调用
    List<CommentsVisitorBean> getStatusCommentByShow(Integer fid,Integer limit);
    //查询小组动态相关的评论回复列表信息--comment/list/g_stus接口调用
    List<CommentsVisitorBean> getStatusCommentByList(Map<String, Object> map);
    //发布小组动态评论 同时更新评论数
    void insertAndUpdateStatusComment(StatusesComment statusComment,Integer id, UserStu user);
}

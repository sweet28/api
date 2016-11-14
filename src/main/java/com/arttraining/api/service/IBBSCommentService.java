package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.BBSComment;

public interface IBBSCommentService {
	 //查询帖子相关的评论回复列表信息--statuses/show/bbs接口调用
    List<CommentsVisitorBean> getBBSCommentByShow(Integer fid, Integer limit);
    //查询帖子相关的评论回复列表信息--comment/list/bbs接口调用
    List<CommentsVisitorBean> getBBSCommentByList(Map<String, Object> map);
    
    //评论帖子的同时更新帖子的评论数
    void insertAndUpdateBBSComment(BBSComment bbsComment,Integer id);
}

package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.CommentsVisitorBean;

public interface IBBSCommentService {
	 //查询帖子相关的评论回复列表信息--statuses/show/bbs接口调用
    List<CommentsVisitorBean> getBBSCommentByShow(Integer fid, Integer limit);
}

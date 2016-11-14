package com.arttraining.api.service;

import java.util.List;


import com.arttraining.api.bean.CommentsVisitorBean;

public interface IWorksCommentService {
	  //查询帖子相关的评论回复列表信息--statuses/show/bbs接口调用
    List<CommentsVisitorBean> getWorkCommentByShow(Integer fid,Integer limit);
}

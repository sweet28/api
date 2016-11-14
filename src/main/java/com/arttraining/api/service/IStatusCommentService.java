package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CommentsVisitorBean;

public interface IStatusCommentService {
	 //查询小组动态相关的名师点评评论回复列表信息--statuses/show/g_stus接口调用
    List<CommentsVisitorBean> getStatusCommentByShow(Integer fid,Integer limit);
    //依据帖子查询某一条评论用户和回复信息
    CommentsVisitorBean getVisitorOrHostInfo(Map<String,Object> map);
}

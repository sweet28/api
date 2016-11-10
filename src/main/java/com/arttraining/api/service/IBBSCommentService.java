package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.BBSComment;

public interface IBBSCommentService {
	 //查询帖子相关的评论回复列表信息
    List<BBSComment> getBBSCommentByShow(Integer fid, Integer limit);
    //依据帖子查询某一条评论用户和回复信息
    CommentsVisitorBean getVisitorOrHostInfo(Map<String,Object> map);
}

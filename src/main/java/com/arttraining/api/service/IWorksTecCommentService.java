package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.WorkCommentTecInfoBean;
import com.arttraining.api.bean.WorkTecCommentBean;

public interface IWorksTecCommentService {
	 //查询名师点评作品的信息--- statuses/show/work接口调用
    List<WorkCommentTecInfoBean> getUserInfoByWorkShow(Integer fid);
    //查询评论信息 默认显示第一条(按照评论时间升序排序) statuses/show/work接口调用
    List<WorkTecCommentBean> getTecCommentByWorkShow(Map<String, Object> map);
}

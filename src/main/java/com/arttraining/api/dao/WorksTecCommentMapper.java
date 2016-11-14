package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.WorkCommentTecInfoBean;
import com.arttraining.api.bean.WorkTecCommentBean;
import com.arttraining.api.pojo.WorksTecComment;

public interface WorksTecCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksTecComment record);

    int insertSelective(WorksTecComment record);

    WorksTecComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksTecComment record);

    int updateByPrimaryKey(WorksTecComment record);
    //查询名师点评作品的信息--- statuses/show/work接口调用
    List<WorkCommentTecInfoBean> selectUserInfoByWorkShow(Integer fid);
    //查询评论信息 默认显示第一条(按照评论时间升序排序) statuses/show/work接口调用
    List<WorkTecCommentBean> selectTecCommentByWorkShow(Map<String, Object> map);
}
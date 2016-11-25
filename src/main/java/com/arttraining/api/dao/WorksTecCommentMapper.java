package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterCommentListBean;
import com.arttraining.api.bean.MasterCommentReBean;
import com.arttraining.api.bean.MasterCommentUserBean;
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
    
    //判断名师是否重复对作品进行点评--tech_comments/create reply接口调用
    WorksTecComment selectTecCommentByMaster(Map<String, Object> map);
    //根据名师ID 作品ID来获取作品和用户详情--assessments/master/show接口调用
    MasterCommentReBean selectWorkByMasterShow(Integer id);
    //根据名师ID 作品ID来获取作品和用户详情--assessments/master/show接口调用
    List<MasterCommentListBean> selectCommentListByMasterShow(Map<String, Object> map);
    //根据名师ID 作品ID来获取作品和用户详情--assessments/master/show接口调用
    MasterCommentUserBean selectCommentUserByMasterShow(Map<String, Object> map);
}
package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterCommentListBean;
import com.arttraining.api.bean.MasterCommentReBean;
import com.arttraining.api.bean.MasterCommentUserBean;
import com.arttraining.api.bean.WorkCommentTecInfoBean;
import com.arttraining.api.bean.WorkTecCommentBean;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksTecComment;

public interface IWorksTecCommentService {
	 //查询名师点评作品的信息--- statuses/show/work接口调用
    List<WorkCommentTecInfoBean> getUserInfoByWorkShow(Integer fid);
    //查询评论信息 默认显示第一条(按照评论时间升序排序) statuses/show/work接口调用
    List<WorkTecCommentBean> getTecCommentByWorkShow(Map<String, Object> map);
    
    //名师点评作品的同时 更新作品的点评数(按照点评名师数来统计点评数)--tech_comments/create reply接口调用
    void insertTecCommentAndUpdateNum(WorksTecComment comment, Works work, Assessments ass);
    //名师回复作品评论信息时执行的方法--tech_comments/reply接口调用
    int insertOneTecComment(WorksTecComment comment);
    //判断名师是否重复对作品进行点评--tech_comments/create reply接口调用
    WorksTecComment getTecCommentByMaster(Map<String, Object> map);
    //根据名师ID 作品ID来获取作品和用户详情--assessments/master/show接口调用
    MasterCommentReBean getOneWorkByMasterShow(Integer id);
    //根据名师ID 作品ID来获取作品和用户详情--assessments/master/show接口调用
    List<MasterCommentListBean> getCommentListByMasterShow(Map<String, Object> map);
    //根据名师ID 作品ID来获取作品和用户详情--assessments/master/show接口调用
    MasterCommentUserBean getCommentUserByMasterShow(Map<String, Object> map);
}

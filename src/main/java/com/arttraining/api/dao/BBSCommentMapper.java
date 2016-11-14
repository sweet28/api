package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.BBSComment;

public interface BBSCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSComment record);

    int insertSelective(BBSComment record);

    BBSComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSComment record);

    int updateByPrimaryKey(BBSComment record);
    
    //查询帖子相关的评论回复列表信息--statuses/show/bbs接口调用
    List<CommentsVisitorBean> selectBBSCommentByShow(@Param("fid") Integer fid,
    		@Param("limit") Integer limit);
    
   //查询帖子相关的评论回复列表信息--comment/list/bbs接口调用
    List<CommentsVisitorBean> selectBBSCommentByList(Map<String, Object> map);
}
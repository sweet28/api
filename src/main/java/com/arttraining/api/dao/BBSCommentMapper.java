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
    
    //查询帖子相关的评论回复列表信息
    List<BBSComment> selectBBSCommentByShow(@Param("fid") Integer fid,
    		@Param("limit") Integer limit);
    //依据帖子查询某一条评论用户和回复信息
    //List<Object> selectVisitorOrHostInfo(Map<String,Object> map);
    CommentsVisitorBean selectVisitorOrHostInfo(Map<String,Object> map);
    
}
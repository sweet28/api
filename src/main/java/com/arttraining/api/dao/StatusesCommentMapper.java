package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.StatusesComment;

public interface StatusesCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesComment record);

    int insertSelective(StatusesComment record);

    StatusesComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesComment record);

    int updateByPrimaryKey(StatusesComment record);
    //查询小组动态相关的名师点评评论回复列表信息
    List<StatusesComment> selectStatusCommentByShow(@Param("fid") Integer fid,
    		@Param("limit") Integer limit);
    //依据帖子查询某一条评论用户和回复信息
    CommentsVisitorBean selectVisitorOrHostInfo(Map<String,Object> map);
}
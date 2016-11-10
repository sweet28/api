package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.StatusesTecComment;

public interface StatusesTecCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesTecComment record);

    int insertSelective(StatusesTecComment record);

    StatusesTecComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesTecComment record);

    int updateByPrimaryKey(StatusesTecComment record);
    
    //查询动态相关的名师点评评论回复列表信息
    List<StatusesTecComment> selectStatusTecCommentByShow(@Param("fid") Integer fid,
    		@Param("limit") Integer limit);
    //依据动态查询某一条评论用户和回复信息
    CommentsVisitorBean selectVisitorOrHostInfo(Map<String,Object> map);
}
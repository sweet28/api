package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.BBSTecComment;

public interface BBSTecCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSTecComment record);

    int insertSelective(BBSTecComment record);

    BBSTecComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSTecComment record);

    int updateByPrimaryKey(BBSTecComment record);
    
    //查询帖子相关的名师点评评论回复列表信息
    List<BBSTecComment> selectBBSTecCommentByShow(@Param("fid") Integer fid,
    		@Param("limit") Integer limit);
    //依据帖子查询某一条评论用户和回复信息
    CommentsVisitorBean selectVisitorOrHostInfo(Map<String,Object> map);
}
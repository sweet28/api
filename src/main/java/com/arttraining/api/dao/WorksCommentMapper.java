package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.CommentsVisitorBean;
import com.arttraining.api.pojo.WorksComment;

public interface WorksCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksComment record);

    int insertSelective(WorksComment record);

    WorksComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksComment record);

    int updateByPrimaryKey(WorksComment record);
    
    //查询帖子相关的评论回复列表信息--statuses/show/bbs接口调用
    List<CommentsVisitorBean> selectWorkCommentByShow(@Param("fid") Integer fid,
    		@Param("limit") Integer limit);
}
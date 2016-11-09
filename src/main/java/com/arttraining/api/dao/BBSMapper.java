package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.BBS;

public interface BBSMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBS record);

    int insertSelective(BBS record);

    BBS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBS record);

    int updateByPrimaryKey(BBS record);
    
    //点赞帖子时 更新帖子表的点赞数量
    int updateBBSLikeNumByPrimaryKey(Integer id);
    //发布帖子
    int insertOneBBSSelective(BBS record);
    //获取首页帖子列表信息 默认显示10条记录
    List<HomePageStatusesBean> selectBBSListByHomepage(Integer limit);
    //是否点评或点赞信息
    /*HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(@Param("u_id")Integer u_id,
    		@Param("s_id")Integer s_id);*/
    HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(Map<String,Object> map);
    
    //获取指定用户发布的帖子列表信息 默认显示10条
    List<HomePageStatusesBean> selectBBSListByUid(@Param("uid")Integer uid,
    		@Param("limit")Integer limit);
}
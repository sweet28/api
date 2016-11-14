package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.bean.StatusesShowBean;
import com.arttraining.api.pojo.Statuses;

public interface StatusesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Statuses record);

    int insertSelective(Statuses record);

    Statuses selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Statuses record);

    int updateByPrimaryKey(Statuses record);
    
    //点赞帖子时 更新帖子表的点赞数量--like/create/g_stus接口调用
    int updateStatusLikeNumByPrimaryKey(Integer id);
    //发布小组动态时执行的方法
    int insertOneStatusSelective(Statuses record);
    
    //获取小组动态列表信息 默认显示10条记录--statuses/public_timeline/g_stus接口调用
    List<HomePageStatusesBean> selectStatusesListByGid(@Param("gid")Integer gid,
    		@Param("offset") Integer offset, @Param("limit") Integer limit);
    //是否点评或点赞信息----statuses/public_timeline/g_stus接口调用
    HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(Map<String,Object> map);
    
    //获取指定用户在某个小组发布的动态列表信息 默认显示10条--statuses/user_timeline/g_stus接口调用
    List<HomePageStatusesBean> selectStatusesListByUidAndGid(@Param("uid")Integer uid,
    		@Param("gid")Integer gid, 
    		@Param("offset") Integer offset, @Param("limit")Integer limit);
    
    //显示指定某一个用户发布的小组动态信息--statuses/show/g_stus接口调用
    StatusesShowBean selectOneStatusByid(Integer id);
    
    //获取指定小组下的成员列表所发的动态信息--group/show接口调用
    List<HomePageStatusesBean> selectGroupStatusesByGid(@Param("gid")Integer gid, 
    		@Param("limit")Integer limit);
    
    //判断是否对其进行点赞  --group/show接口调用
    HomeLikeOrCommentBean selectIsLikeOrAtt(Map<String,Object> map);
    
    //发布小组动态评论时更新评论数 comments/create/g_stus接口调用
    int updateStatusCommNumByPrimaryKey(Integer id);
}
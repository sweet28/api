package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.bean.StatusesShowBean;
import com.arttraining.api.pojo.BBS;

public interface BBSMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBS record);

    int insertSelective(BBS record);

    BBS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBS record);

    int updateByPrimaryKey(BBS record);
    
    //点赞帖子时 更新帖子表的点赞数量--like/create/bbs接口调用
    int updateBBSLikeNumByPrimaryKey(Integer id);
    //发布帖子--statuses/publish show/bbs接口调用
    int insertOneBBSSelective(BBS record);
    //获取首页帖子列表信息 默认显示10条记录
    List<HomePageStatusesBean> selectBBSListByHomepage(@Param("offset") Integer offset,
    		@Param("limit") Integer limit);
    //是否点评或点赞信息--statuses/public_timeline/bbs接口调用
    HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(Map<String,Object> map);
    
    //获取指定用户发布的帖子列表信息 默认显示10条--statuses/user_timeline/bbs接口调用
    List<HomePageStatusesBean> selectBBSListByUid(@Param("uid")Integer uid,
    		@Param("offset")Integer offset,
    		@Param("limit")Integer limit);
    
    //显示指定某一个用户发布的帖子信息--statuses/show/bbs接口调用
    StatusesShowBean selectOneBBSByid(Integer id);
    //发布评论时更新评论数 comments/create/bbs接口调用
    int updateBBSCommNumByPrimaryKey(Integer id);
    //更新帖子相关数量
    int updateNumberBySelective(BBS record);
    
    //查看我评论过的帖子列表信息--statuses/show_my/bbs接口调用
    List<HomePageStatusesBean> selectBBSListByMyComment(@Param("uid")Integer uid,
    		@Param("offset")Integer offset,
    		@Param("limit")Integer limit);
    
    //coffee add 0104 依据帖子ID来判断是否发送帖子附件
    String selectBBSAttrInfoById(Integer id);  
}
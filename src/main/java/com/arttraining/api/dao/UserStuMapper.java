package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.LikeUserBean;
import com.arttraining.api.bean.LikeUserPicBean;
import com.arttraining.api.bean.UserNumberBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.UserStu;

public interface UserStuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserStu record);

    int insertSelective(UserStu record);

    UserStu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStu record);

    int updateByPrimaryKey(UserStu record);
    
    //todo:根据账户获取用户信息
    UserStu selectUserByAccount(String account);
    
    //依据uid查询相应的用户信息--show接口 验证成功
    UserStuShowBean selectUserByUid(Integer id);
    
    //设置用户信息--set_info接口 验证成功
    int setUserStuInfo(UserStu record);
    
    //查询被点赞信息id下对应的用户信息  ---帖子
    List<LikeUserBean> selectBBSLikeUserByFid(@Param("fid") Integer fid,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    
   //查询被点赞信息id下对应的用户信息  ---测评作品
    List<LikeUserBean> selectWorksLikeUserByFid(@Param("fid") Integer fid,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    
    //查询被点赞信息id下对应的用户信息  ---小组动态
    List<LikeUserBean> selectStatusesLikeUserByFid(@Param("fid") Integer fid,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    
    
  //查询被点赞信息id下对应的用户头像信息  ---帖子
    List<LikeUserPicBean> selectBBSLikeUserPicByFid(@Param("fid") Integer fid,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    
  //查询被点赞信息id下对应的用户头像信息  ---测评信息
    List<LikeUserPicBean> selectWorksLikeUserPicByFid(@Param("fid") Integer fid,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    
  //查询被点赞信息id下对应的用户头像信息  ---小组动态
    List<LikeUserPicBean> selectStatusesLikeUserPicByFid(@Param("fid") Integer fid,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    //查询小组动态/帖子/作品回复的名称 statuses/show/bbs g_stus work接口调用
    String selectUserNameById(Integer id);
    
    //根据用户ID获取用户数目信息 --users/num接口调用
    UserNumberBean selectUserNumberByUid(Integer id);
}
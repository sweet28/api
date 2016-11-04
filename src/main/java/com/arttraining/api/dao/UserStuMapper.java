package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.LikeUserBean;
import com.arttraining.api.bean.LikeUserPicBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.UserStu;

public interface UserStuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserStu record);

    int insertSelective(UserStu record);

    UserStu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStu record);

    int updateByPrimaryKey(UserStu record);
    
    //依据uid查询相应的用户信息--show
    UserStuShowBean selectUserByUid(Integer id);
    //设置用户信息--set_info
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
}
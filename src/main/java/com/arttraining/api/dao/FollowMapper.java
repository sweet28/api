package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.FollowCreateBean;
import com.arttraining.api.bean.FollowFansBean;
import com.arttraining.api.bean.FollowUserBean;
import com.arttraining.api.pojo.Follow;

public interface FollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);
    //判断登录是否重复对名师/机构/爱好者用户关注 
    Follow selectIsExistFollow(Map<String, Object> map);
    //依据类型不同 添加关注信息--follow/create接口调用
    FollowCreateBean selectUserInfoByFollowCreate(Map<String, Object> map);
    //coffee add 1117--根据用户ID获取用户粉丝列表 follow/fans/list接口调用
    List<FollowFansBean> selectFollowFansList(Map<String, Object> map);
    //coffee add 1117--根据用户ID获取用户粉丝列表 follow/follow/list接口调用
    List<FollowFansBean> selectFollowList(Map<String, Object> map);
    //依据用户ID和类型查询相应的用户信息-- follow/fans/list follow/follow/list接口调用 
    FollowUserBean selectFollowUserById(@Param("uid") Integer uid,
    		@Param("utype") String utype);
}
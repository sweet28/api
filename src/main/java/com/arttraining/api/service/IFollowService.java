package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.FollowCreateBean;
import com.arttraining.api.bean.FollowFansBean;
import com.arttraining.api.bean.FollowUserBean;
import com.arttraining.api.pojo.Follow;

public interface IFollowService {
	//依据类型不同 添加关注信息--follow/create接口调用
    FollowCreateBean getUserInfoByFollowCreate(Map<String, Object> map);
    //添加关注信息--follow/create接口调用
    int insertOneFollow(Follow follow);
    //coffee add 1117--根据用户ID获取用户粉丝列表 follow/fans/list接口调用
    List<FollowFansBean> getFollowFansList(Map<String, Object> map);
    //coffee add 1117--根据用户ID获取用户粉丝列表 follow/follow/list接口调用
    List<FollowFansBean> getFollowList(Map<String, Object> map);
    //依据用户ID和类型查询相应的用户信息-- follow/fans/list follow/follow/list接口调用 
    FollowUserBean getFollowUserById(Integer uid,String utype);
}

package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.LikeUserBean;
import com.arttraining.api.bean.LikeUserPicBean;
import com.arttraining.api.bean.UserNumberBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.pojo.UserStu;

public interface IUserStuService {
	//依据用户ID查询相应的爱好者用户信息(users/show接口)
	public UserStu getUserStuById(Integer userStuId);
	
	//传递参数修改相应的爱好者用户信息
	public Integer updateUserStuBySelective(UserStu record);
	
	//设置用户信息--set_info
	public Integer setUserStuInfoBySelective(UserStu record);
	//show接口 依据用户id查询部分用户信息
	public UserStuShowBean showUserStuById(Integer userStuId);
	
	 //查询被点赞信息id下对应的用户信息  ---帖子
    List<LikeUserBean> listBBSLikeUserByFid(Integer fid,
    		Integer offset,Integer limit);
    //查询被点赞信息id下对应的用户信息  ---测评作品
    List<LikeUserBean> listWorksLikeUserByFid(Integer fid,
    		Integer offset,Integer limit);
    //查询被点赞信息id下对应的用户信息  ---小组动态
    List<LikeUserBean> listStatusesLikeUserByFid(Integer fid,
    		Integer offset,Integer limit);
    
    //查询被点赞信息id下对应的用户头像信息  ---帖子
    List<LikeUserPicBean> listBBSLikeUserPicByFid(Integer fid,
    		Integer offset,Integer limit);
    
    //查询被点赞信息id下对应的用户头像信息  ---测评信息
    List<LikeUserPicBean> listWorksLikeUserPicByFid(Integer fid,
    		Integer offset,Integer limit);
    
    //查询被点赞信息id下对应的用户头像信息  ---小组动态
    List<LikeUserPicBean> listStatusesLikeUserPicByFid(Integer fid,
    		Integer offset,Integer limit);
    //根据登录账号查询用户
	UserStu getUserStuByAccount(String account);
	
	//注册用户
	int insert(UserStu userStu);
	
	//查询小组动态/帖子/作品回复的名称 statuses/show/bbs g_stus work接口调用
    String getUserNameById(Integer id);
    
    //根据用户ID获取用户数目信息 --users/num接口调用
    UserNumberBean getUserNumberByUid(Integer id);

}

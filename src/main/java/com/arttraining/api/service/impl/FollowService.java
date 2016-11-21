package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.FollowCreateBean;
import com.arttraining.api.bean.FollowFansBean;
import com.arttraining.api.bean.FollowUserBean;
import com.arttraining.api.dao.FollowMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IFollowService;

@Service("followService")
public class FollowService implements IFollowService {
	@Resource
	private FollowMapper followDao;
	@Resource
	private UserStuMapper userStuDao;
	
	@Override
	public FollowCreateBean getUserInfoByFollowCreate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.followDao.selectUserInfoByFollowCreate(map);
	}

	@Override
	public int insertOneFollow(Follow follow) {
		// TODO Auto-generated method stub
		return this.followDao.insertSelective(follow);
	}

	@Override
	public List<FollowFansBean> getFollowFansList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.followDao.selectFollowFansList(map);
	}

	@Override
	public List<FollowFansBean> getFollowList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.followDao.selectFollowList(map);
	}

	@Override
	public FollowUserBean getFollowUserById(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.followDao.selectFollowUserById(uid, utype);
	}

	@Override
	public void insertOneFollowAndUpdateNum(Follow follow, UserStu follow_user,
			UserStu fan_user) {
		// TODO Auto-generated method stub
		this.followDao.insertSelective(follow);
		if(follow_user!=null) {
			this.userStuDao.updateNumberBySelective(follow_user);
		}
		if(fan_user!=null) {
			this.userStuDao.updateNumberBySelective(fan_user);
		}
	}

	@Override
	public Follow getIsExistFollow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.followDao.selectIsExistFollow(map);
	}

}

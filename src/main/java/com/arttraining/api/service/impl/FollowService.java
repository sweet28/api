package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.FollowCreateBean;
import com.arttraining.api.bean.FollowFansBean;
import com.arttraining.api.bean.FollowUserBean;
import com.arttraining.api.dao.FollowMapper;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.service.IFollowService;

@Service("followService")
public class FollowService implements IFollowService {
	@Resource
	private FollowMapper followDao;
	
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

}

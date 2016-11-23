package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.FollowCreateBean;
import com.arttraining.api.bean.FollowFansBean;
import com.arttraining.api.bean.FollowUserBean;
import com.arttraining.api.dao.FollowMapper;
import com.arttraining.api.dao.UserOrgMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.UserTechMapper;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.pojo.UserOrg;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.IFollowService;

@Service("followService")
public class FollowService implements IFollowService {
	@Resource
	private FollowMapper followDao;
	@Resource
	private UserStuMapper userStuDao;
	@Resource
	private UserTechMapper userTecDao;
	@Resource
	private UserOrgMapper userOrgDao;
	
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

//	@Override
//	public void insertOneFollowAndUpdateNum(Follow follow, UserStu follow_user,
//			UserStu fan_user) {
//		// TODO Auto-generated method stub
//		this.followDao.insertSelective(follow);
//		if(follow_user!=null) {
//			this.userStuDao.updateNumberBySelective(follow_user);
//		}
//		if(fan_user!=null) {
//			this.userStuDao.updateNumberBySelective(fan_user);
//		}
//	}

	@Override
	public Follow getIsExistFollow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.followDao.selectIsExistFollow(map);
	}

	@Override
	public void insertOneFollowAndUpdateNum(Follow follow, Integer follow_id,
			String follow_type, Integer fan_id, String fan_type) {
		// TODO Auto-generated method stub
		this.followDao.insertSelective(follow);
		//关注量
		switch (follow_type) {
		case "stu":
			UserStu follow_userStu = new UserStu();
			follow_userStu.setId(follow_id);
			follow_userStu.setFollowNum(1);
			this.userStuDao.updateNumberBySelective(follow_userStu);
			break;
		case "tec":
			UserTech follow_userTec = new UserTech();
			follow_userTec.setId(follow_id);
			follow_userTec.setFollowNum(1);
			this.userTecDao.updateNumberBySelective(follow_userTec);
			break;
		case "org":
			UserOrg follow_userOrg=new UserOrg();
			follow_userOrg.setId(follow_id);
			follow_userOrg.setFollowNum(1);
			this.userOrgDao.updateNumberBySelective(follow_userOrg);
			break;
		default:
			break;
		}
		//粉丝量
		switch (fan_type) {
		case "stu":
			UserStu fan_userStu = new UserStu();
			fan_userStu.setId(fan_id);
			fan_userStu.setFansNum(1);
			this.userStuDao.updateNumberBySelective(fan_userStu);
			break;
		case "tec":
			UserTech fan_userTec = new UserTech();
			fan_userTec.setId(fan_id);
			fan_userTec.setFansNum(1);
			this.userTecDao.updateNumberBySelective(fan_userTec);
			break;
		case "org":
			UserOrg fan_userOrg=new UserOrg();
			fan_userOrg.setId(fan_id);
			fan_userOrg.setFansNum(1);
			this.userOrgDao.updateNumberBySelective(fan_userOrg);
			break;
		default:
			break;
		}
		
	}

}

package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.GroupUserBean;
import com.arttraining.api.dao.GroupMapper;
import com.arttraining.api.dao.GroupUserMapper;
import com.arttraining.api.pojo.Group;
import com.arttraining.api.pojo.GroupUser;
import com.arttraining.api.service.IGroupUserService;

@Service("groupUserService")
public class GroupUserService implements IGroupUserService {
	@Resource
	private GroupUserMapper groupUserDao;
	@Resource
	private GroupMapper groupDao;
	
	@Override
	public void updateGroupAndUserByCreate(Group group, GroupUser groupUser) {
		// TODO Auto-generated method stub
		this.groupDao.updatePeopleNumByCreate(group);
		this.groupUserDao.insertSelective(groupUser);
	}

	@Override
	public void updateGroupAndUserByExit(Group group, GroupUser groupUser) {
		// TODO Auto-generated method stub
		this.groupDao.updatePeopleNumByExit(group);
		this.groupUserDao.updateGroupUserByExit(groupUser);
	}

	@Override
	public GroupUserBean getGroupUserInfoByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectGroupUserInfoByUid(map);
	}

	@Override
	public List<GroupUser> getGroupUserListByGid(Integer gid, Integer offset,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectGroupUserListByGid(gid, offset, limit);
	}
}

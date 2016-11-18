package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.GroupListBean;
import com.arttraining.api.bean.GroupListMyBean;
import com.arttraining.api.bean.GroupShowBean;
import com.arttraining.api.dao.GroupMapper;
import com.arttraining.api.dao.GroupUserMapper;
import com.arttraining.api.pojo.Group;
import com.arttraining.api.pojo.GroupUser;
import com.arttraining.api.service.IGroupService;

@Service("groupService")
public class GroupService implements IGroupService {
	@Resource
	private GroupMapper groupDao;
	@Resource
	private GroupUserMapper groupUserDao;
	
	@Override
	public void insertOneGroupAndUser(Group group, GroupUser groupUser) {
		// TODO Auto-generated method stub
		this.groupDao.insertOneGroup(group);
		Integer id = group.getId();
		groupUser.setGroupId(id);
		this.groupUserDao.insertSelective(groupUser);
	}

	@Override
	public String getUerPicByIdAndType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.groupDao.selectUerPicByIdAndType(map);
	}

	@Override
	public Group getIsRepeatGroup(Integer uid, String utype, String name) {
		// TODO Auto-generated method stub
		return this.groupDao.selectIsRepeatGroup(uid, utype, name);
	}

	@Override
	public List<GroupListBean> getGroupList(Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroupList(offset, limit);
	}

	@Override
	public List<GroupListMyBean> getGroupListMy(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroupListMy(map);
	}

	@Override
	public GroupShowBean getGroupShowById(Integer id) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroupShowById(id);
	}

	@Override
	public List<GroupListBean> getGroupListBySearch(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroupListBySearch(map);
	}	
}

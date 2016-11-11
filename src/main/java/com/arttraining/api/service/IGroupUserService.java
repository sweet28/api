package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.GroupUserBean;
import com.arttraining.api.pojo.Group;
import com.arttraining.api.pojo.GroupUser;

public interface IGroupUserService {
	//加入小组时 新增小组成员信息 同时更新小组成员数
	void updateGroupAndUserByCreate(Group group,GroupUser groupUser);
	//退出小组时 修改小组成员信息 同时更新小组成员数
	void updateGroupAndUserByExit(Group group,GroupUser groupUser);
	 //获取小组成员列表信息
    List<GroupUser> getGroupUserListByGid(Integer gid, Integer offset, Integer limit);
    //获取小组成员详细的信息
    GroupUserBean getGroupUserInfoByUid(Map<String, Object> map);
}

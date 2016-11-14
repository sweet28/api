package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.GroupShowUserBean;
import com.arttraining.api.bean.GroupUserBean;
import com.arttraining.api.pojo.Group;
import com.arttraining.api.pojo.GroupUser;

public interface IGroupUserService {
	//加入小组时 新增小组成员信息 同时更新小组成员数
	void updateGroupAndUserByCreate(Group group,GroupUser groupUser);
	//退出小组时 修改小组成员信息 同时更新小组成员数
	void updateGroupAndUserByExit(Group group,GroupUser groupUser);
	 //获取小组成员列表信息--group/users接口调用
    List<GroupUserBean> getGroupUserListByGid(Integer gid, Integer offset, Integer limit);

    //依据小组ID来获取小组成员列表信息--group/show接口调用
    List<GroupShowUserBean> getGroupUserListByGroupShow(Integer gid, Integer limit);
}

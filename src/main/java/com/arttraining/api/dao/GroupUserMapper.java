package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.GroupShowUserBean;
import com.arttraining.api.bean.GroupUserBean;
import com.arttraining.api.pojo.GroupUser;

public interface GroupUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUser record);

    int insertSelective(GroupUser record);

    GroupUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUser record);

    int updateByPrimaryKey(GroupUser record);
    
    //退出小组时 将小组成员标记置为1--group/exit接口调用
    int updateGroupUserByExit(GroupUser record);
    //获取小组成员列表信息--group/users接口调用
    List<GroupUserBean> selectGroupUserListByGid(@Param("gid") Integer gid,
    		@Param("offset") Integer offset, @Param("limit") Integer limit);
    //依据小组ID来获取小组成员列表信息--group/show接口调用
    List<GroupShowUserBean> selectGroupUserListByGroupShow(@Param("gid") Integer gid,
    		@Param("limit") Integer limit);
}
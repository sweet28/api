package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.arttraining.api.bean.GroupUserBean;
import com.arttraining.api.pojo.GroupUser;

public interface GroupUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUser record);

    int insertSelective(GroupUser record);

    GroupUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUser record);

    int updateByPrimaryKey(GroupUser record);
    
    //退出小组时 将小组成员标记置为1
    int updateGroupUserByExit(GroupUser record);
    //获取小组成员列表信息
    List<GroupUser> selectGroupUserListByGid(@Param("gid") Integer gid,
    		@Param("offset") Integer offset, @Param("limit") Integer limit);
    //获取小组成员详细的信息
    GroupUserBean selectGroupUserInfoByUid(Map<String, Object> map);
}
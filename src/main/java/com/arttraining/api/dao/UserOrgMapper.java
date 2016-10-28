package com.arttraining.api.dao;

import com.arttraining.api.pojo.UserOrg;

public interface UserOrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOrg record);

    int insertSelective(UserOrg record);

    UserOrg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOrg record);

    int updateByPrimaryKey(UserOrg record);
    
    //coffee add 新增依据orgId 查询对应的name
    String selectNameByPrimaryKey(Integer id);
}
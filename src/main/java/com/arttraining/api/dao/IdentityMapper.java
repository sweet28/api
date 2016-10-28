package com.arttraining.api.dao;

import com.arttraining.api.pojo.Identity;

public interface IdentityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Identity record);

    int insertSelective(Identity record);

    Identity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Identity record);

    int updateByPrimaryKey(Identity record);
    //coffee 1027 select name by primary key
    String selectNameByPrimaryKey(Integer id);
    //end
}
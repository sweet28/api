package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.IdentityListBean;
import com.arttraining.api.pojo.Identity;

public interface IdentityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Identity record);

    int insertSelective(Identity record);

    Identity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Identity record);

    int updateByPrimaryKey(Identity record);
    //获取身份列表--identity/list接口调用
    List<IdentityListBean> selectIdentityList();
}
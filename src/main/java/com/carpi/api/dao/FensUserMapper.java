package com.carpi.api.dao;

import com.carpi.api.pojo.FensUser;

public interface FensUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensUser record);

    int insertSelective(FensUser record);

    FensUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensUser record);

    int updateByPrimaryKey(FensUser record);
    
    //根据手机号查询用户是否注册
    FensUser selectRegister(FensUser fensUser);
    
    //查询推荐人是否存在
    FensUser selectReferee(String refereePhone);
    
    //更新密码
    int updatePwd(FensUser fensUser);
    
    
}
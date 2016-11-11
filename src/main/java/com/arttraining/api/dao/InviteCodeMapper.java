package com.arttraining.api.dao;

import com.arttraining.api.pojo.InviteCode;

public interface InviteCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InviteCode record);

    int insertSelective(InviteCode record);

    InviteCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteCode record);

    int updateByPrimaryKey(InviteCode record);
    
    InviteCode selectByInviteCode(String inviteCode);
}
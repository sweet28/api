package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.FensTeam;

public interface FensTeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensTeam record);

    int insertSelective(FensTeam record);

    FensTeam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensTeam record);

    int updateByPrimaryKey(FensTeam record);
    
    //根据手机号码查询是否存在已有的手机号码
    FensTeam selectFensTeam(String phone);
    
    //粉丝团列表
    List<FensTeam> selectAll(Integer fensUserId);
}
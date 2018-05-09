package com.carpi.api.dao;

import com.carpi.api.pojo.FensTeam;

public interface FensTeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensTeam record);

    int insertSelective(FensTeam record);

    FensTeam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensTeam record);

    int updateByPrimaryKey(FensTeam record);
}
package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.FensMiner;

public interface FensMinerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensMiner record);

    int insertSelective(FensMiner record);

    FensMiner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensMiner record);

    int updateByPrimaryKey(FensMiner record);
    
    //根据粉丝id查询矿机
    List<FensMiner> selectMiner(Integer fensUserId);
    //根据粉丝id查询A矿机
    List<FensMiner> selectAMiner(Integer fensUserId);
    //根据粉丝id查询B矿机
    List<FensMiner> selectBMiner(Integer fensUserId);
}
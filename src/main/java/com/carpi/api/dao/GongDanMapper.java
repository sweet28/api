package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.GongDan;

public interface GongDanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GongDan record);

    int insertSelective(GongDan record);

    GongDan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GongDan record);

    int updateByPrimaryKey(GongDan record);
    
    //查询历史工单
    List<GongDan> selectList(@Param("fensUserId") Integer fensUserId);
}
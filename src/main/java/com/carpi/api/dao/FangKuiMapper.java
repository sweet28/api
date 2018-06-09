package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FangKui;

public interface FangKuiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FangKui record);

    int insertSelective(FangKui record);

    FangKui selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FangKui record);

    int updateByPrimaryKey(FangKui record);
    
    //根据工单id查询反馈列表
    List<FangKui> selectList(@Param("gongdanId") Integer gongdanId);
}
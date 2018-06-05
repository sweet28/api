package com.carpi.api.dao;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.QuanDakuanRecord;

public interface QuanDakuanRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuanDakuanRecord record);

    int insertSelective(QuanDakuanRecord record);

    QuanDakuanRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuanDakuanRecord record);

    int updateByPrimaryKey(QuanDakuanRecord record);
    
    //查询券详情
    QuanDakuanRecord selectQuan(@Param("quanId") Integer quanId);
}
package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.Price;

public interface PriceMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Price record);

    int insertSelective(Price record);

    Price selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Price record);

    int updateByPrimaryKey(Price record);
    
    //查询今日价格
    Price selectToday();
    
    //根据时间段查询
    List<Price> selectAll(@Param("startTime")String startTime,@Param("endTime") String endTime);
}
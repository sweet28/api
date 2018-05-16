package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.BPool;

public interface BPoolMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BPool record);

	int insertSelective(BPool record);

	BPool selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BPool record);

	int updateByPrimaryKey(BPool record);

	// b矿池列表
	List<BPool> selectBpool(@Param("fensUserId") Integer fensUserId);
}
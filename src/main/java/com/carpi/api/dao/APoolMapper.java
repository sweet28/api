package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.APool;

public interface APoolMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(APool record);

	int insertSelective(APool record);

	APool selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(APool record);

	int updateByPrimaryKey(APool record);

	// a矿池列表
	List<APool> selectApool(@Param("fensUserId") Integer fensUserId);
}
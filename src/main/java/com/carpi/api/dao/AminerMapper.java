package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.Aminer;

public interface AminerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Aminer record);

	int insertSelective(Aminer record);

	Aminer selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Aminer record);

	int updateByPrimaryKey(Aminer record);

	// 查询a矿机列表
	List<Aminer> selectAMiner();
	
	//根据类型查询矿机信息
	Aminer selectType(@Param("type") Integer type);
}
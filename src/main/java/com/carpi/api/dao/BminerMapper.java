package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.Bminer;

public interface BminerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Bminer record);

	int insertSelective(Bminer record);

	Bminer selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Bminer record);

	int updateByPrimaryKey(Bminer record);

	// b矿机的列表
	List<Bminer> selectBMiner();

	// 根据类型查询矿机信息
	Bminer selectType(@Param("type") Integer type);
}
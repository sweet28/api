package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.QuanDakuanRecord;

public interface QuanDakuanRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(QuanDakuanRecord record);

	int insertSelective(QuanDakuanRecord record);

	QuanDakuanRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(QuanDakuanRecord record);

	int updateByPrimaryKey(QuanDakuanRecord record);

	// 查询券详情
	QuanDakuanRecord selectQuan(@Param("quanId") Integer quanId);

	// 根据券id查询打款记录表中的信息（主要是打款人id）
	List<QuanDakuanRecord> selectlist(@Param("quanId") Integer quanId, @Param("dakuanType") Integer dakuanType);
}
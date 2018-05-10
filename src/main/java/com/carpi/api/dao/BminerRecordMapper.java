package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.BminerRecord;

public interface BminerRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BminerRecord record);

	int insertSelective(BminerRecord record);

	BminerRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BminerRecord record);

	int updateByPrimaryKey(BminerRecord record);

	// 查询b矿机的交易记录
	List<BminerRecord> selectBRecord();
}
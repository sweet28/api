package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.AminerRecord;

public interface AminerRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AminerRecord record);

	int insertSelective(AminerRecord record);

	AminerRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AminerRecord record);

	int updateByPrimaryKey(AminerRecord record);

	// 查询a矿机的交易记录
	List<AminerRecord> selectARecord(Integer fensUserId);

}
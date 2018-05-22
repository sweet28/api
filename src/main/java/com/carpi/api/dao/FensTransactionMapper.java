package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.FensTransaction;

public interface FensTransactionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FensTransaction record);

    int insertSelective(FensTransaction record);

    FensTransaction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FensTransaction record);

    int updateByPrimaryKey(FensTransaction record);
    
    //查询粉丝的交易记录(可根据粉丝id查询)
    List<FensTransaction> selectFensRecord(FensTransaction fensTransaction);

	List<FensTransaction> selectCJFensRecord(FensTransaction fensTransaction);

	List<FensTransaction> selectCJFensRecordByID(Integer fensUserId);
}
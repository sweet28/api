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
    //查询成交交易
	List<FensTransaction> selectCJFensRecord(FensTransaction fensTransaction);

	List<FensTransaction> selectCJFensRecordByID(Integer fensUserId);
	//查询粉丝待付款交易
	List<FensTransaction> selectDFKRecord(FensTransaction fensTransaction);
	//查询粉丝待收款交易
	List<FensTransaction> selectDSKRecord(FensTransaction fensTransaction);
	//查询粉丝完成交易
	List<FensTransaction> selectYWCRecord(FensTransaction fensTransaction);
	//查询粉丝交易
	List<FensTransaction> selectGDRecord(FensTransaction fensTransaction);
}
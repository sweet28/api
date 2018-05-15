package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FoneyRecord;

public interface FoneyRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FoneyRecord record);

    int insertSelective(FoneyRecord record);

    FoneyRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FoneyRecord record);

    int updateByPrimaryKey(FoneyRecord record);
    
    //钱包转账记录列表
    List<FoneyRecord> selectWalletRecord(@Param("fensUserId")Integer fensUserId);
}
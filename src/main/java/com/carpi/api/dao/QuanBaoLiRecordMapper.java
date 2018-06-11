package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.QuanBaoLiRecord;

public interface QuanBaoLiRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuanBaoLiRecord record);

    int insertSelective(QuanBaoLiRecord record);

    QuanBaoLiRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuanBaoLiRecord record);

    int updateByPrimaryKey(QuanBaoLiRecord record);
    
    //券宝理个人订单查询
    List<QuanBaoLiRecord> selectList(@Param("fensUserId") Integer fensUserId,@Param("orderType") Integer orderType);
    
    //没人每天只能购买一张券
    List<QuanBaoLiRecord> check(@Param("fensUserId") Integer fensUserId);
    
}
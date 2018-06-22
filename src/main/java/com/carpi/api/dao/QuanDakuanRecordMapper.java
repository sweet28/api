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
	
	// 根据券积分id查询打款记录表中的信息
	List<QuanDakuanRecord> selectlistCouponGiftbyType(@Param("quanId") Integer quanId, @Param("dakuanType") Integer dakuanType);
	
	// 根据券积分id查询打款订单记录表信息
	List<QuanDakuanRecord> selectlistCouponGift(@Param("giftId") Integer giftId);
	
	// 根据券积分id查询打款订单记录表信息
	List<QuanDakuanRecord> selectlistCouponOrder(@Param("quanId") Integer quanId);
	
	// 根据用户id查询待打款订单记录表信息
	List<QuanDakuanRecord> selectDFKlistByFensUserId(@Param("dakuangId") Integer dakuangId);
	
	// 根据用户id查询待打款订单记录表信息
	List<QuanDakuanRecord> selectDSKlistByFensUserId(@Param("shoukuanId") Integer shoukuanId);
}
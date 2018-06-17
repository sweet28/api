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

	// 券宝理个人订单查询
	List<QuanBaoLiRecord> selectList(QuanBaoLiRecord record);

	// 查询还在进行中的券 
	int selectsum(@Param("fensUserId") Integer fensUserId,@Param("quanId") Integer quanId);

	// 没人每天只能购买一张券
	List<QuanBaoLiRecord> check(@Param("fensUserId") Integer fensUserId);
	
	//根据粉丝Id查询券信息
	QuanBaoLiRecord selectById(@Param("id") Integer id,@Param("orderType") Integer orderType);

	List<QuanBaoLiRecord> selectCouponGiftInfo(@Param("fensUserId") Integer fensUserId,@Param("quanId") Integer quanId,@Param("refereePhone")String refereePhone);

	Double selectCouponGiftTotalValue(@Param("fensUserId") Integer fensUserId, @Param("refereePhone")String refereePhone);

	List<QuanBaoLiRecord> selectCouponRealGiftInfo(@Param("fensUserId") Integer fensUserId,@Param("quanId") Integer quanId,@Param("refereePhone")String refereePhone);

	Double selectCouponGiftRealTotalValue(@Param("fensUserId") Integer fensUserId, @Param("refereePhone")String refereePhone);

}
package com.carpi.api.service;

import java.text.ParseException;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.QuanBaoLiRecord;

public interface QuanBaoLiRecordService {

	//券宝理列表
	public JsonResult selectList();
	
	//券宝理个人订单
	public JsonResult selectOne(QuanBaoLiRecord quanBaoLiRecord);
	
	//查询券详情
	public JsonResult xiangQing(Integer id);
	
	//根据id查询匹配订单信息
	public JsonResult pipeixiangQing(Integer id);
		
	//券保理出场
	JsonResult quanChuChang(Integer id,Integer type,Integer dakuantype);
	
	//券保理买家付款
	JsonResult fuk(Integer pipeiId, String yfkUrl);

	// 券保理卖家收款
	JsonResult shouk(Integer pipeiId);

	JsonResult couponGiftInfo(Integer fensUserId, String phone);

	public JsonResult couponGiftList(Integer valueOf, String phone);

	public JsonResult couponGiftListInfo(Integer valueOf);

	JsonResult shoukuanCoupon(Integer pipeiId, Integer type);

	public JsonResult fukCoupon(Integer pipeiId, Integer type);

	public JsonResult selectCouponDFK(Integer valueOf);

	public JsonResult selectCouponDSK(Integer valueOf);

	public JsonResult couponOrderList(Integer id);

	public JsonResult quanOut(Integer quanId, Integer fensUserId) throws ParseException;

	public JsonResult fuk(Integer pipeiId);

	public JsonResult fukCoupon(Integer valueOf, String yfkurl, int type);
}

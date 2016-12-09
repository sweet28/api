package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CouponsListBean;

public interface ICouponService {
	 //获取优惠券列表信息--coupon/list接口调用
    List<CouponsListBean> getCouponListByUid(Map<String, Object> map);
    //超过支付时间后 如果勾选了优惠券 则需要将优惠券的状态恢复原来状态 0代表测评 1代表课程 2代表通用 3代表会员卡
    int updateOneCouponInfoByOrderId(Map<String, Object> map);
}

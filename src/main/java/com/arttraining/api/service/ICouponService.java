package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CouponsListBean;

public interface ICouponService {
	 //获取优惠券列表信息--coupon/list接口调用
    List<CouponsListBean> getCouponListByUid(Map<String, Object> map);
}

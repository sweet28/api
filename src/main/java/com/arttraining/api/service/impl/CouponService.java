package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CouponsListBean;
import com.arttraining.api.dao.CouponMapper;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.service.ICouponService;

@Service("couponService")
public class CouponService implements ICouponService {
	@Resource
	private CouponMapper couponDao;

	@Override
	public List<CouponsListBean> getCouponListByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.couponDao.selectCouponListByUid(map);
	}

	@Override
	public int updateOneCouponInfoByOrderId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.couponDao.updateCouponInfoByOrderId(map);
	}

	@Override
	public int insertOneCoupon(Coupon coupon) {
		// TODO Auto-generated method stub
		//return this.couponDao.insertSelective(coupon);
		return this.couponDao.insert(coupon);
	}

	

}

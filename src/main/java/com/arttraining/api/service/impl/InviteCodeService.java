package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.CouponMapper;
import com.arttraining.api.dao.InviteCodeMapper;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.InviteCode;
import com.arttraining.api.service.IInviteCodeService;

@Service("InviteCodeService")
public class InviteCodeService implements IInviteCodeService{
	@Resource
	private InviteCodeMapper inviteCodeDao;
	@Resource
	private CouponMapper couponDao;

	@Override
	public int insert(InviteCode inviteCode) {
		return this.inviteCodeDao.insertSelective(inviteCode);
	}

	@Override
	public InviteCode selectByCode(String inviteCode) {
		return this.inviteCodeDao.selectByInviteCode(inviteCode);
	}

	@Override
	public void updateCodeAndCoupon(InviteCode inviteCode, Coupon coupon) {
		//1.先修改邀请码表
		this.inviteCodeDao.updateByPrimaryKeySelective(inviteCode);
		//2.然后修改优惠券
		System.out.println("33333");
		this.couponDao.insertSelective(coupon);
		System.out.println("44444");
	}

	

}

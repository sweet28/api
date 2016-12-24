package com.arttraining.api.service;

import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.InviteCode;

public interface IInviteCodeService {
	public int insert(InviteCode inviteCode);
	
	public void updateCodeAndCoupon(InviteCode inviteCode, Coupon coupon);

	public InviteCode selectByCode(String inviteCode);
}

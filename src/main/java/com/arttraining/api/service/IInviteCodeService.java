package com.arttraining.api.service;

import com.arttraining.api.pojo.InviteCode;

public interface IInviteCodeService {
	public int insert(InviteCode inviteCode);
	
	public int update(InviteCode inviteCode);

	public InviteCode selectByCode(String inviteCode);
}

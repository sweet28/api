package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.InviteCodeMapper;
import com.arttraining.api.pojo.InviteCode;
import com.arttraining.api.service.IInviteCodeService;

@Service("InviteCodeService")
public class InviteCodeService implements IInviteCodeService{
	@Resource
	private InviteCodeMapper inviteCodeDao;

	@Override
	public int insert(InviteCode inviteCode) {
		return this.inviteCodeDao.insertSelective(inviteCode);
	}

	@Override
	public int update(InviteCode inviteCode) {
		return this.inviteCodeDao.updateByPrimaryKeySelective(inviteCode);
	}

	@Override
	public InviteCode selectByCode(String inviteCode) {
		return this.inviteCodeDao.selectByInviteCode(inviteCode);
	}

}

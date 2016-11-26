package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.SMSCheckCodeMapper;
import com.arttraining.api.pojo.SMSCheckCode;
import com.arttraining.api.service.ISMSService;

@Service("SMSService")
public class SMSService implements ISMSService{
	@Resource
	private SMSCheckCodeMapper smsCheckCodeDao;

	@Override
	public SMSCheckCode getSMSCheckCode(SMSCheckCode smsCheckCode) {
		// TODO Auto-generated method stub
		return this.smsCheckCodeDao.selectByMobileAndType(smsCheckCode);
	}

	@Override
	public int insert(SMSCheckCode smsCheckCode) {
		// TODO Auto-generated method stub
		return this.smsCheckCodeDao.insertSelective(smsCheckCode);
	}

	@Override
	public int update(SMSCheckCode smsCheckCode) {
		// TODO Auto-generated method stub
		return this.smsCheckCodeDao.updateByPrimaryKeySelective(smsCheckCode);
	}

	@Override
	public SMSCheckCode getOneSmsInfo(SMSCheckCode smsCheckCode) {
		// TODO Auto-generated method stub
		return this.smsCheckCodeDao.selectOneSmsInfo(smsCheckCode);
	}

}

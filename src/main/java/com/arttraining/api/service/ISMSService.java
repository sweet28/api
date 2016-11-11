package com.arttraining.api.service;

import com.arttraining.api.pojo.SMSCheckCode;

public interface ISMSService {
	int insert(SMSCheckCode smsCheckCode);
	
	int update(SMSCheckCode smsCheckCode);

	SMSCheckCode getSMSCheckCode(SMSCheckCode smsCheckCode);
}

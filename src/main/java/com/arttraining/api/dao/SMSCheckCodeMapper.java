package com.arttraining.api.dao;

import com.arttraining.api.pojo.SMSCheckCode;

public interface SMSCheckCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SMSCheckCode record);

    int insertSelective(SMSCheckCode record);

    SMSCheckCode selectByPrimaryKey(Integer id);
    
    SMSCheckCode selectByMobileAndType(SMSCheckCode smsCheckCode);

    int updateByPrimaryKeySelective(SMSCheckCode record);

    int updateByPrimaryKey(SMSCheckCode record);
    
    SMSCheckCode selectOneSmsInfo(SMSCheckCode smsCheckCode);
}
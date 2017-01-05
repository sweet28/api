package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.pojo.ThirdLogin;

public interface IThirdLoginService {
	 //coffee add 0104 依据登录方式和uid 判断是否登录过
    public ThirdLogin getLoginInfoByUid(Map<String, Object> map);
    //coffee add 0104 新增一条第三方登录信息
    public int insertOneThirdInfo(ThirdLogin third);
}

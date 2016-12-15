package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.pojo.Token;

public interface ITokenService {
	//新增token数据
	public int insertTokenInfo(Token token);
	//判断token是否存在
	public Token getOneTokenInfo(Map<String, Object> map);
	//修改token数据
	public int updateTokenInfo(Token token);
	//验证token是否有效
	public boolean checkToken(String token);
}

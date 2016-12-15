package com.arttraining.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.TokenMapper;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.service.ITokenService;
import com.arttraining.commons.util.RedisUtil;

@Service("tokenService")
public class TokenService implements ITokenService {
	@Resource
	private  TokenMapper tokenDao;
	
	@Override
	public int insertTokenInfo(Token token) {
		// TODO Auto-generated method stub
		return this.tokenDao.insertSelective(token);
	}

	@Override
	public Token getOneTokenInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.tokenDao.selectOneTokenInfo(map);
	}

	@Override
	public int updateTokenInfo(Token token) {
		// TODO Auto-generated method stub
		return this.tokenDao.updateByPrimaryKeySelective(token);
	}

	@Override
	public boolean checkToken(String token) {
		// TODO Auto-generated method stub
		boolean flag = false; 
		//首先判断数据库中是否存在token 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		Token t=this.tokenDao.selectOneTokenInfo(map);
		if(t!=null) {
			flag = RedisUtil.checkExpire(token);
		}
		return flag;
	}
	
}

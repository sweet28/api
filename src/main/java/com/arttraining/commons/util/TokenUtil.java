package com.arttraining.commons.util;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import redis.clients.jedis.Jedis;

public class TokenUtil {
	// todo:生成token
	public static String generateToken(String account) {
		String nowTime = System.currentTimeMillis() + "yunhuyi_arttraining";
		String token = MD5.encodeString(MD5.encodeString(account + nowTime));
		
		Jedis jedis = RedisUtil.getJedisObject();
		jedis.set(token, account);
		jedis.expire(token, ConfigUtil.EXPIRE_TIME);
		RedisUtil.recycleJedisOjbect(jedis); // 将 获取的jedis实例对象还回池中

		return token;
	}

	// todo:验证token是否有效
	public static boolean checkToken(String token) {
		boolean flag = false;
		//flag = RedisUtil.checkExpire(token);
		flag=true;
		return flag;
	}

	// todo:将token有效期延期，token默认有效期为7天，当天操作过token将有效期延期一天
	public static boolean delayTokenDeadline(String token) {
		boolean flag = false;
		RedisUtil.expire(token, ConfigUtil.EXPIRE_TIME);

		return flag;
	}

	// todo:清除token
	public static boolean deleteToken(String token) {
		boolean flag = false;
		RedisUtil.deleteByKey(token);

		return flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(generateToken("15201633796"));
		System.out.println(MD5.check(MD5.encodeString("15201633796"),
				generateToken("15201633796")));
	}

}

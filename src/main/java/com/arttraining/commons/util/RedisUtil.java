package com.arttraining.commons.util;

import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static JedisPool jedisPool = null;
	private static int expire = 60000;

	// 静态代码初始化池配置
	static {
		try {
			Properties props = new Properties();
			props.load(RedisUtil.class.getClassLoader().getResourceAsStream(
					"redis.properties"));

			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();

			// 设置池配置项值
			config.setMaxTotal(Integer.valueOf(props
					.getProperty("redis.maxActive")));
			config.setMaxIdle(Integer.valueOf(props
					.getProperty("redis.maxIdle")));
			config.setMaxWaitMillis(Long.valueOf(props
					.getProperty("redis.maxWait")));
			config.setTestOnBorrow(Boolean.valueOf(props
					.getProperty("redis.testOnBorrow")));
			config.setTestOnReturn(Boolean.valueOf(props
					.getProperty("redis.testOnReturn")));

			// 根据配置实例化jedis池
			jedisPool = new JedisPool(config, props.getProperty("redis.host"),
					Integer.valueOf(props.getProperty("redis.port")),10000);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 获得jedis对象 */
	public static Jedis getJedisObject() {
		try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	/** 归还jedis对象 */
	public static void recycleJedisOjbect(Jedis jedis) {
		if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
	}
	
	 /** 设置过期时间 单位秒*/
    public static void expire(String key, int seconds) {
        if (seconds <= 0) { 
            return;
        }
        Jedis jedis = getJedisObject();
        jedis.expire(key, seconds);
        recycleJedisOjbect(jedis);
    }
    
    /** 检查是否过期 */
    public static boolean checkExpire(String key) {
    	boolean flag = false;
        Jedis jedis = getJedisObject();
        flag = jedis.exists(key);
        recycleJedisOjbect(jedis);
        
        return flag;
    }
 
    /**
     * 设置默认过期时间
     * @param key
     */
    public static void expire(String key) {
        expire(key, expire);
    }
    
    /**
     * 根据key删除数据
     * @param key
     */
    public static void deleteByKey(String key) {
        Jedis jedis = getJedisObject();
        jedis.del(key);
        recycleJedisOjbect(jedis);
    }
     
	/**
	 * 
	 * 测试jedis池方法
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = getJedisObject();// 获得jedis实例

		// 获取jedis实例后可以对redis服务进行一系列的操作
//		jedis.set("name", "zhuxun");
//		jedis.expire("name", 1);
//		Thread.sleep(5000);
		System.out.println(jedis.get("009b41bc1dfc18ee0aa66a2e998fba4c"));
//		jedis.del("8a6f4a59223c77c5a0f60986a6350bb4");
		System.out.println(jedis.del("009b41bc1dfc18ee0aa66a2e998fba4c"));
		System.out.println(checkExpire("009b41bc1dfc18ee0aa66a2e998fba4c"));
		System.out.println(jedis.get("009b41bc1dfc18ee0aa66a2e998fba4c"));
		recycleJedisOjbect(jedis); // 将 获取的jedis实例对象还回池中
		
//		String token = TokenUtil.generateToken("15201633796");
//		System.out.println(token);
	}
}

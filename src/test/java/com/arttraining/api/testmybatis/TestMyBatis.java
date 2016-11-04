package com.arttraining.api.testmybatis;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IUserStuService;
import com.arttraining.api.service.impl.InformationService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	// private ApplicationContext ac = null;
	@Resource
	private IUserStuService userService = null;
	@Resource
	private InformationService informationService;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// userService = (IUserService) ac.getBean("userService");
	// }

	@Test
	public void test() {
		//List<InformationListBean> informationList = this.informationService.getInformationList();
		InformationShowBean informationShow = this.informationService.getOneInformation(1);
		
		System.out.println(JSON.toJSONString(informationShow));
		//UserStu user = userService.getUserStuById(1);
		//System.out.println(user.getUserMobile());
		// logger.info("值："+user.getUserName());
		//logger.info(JSON.toJSONString(user));
	}
}
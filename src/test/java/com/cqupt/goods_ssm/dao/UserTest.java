package com.cqupt.goods_ssm.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.cqupt.goods_ssm.domain.TUser;

public class UserTest {
	
	
	//需要先获取spring容器
	private ApplicationContext applicationContext;
	@Before
	public void setUp() throws Exception{
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-dao.xml");
	}
	@Test
	public void testFindUserById() throws Exception {
		String uid = "32DB3700D2564254982BC58B0E4D95BC";
		TUserDAO userMapper = (TUserDAO) applicationContext.getBean("TUserDAO");
		TUser user = userMapper.selectByPrimaryKey(uid);
		System.out.println(user.getLoginname()+".......................");
	}
}

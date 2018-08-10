package com.cqupt.goods_ssm.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cqupt.goods_ssm.dao.TUserDAOExtend;
import com.cqupt.goods_ssm.domain.extend.TUserExtend;
import com.cqupt.goods_ssm.domain.vo.TUserExtendVo;
import com.cqupt.goods_ssm.exception.UserException;
import com.cqupt.goods_ssm.service.UserService;
import com.cqupt.goods_ssm.util.CommonUtils;
import com.cqupt.goods_ssm.util.mail.Mail;
import com.cqupt.goods_ssm.util.mail.MailUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	TUserDAOExtend userDaoExtend;
	
	/*@Autowired
	CommonUtils comomUtils;*/
	
	public TUserExtend login(TUserExtend user) throws Exception {
		//TUserExtendVo用来保证查询条件
		TUserExtendVo tUserExtendVo = new TUserExtendVo();
		tUserExtendVo.setUserExtend(user);
		
		TUserExtend userFind = userDaoExtend.findUserByNameAndPassword(tUserExtendVo);
		
		if(userFind!=null){
			return userFind;
		}
		return null;
	}

	public void regist(TUserExtend user) {
		CommonUtils commonUtils = new CommonUtils();
		//1.补齐uid
		user.setUid(commonUtils.uuid());
		user.setStatus(false);
		user.setActivationcode(commonUtils.uuid()+commonUtils.uuid());
		//2.进行注册
		
		try {
			userDaoExtend.add(user);
			System.out.println("插入t_user成功\n");
		} catch (Exception e) {
			System.out.println("插入t_user成功后出错\n");
			new RuntimeException(e);
		}
		
		//3.发邮件
		Properties prop=new Properties();
		try {
	
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
			
			System.out.println("读取配置文件email_template.properties成功\n");
		} catch (IOException e1) {
			System.out.println("读取配置文件email_template.properties出现异常\n");
			throw new RuntimeException(e1);
		}
		//(1)建立Session
		String name=prop.getProperty("username");
		String host=prop.getProperty("host");
		String password=prop.getProperty("password");
		Session session=MailUtils.createSession(host, name, password);
		// (2)建立邮件对象（mail类）
		String from=prop.getProperty("from");
		String to=user.getEmail();
		String subject=prop.getProperty("subject");
		String content=MessageFormat.format(prop.getProperty("content"), user.getActivationcode());
		Mail mail=new Mail(from, to, subject,content);
		
		try {
			//(3)发送
			MailUtils.send(session, mail);
			System.out.println("发送邮件成功！\n");
			
		} catch (MessagingException e) {
			new RuntimeException(e);
		} catch (IOException e) {
			new RuntimeException(e);
		}		
		
	}

	
	public void activation(String code) throws Exception {
		//1.通过激活码 得到user  queryUserByCode
		TUserExtend user = null;
		user = userDaoExtend.queryUserByCode(code);
		//2.判断user是否为null
		if(user==null) throw new UserException("激活码不正确！");
		//3.判断user的status是否为true
		if(user.getStatus())  throw new UserException("用户已经激活！");
		
		//4.执行updateUserStatus，激活user
		HashMap<Object, Object> map = new HashMap<>();
		map.put("status", true);
		map.put("uid", user.getUid());
		
		userDaoExtend.updateUserStatus(map);
		
	}

	
	public void updatePassword(String uid, String loginpass, String newloginpass)
			throws Exception {
		/*
		 * 1.根据id和loginpass放入map  查找用户
		 * 2.如果查找的用户不存在，说明原密码错误
		 * 3.如果存在，更改
		 */
		HashMap<Object,Object> map = new HashMap();
		map.put("uid", uid);
		map.put("loginpass", loginpass);
		Integer num = userDaoExtend.findByUidAndPass(map);
		if(!(num.intValue()>0)){
			throw new UserException("原密码错误！");
		}
//		System.out.println("");
		map.put("newloginpass", newloginpass);
		userDaoExtend.updatePassword(map);
	}

}

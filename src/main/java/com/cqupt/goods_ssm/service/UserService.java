package com.cqupt.goods_ssm.service;

import com.cqupt.goods_ssm.domain.TUser;
import com.cqupt.goods_ssm.domain.extend.TUserExtend;

public interface UserService {

	public TUserExtend login(TUserExtend user) throws Exception;
	
	public void regist(TUserExtend user)throws Exception;
	
	public void activation(String code)throws Exception;

	public void updatePassword(String uid,String loginpass,String newloginpass)throws Exception;
	
}

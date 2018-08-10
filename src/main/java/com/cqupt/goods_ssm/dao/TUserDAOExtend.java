package com.cqupt.goods_ssm.dao;

import java.util.HashMap;

import com.cqupt.goods_ssm.domain.extend.TUserExtend;
import com.cqupt.goods_ssm.domain.vo.TUserExtendVo;

public interface TUserDAOExtend {
	public TUserExtend findUserByNameAndPassword(TUserExtendVo tUserExtendVo) throws Exception;

	public void add(TUserExtend user)throws Exception;

	public TUserExtend queryUserByCode(String code)throws Exception;
	
	public void updateUserStatus(HashMap map)throws Exception;
	
	public Integer findByUidAndPass(HashMap map)throws Exception;

	public void updatePassword(HashMap map)throws Exception;
	
}

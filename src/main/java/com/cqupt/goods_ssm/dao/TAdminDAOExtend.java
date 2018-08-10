package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.extend.TAdminExtend;


public interface TAdminDAOExtend {
	//通过name和pass
	public TAdminExtend findAmdinByAdminnameAndpwd(TAdminExtend admin)throws Exception;
	
}
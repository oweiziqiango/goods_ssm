package com.cqupt.goods_ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.goods_ssm.dao.TAdminDAOExtend;
import com.cqupt.goods_ssm.domain.extend.TAdminExtend;
import com.cqupt.goods_ssm.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	TAdminDAOExtend adminDAO;
	public TAdminExtend login(TAdminExtend admin) throws Exception {
		TAdminExtend _admin = adminDAO.findAmdinByAdminnameAndpwd(admin);
		return _admin;
	}
	
}

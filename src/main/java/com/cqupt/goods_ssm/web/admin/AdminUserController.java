package com.cqupt.goods_ssm.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqupt.goods_ssm.domain.extend.TAdminExtend;
import com.cqupt.goods_ssm.service.AdminService;


@Controller
@RequestMapping("/admin")
public class AdminUserController {
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response,
			TAdminExtend admin) throws Exception{
		TAdminExtend _admin = adminService.login(admin);
		if(_admin == null){
			request.setAttribute("msg", "用户名或者密码错误！");
			return "forward:/adminjsps/login.jsp";
		}
		request.getSession().setAttribute("admin", admin);
		return "redirect:/adminjsps/admin/index.jsp";
	}
}

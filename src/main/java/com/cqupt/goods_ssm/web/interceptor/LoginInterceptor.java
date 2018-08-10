package com.cqupt.goods_ssm.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cqupt.goods_ssm.domain.extend.TUserExtend;

/**
 * 登录拦截器
 *  需要在spring-mvc中完成配置
 */
public class LoginInterceptor implements HandlerInterceptor{

	/*
	 * 执行handler之前
	 * 返回值 表示是否拦截器放行
	 * 比如登录认证，权限校验
	 */
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		
		System.out.println("LoginInterceptor......preHandle");
		
		StringBuffer url =request.getRequestURL(); 
		
		if(url.indexOf("login.action")>=0){
			return true;
		}
		
		HttpSession session = request.getSession();
		
		 TUserExtend user = (TUserExtend) session.getAttribute("sessionUser");
		// String username = user.getLoginname();
		if(user!=null){
			return true;
		}
		request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
		//return true; //表示放行
		return false;  //表示拦截
	}

	/*
	 * 执行handler，返回ModelAndView之前
	 * 
	 * 比如统一指定视图
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor......postHandle");
	}

	/*
	 * handler执行完成之后
	 * 比如用来处理统一处理异常 比如统一日志信息
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception ex)
			throws Exception {
		System.out.println("LoginInterceptor......afterCompletion");
	}
}

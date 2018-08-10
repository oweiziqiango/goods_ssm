package com.cqupt.goods_ssm.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cqupt.goods_ssm.domain.extend.TAdminExtend;
import com.cqupt.goods_ssm.domain.extend.TUserExtend;

/**
 * 登录拦截器
 *  需要在spring-mvc中完成配置
 */
public class AmdinLoginInterceptor implements HandlerInterceptor{

	/*
	 * 执行handler之前
	 * 返回值 表示是否拦截器放行
	 * 比如登录认证，权限校验
	 */
	//注意拦截器的逻辑
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		System.out.println("AmdinLoginInterceptor......preHandle");
		
		StringBuffer url =request.getRequestURL(); 
		if(url.indexOf("login.action")>=0){
			return true;
		}
		
		TAdminExtend admin =(TAdminExtend) request.getSession().getAttribute("admin");
		if(admin==null){
			request.setAttribute("msg", "您还没有登录，请不要瞎溜达！");
			request.getRequestDispatcher("/adminjsps/login.jsp").forward(request,response);
			return false;
		}
		return true;
	}

	/*
	 * 执行handler，返回ModelAndView之前
	 * 
	 * 比如统一指定视图
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView modelAndView) throws Exception {
		System.out.println("AmdinLoginInterceptor......postHandle");
	}

	/*
	 * handler执行完成之后
	 * 比如用来处理统一处理异常 比如统一日志信息
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception ex)
			throws Exception {
		System.out.println("AmdinLoginInterceptor......afterCompletion");
	}
}

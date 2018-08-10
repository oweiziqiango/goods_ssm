package com.cqupt.goods_ssm.web.frontend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqupt.goods_ssm.domain.extend.TCategoryExtend;
import com.cqupt.goods_ssm.service.CategoryService;

/**
 * 分类模块
 * 
 * 
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	/*
	 * 展示 分类列表
	 * 
	 * main.jsp中的左边调用
	 * 获取分类数据 转发到left.jsp页面 显示
	 */
	@RequestMapping("/findAllCategory")
	public String findAllCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//调用CategoryService
		List<TCategoryExtend> allCategory = categoryService.findAllCategory();
		request.setAttribute("parents", allCategory);
		//2018.7.27 spring-web.xml中配置的前缀和后缀 在forward和redirect中不管用，在modelandview.setViewName中可以使用
		return "forward:/jsps/left.jsp";
	}
}

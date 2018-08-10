package com.cqupt.goods_ssm.web.admin;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cqupt.goods_ssm.domain.TCategory;
import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.extend.TCategoryExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.service.BookService;
import com.cqupt.goods_ssm.service.CategoryService;
import com.cqupt.goods_ssm.util.CommonUtils;

/**
 * 后台分类管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	BookService bookService;
	
	
	//返回json数据
	@RequestMapping("/ajaxFindChild")
	public @ResponseBody List<TCategoryExtend> ajaxFindChild(HttpServletRequest request,HttpServletResponse response,
			String pid)throws Exception{
		List<TCategoryExtend> child = categoryService.findChildByPid(pid);
		return child;
	}
	
	@RequestMapping("/deleteChild")
	public String deleteChild(HttpServletRequest request,HttpServletResponse response,
			String cid)throws Exception{
		
		int countBook= bookService.findBookCountByCid(cid);
		if(countBook>0){
			request.setAttribute("msg","该分类下还有图书，不能删除！");
			return "forward:/adminjsps/msg.jsp";
		}
		
		else{
			categoryService.delete(cid);
			return "forward:findAllCategory";
		}
	}
	
	@RequestMapping("/deleteParent")
	public String deleteParent(HttpServletRequest request,HttpServletResponse response,
			String cid)throws Exception{
		int countChild = categoryService.findChildCountByPid(cid);
		
		if(countChild > 0){
			request.setAttribute("msg", "该分类下还有二级分类，不能删除！");
			return "forward:/adminjsps/msg.jsp";
		}else{
			categoryService.delete(cid);
			return "forward:findAllCategory";
		}
	}
	
	@RequestMapping("/editChildPre")
	public String editChildPre(HttpServletRequest request,HttpServletResponse response,
			String cid)throws Exception{
		TCategoryExtend category = categoryService.load(cid);
		TCategoryExtend parent = categoryService.load(category.getPid());
		category.setParent(parent);
		
		List<TCategoryExtend> parents = categoryService.findAllParent();
		request.setAttribute("parents", parents);
		request.setAttribute("child", category);
		
		return "forward:/adminjsps/admin/category/edit2.jsp";
	}
	
	@RequestMapping("/editChild")
	public String editChild(HttpServletRequest request,HttpServletResponse response,
			TCategoryExtend child)throws Exception{
		categoryService.edit(child);
		return "forward:findAllCategory";
	}
	/*
	 * 修改一级分类的第一步
	 * 根据cid 获取该Category
	 * 返回到add2.jsp
	 */
	@RequestMapping("/editParentPre")
	public String editParentPre(HttpServletRequest request,HttpServletResponse response,
			String cid)throws Exception{
		
		TCategoryExtend parent = categoryService.load(cid);
		
		request.setAttribute("parent", parent);
		
		return "forward:/adminjsps/admin/category/edit.jsp";
	
	}
	
	@RequestMapping("/editParent")
	public String editParent(HttpServletRequest request,HttpServletResponse response,
			TCategoryExtend parent)throws Exception{
		categoryService.edit(parent);
		return "forward:findAllCategory";
	}
	
	@RequestMapping("/addChild")
	public String addChild(HttpServletRequest request,HttpServletResponse response,
			TCategoryExtend category)throws Exception{
		//二级分类 生成cid
		CommonUtils comomUtils = new CommonUtils();
		category.setCid(comomUtils.uuid());
		//给二级分类 添加一级分类的parent.pid
		TCategoryExtend parent = new TCategoryExtend();
		parent.setCid(category.getPid());
		System.out.println("pid addChild........."+category.getPid());
		category.setParent(parent);
		
		categoryService.add(category);
		
		return "forward:findAllCategory";
	}
	/*
	 * 添加二级分类
	 * 需要查询所以父分类 并返回
	 */
	@RequestMapping("/addChildPre")
	public String addChildPre(HttpServletRequest request,HttpServletResponse response,
			String pid)throws Exception{
		List<TCategoryExtend> parents = categoryService.findAllParent();
		request.setAttribute("pid", pid);
		request.setAttribute("parents", parents);
		return "forward:/adminjsps/admin/category/add2.jsp";
	}
	
	
	
	/*
	 * 后台 添加一级分类
	 */
	@RequestMapping("/addParent")
	public String addParent(HttpServletRequest request,HttpServletResponse response,
			TCategoryExtend category)throws Exception{
		CommonUtils comomUtils = new CommonUtils();
		category.setCid(comomUtils.uuid());
		categoryService.add(category);
		return "forward:findAllCategory";
	}
	
	/*
	 * 后台查看所有分类  
	 * 使用 CategoryService的方法
	 */
	@RequestMapping("/findAllCategory")
	public String findAllCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<TCategoryExtend> listCategory = categoryService.findAllCategory();
		request.setAttribute("parents", listCategory);
		return "forward:/adminjsps/admin/category/list.jsp";
	}
	
}

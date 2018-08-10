package com.cqupt.goods_ssm.web.admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.cqupt.goods_ssm.domain.TBook;
import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.extend.TCategoryExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.service.BookService;
import com.cqupt.goods_ssm.service.CategoryService;
import com.cqupt.goods_ssm.util.CommonUtils;
/**
 * 后台图书管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminBookController {
	@Autowired
	BookService bookService;
	@Autowired
	CategoryService categoryService;
	/*
	 * 添加图书
	 * 1.完成解析上传的三步
	 * 2.把List<FileItem>内容，映射成book对象
	 * 3.处理图片
	 * 4.填加到book中
	 * 5.调用service完成添加
	 * 6.转发
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,HttpServletResponse response,
			TBookExtend book,MultipartFile image_b,MultipartFile image_w) throws IllegalStateException, IOException{
		// path = 全局路径/book_img 
		String path=request.getSession().getServletContext().getRealPath("/book_img");
		System.out.println(path);
		String originalFilename1 = image_b.getOriginalFilename();
		String originalFilename2 = image_w.getOriginalFilename();
		CommonUtils commonUtils = new CommonUtils();
		
		String newFilename1 = commonUtils.uuid()+"_b"+originalFilename1.substring(originalFilename1.lastIndexOf("."));
		String newFilename2 = commonUtils.uuid()+"_w"+originalFilename2.substring(originalFilename2.lastIndexOf("."));
	
		//全局路径
		String imageB = path+"\\"+newFilename1;
		String imageW = path+"\\"+newFilename2;
		File newFile1 = new File(imageB);
		File newFile2 = new File(imageW);
		
		image_b.transferTo(newFile1);
		image_w.transferTo(newFile2);
		
		book.setBid(commonUtils.uuid());
		//保存相对路径
		book.setImageB("book_img/"+newFilename1);
		book.setImageW("book_img/"+newFilename2);
		
		bookService.add(book);
		request.setAttribute("msg", "添加成功！");
		return "forward:/adminjsps/msg.jsp";
		
	}
	
	
	/*
	 * 1.获取所以一级分类
	 * 2.保存之后，转发到add.jsp
	 */
	@RequestMapping("/addPre")
	public String addPre(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//这里返回的包含children
		List<TCategoryExtend> parents = categoryService.findAllCategory();
		request.setAttribute("parents", parents);
		return "forward:/adminjsps/admin/book/add.jsp";
		
	}
	
	//修改（编辑）
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,
			TBookExtend book,String price) throws Exception{
		System.out.println(book.toString());
		bookService.edit(book);
		//book.setPrice((BigDecimal)book.getPrice());
		book.setPrice(new BigDecimal(price));
		//需要类型转换
		//BigDecimal price = book.getPrice();
		//System.out.println(price);
		request.setAttribute("msg", "修改成功！");
		return "forward:/adminjsps/msg.jsp";
	}
	
	
	//删除
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,HttpServletResponse response,
			String bid) throws Exception{
		TBookExtend book = bookService.load(bid);
		String savaPath = request.getSession().getServletContext().getRealPath("/");//获取真实路径
		new File(savaPath, book.getImageW()).delete();//删除图片
		new File(savaPath, book.getImageB()).delete();//删除图片
		bookService.delete(bid);
		request.setAttribute("msg", "删除成功！");
		return "forward:/adminjsps/msg.jsp";
	}
	
	
	@RequestMapping("/loadBook")
	public String loadBook(HttpServletRequest request,HttpServletResponse response,
			String bid) throws Exception{
		TBookExtend book = bookService.load(bid);
		
		
		TCategoryExtend load = categoryService.load(book.getCid());
		book.setCategory(load);
		
		List<TCategoryExtend> listCategory = categoryService.findAllCategory();
		request.setAttribute("parents", listCategory);
		
		request.setAttribute("book", book);
		return "forward:/adminjsps/admin/book/desc.jsp";
		
	}
	
		
	//findAllCategory
	@RequestMapping("/findBookOfLeft")
	public String findBookOfLeft(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<TCategoryExtend> listCategory = categoryService.findAllCategory();
		request.setAttribute("parents", listCategory);
		return "forward:/adminjsps/admin/book/left.jsp";
		
	}
	//findByCategory
	@RequestMapping("/findByCategory")
	public String findByCategory(HttpServletRequest request,HttpServletResponse response,
			String cid) throws Exception{
		/*
		 * 1.得到pc
		 * 2.得到url
		 * 3.获取查询条件 本条件是cid，分类id
		 * 4.使用pc和cid调用service#findByCategory得到pagebean
		 * 5.给pagebean设置url，保存pagebean，转发到/jsp/book/list.jsp
		 */
		//1.得到pc
		int pc=getPc(request);
		//2.得到url
		String url=getUrl(request);
		//3.获取查询条件
		//pc
		//4.使用pc和cid调用service
		PageBean<TBookExtend> pb=bookService.findByCategory(cid, pc);
		//5.给pagebean设置url
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "forward:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 获得pc 默认为1 如果有其他值，设置为对应值
	 */
	private int getPc(HttpServletRequest req){
		String pc=req.getParameter("pc");
		int n=1;
		System.out.println("pc is "+ pc+"\n");
		if(pc!=null&&!pc.trim().isEmpty()){
			n=Integer.parseInt(pc);
		}
		return n;
	}
	
	
	/*
	 * 获得url
	 * http://localhost:8080 req.getRequestURI()+?+req.getQueryString()
	 * http://localhost:8080/goods/BookServlet?method=findByBname&bname=xx&pc=xx
	 */
	private String getUrl(HttpServletRequest req){
		String url=req.getRequestURI()+"?"+req.getQueryString();
		int index=url.lastIndexOf("&pc=");
		if(index!=-1){
			//截取url，去掉关于pc参数的部分
			url=url.substring(0, index);
		}
		return url;
	}
}

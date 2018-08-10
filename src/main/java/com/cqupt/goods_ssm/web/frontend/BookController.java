package com.cqupt.goods_ssm.web.frontend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cqupt.goods_ssm.domain.TBook;
import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.domain.vo.TBookExtendVo;
import com.cqupt.goods_ssm.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {

	
	@Autowired
	BookService bookService;
	
	/*
	 * list.jsp页面中
	 * 点击 某个图书图片时  加载单个图书 转发到图书详情页面
	 */
	@RequestMapping("/load")
	public ModelAndView load(HttpServletRequest request,HttpServletResponse response,
			String bid)throws Exception{
		
		TBook book = bookService.load(bid);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/book/desc");
		modelAndView.addObject("book", book);
		return modelAndView;
//		request.setAttribute("book", book);
//		return "forward:/jsps/book/list.jsp";
	}
	/*
	 * list.jsp页面中
	 * 点击 某个图书的出版社时   根据出版社查询 该出版所有图书 在list中展示
	 * 2018.7.28
	 * 前端页面时通过a标签的href访问的后台action，使用的get方式，所以有乱码出现
	 */
	@RequestMapping("/findByPress")
	public String findByPress(HttpServletRequest request,HttpServletResponse response,
			String press)throws Exception{
		System.out.println(press);
		int pc = getPc(request);
		//http://localhost:8080/book/findByPress.action?press=xxx
		String url = getUrl(request);
		
		PageBean<TBookExtend> pb = bookService.findByPress(press, pc);
		
		pb.setUrl(url);
		
		request.setAttribute("pb", pb);
		
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("/book/list");
//		modelAndView.addObject("pb", pb);
//		
//		return modelAndView;
		return "forward:/jsps/book/list.jsp";
	}
	/*
	 * list.jsp页面中
	 * 点击 某个图书的作者，根据作者查询 该作者所有图书  在list中展示
	 */
	@RequestMapping("/findByAuthor")
	public String findByAuthor(HttpServletRequest request,HttpServletResponse response,
			String author)throws Exception{
		
		int pc = getPc(request);
		String url = getUrl(request);
		PageBean<TBookExtend> pb = new PageBean<>();
		pb=bookService.findByAuthor(author, pc);
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "forward:/jsps/book/list.jsp";
	}
	
	/*
	 * 根据cid分类，查找对应的图书
	 */
	//注释掉的是使用restful接口格式
	//@RequestMapping("/findByCategory/{cid}/{pc}")
	//public String findByCategory(HttpServletRequest request,HttpServletResponse response,
	//@PathVariable("cid") String cid,@PathVariable("pc") String pc)throws Exception{
	@RequestMapping("/findByCategory")
	public String findByCategory(HttpServletRequest request,HttpServletResponse response,
			String cid)throws Exception{
		/*
		 * 1.得到pc
		 * 2.得到url（用于点击分页上的按钮）
		 * 3.获取查询条件   本条件是cid
		 * 4.使用pc和cid 查询所属分类的图书 放入pagebean  用于分页
		 * 5.给pagebean设置url，保存pagebean，
		 * 	 转发到jsps/book/list.jsp
		 */
		int pc = getPc(request);
		
		String url = getUrl(request);
		
		PageBean<TBookExtend> pb = bookService.findByCategory(cid, pc);
		
		pb.setUrl(url);
		
		request.setAttribute("pb", pb);
		request.setCharacterEncoding("UTF-8");
		return "forward:/jsps/book/list.jsp";
	}
	/*
	 * 获取url
	 * http://localhost:8080 req.getRequestURI()+?+req.getQueryString()
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
	 * 点击搜索时，使用包装类获取查询条件 综合查询
	 */
	@RequestMapping("/findByCombination")
	public String findByCombination(HttpServletRequest request,HttpServletResponse response,
			TBookExtend book)throws Exception{
		//System.out.println("bname:.............."+bookVo.getBookExtend().getBname());
		System.out.println("bname............"+book.getBname());
		/*TBookExtendVo bookVo = new TBookExtendVo();
		bookVo.setBookExtend(book);*/
		int pc = getPc(request);
		String url = getUrl(request);
		PageBean<TBookExtend> pb = bookService.findByCombination(book,pc);
		
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "forward:/jsps/book/list.jsp";
	}
	/*
	 * 点击搜索时，根据输入内容（图书名） 模糊查询
	 */
	@RequestMapping("/findByBname")
	public String findByBname(HttpServletRequest request,HttpServletResponse response,
		String bname)throws Exception{
		int pc = getPc(request);
		String url = getUrl(request);
		PageBean<TBookExtend> pb = new PageBean<>();
		pb = bookService.findByBname(bname, pc);
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "forward:/jsps/book/list.jsp";
	}
}

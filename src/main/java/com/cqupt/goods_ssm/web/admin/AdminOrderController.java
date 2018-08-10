package com.cqupt.goods_ssm.web.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqupt.goods_ssm.domain.extend.TOrderExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.service.OrderService;
/**
 * 后台订单管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminOrderController {
	@Autowired
	OrderService orderService;
	
	private int getPc(HttpServletRequest req) {
		String pc = req.getParameter("pc");
		int n = 1;
		System.out.println("pc is " + pc + "\n");
		if (pc != null && !pc.trim().isEmpty()) {
			n = Integer.parseInt(pc);
		}
		return n;
	}

	/*
	 * 获得url http://localhost:8080 req.getRequestURI()+?+req.getQueryString()
	 * http://localhost:8080/goods/OrderServlet?method=myByOrder&bname=xx&pc=xx
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			// 截取url，去掉关于pc参数的部分
			url = url.substring(0, index);
		}
		return url;
	}
	
	@RequestMapping("/findAllOrder")
	public String findAllOrder(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PageBean<TOrderExtend> pb = new PageBean<TOrderExtend>();
		int pc = getPc(request);
		String url = getUrl(request);
		
		pb = orderService.findAllOrder(pc);
		
		pb.setUrl(url);
		
		request.setAttribute("pb", pb);
		
		return "forward:/adminjsps/admin/order/list.jsp";
	}
	/*
	 * status改为5
	 */
	@RequestMapping("/cancel")
	public String cancel(HttpServletRequest request,HttpServletResponse response,
			String oid)throws Exception{
		int status = orderService.findStatus(oid);
		
		if(status==1){
			orderService.updateOrderStatus(oid, 5);
			request.setAttribute("code", "success");
			request.setAttribute("msg", "已经取消！是否还有继续看看？");
			return "forward:/adminjsps/admin/msg.jsp";
		}else{
			request.setAttribute("code", "error");
			request.setAttribute("msg", "状态不对，不能取消！");
			return "forward:/adminjsps/admin/msg.jsp";
		}
	}
	
	@RequestMapping("/load")
	public String load(HttpServletRequest request,HttpServletResponse response,
			String oid,String btn)throws Exception{
		TOrderExtend load = orderService.load(oid);
		request.setAttribute("order", load);
		request.setAttribute("btn", btn);
		return "forward:/adminjsps/admin/order/desc.jsp";
	}
	/*
	 * status 改为 3 
	 */
	@RequestMapping("/delivery")
	public String delivery(HttpServletRequest request,HttpServletResponse response,
			String oid)throws Exception{
		int status = orderService.findStatus(oid);
		
		if(status==2){
			orderService.updateOrderStatus(oid, 3);
			request.setAttribute("code", "success");
			request.setAttribute("msg", "已经发货");
			return "forward:/adminjsps/admin/msg.jsp";
		}else{
			request.setAttribute("code", "error");
			request.setAttribute("msg", "状态不对，不能发货！");
			return "forward:/adminjsps/admin/msg.jsp";
		}
	}
}

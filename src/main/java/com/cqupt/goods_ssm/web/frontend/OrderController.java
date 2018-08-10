package com.cqupt.goods_ssm.web.frontend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;
import com.cqupt.goods_ssm.domain.extend.TOrderExtend;
import com.cqupt.goods_ssm.domain.extend.TOrderitemExtend;
import com.cqupt.goods_ssm.domain.extend.TUserExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.service.CartItemService;
import com.cqupt.goods_ssm.service.OrderService;
import com.cqupt.goods_ssm.util.CommonUtils;
import com.cqupt.goods_ssm.util.PaymentUtil;

/**
 * 订单模块的controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	CartItemService cartItemService;//用于购物车将购物车条目生成订单之后，删除购物车里的条目
	/*@Autowired
	CartItemService cartItemService;*/
	
	/*
	 * 支付有三个方法 
	 * 1.paymentPre 支付前
	 * 2.payment 支付执行
	 * 3.back 反馈支付状态信息
	 */
	//paymentPre oid  点击提交订单之后 进入该方法 调转到订单创建成功页面
	@RequestMapping("/paymentPre")
	public String paymentPre(HttpServletRequest request,HttpServletResponse response,
			String oid)throws Exception{
		//String oid = request.getParameter("oid");
		TOrderExtend order = orderService.load(oid);
		request.setAttribute("order", order);
		//简单的页面调整  不加forward和redirect 的话 请注意设置了前后缀 
		//forward 只有一次http请求
		//redirect 有两次http请求
		return "/order/pay";
	}
	
	//payment 会重定向到银行支付页面
	@RequestMapping("/payment")
	public void payment(HttpServletRequest request,HttpServletResponse response,
			String oid)throws Exception{
		/*
		 * 1.获取13个参数
		 */
		Properties pro = new Properties();
		pro.load(this.getClass().getClassLoader()
				.getResourceAsStream("payment.properties"));

		String p0_Cmd = "Buy"; // 业务类型 固定值“Buy”
		String p1_MerId = pro.getProperty("p1_MerId");// 商户编号 商户在易宝支付系统的唯一身份标识
		//String p2_Order = request.getParameter("oid");// 商户订单号
		String p2_Order = oid;// 商户订单号
		String p3_Amt = "0.01";// 支付金额
		String p4_Cur = "CNY";// 交易币种 固定值 ”CNY”.
		String p5_Pid = "";// 商品名称
		String p6_Pcat = "";// 商品种类
		String p7_Pdesc = "";// 商品描述
		String p8_Url = pro.getProperty("p8_Url");// 商户接收支付成功数据的地址
		String p9_SAF = "";// 送货地址
		String pa_MP = "";// 商户扩展信息
		String pd_FrpId = request.getParameter("yh");// 支付通道编码
		String pr_NeedResponse = "1";// 应答机制 固定值为“1”

		/*
		 * 2.计算hmac 需要13个参数 需要keyValue 需要加密算法
		 */
		String keyValue = pro.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 3.重定向到易宝的网关，给出14个参数，与易宝进行对接
		 */
		StringBuilder sb = new StringBuilder(
				"https://www.yeepay.com/app-merchant-proxy/node");
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);
		response.sendRedirect(sb.toString());
		//return null;
	}
	//back  银行支付完成之后，将数据返回到该方法
	@RequestMapping("/back")
	public String back(HttpServletRequest request,HttpServletResponse response)throws Exception{
		/*
		 * 1.获得12个参数
		 */
		String p1_MerId = request.getParameter("p1_MerId");// 商户编号
		String r0_Cmd = request.getParameter("r0_Cmd");// 业务类型
		String r1_Code = request.getParameter("r1_Code");// 支付结果
		String r2_TrxId = request.getParameter("r2_TrxId");// 易宝支付交易流水号
		String r3_Amt = request.getParameter("r3_Amt");// 支付金额
		String r4_Cur = request.getParameter("r4_Cur");// 交易币种
		String r5_Pid = request.getParameter("r5_Pid");// 商品名称
		String r6_Order = request.getParameter("r6_Order");// 商户订单号
		String r7_Uid = request.getParameter("r7_Uid");// 易宝支付会员ID
		String r8_MP = request.getParameter("r8_MP");// 商户扩展信息
		String r9_BType = request.getParameter("r9_BType");// 交易结果返回类型
		String hmac = request.getParameter("hmac");// 签名数据
		/*
		 * 2.从本地配置文件中  获取keyValue
		 */
		Properties pro = new Properties();
		pro.load(this.getClass().getClassLoader()
				.getResourceAsStream("payment.properties"));
		String keyValue = pro.getProperty("keyValue");
		/*
		 * 3. 验证调用者身份
		 * 如果验证失败，返回错误信息到msg.jsp 如果验证成功，判断是重定向还是点对点相应
		 * 如果是重定向，修改订单状态，保存成功信息，转发到msg.jsp 
		 * 如果是点对点，修改订单状态，返回success 
		 */
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if(!bool){
			request.setAttribute("code", "error");
			request.setAttribute("msg", "签名验证失败，交易失败！");
			return "forward:/jsps/msg.jsp";
		}
		if(r1_Code.equals("1")){
			orderService.updateOrderStatus(r6_Order, 2);
			if(r9_BType.equals("1")){
				//转到自己服务器上的页面
				request.setAttribute("code", "success");
				request.setAttribute("msg", "支付成功！");
				return "forward:/jsps/msg.jsp";
			}else if(r9_BType.equals("2")){
				//留在银行支付的页面
				response.getWriter().print("success");//响应页面
			}
		}
		return null;
	}
	
	
	
	//confirm 确认收货 已完成
	@RequestMapping("/confirm")
	public String confirm(HttpServletRequest request,HttpServletResponse response,
			String oid)throws Exception{
		int status = 4;
		orderService.updateOrderStatus(oid,status);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "已经取消！是否还有继续看看？ ");
		return "forward:/jsps/msg.jsp";
	}
	
	
	//cancel  取消订单 
	@RequestMapping("/cancel")
	public String cancel(HttpServletRequest request,HttpServletResponse response,
			String oid)throws Exception{
		int status = 5;
		orderService.updateOrderStatus(oid,status);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "已经取消！是否还有继续看看？ ");
		return "forward:/jsps/msg.jsp";
	}
	
	
	//load 查看单个订单
	@RequestMapping("/load")
	public String load(HttpServletRequest request,HttpServletResponse response,
			String oid,String btn)throws Exception{
		TOrderExtend  order = orderService.load(oid);
		request.setAttribute("order", order);
		request.setAttribute("btn", btn);
		return "forward:/jsps/order/desc.jsp";
	}
	
	
 	/*
 	 * myOrder 我的购物车
 	 * 1.首先获取sessionUser 取得uid
 	 * 2.声明pb类，获取pc，url
 	 * 3.调用orderService 返回pb
 	 * 4.pb设置url
 	 * 5.返回pb给前台
 	 * 使用分页
 	 */
	@RequestMapping("/myOrder")
	public String myOrder(HttpServletRequest request)throws Exception{
		//通过session获取user信息
		TUserExtend user = (TUserExtend) request.getSession().getAttribute("sessionUser");
		
		System.out.println(user.getLoginname()+"...1..........");
		int pc = getPc(request);
		//System.out.println("2..........");
		String url=getUrl(request);
		System.out.println(url+"...3..........");
		//根据user的uid查询的order类
		PageBean<TOrderExtend> pb = orderService.myOrder(user.getUid(), pc);
		System.out.println(pb.getTp()+"4..........");
		pb.setUrl(url);
		System.out.println("5..........");
		
		request.setAttribute("pb", pb);
		
		return "forward:/jsps/order/list.jsp";
	}
	
	/*
	 * 生成订单
	 *  获取提交的购物车条目信息
	 * 1.创建order对象
	 * 2.创建个orderitem对象 添加到order对象
	 * 3.调用service方法 创建订单
	 * 	   删除已成订单的购物车条目
	 * 4.将参数 返回forward:/jsps/order/ordersucc.jsp
	 */
	@RequestMapping("/createOrder")
	public String createOrder(HttpServletRequest request,HttpServletResponse response,
			String cartItemIds,TOrderExtend order)throws Exception{
		CommonUtils commonUtils = new CommonUtils();
		//补全order信息  address total 由前端页面传值
		order.setOid(commonUtils.uuid());//1
		/*
		 * 
		 * 表中的时间字段是char类型
		 * 前台没有传递关于时间的
		 * order类中ordertime也是String类型
		 * 在order类setOrdertime的时候，设置订单时间的String格式（使用date()生成真实的时间）
		 */
		order.setOrdertime(String.format("%tF %<tT", new Date()));// 2
		
		TUserExtend user = (TUserExtend) request.getSession().getAttribute("sessionUser");
		order.setUser(user);//
		System.out.println(user.getLoginname());
		System.out.println(user.getUid());
		order.setUid(user.getUid());//3
		order.setStatus(1);//4 默认是等待支付

		//购物车提交的条目信息
		List<TCartitemExtend> listCartItem = cartItemService.loadCartItems(cartItemIds);
		// 一个cartItem条目 对应一个orderItem条目
		List<TOrderitemExtend> listOrderItem = new ArrayList<TOrderitemExtend>();
		for (TCartitemExtend cartItem : listCartItem) {
			TOrderitemExtend orderItem = new TOrderitemExtend();
			
			orderItem.setOrderitemid(commonUtils.uuid());
			//book的数量和总计
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(new BigDecimal(cartItem.getSubtotal()));
			//订单条目中关于book
			orderItem.setBook(cartItem.getBook());
			orderItem.setBid(cartItem.getBook().getBid());
			orderItem.setBname(cartItem.getBook().getBname());
			orderItem.setCurrprice(cartItem.getBook().getCurrprice());//单价
			orderItem.setImageB(cartItem.getBook().getImageB());
			//订单条目中关于order
			orderItem.setOrder(order);
			orderItem.setOid(order.getOid());
			
			listOrderItem.add(orderItem);
		}
		BigDecimal total = new BigDecimal("0");
		for (TOrderitemExtend orderItem : listOrderItem) {
			total = total.add(orderItem.getSubtotal());
		}
		//bigDecimal的用法  
		order.setTotal(total.doubleValue());
		order.setListOrderItem(listOrderItem);
		//生成订单
		orderService.createOrder(order);
		//批量删除 
		cartItemService.batchDelete(cartItemIds);
		request.setAttribute("order", order);
		return "forward:/jsps/order/ordersucc.jsp";
	}
	
	/*
	 * 获得pc 默认为1 如果有其他值，设置为对应值
	 */
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
		//2018.7.31 这里不够完善 如果req.getQueryString()为空，即为null
		String url = req.getRequestURI() + "?" + req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			// 截取url，去掉关于pc参数的部分
			url = url.substring(0, index);
		}
		return url;
	}
}

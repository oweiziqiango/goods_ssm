package com.cqupt.goods_ssm.web.frontend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;
import com.cqupt.goods_ssm.domain.extend.TUserExtend;
import com.cqupt.goods_ssm.domain.vo.TCartitemExtendVo;
import com.cqupt.goods_ssm.service.CartItemService;

/**
 * 购物车条目的Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cart")
public class CartItemController {
	
	@Autowired
	CartItemService cartitemService;
	
	/*
	 * 点击结算  加载购物车条目
	 * 1.获取cartitemid 的 字符串
	 * 2.String total 转发成 Double _total
	 * 3.根据cartitemid 字符串 查询购物车条目
	 * 4.request添加参数
	 * 5.返回到jsps/cart/showitem.jsp
	 */
	@RequestMapping("/loadCartItems")
	public String loadCartItems(HttpServletRequest request,HttpServletResponse response,
			String cartItemIds,String total)throws Exception{
		
		double _total=Double.parseDouble(total);
		List<TCartitemExtend> listCartItem=cartitemService.loadCartItems(cartItemIds);
		request.setAttribute("listCartItem", listCartItem);
		request.setAttribute("total", _total);
		request.setAttribute("cartItemIds", cartItemIds);
		//已经在spring-web.xml中配置了前缀和后缀
		return "/cart/showitem";
	}
	/*
	 * ajax 动态更改 购物车条目的数量
	 * 
	 * 1.获得对应参数cartItemId和quantity输入映射到包装类中
	 * 2.调用service方法更改数量，返回已经更改的cartitem类
	 * 3.将所有查询到的信息都放到vo类中，转成json格式
     * 4.返回
	 */
	@RequestMapping("/updateQuantityCartItem")
	public @ResponseBody TCartitemExtendVo updateQuantityCartItem(HttpServletRequest request,HttpServletResponse response,
	TCartitemExtend cartitemExtend)throws Exception{
		//2018.7.29 返回json形式 需要jar 返回quantity，subtotal字段
		TCartitemExtend _cartitemExtend=cartitemService.updateQuantityCartItem(cartitemExtend);
		TCartitemExtendVo cartItemExtendVo = new TCartitemExtendVo();
		cartItemExtendVo.setCartItem(_cartitemExtend);
		cartItemExtendVo.setSubtotal(_cartitemExtend.getSubtotal());
		return cartItemExtendVo;
	}
	/*
	 * 批量删除，单个删除，根据id删除
	 */
	@RequestMapping("/batchDelete")
	public String batchDelete(HttpServletRequest request,HttpServletResponse response,
			String cartItemIds)throws Exception{
		System.out.println("cartItemIds"+cartItemIds+".................");
		cartitemService.batchDelete(cartItemIds);
		return "forward:myCart.action";
	}
	/*
	 * 在图书详情页面中 点击购买 添加到购物车中 调整到mycart.action 即购物车list.jsp页面
	 */
	@RequestMapping("/addCartItem")
	public String addCartItem(HttpServletRequest request,HttpServletResponse response,
			TCartitemExtend cartitem)throws Exception{
		/*
		 * 1.获取user
		 * 2.补全cartitem
		 * 3.service添加到cartitem表  传入参数 quantity bid uid 
		 */
		TUserExtend user = (TUserExtend) request.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		cartitem.setUid(uid);
		System.out.println("Quantity......."+cartitem.getQuantity());
		cartitemService.addCartItem(cartitem);
		return "forward:myCart.action";
	}
	/*
	 * 点击top.jsp上我的购物车 ，加载购物车条目信息，到/jsps/book/list.jsp
	 */
	@RequestMapping("/myCart")
	public String myCart(HttpServletRequest request,HttpServletResponse response)throws Exception{
		TUserExtend user = (TUserExtend) request.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		//调用service findByUser
		List<TCartitemExtend> cartItemList = cartitemService.findByUser(uid);
		
		request.setAttribute("cartItemList", cartItemList);
		return "forward:/jsps/cart/list.jsp";
	}
}

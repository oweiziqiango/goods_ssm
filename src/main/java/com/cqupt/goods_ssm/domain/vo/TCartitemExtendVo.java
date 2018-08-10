package com.cqupt.goods_ssm.domain.vo;

import com.cqupt.goods_ssm.domain.TCartitem;
import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;



/**
 * 购物车条目的包装类
 * 
 * 包装查询条件
 * 
 * 如批量删除的条目信息，更该条目中图书的数量
 * 
 *
 */
public class TCartitemExtendVo{
	private TCartitemExtend cartItem;
	private double subtotal;
	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public TCartitemExtend getCartItem() {
		return cartItem;
	}

	public void setCartItem(TCartitemExtend cartItem) {
		this.cartItem = cartItem;
	}
	
}
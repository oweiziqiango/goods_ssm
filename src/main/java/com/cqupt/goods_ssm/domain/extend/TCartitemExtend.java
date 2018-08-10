package com.cqupt.goods_ssm.domain.extend;

import java.math.BigDecimal;

import com.cqupt.goods_ssm.domain.TCartitem;

/**
 * 购物车条目的扩展类
 * 
 * @author Administrator
 *
 */
public class TCartitemExtend extends TCartitem{
	private TBookExtend book;//该条目所属图书
	private TUserExtend user;//该条目所属用户
	
	/*
	 * 购物车条目小计 book.currPrice * quantity
	 */
	public double getSubtotal(){
		/*
		 * Java中提供了大数字的操作类，
		 * 即java.math.BinInteger类和java.math.BigDecimal类。
		 * 这两个类用于高精度计 算，其中BigInteger类是针对大整数的处理类，
		 * 而BigDecimal类则是针对大小数的处理类。精确计算，必须使用String类型构造器。
		 */
		BigDecimal b1=new BigDecimal(book.getCurrprice() + "");
		BigDecimal b2=new BigDecimal(super.getQuantity() + "");
		BigDecimal b3= b1.multiply(b2);
		return b3.doubleValue();
	}
	
	public TBookExtend getBook() {
		return book;
	}
	public void setBook(TBookExtend book) {
		this.book = book;
	}
	public TUserExtend getUser() {
		return user;
	}
	public void setUser(TUserExtend user) {
		this.user = user;
	}
	
	
}
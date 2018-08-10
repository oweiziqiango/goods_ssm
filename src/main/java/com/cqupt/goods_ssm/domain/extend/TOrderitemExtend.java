package com.cqupt.goods_ssm.domain.extend;

import com.cqupt.goods_ssm.domain.TOrderitem;

/**
 * 订单条目类的扩展类
 * @author Administrator
 *
 */
public class TOrderitemExtend extends TOrderitem{
    private TBookExtend book;
    private TOrderExtend order;
	public TBookExtend getBook() {
		return book;
	}
	public void setBook(TBookExtend book) {
		this.book = book;
	}
	public TOrderExtend getOrder() {
		return order;
	}
	public void setOrder(TOrderExtend order) {
		this.order = order;
	}
}
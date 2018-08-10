package com.cqupt.goods_ssm.domain.extend;

import java.util.List;

import com.cqupt.goods_ssm.domain.TOrder;

/**
 * 订单模块的扩展类
 * @author Administrator
 *
 */
public class TOrderExtend extends TOrder{
	//订单所属用户
	private TUserExtend user;
	//订单中的订单条目
	private List<TOrderitemExtend> listOrderItem;
	
	public List<TOrderitemExtend> getListOrderItem() {
		return listOrderItem;
	}

	public void setListOrderItem(List<TOrderitemExtend> listOrderItem) {
		this.listOrderItem = listOrderItem;
	}

	public TUserExtend getUser() {
		return user;
	}

	public void setUser(TUserExtend user) {
		this.user = user;
	}
	
}
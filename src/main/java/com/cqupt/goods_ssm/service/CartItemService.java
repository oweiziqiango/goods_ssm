package com.cqupt.goods_ssm.service;

import java.util.List;

import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;

/**
 * 购物车条目的service层接口
 * @author Administrator
 *
 */
public interface CartItemService {
	//根据uid查询    findByUser
	public List<TCartitemExtend> findByUser(String uid)throws Exception;
	//点击购买 添加到购物车中
	public void addCartItem(TCartitemExtend cartitem)throws Exception;
	
	//批量删除
	public void batchDelete(String cartItemIds)throws Exception;
	//更改购物车条目的数量
	public TCartitemExtend updateQuantityCartItem(TCartitemExtend cartitemExtend)throws Exception;
	//点击购买 加载购物车条目
	public List<TCartitemExtend> loadCartItems(String cartItemIds)throws Exception;
}

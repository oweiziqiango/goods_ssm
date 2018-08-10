package com.cqupt.goods_ssm.service;

import java.util.List;

import com.cqupt.goods_ssm.domain.extend.TOrderExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;

public interface OrderService {
	public PageBean<TOrderExtend> myOrder(String uid,int pc)throws Exception;
	
	//生成订单order
	public void createOrder(TOrderExtend order);
	
	//根据oid 查询order相关信息
	public TOrderExtend load(String oid);
	//更新状态
	public void updateOrderStatus(String oid, int status);
	//查询所有订单
	public PageBean<TOrderExtend> findAllOrder(int pc)throws Exception;
	//查询订单状态
	public int findStatus(String oid);
	
	
	
}

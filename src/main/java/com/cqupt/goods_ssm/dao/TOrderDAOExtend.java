package com.cqupt.goods_ssm.dao;

import java.util.HashMap;
import java.util.List;

import com.cqupt.goods_ssm.domain.extend.TOrderExtend;

public interface TOrderDAOExtend {
	//根据 uid 查询多个 order
	public List<TOrderExtend> findOrderByUid(HashMap<Object, Object> map)throws Exception;
	//根据uid 查询该user有几个order
	public Integer findOrderCountByUid(String uid)throws Exception;
	//插入order
	public void addOrder(TOrderExtend order);
	
	//findOrderByOid  load加载订单信息 根据oid查询所关联的所有信息
	public TOrderExtend findOrderByOid(String oid);

	//updateOrderStatus 修改order的状态
	public void updateOrderStatus(HashMap<Object, Object> map);
	//后台操作
	//查询所有order的数量
	public int findAllOrderCount();
	//查询所有order
	public List<TOrderExtend> findAllOrder();
	public int findStatus(String oid);
	
}
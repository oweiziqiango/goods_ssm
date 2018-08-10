package com.cqupt.goods_ssm.dao;

import java.util.List;

import com.cqupt.goods_ssm.domain.extend.TOrderExtend;
import com.cqupt.goods_ssm.domain.extend.TOrderitemExtend;

public interface TOrderitemDAOExtend {
	//findByOidOrderItem 根据一个order  查询对应的多个orderitem
	public List<TOrderitemExtend> findByOidOrderItem(String oid)throws Exception;
	//创建订单时，将订单条目信息 插入到orderitem表中
	public void addOrderitem(TOrderitemExtend orderItem);
}
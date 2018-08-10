package com.cqupt.goods_ssm.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.goods_ssm.dao.TOrderDAO;
import com.cqupt.goods_ssm.dao.TOrderDAOExtend;
import com.cqupt.goods_ssm.dao.TOrderitemDAOExtend;
import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;
import com.cqupt.goods_ssm.domain.extend.TOrderExtend;
import com.cqupt.goods_ssm.domain.extend.TOrderitemExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.domain.page.PageConstant;
import com.cqupt.goods_ssm.service.OrderService;
/**
 * 订单模块的service的实现类
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	TOrderDAOExtend orderDAO;//DAO 为扩展
	@Autowired
	TOrderDAO orderDao;//Dao 为原配
	@Autowired
	TOrderitemDAOExtend orderitemDAO;
	/*
	 * 点击购物车
	 * 获取对应用户的order
	 */
	public PageBean<TOrderExtend> myOrder(String uid,int pc) throws Exception {
		
		PageBean<TOrderExtend> pb = new PageBean<TOrderExtend>(); 
		//每页记录数
		int ps = PageConstant.ORDER_PAGE_SIZE;
		//总记录数
		int tr = orderDAO.findOrderCountByUid(uid);
		//设置查询条件
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("uid", uid);
		map.put("begin", (pc-1)*ps);
		map.put("end", ps);
		//根据查询条件 返回pc页的orderlist 
		//一对多 一个用户 对 多个订单
		List<TOrderExtend> orderList = orderDAO.findOrderByUid(map);
		//循环order 添加orderitem
		//一对多 一个订单 对 多个订单条目  
		for(TOrderExtend order:orderList){
			//根据oid 查询orderitem 关联查询book
			//一对一    一个订单条目 对 一图书 
			List<TOrderitemExtend> listOrderItem = orderitemDAO.findByOidOrderItem(order.getOid());
			//这里可以直接实现所有的关联查询  直接返回一个order 包含listOrderItem(其中每个orderItem包含book)
			order.setListOrderItem(listOrderItem);
		}
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		pb.setBeanList(orderList);
		//2018.7.31 一个地方没有注意 返回null 就GG一下午加一晚上
		//return null;
		return pb;
	}
	/*
	 * 生成订单
	 * 1.插入到order表
	 * 2.listCartItem 插入到orderitem表
	 * 3.完成
	 */
	public void createOrder(TOrderExtend order) {
		orderDAO.addOrder(order);
		for(TOrderitemExtend orderItem:order.getListOrderItem()){
			orderitemDAO.addOrderitem(orderItem);
		}
	}
	/*
	 * 加载单个订单的详情
	 */
	public TOrderExtend load(String oid) {
		TOrderExtend order = orderDAO.findOrderByOid(oid);
		return order;
	}
	/*
	 * 修改订单状态
	 */
	public void updateOrderStatus(String oid, int status) {
		HashMap<Object,Object> map = new HashMap<>();
		map.put("oid", oid);
		map.put("status", status);
		orderDAO.updateOrderStatus(map);
	}
	@Override
	public PageBean<TOrderExtend> findAllOrder(int pc) throws Exception {
		
		
		PageBean<TOrderExtend> pb = new PageBean<TOrderExtend>(); 
		//每页记录数
		int ps = PageConstant.ORDER_PAGE_SIZE;
		//总记录数
		int tr = orderDAO.findAllOrderCount();
		
		//根据查询条件 返回pc页的orderlist 
		//一对多 一个用户 对 多个订单
		List<TOrderExtend> orderList = orderDAO.findAllOrder();
		//循环order 添加orderitem
		//一对多 一个订单 对 多个订单条目  
		for(TOrderExtend order:orderList){
			//根据oid 查询orderitem 关联查询book
			//一对一    一个订单条目 对 一图书 
			List<TOrderitemExtend> listOrderItem = orderitemDAO.findByOidOrderItem(order.getOid());
			//这里可以直接实现所有的关联查询  直接返回一个order 包含listOrderItem(其中每个orderItem包含book)
			order.setListOrderItem(listOrderItem);
		}
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		pb.setBeanList(orderList);
		//2018.7.31 一个地方没有注意 返回null 就GG一下午加一晚上
		//return null;
		return pb;
	}
	@Override
	public int findStatus(String oid) {
		int status = orderDAO.findStatus(oid);
		return status;
	}

}

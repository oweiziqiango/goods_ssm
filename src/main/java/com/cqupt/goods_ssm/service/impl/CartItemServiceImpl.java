package com.cqupt.goods_ssm.service.impl;

import java.util.List;
import com.cqupt.goods_ssm.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.goods_ssm.dao.TCartitemDAOExtend;
import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;
import com.cqupt.goods_ssm.service.CartItemService;
/**
 *  购物车条目的service层接口的实现类
 * @author Administrator
 *
 */
@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	TCartitemDAOExtend cartitemDAOExtend; //扩展类的dao
//	@Autowired
//	TCartitemDAO cartitemDAO;//自动生成关于cartitem类的dao
	
	//根据uid查询    findByUser
	public List<TCartitemExtend> findByUser(String uid) throws Exception {
		List<TCartitemExtend> listCartItem = cartitemDAOExtend.findByUser(uid);
		return listCartItem;
	}

	//点击购买 添加到购物车中
	public void addCartItem(TCartitemExtend cartitem) throws Exception {
		
		TCartitemExtend _cartitem = cartitemDAOExtend.findByUidandBid(cartitem);
		if(_cartitem!=null){
			System.out.println("_cartitem.getQuantity()......"+_cartitem.getQuantity());
			int quantity = _cartitem.getQuantity()+cartitem.getQuantity();
			_cartitem.setQuantity(quantity);
			cartitemDAOExtend.updateQuantity(_cartitem);
		}else{
			CommonUtils commonUtils = new CommonUtils();
			cartitem.setCartitemid(commonUtils.uuid());
			cartitemDAOExtend.addCartItem(cartitem);
		}
	}

	//批量删除
	public void batchDelete(String cartItemIds)throws Exception {
		String[] cartItemId=cartItemIds.split(",");
		cartitemDAOExtend.batchDelete(cartItemId);
	}

	//更改购物车条目数量
	public TCartitemExtend updateQuantityCartItem(TCartitemExtend cartitemExtend) throws Exception{
		cartitemDAOExtend.updateQuantity(cartitemExtend);
		TCartitemExtend user = cartitemDAOExtend.findByCartItemId(cartitemExtend.getCartitemid());
		return user;
	}

	//加载购物车条目 用于生成订单时显示
	public List<TCartitemExtend> loadCartItems(String cartItemIds)throws Exception{
		String[] cartItemId=cartItemIds.split(",");
		List<TCartitemExtend> listCartitem = cartitemDAOExtend.loadCartItems(cartItemId);
		return listCartitem;
	}
	
}

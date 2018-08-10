package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.extend.TCartitemExtend;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 购物车条目扩展类 对应的 Dao
 */
public interface TCartitemDAOExtend {
   //通过uid查询cartItem
   public List<TCartitemExtend> findByUser(@Param("uid") String uid)throws Exception;
   //点击购买 添加到cartItem表中 
   public void addCartItem(TCartitemExtend cartitem)throws Exception;
   //findByUidandBid  添加到cartItem之前 查询是否已经存在
   public TCartitemExtend findByUidandBid(TCartitemExtend cartitem)throws Exception;
   //updateQuantity 修改数量
   public void updateQuantity(TCartitemExtend cartitem)throws Exception;
   //批量删除 传入cartitemid的数组
   public void batchDelete(String[] cartItemId)throws Exception;
   //根据cartitemid 查找对应的cartitem类
   public TCartitemExtend findByCartItemId(String cartitemid)throws Exception;
   //点击结算时，查询购物车内选择条目信息
   public List<TCartitemExtend> loadCartItems(String[] cartItemId)throws Exception;
}
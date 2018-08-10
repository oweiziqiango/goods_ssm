package com.cqupt.goods_ssm.domain.vo;

import com.cqupt.goods_ssm.domain.extend.TUserExtend;



/**
 * 用于关于User的查询操作
 * 
 * 包装类，包装查询条件，保存查询结果
 * 
 * 关联查询时   用的到
 * 比如查询用户的订单 购物车等信息
 * @author Administrator
 *
 */
public class TUserExtendVo {
	//可替代直接使用TUserExtend类
	TUserExtend userExtend;

	public TUserExtend getUserExtend() {
		return userExtend;
	}

	public void setUserExtend(TUserExtend userExtend) {
		this.userExtend = userExtend;
	}
	
}

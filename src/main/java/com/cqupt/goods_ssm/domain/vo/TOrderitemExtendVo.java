package com.cqupt.goods_ssm.domain.vo;

import com.cqupt.goods_ssm.domain.TOrderitem;
import com.cqupt.goods_ssm.domain.extend.TOrderitemExtend;

/**
 * 订单条目类的包装类
 * @author Administrator
 *
 */
public class TOrderitemExtendVo{
    private TOrderitemExtend orderitemExtend;

	public TOrderitemExtend getOrderitemExtend() {
		return orderitemExtend;
	}

	public void setOrderitemExtend(TOrderitemExtend orderitemExtend) {
		this.orderitemExtend = orderitemExtend;
	}
    
}
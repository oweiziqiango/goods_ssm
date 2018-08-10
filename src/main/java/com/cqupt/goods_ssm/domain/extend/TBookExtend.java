package com.cqupt.goods_ssm.domain.extend;



import com.cqupt.goods_ssm.domain.TBook;

/**
 * Book模块的book类的扩展类
 * 
 * @author Administrator
 *
 */
public class TBookExtend extends TBook{
	//该书所属分类
   private TCategoryExtend category;

public TCategoryExtend getCategory() {
	return category;
}

public void setCategory(TCategoryExtend category) {
	this.category = category;
}
}
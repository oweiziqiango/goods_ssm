package com.cqupt.goods_ssm.domain.extend;

import java.util.List;

import com.cqupt.goods_ssm.domain.TCategory;

/**
 * 分类的扩展类
 * 
 * 添加父分类
 * 添加子分类list
 * @author Administrator
 *
 */
public class TCategoryExtend extends TCategory{
	private TCategoryExtend parent;
	
	private List<TCategoryExtend> children;

	public TCategoryExtend getParent() {
		return parent;
	}

	public void setParent(TCategoryExtend parent) {
		this.parent = parent;
	}

	public List<TCategoryExtend> getChildren() {
		return children;
	}

	public void setChildren(List<TCategoryExtend> children) {
		this.children = children;
	}

	
}
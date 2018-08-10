package com.cqupt.goods_ssm.service;

import java.util.List;

import com.cqupt.goods_ssm.domain.TCategory;
import com.cqupt.goods_ssm.domain.extend.TCategoryExtend;

public interface CategoryService {
	//findAllCategory 分类展示
		public List<TCategoryExtend> findAllCategory()throws Exception;
		//添加分类
		public void add(TCategoryExtend category)throws Exception;
		//后台查询所有一级分类
		public List<TCategoryExtend> findAllParent()throws Exception;
		//根据cid 返回Category
		public TCategoryExtend load(String cid) throws Exception;
		//根据Category 进行修改
		public void edit(TCategoryExtend category)throws Exception;
		//根据cid删除
		public void delete(String cid);
		//根据pid 查询子分类个数
		public int findChildCountByPid(String pid);
		//根据pid 查询子分类
	    public List<TCategoryExtend> findChildByPid(String pid)throws Exception;

}

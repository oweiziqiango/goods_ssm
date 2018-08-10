package com.cqupt.goods_ssm.dao;

import java.util.List;

import com.cqupt.goods_ssm.domain.extend.TCategoryExtend;
/**
 * TCategoryExtend的mapper接口类
 * Dao
 * @author Administrator
 *
 */
public interface TCategoryDAOExtend {

	//findAllCategory
	public List<TCategoryExtend> findAllParent()throws Exception;
	//findByPid
	public List<TCategoryExtend> findByPid(String cid)throws Exception;

	//后台 添加分类
	public void add(TCategoryExtend category)throws Exception;
	//后台修改时 加载单个Category
	public TCategoryExtend load(String cid)throws Exception;
	//后台修改
	public void edit(TCategoryExtend parent)throws Exception;
	//后台删除
	public void delete(String cid);
	//后台查询一级分类下的二级分类的个数
	public Integer findChildCountByPid(String pid);

}
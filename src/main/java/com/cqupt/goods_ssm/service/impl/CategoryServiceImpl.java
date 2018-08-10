package com.cqupt.goods_ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.goods_ssm.dao.TCategoryDAO;
import com.cqupt.goods_ssm.dao.TCategoryDAOExtend;
import com.cqupt.goods_ssm.domain.TCategory;
import com.cqupt.goods_ssm.domain.extend.TCategoryExtend;
import com.cqupt.goods_ssm.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	TCategoryDAOExtend categoryExtendDAO;
	@Autowired
	TCategoryDAO categoryDao;
	/*
	 * 展示分类
	 * @see com.cqupt.goods_ssm.service.CategoryService#findAllCategory()
	 */
	public List<TCategoryExtend> findAllCategory() throws Exception {
		/*
		 * 1.先查询所有一级分类
		 * 2.循环查询二级分类，添加到一级分类的children属性中
		 * 3.返回allCategory
		 */
		List<TCategoryExtend> allCategory = null;
		
		
		//此时只有一级分类 pid = null  children = null
		allCategory = categoryExtendDAO.findAllParent();
		
		if(allCategory==null){
			throw new Exception("没有分类");
		}
		//查询每个一级分类的二级分类
		for(TCategoryExtend categroy:allCategory){
			List<TCategoryExtend> children = categoryExtendDAO.findByPid(categroy.getCid());
			//将二级分类和一级分类相关联
			categroy.setChildren(children);
			//将每个二级分类的parent属性 置为 所属一级分类
			for(TCategoryExtend child:children){
				child.setParent(categroy);
			}
		}
		
		return allCategory;
	}
	//后台添加分类
	public void add(TCategoryExtend category) throws Exception {
		categoryExtendDAO.add(category);
	}
	//后台 查询所有一级分类
	public List<TCategoryExtend> findAllParent() throws Exception {
		List<TCategoryExtend> parents = categoryExtendDAO.findAllParent();
		return parents;
	}
	
	
	@Override
	public TCategoryExtend load(String cid) throws Exception {
		//TCategoryExtend category = categoryExtendDAO.load(cid);
		TCategoryExtend load = categoryExtendDAO.load(cid);
		if(load.getPid()!=null)
		{
			//得到一级分类
			TCategoryExtend load2 = categoryExtendDAO.load(load.getPid());
			load.setParent(load2);
		}
		return  categoryExtendDAO.load(cid);
	}
	@Override
	public void edit(TCategoryExtend category) throws Exception {
		categoryExtendDAO.edit(category);
	}
	@Override
	public void delete(String cid) {
		categoryExtendDAO.delete(cid);
	}
	@Override
	public int findChildCountByPid(String pid) {
		//categoryExtendDAO.findChildCountByPid(pid);
		return categoryExtendDAO.findChildCountByPid(pid);
	}
	@Override
	public List<TCategoryExtend> findChildByPid(String pid) throws Exception {
		List<TCategoryExtend> child = categoryExtendDAO.findByPid(pid);
		return child;
	}
}

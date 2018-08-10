package com.cqupt.goods_ssm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.vo.TBookExtendVo;



public interface TBookDAOExtend {

	/*
	 * 按分类查询时所需
	 */
	public List<TBookExtend> findByCategory(HashMap<Object,Object> map) throws Exception;
	public Integer findCountByCid(String cid)throws Exception;
	
	/*
	 * 点击 某个图书图片时  加载单个图书 
	 */
	//public TBookExtend load(String bid)throws Exception;
	
	//根据press 查询图书list
	//2018.7.29 因为在xml中使用了sql片段，xml中无法直接获取到属性名为press参数的String类型的值，需要使用标签@Param指定参数名称
	public Integer findCountByPress(@Param("press") String press)throws Exception;
	public List<TBookExtend> findByPress(HashMap<Object,Object> map) throws Exception;
	
	//根据author 查询图书list
	public List<TBookExtend> findByAuthor(HashMap<Object,Object> map) throws Exception;
	public Integer findCountByAuthor(@Param("author") String author)throws Exception;
	
	//根据bname 查询图书list
	public List<TBookExtend> findByBname(HashMap<Object,Object> map) throws Exception;
	public Integer findCountByBname(@Param("bname") String bname)throws Exception;
	
	//findByCombination 综合查询
	public List<TBookExtend> findByCombination(TBookExtendVo bookExtendVo)throws Exception;
	public Integer findCountByCombination(TBookExtendVo bookExtendVo)throws Exception;
	
	public TBookExtend load(String bid);
	
	public void delete(String bid);

	public void edit(TBookExtend book);
	
	public void add(TBookExtend book);
	
	
	

}
package com.cqupt.goods_ssm.service;

import com.cqupt.goods_ssm.domain.TBook;
import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.domain.vo.TBookExtendVo;


public interface BookService {
	//根据cid 查询图书，以分页的形式返回
	public PageBean<TBookExtend> findByCategory(String cid,int pc)throws Exception;	
	//点击 某个图书图片时  加载单个图书 
	public TBookExtend load(String bid)throws Exception;
	
	//根据出版社 查询图书list
	public PageBean<TBookExtend> findByPress(String press,int pc)throws Exception;
	
	//根据作者 查询图书list
	public PageBean<TBookExtend> findByAuthor(String author,int pc)throws Exception;
	
	//根据bname 查询图书list
	public PageBean<TBookExtend> findByBname(String bname,int pc)throws Exception;
	
	//findByCombination
	public PageBean<TBookExtend> findByCombination(TBookExtend bookExtend,int pc)throws Exception;
	//根据cid  查询图书数量
	public Integer findBookCountByCid(String cid)throws Exception;
	
	//根据bid 删除图书
	public void delete(String bid);
	
	public void edit(TBookExtend book);
	
	public void add(TBookExtend book);
}

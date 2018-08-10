package com.cqupt.goods_ssm.domain.vo;



import java.util.List;

import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;

/**
 * Book模块的book类的扩展类
 * 包装查询条件
 * 获取查询条件 返回查询结果
 * @author Administrator
 *
 */
public class TBookExtendVo{
	//可替代直接使用TBookExtend类
	private TBookExtend bookExtend;
	//批量删除时 接受批量的cid
	private List<TBookExtend> listBooks;
	
	private int begin;
	private int end;
	
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public TBookExtend getBookExtend() {
		return bookExtend;
	}
	public void setBookExtend(TBookExtend bookExtend) {
		this.bookExtend = bookExtend;
	}
	public List<TBookExtend> getListBooks() {
		return listBooks;
	}
	public void setListBooks(List<TBookExtend> listBooks) {
		this.listBooks = listBooks;
	}	
}
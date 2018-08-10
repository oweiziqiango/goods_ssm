package com.cqupt.goods_ssm.domain.page;

import java.util.List;

/*
 * 用于分页显示
 */
public class PageBean<T> {
	private int pc;//pagecode 当前页码
	private int tr;//totalrecord 总记录数
	private int ps;//pagesize 每页记录数
	
	private String url; //请求路径和参数   例如:/BookServlet?method=findxxx&cid=1&banme=2
	private List<T> beanList;//当前页内容
	//这里没有 String tp，只有getTp 为什么最后servlet也能把tp的值传给页面，是页面自己计算的 还是在PgaeBean里完成的计算
	//计算总页数tp
	public int getTp() {
			int tp=tr/ps;
			return tr % ps == 0 ? tp : tp+1;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	@Override
	public String toString() {
		return "PageBean [pc=" + pc + ", tr=" + tr + ", ps=" + ps + ", url="
				+ url + ", beanList=" + beanList + "]";
	}
	
}

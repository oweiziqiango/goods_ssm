package com.cqupt.goods_ssm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqupt.goods_ssm.dao.TBookDAO;
import com.cqupt.goods_ssm.dao.TBookDAOExtend;
import com.cqupt.goods_ssm.domain.extend.TBookExtend;
import com.cqupt.goods_ssm.domain.page.PageBean;
import com.cqupt.goods_ssm.domain.page.PageConstant;
import com.cqupt.goods_ssm.domain.vo.TBookExtendVo;
import com.cqupt.goods_ssm.service.BookService;


/*
 * 2018.7.28  思考
 * 将之前dao（queryRunner）的复杂操作 （使用一个表达式类（参数名，操作符，数值），使用通用的查询方法，拼接where_sql语句,添加？对应的参数，进行查询）转移到service来
 * 将service中重复的代码，想办法抽离出来，形成单独的类或者方法。
 * 
 * 其实也可以直接使用包装类，在controller中访问方法、参数绑定时就传递到包装类，包装类中包含查询条件
 * 根据dao中的动态sql来拼接所需要的查询语句
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	TBookDAOExtend bookDao;
	@Autowired
	TBookDAO tBookDao;
	
	//根据cid 查询图书，以分页的形式返回
	/*
	 * @Cacheable(value="cacheTest")：
	 * 在查询时，会先在缓存中查找数据，当缓存中数据不存在时，才会执行之后方法查找数据库
	 */
	@Cacheable(value="cacheTest")
	public PageBean<TBookExtend> findByCategory(String cid, int pc)
			throws Exception {

		PageBean<TBookExtend> pb = new PageBean<TBookExtend>(); 
		//每页记录数
		int ps = PageConstant.BOOK_PAGE_SIZE;
		//总记录数
		int tr = bookDao.findCountByCid(cid);
		
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		map.put("cid",cid);
		map.put("begin", (pc-1)*ps);
		map.put("end", ps);
		//得到beanlist
		List<TBookExtend> listBook = bookDao.findByCategory(map);
		
		pb.setPc(pc);//当前页码
		pb.setPs(ps);//每页记录数
		pb.setTr(tr);//总记录数
		pb.setBeanList(listBook);//当前页内容
		
		return pb;
	}


	/*
	 * 点击 某个图书图片时  加载单个图书 
	 */
	public TBookExtend load(String bid) throws Exception {
		
		TBookExtend book = bookDao.load(bid);
		
		return book;
	}

	//根据出版社 查询图书list
	public PageBean<TBookExtend> findByPress(String press, int pc)
			throws Exception {
		
		/*
		 * 1.创建PageBean pb
		 * 2.获取ps
		 * 3.获取tr
		 * 4.获取beanlist
		 * 5.赋值给pb
		 */
		PageBean<TBookExtend> pb = new PageBean<TBookExtend>(); 
		//每页记录数
		int ps = PageConstant.BOOK_PAGE_SIZE;
		//总记录数
		int tr = bookDao.findCountByPress(press);
		
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		map.put("press",press);
		map.put("begin", (pc-1)*ps);
		map.put("end", ps);
		//得到beanlist
		List<TBookExtend> listBook = bookDao.findByPress(map);
		
		pb.setPc(pc);//当前页码
		pb.setPs(ps);//每页记录数
		pb.setTr(tr);//总记录数
		pb.setBeanList(listBook);//当前页内容
		
		return pb;
	}
	
	//根据author 查询图书list
	public PageBean<TBookExtend> findByAuthor(String author, int pc)
			throws Exception {
		PageBean<TBookExtend> pb = new PageBean<TBookExtend>(); 
		//每页记录数
		int ps = PageConstant.BOOK_PAGE_SIZE;
		//总记录数
		int tr = bookDao.findCountByAuthor(author);
		
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		map.put("author",author);
		map.put("begin", (pc-1)*ps);
		map.put("end", ps);
		//得到beanlist
		List<TBookExtend> listBook = bookDao.findByAuthor(map);
		
		pb.setPc(pc);//当前页码
		pb.setPs(ps);//每页记录数
		pb.setTr(tr);//总记录数
		pb.setBeanList(listBook);//当前页内容
		
		return pb;
	}
	//根据bname 查询图书list
	public PageBean<TBookExtend> findByName(String bname, int pc)
			throws Exception {
		PageBean<TBookExtend> pb = new PageBean<TBookExtend>(); 
		//每页记录数
		int ps = PageConstant.BOOK_PAGE_SIZE;
		//总记录数
		int tr = bookDao.findCountByBname(bname);
		
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		map.put("bname",bname);
		map.put("begin", (pc-1)*ps);
		map.put("end", ps);
		//得到beanlist
		List<TBookExtend> listBook = bookDao.findByBname(map);
		
		pb.setPc(pc);//当前页码
		pb.setPs(ps);//每页记录数
		pb.setTr(tr);//总记录数
		pb.setBeanList(listBook);//当前页内容
		
		return pb;
	}

	/*
	 * 根据bname 模糊查询 
	 */
	public PageBean<TBookExtend> findByBname(String bname, int pc)
			throws Exception {
		int ps = PageConstant.BOOK_PAGE_SIZE;
		int tr = bookDao.findCountByBname(bname);
		PageBean<TBookExtend> pb = new PageBean<>();
		List<TBookExtend> listBook = new ArrayList<TBookExtend>();
		HashMap<Object,Object> map = new HashMap<>();
		map.put("bname", bname);
		map.put("begin", (pc-1)*ps);
		map.put("end", ps);
		listBook = bookDao.findByBname(map);
		pb.setBeanList(listBook);
		pb.setPs(ps);
		pb.setTr(tr);
		pb.setPc(pc);
		return pb;
	}


	@Override
	public PageBean<TBookExtend> findByCombination(TBookExtend bookExtend,int pc)
			throws Exception {
		TBookExtendVo bookVo = new TBookExtendVo();
		bookVo.setBookExtend(bookExtend);
		int ps = PageConstant.BOOK_PAGE_SIZE;
		int tr = bookDao.findCountByCombination(bookVo);
		PageBean<TBookExtend> pb = new PageBean<TBookExtend>();
		bookVo.setBegin((pc-1)*ps);
		bookVo.setEnd(ps);
		List<TBookExtend> listBook = bookDao.findByCombination(bookVo);
		pb.setBeanList(listBook);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	}


	@Override
	public Integer findBookCountByCid(String cid) throws Exception {
		Integer count = bookDao.findCountByCid(cid);
		return count;
	}


	@Override
	public void delete(String bid) {
		
		bookDao.delete(bid);
	}
	/*
	 * @CacheEvict(value="cacheTest",allEntries=true)：
	 * 在执行增删改操作时，为了保证缓存和数据库信息一致性，会将缓存信息清空。
	 */
	@CacheEvict(value="cacheTest")
	public void edit(TBookExtend book) {
		bookDao.edit(book);
	}


	@Override
	public void add(TBookExtend book) {
		bookDao.add(book);
	}
	
}

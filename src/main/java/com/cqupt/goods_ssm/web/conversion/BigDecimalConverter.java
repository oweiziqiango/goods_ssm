package com.cqupt.goods_ssm.web.conversion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 自定义参数绑定
 * @author Administrator
 */
public class BigDecimalConverter implements Converter<String,BigDecimal>{

	public BigDecimal convert(String source) {
		//将数字字符串转化中BigDecimal
		if(source==null||source=="")
			 return null;
		 return new BigDecimal(source);
	}
}

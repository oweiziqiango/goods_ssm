package com.cqupt.goods_ssm.util;

import java.util.UUID;

public class CommonUtils {
	/**
	 * 返回一个不重复的字符串
	 * @return
	 */
	public String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}

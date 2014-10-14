package com.sinoglobal.app.service.parse;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author ty
 * @createdate 2012-6-26 下午10:32:28
 * @Description: 解析xml
 */
public class ParseJson {
	private static ParseJson parse;

	private ParseJson() {
	}

	static {
		parse = new ParseJson();
	}

	public static ParseJson getInstance() {
		return parse;
	}

	/**
	 * @author ty
	 * @createdate 2012-9-19 下午1:34:24
	 * @Description: 解析配置信息
	 * @param json
	 * @return
	 */
	public Object configuration(String json) {
		return JSON.parseObject(json, Object.class);
	}

	
	/**
	 * @author ty
	 * @createdate 2012-10-10 上午8:50:37
	 * @Description: 解析JSON为对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T parseObject(String json,Class<T> cls){
		return (T) JSON.parseObject(json, cls);
	}
	
}

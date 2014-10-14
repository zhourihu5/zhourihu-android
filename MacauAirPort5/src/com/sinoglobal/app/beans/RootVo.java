package com.sinoglobal.app.beans;

import java.io.Serializable;

/**
 * 
 *
 * @author   ty
 * @Date	 Sep 21, 2012  1:21:21 PM
 * @version  
 */
public class RootVo implements Serializable {
	private static final long serialVersionUID = 770140870626813911L;
	protected int statusCode;		//状态码
	protected String message;		//失败描述信息
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}

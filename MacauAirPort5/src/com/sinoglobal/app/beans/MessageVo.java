package com.sinoglobal.app.beans;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.sinoglobal.app.activity.FlyApplication;

import net.tsz.afinal.annotation.sqlite.Id;


/**
 * 
 * @author zhourihu
 * @createdate 2014年9月17日13:37:14
 * @Description: TODO(用一句话描述该类做什么)航班信息
 *
 */

public class MessageVo implements Serializable{
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
   	
}


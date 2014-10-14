package com.sinoglobal.app.beans;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.sinoglobal.app.activity.FlyApplication;

import net.tsz.afinal.annotation.sqlite.Id;


/**
 * 
 * @author zhourihu
 * @createdate 2014年5月15日 下午2:35:58
 * @Description: TODO(用一句话描述该类做什么) 推送消息
 *
 */

public class PushMessageVo implements Serializable{
//	private static final long serialVersionUID = 6234897826450204223L;
	@Id(column="title")
	private String title;
	@JSONField(name="body_value")
	private String content;
	private String nid;
	@JSONField(name="changed")
	private String time;
	private String language=FlyApplication.language;
	
	public PushMessageVo() {
		// TODO Auto-generated constructor stub
	}
	
	public PushMessageVo(String title, String time) {
		super();
		this.title = title;
		this.time = time;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PushMessageVo other = (PushMessageVo) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
   	
}


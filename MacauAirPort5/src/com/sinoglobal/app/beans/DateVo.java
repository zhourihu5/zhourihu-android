package com.sinoglobal.app.beans;

import java.io.Serializable;

public class DateVo implements Serializable{
	private static final long serialVersionUID = -1221175291407945777L;

	private String year;//年"2013"
	private String month;//月 09-10-11-12
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}

package com.sinoglobal.app.beans;

import java.io.Serializable;


/**
 * 
 * @author zhourihu
 * @createdate 2014年5月15日 下午2:35:58
 * @Description: TODO(用一句话描述该类做什么) 初始化信息 即版本信息
 * 
    {
    "no": "1",
    "updateDate": "2014-07-06",
    "visitUrl": "http://www.baidu.com"
}
 */
public class QuestionNaireVo implements Serializable{
	private String no;
	private String updateDate;
	private String visitUrl;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getVisitUrl() {
		return visitUrl;
	}
	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	
}


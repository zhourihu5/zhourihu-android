package com.sinoglobal.app.beans;

import java.io.Serializable;


/**
 * 
 * @author zhourihu
 * @createdate 2014年5月15日 下午2:35:58
 * @Description: TODO(用一句话描述该类做什么) 初始化信息 即版本信息
 * 
   {
    "versionNo": "1",
    "updateDate": "2014-07-06",
    "visitUrl": "http://www.baidu.com"
}
 */
public class VersionVo implements Serializable{
	private String versionNo;
	private String updateDate;
	private String visitUrl;
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
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


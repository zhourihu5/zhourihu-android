package com.sinoglobal.app.beans;

import java.io.Serializable;

/**
 * 
 * @author zhourihu
 * @createdate 2014年6月27日 下午2:20:21
 * @Description: TODO(用一句话描述该类做什么)  广告
 */
public class BannerVo implements Serializable {

	private String imgUrl; // 图片url
	private String url; //为广告跳转地址
	private String no;//编号
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
    
}

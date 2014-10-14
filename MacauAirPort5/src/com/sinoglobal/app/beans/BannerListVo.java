package com.sinoglobal.app.beans;

import java.util.List;

public class BannerListVo extends RootVo {

	/**
	 * @author sbk
	 * @adddate 2013-9-25 上午11:34:41
	 *	广告列表类
	 */
	private List<BannerVo> list;

	public void setList(List<BannerVo> list) {
		this.list = list;
	}

	public List<BannerVo> getList() {
		return list;
	}

}

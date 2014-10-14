package com.sinoglobal.app.service.parse;

import java.io.IOException;
import java.io.InputStream;

import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.util.TextUtil;

//package cn.itkt.travelsky.service.parse;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import cn.itkt.travelsky.activity.ItktApplication;
//import cn.itkt.travelsky.utils.TextUtil;
//
public class TestJson {
	//
	private static String textToJson(String fileName) {
		try {
			InputStream is = FlyApplication.context.getAssets().open(fileName);
			String json = TextUtil.InputStreamToString(is);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static String getBannerList() {
		try {
			return textToJson("BannerList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getPushMessageList() {
		try {
			return textToJson("MessageList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getQuestionNaire() {
		try {
			return textToJson("QuestionNaire");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getNewVersion() {
		try {
			return textToJson("VersionInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static String getMapData() {
		try {
			return textToJson("MapData");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年7月10日 下午3:26:18
	 * @Description: (用一句话描述该方法做什么)从assets加载关于我们的英文介绍信息
	 * @return
	 *
	 */

	public static CharSequence getIntroduction() {
		try {
			return textToJson("introduction");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static String getFacilityListCn() {
		try {
			return textToJson("facilityList-cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getFacilityListEn() {
		try {
			return textToJson("facilityList-en");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}


package com.sinoglobal.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.sinoglobal.app.util.constants.Constants;

public class Account extends Constants{

	//本地检查用户是否处于登录状态
	public static boolean isLogin(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
		String accountId = sp.getString(SHARED_PREF_LOGIN_ACCOUNT_ID, null);
		if(!TextUtils.isEmpty(accountId)) {
			return true;
		}
		return false;
	}
	
//	//本地获取用户的账号信息
//	public static AccountInfo getLoginAccount(Context context) {
//		if(isLogin(context)) {
//			AccountInfo info = new AccountInfo();
//			SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
//			info.userId = sp.getString(SHARED_PREF_LOGIN_ACCOUNT_ID, null);
//			info.phoneNumber = sp.getString(SHARED_PREF_LOGIN_ACCOUNT_PHONE, null);
//			info.status = sp.getInt(SHARED_PREF_LOGIN_ACCOUNT_STATUS, 0);
//			info.encodedPassword = sp.getString(SHARED_PREF_LOGIN_ACCOUNT_PASSWORD, null);
//			info.userName = sp.getString(SHARED_PREF_LOGIN_ACCOUNT_NAME,null);
//			return info;
//		}
//		return null;
//	}
	
//	//用户是否配置过"自动登录"
//	public static boolean isAutoLoginConfig(Context context) {
//		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
//		return sp.contains(SHARED_PREF_AUTO_LOGIN);
//	}
	
	//返回用户自动登录状态
	public static boolean getAutoLoginStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
		return sp.getBoolean(SHARED_PREF_AUTO_LOGIN, true);
	}
	//保存用户是否自动登录
	public static void saveAutoLoginStatus(Context context, boolean status) {
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
		sp.edit().putBoolean(SHARED_PREF_AUTO_LOGIN, status).commit();
	}
	//保存用户腾讯微博登录状态
	public static void saveTencentStatus(Context context, boolean status) {
		SharedPreferences sp = context.getSharedPreferences(TENCENT_STATE, Activity.MODE_PRIVATE);
		sp.edit().putBoolean(TENCENT_STATE, status).commit();
	}
	//保存用户新浪微博登录状态
	public static void saveSinaStatus(Context context, boolean status) {
		SharedPreferences sp = context.getSharedPreferences(SINA_STATE, Activity.MODE_PRIVATE);
		sp.edit().putBoolean(SINA_STATE, status).commit();
	}
	//返回腾讯微博绑定状态
	public static boolean getTencentStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences(TENCENT_STATE, Activity.MODE_PRIVATE);
		return sp.getBoolean(TENCENT_STATE, false);
	}
	//返回新浪微博绑定状态
	public static boolean getSinaStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SINA_STATE, Activity.MODE_PRIVATE);
		return sp.getBoolean(SINA_STATE, false);
	}
	//清除用户SNS状态
	public static void clearSnsStatus(Context context, boolean status) {
		SharedPreferences sp = context.getSharedPreferences(TENCENT_STATE, Activity.MODE_PRIVATE);
		SharedPreferences sp2 = context.getSharedPreferences(SINA_STATE, Activity.MODE_PRIVATE);
		sp.edit().remove(SHARED_PREF_AUTO_LOGIN).commit();
		sp2.edit().remove(SHARED_PREF_AUTO_LOGIN).commit();
	}
	//清除用户自动登录
	public static void clearAutoLoginStatus(Context context, boolean status) {
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
		sp.edit().remove(SHARED_PREF_AUTO_LOGIN).commit();
	}
	
//	//清除用户登录信息
//	public static void clearLoginInfo(Context context) {
//		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
//		sp.edit().remove(SHARED_PREF_ACCOUNT_ID).commit();
//		sp.edit().remove(SHARED_PREF_LOGIN_ACCOUNT_ID).commit();
//		sp.edit().remove(SHARED_PREF_LOGIN_ACCOUNT_STATUS).commit();
//		sp.edit().remove(SHARED_PREF_LOGIN_ACCOUNT_PHONE).commit();
//		sp.edit().remove(SHARED_PREF_LOGIN_ACCOUNT_PASSWORD).commit();
//	}
	
	
	
	
	
//	public static boolean isNewVersion(Context context) {
//		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
//		
//		int count = sp.getInt(SHARED_PREF_VERSION_START_COUNT + MyApplication.sVersionCode, 0);
//		if(count == 1) {
//			return true;
//		}
//		return false;
//	}
	
//	public static void clearOldRelated(Context context) {
//		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
//		
//		Map<String, ?> map = sp.getAll();
//		Iterator<String> it = map.keySet().iterator();
//		String temp = null;
//		while(it.hasNext()) {
//			temp = it.next();
//			if(temp.startsWith(SHARED_PREF_VERSION_START_COUNT) && !temp.endsWith(MyApplication.sVersionCode+"")) {
//				sp.edit().remove(temp).commit();
//			}
//			if(temp.startsWith(SHARED_PREF_HAVE_ASK_RATE)) {
//				sp.edit().remove(temp).commit();
//			} else if(temp.startsWith(SHARED_PREF_HAVE_ASK_SHORTCUT)) {
//				sp.edit().remove(temp).commit();
//			}
//		}
//	}
	
	
	
	
//	public static boolean isGuideDisplayed(Context context, String tag) {
//		SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
//		return sp.contains(tag + MyApplication.sVersionCode);
//	}
}

package com.sinoglobal.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author ty
 * @createdate 2012-6-4 下午2:41:24
 * @Description: 网络工具类
 */
public class NetWorkUtil {
	//true 得采用代理上网，false不需要
//	public static boolean ONLYWAP;
	//true 没有网络，false有网络
	public static boolean NO_NETWORK;
	
	/**
	 * 获取网络连接NetworkInfo对象
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo;
	}
	
	/**
	 * 网络类型
	 * @param context
	 */
	public static void getNetWorkInfoType(Context context){
		 NetworkInfo networkinfo = getNetworkInfo(context);
		 if(networkinfo!=null){
			 NO_NETWORK=false;
			/*if (networkinfo.getTypeName() == "WIFI") {
				 ONLYWAP = false;
			 }
			 if (networkinfo.getTypeName().equalsIgnoreCase("MOBILE")) {
				 if ("cmwap".equalsIgnoreCase(networkinfo.getExtraInfo())
						|| "3gwap".equalsIgnoreCase(networkinfo.getExtraInfo())
						|| "ctwap".equalsIgnoreCase(networkinfo.getExtraInfo())) {
					 ONLYWAP = true;
				 } else {
					 ONLYWAP = false;
				 }
			 }*/
		 }else{
			 NO_NETWORK=true;
		 }
	}

	public static boolean isNetAvailable(Context context) {
		// TODO Auto-generated method stub
		return null!=getNetworkInfo(context);
	}
}

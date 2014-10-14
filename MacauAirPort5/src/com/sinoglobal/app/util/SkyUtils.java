package com.sinoglobal.app.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.util.constants.Constants;

/**
 * @author ty
 * @createdate 2012-8-17 上午10:29:29
 * @Description: 获取终端id
 */
public class SkyUtils {
	private Context mContext;
	private static SkyUtils instance;
	private static String CHANNEL_NAME; //渠道名
	private static final String CHANNEL_KEY = "UMENG_CHANNEL";
	private static final String PHONE003 = "phone003";
	
	private SkyUtils(Context context) {
		mContext = context;
	}
	
	public static synchronized SkyUtils getInstance(Context context) {
		if (instance == null) {
			instance = new SkyUtils(context);
		}
		return instance;
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 上午10:32:09
	 * @Description: 终端id 
	 * 	格式 “UCID:密文” ，UCID格式是设备ID+“;”+渠道ID 密文是UCID和PHONE001字符串连接后，经过MD5加密
	 * @return
	 */
	public void getTerminalID() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		if(TextUtil.stringIsNull(imei)){  //没有获取IMEI，改用MAC地址
			WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			imei=info.getMacAddress();
			if(TextUtil.stringIsNotNull(imei)){ 
				imei=imei.replaceAll(":", "");
			}else{   //MAC地址，改用动态生成36位的UUID
				imei=SharedPreferenceUtil.getString(mContext, Constants.UUID);  //取出保存的值
				if(TextUtil.stringIsNull(imei)){
					imei=java.util.UUID.randomUUID().toString();
					SharedPreferenceUtil.saveString(mContext, Constants.UUID,imei);  //保存
				}
			}
		}
		String CHANEL_CODE = getChanel(mContext);  //渠道ID将来需要从资源文件中动态获取
		String terminalID = imei + ";" + CHANEL_CODE + PHONE003;
		MD5 md5 = new MD5();
		String md5ID = md5.getMD5ofStr(terminalID);
		FlyApplication.TERMINAL_ID = imei + ";" + CHANEL_CODE + ":" + md5ID;
	}
	/**
	 * 
	 * @author ty
	 * @createdate 2013-3-21 上午10:54:58
	 * @Description: 获得设备号
	 *
	 */
	public void getTerminalPID() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		if(TextUtil.stringIsNull(imei)){  //没有获取IMEI，改用MAC地址
			WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			imei=info.getMacAddress();
			if(TextUtil.stringIsNotNull(imei)){ 
				imei=imei.replaceAll(":", "");
			}else{   //MAC地址，改用动态生成36位的UUID
				imei=SharedPreferenceUtil.getString(mContext, Constants.UUID);  //取出保存的值
				if(TextUtil.stringIsNull(imei)){
					imei=java.util.UUID.randomUUID().toString();
					SharedPreferenceUtil.saveString(mContext, Constants.UUID,imei);  //保存
				}
			}
		}
		String CHANEL_CODE = getChanel(mContext);  //渠道ID将来需要从资源文件中动态获取
		String terminalID = imei + ";" + CHANEL_CODE + PHONE003;
		MD5 md5 = new MD5();
		String md5ID = md5.getMD5ofStr(terminalID);
//		FlyApplication.TERMINAL_ID = imei + ";" + CHANEL_CODE + ":" + md5ID;
		FlyApplication.TERMINAL_ID = imei;
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 上午10:38:48
	 * @Description: 获取渠道名
	 * @param ctx
	 * @return
	 */
	public String getChanel(Context ctx) {
		if(TextUtils.isEmpty(CHANNEL_NAME)){
			try {
				ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(
						ctx.getPackageName(), PackageManager.GET_META_DATA);
				Object value = ai.metaData.get(CHANNEL_KEY);
				if (value != null) {
					CHANNEL_NAME = value.toString();
				}else{
					CHANNEL_NAME="000000";
				}
			} catch (Exception e) {
			}
		}
		return CHANNEL_NAME;
	}
}

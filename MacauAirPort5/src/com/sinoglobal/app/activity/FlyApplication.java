package com.sinoglobal.app.activity;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.frontia.FrontiaApplication;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.PushUtils;
import com.sinoglobal.app.util.SharedPreferenceUtil;
import com.sinoglobal.app.util.SkyUtils;



//创建，终止，内存不足和配置改变
public class FlyApplication extends FrontiaApplication implements Serializable{
	public static final long serialVersionUID = 4656071326644680147L;
	public static boolean IS_EXIST_SDCARD;
	public static String CACHE_DIR_SD;                     //资源缓存的SD卡的文件
	public static String CACHE_DIR_SYSTEM;                 //资源缓存的SYSTEM;卡的文件
	public static final String WHOLESALE_CONV = ".cach";
	public static String LOG;                           //日志保存的SD卡的目录
	public static String AllLOG;
	public static String TERMINAL_ID;
	public static final String DBVERSION_SYS="DBVERSION_SYS";
	public static String USER_ID="";                       //用户的Id
	public static FlyApplication context;
	public static long THE_LAST_TIME;							// 上一次使用时间，


	public static int LOADING_PROCESS=0;       //下载进度
	public static String DOWNLOAD_CLIENT_PATH;  //储存路径

	public static boolean IS_UMENG=true;   //友盟统计控制开关，true允许使用，false则反, 配置后需要在下次启动后生效
	public static String sUniquelyCode;//imei号,手机设备唯一标识
//	public static int screenWidth;
	/**
	 * zh-cn：中文，返回中文字用了编码   en：英文
	 */
	public static String language="zh-cn";
	private static boolean isEnglishDefault;//英语是否是默认语言

	@Override
	public void onCreate() {
		super.onCreate();
		context=this;
		//		initConstants();
		setLanguage();
		initParameter();
		initPushWithApiKey();
	}
	 // 以apikey的方式绑定 启动推送
    private void initPushWithApiKey() {
        // Push: 无账号初始化，用api key绑定
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                PushUtils.getMetaValue(this, "api_key"));
    }
    public void setLanguage() {
    	Configuration config = getResources().getConfiguration();
    	DisplayMetrics metrics = getResources().getDisplayMetrics();
    	language=config.locale.getLanguage();
    	LogUtil.e("language", language);
    	isEnglishDefault=SharedPreferenceUtil.getLanguageState(language.contains("en"));
    	LogUtil.e("isEnglishDefault", isEnglishDefault);
    	List<String>tags=new ArrayList<String>();
    	if(isEnglishDefault){//
			config.locale = Locale.ENGLISH;
			getResources().updateConfiguration(config, metrics);
			language="en";
			tags.add(language);
		}else{
			//config.locale = Locale.SIMPLIFIED_CHINESE;
//			config.locale = Locale.TRADITIONAL_CHINESE;//f繁体中文？
			config.locale = Locale.TAIWAN;//f繁体中文？
			getResources().updateConfiguration(config, metrics);
			language="zh-cn";
			tags.add(language);
		}
    	PushManager.setTags(getApplicationContext(), tags);
    	LogUtil.d("设置语言后 language", language);
    	
	}
    public static boolean isEnglishDefault() {
		return isEnglishDefault;
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/**
	 * @author ty
	 * @createdate 2012-7-2 下午5:42:04
	 * @Description: 初始化常量
	 */
	//	private void initConstants(){
	//		IS_EXIST_SDCARD = Environment.getExternalStorageState().equals(
	//                android.os.Environment.MEDIA_MOUNTED); 
	//        // 判断SD卡是否存在
	//        if (IS_EXIST_SDCARD) {
	//            // 获取跟目录
	//        	File sdDir = Environment.getExternalStorageDirectory().getAbsoluteFile();
	//        	DIR=sdDir.toString();
	//        }else{
	//        	DIR="/mnt/sdcard";
	//        }
	//        CACHE_DIR_SD =DIR+"/fly/cache/";
	//    	LOG=CACHE_DIR_SD+"cache.log";
	//    	AllLOG=CACHE_DIR_SD+"allcache.log";
	////    	CAMERA_ALBUM=DIR+"/dcim/Camera/";
	//    	CACHE_DIR_SYSTEM="data/data/"+getPackageName()+"/fileCache/";
	//    	FileLocalCache.checkDir(CACHE_DIR_SD);
	//    	FileLocalCache.checkDir(CACHE_DIR_SYSTEM);
	//    	NetWorkUtil.getNetWorkInfoType(getApplicationContext());
	//    	MemberInfoVo memberInfo = (MemberInfoVo)FileLocalCache.getSerializableData(Constants.CACHE_DIR_SYSTEM, Constants.FILE_NAME_MEMBERINFO);
	//    	if (memberInfo != null) {
	//			FlyApplication.USER = memberInfo;
	//			if(memberInfo.getUid()!=null){
	//				FlyApplication.USER_ID = memberInfo.getUid();
	//			}else{
	//				FlyApplication.USER_ID = memberInfo.getUserId();
	//			}
	//		}
	//	}

	/**
	 * @author ty
	 * @createdate 2012-7-2 下午5:41:46
	 * @Description: 初始化可变参数
	 */
	private void initParameter(){
		SkyUtils.getInstance(getApplicationContext()).getTerminalPID();
//		screenWidth=this.getResources().getDisplayMetrics().widthPixels;

	}
	/**
	 * 获得imei
	 * 
	 * @param context
	 * @return
	 */
	public static final String getUniquely(Context context) {
		if (TextUtils.isEmpty(sUniquelyCode)) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			sUniquelyCode = tm.getDeviceId();
			if (TextUtils.isEmpty(sUniquelyCode)) {
				WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				sUniquelyCode = wm.getConnectionInfo().getMacAddress();
			}

			if (TextUtils.isEmpty(sUniquelyCode)) {
				sUniquelyCode = getUniquelyCodeFromMacAddress(getLocalMacAddress());
			}
		}
		return sUniquelyCode;
	}
	private static String getUniquelyCodeFromMacAddress(String macAddress) {
		return macAddress.replaceAll(":", "");
	}
	private static String getLocalMacAddress() {
		String macAddr = null;
		char[] buf = new char[1024];
		InputStreamReader isr = null;

		try {
			Process pp = Runtime.getRuntime().exec("busybox ifconfig eth0");
			isr = new InputStreamReader(pp.getInputStream());
			isr.read(buf);
			macAddr = new String(buf);
			int start = macAddr.indexOf("HWaddr") + 7;
			int end = start + 18;
			macAddr = macAddr.substring(start, end);
		} catch (Exception e) {
			macAddr = "Read Exception";
		}
		return macAddr;
	}
}

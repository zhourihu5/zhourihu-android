package com.sinoglobal.app.util;

import android.util.Log;

public class LogUtil {

	private static final String TAG="--LogUtil--";
	public static final boolean FLAG=true;  //测试环境为true,生产环境为false
	
	public static void i(Object message){
		if(FLAG){
			Log.i(TAG, message.toString());
		}
	}
	public static void i(String tag,Object message){
		if(FLAG){
			Log.i(tag, message.toString());
		}
	}
	
	public static void e(Object message){
		if(FLAG){
			Log.e(TAG, message.toString());
		}
	}
	public static void e(String tag,Object message){
		if(FLAG){
			Log.e(tag, message.toString());
		}
	}
	
	public static void d(Object message){
		if(FLAG){
			Log.d(TAG, message.toString());
		}
	}
	public static void d(String tag,Object message){
		if(FLAG){
			Log.d(tag, message.toString());
		}
	}
	
	public static void w(Object message){
		if(FLAG){
			Log.w(TAG, message.toString());
		}
	}
	public static void w(String tag,Object message){
		if(FLAG){
			Log.w(tag, message.toString());
		}
	}
	
	public static void w(Object message,Throwable tr){
		if(FLAG){
			Log.w(TAG, message.toString(),tr);
		}
	}
	public static void w(String tag,Object message,Throwable tr){
		if(FLAG){
			Log.w(tag, message.toString(),tr);
		}
	}
}

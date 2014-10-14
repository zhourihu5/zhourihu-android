package com.sinoglobal.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.util.http.CustomerHttpClient;
import com.aims.mia.R;


/**
 * 常用工具类
 * 
 * @author ty
 * 
 */
public class FlyUtil {
	// 保存所有已经打开的Activity的
	public static ArrayList<Activity> ALL_ACTIVITY = new ArrayList<Activity>();

	/**
	 * Activity通过Intent启动另一个Activity
	 * 
	 * @param context
	 *            当前Activity对象
	 * @param cls
	 *            另一个Activity类
	 */
	public static void intentForward(Context context, Class<?> cls) {
		context.startActivity(new Intent(context, cls));
	}

	public static void intentForward(Context context, Class<?> cls, Intent intent) {
		context.startActivity(intent.setClass(context, cls));
	}

	public static void intentForward(Context context, Intent intent) {
		context.startActivity(intent);
	}

	/**
	 * 有网络：Activity通过Intent启动另一个Activity
	 * 
	 * @param context
	 *            当前Activity对象
	 * @param cls
	 *            另一个Activity类
	 */
	public static void intentForwardNetWork(Context context, Class<?> cls) {
		if (NetWorkUtil.NO_NETWORK) {
			showToastAlertNotNetWorkInfo(context);
			return;
		}
		intentForward(context, cls);
	}

	public static void intentForwardNetWork(Context context, Class<?> cls, Intent intent) {
		if (NetWorkUtil.NO_NETWORK) {
			showToastAlertNotNetWorkInfo(context);
			return;
		}
		intentForward(context, cls, intent);
	}

	public static void intentForwardNetWork(Context context, Intent intent) {
		if (NetWorkUtil.NO_NETWORK) {
			showToastAlertNotNetWorkInfo(context);
			return;
		}
		intentForward(context, intent);
	}

	/**
	 * 有网络，启动另一个Activity,并有返回值
	 * 
	 * @param activity
	 * @param cls
	 * @param intent
	 *            可以为空
	 * @param requestCode
	 *            请求码
	 */
	public static void intentFowardResultNetWork(Activity activity, Intent intent, int requestCode) {
		if (NetWorkUtil.NO_NETWORK) {
			showToastAlertNotNetWorkInfo(activity);
			return;
		}
		intentFowardResult(activity, intent, requestCode);
	}

	/**
	 * 无网络，启动另一个Activity,并有返回值
	 * 
	 * @param activity
	 * @param cls
	 * @param intent
	 *            可以为空
	 * @param requestCode
	 *            请求码
	 */
	public static void intentFowardResult(Activity activity, Intent intent, int requestCode) {
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 判断List集合不为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean listIsNotNull(List<? extends Object> list) {
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断List 集合为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean listIsNull(List<? extends Object> list) {
		if (null == list || list.size() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 退出应用程序, 销毁所有Activity、service、
	 */
	public static void exitApp() {
		LogUtil.w("--- 销毁 Activity size--->>:" + ALL_ACTIVITY.size());
//		 FlyApplication.USER = null;
		 FlyApplication.USER_ID = "";
//		 FlyUtil.clearList(FlyApplication.passengerVoList);
//		 FlyUtil.clearList(FlyApplication.addressList);
		for (Activity ac : ALL_ACTIVITY) {
			if (!ac.isFinishing()) {
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();
		HttpClient httpClient = CustomerHttpClient.getInstance();
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
	/**
	 * 退出应用程序, 销毁所有Activity、service、
	 */
	public static void exitApp(boolean isShutDownConnection) {
		LogUtil.w("--- 销毁 Activity size--->>:" + ALL_ACTIVITY.size());
//		 FlyApplication.USER = null;
		FlyApplication.USER_ID = "";
//		 FlyUtil.clearList(FlyApplication.passengerVoList);
//		 FlyUtil.clearList(FlyApplication.addressList);
		for (Activity ac : ALL_ACTIVITY) {
			if (!ac.isFinishing()) {
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();
		if(isShutDownConnection){
			HttpClient httpClient = CustomerHttpClient.getInstance();
			if (httpClient != null && httpClient.getConnectionManager() != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * 
	 * @author ty
	 * @createdate 2012-6-23 下午7:46:07
	 * @Description: 添加打开的Activity到列表中
	 * @param activity
	 */
	public static void addAppActivity(Activity activity) {
		if (!ALL_ACTIVITY.contains(activity)) {
			ALL_ACTIVITY.add(activity);
		}
	}

	/**
	 * 
	 * @author ty
	 * @createdate 2012-6-23 下午7:47:02
	 * @Description: 移除列表中的Activity
	 * @param activity
	 */
	public static void removeAppActivity(Activity activity) {
		if (ALL_ACTIVITY.contains(activity)) {
			ALL_ACTIVITY.remove(activity);
		}
	}

	/**
	 * Toast提示网络不可用
	 * 
	 * @param context
	 */
	public static void showToastAlertNotNetWorkInfo(Context context) {
		Toast.makeText(context, "没有网络", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 返回主页
	 * 
	 * @param activity
	 */
	public static void goHome(Activity activity) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//		FlyUtil.intentForward(activity, HomeActivity.class, intent);
		activity.finish();
	}

	/**
	 * 清除map集合中的值，释放资源
	 * 
	 * @param map
	 */
	public static void clearMap(Map<? extends Object, ? extends Object> map) {
		if (null != map)
			map.clear();
	}

	/**
	 * 清除List集合中的值，释放资源
	 * 
	 * @param map
	 */
	public static void clearList(List<?> list) {
		if (null != list)
			list.clear();
	}

	/**
	 * 清除ListView的值，释放资源
	 * 
	 * @param listView
	 */
	public static void clearListView(ListView listView) {
		if (null != listView) {
			listView.destroyDrawingCache();
			listView = null;
		}
	}

	/**
	 * 清除GridView的值，释放资源
	 * 
	 * @param gridView
	 */
	public static void clearGridView(GridView gridView) {
		if (null != gridView)
			gridView = null;
	}

	/**
	 * 清除ListView的值，释放资源
	 * 
	 * @param listView
	 */
	public static void clearAdapter(Adapter adapter) {
		if (null != adapter)
			adapter = null;
	}

	/**
	 * 清除对象
	 * 
	 * @param object
	 */
	public static void clearObject(Object object) {
		if (null != object) {
			object = null;
		}
	}

	/**
	 * 清除对象ViewGroup
	 * 
	 * @param object
	 */
	public static void clearViewGroup(ViewGroup object) {
		if (null != object) {
			object.removeAllViews();
			object = null;
		}
	}

	/**
	 * 清除Alert对象
	 * 
	 * @param object
	 */
	public static void dismissAlert(Dialog dialog) {
		if (null != dialog) {
			dialog.dismiss();
			dialog = null;
		}
	}

	/**
	 * dip转像素,不同分辨率适配
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 获取屏幕宽高
	 * 
	 * @param activity
	 * @return
	 */
	public static int[] getDisplay(Activity activity) {
		int[] display = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		display[0] = dm.widthPixels;
		display[1] = dm.heightPixels;
		return display;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-24 上午10:21:12
	 * @Description: 判断用户是否登录
	 * @return
	 */
	public static boolean isLogin() {
		// if(ItktApplication.USER!=null){
		// return true;
		// }
		return false;
	}

	/**
	 * @author ty
	 * @createdate 2012-10-14 下午10:57:05
	 * @Description: 处理涉及需要验证用户登录功能
	 * @param context
	 * @param clazz
	 *            最终目录Activity
	 * @param intent
	 *            为最终目录Activity需要传入参数
	 */
	public static void intentForwardValidateLogin(Context context, Class<?> clazz, Intent intent) {
		if (isLogin()) {
			intentForwardNetWork(context, clazz, intent);
		} else {
			// intent.putExtra(IntentConstants.VALIDATE_INTENT_TARGET, clazz);
			// intent.putExtra(IntentConstants.VALIDATE_LOGIN, true);
			// intent.setClass(context, LoginActivity.class);
			// intentForward(context,intent);
		}
	}

	/**
	 * @author ty
	 * @createdate 2012-10-14 下午10:59:01
	 * @Description: 用户登录成功后再返回
	 * @param context
	 * @param clazz
	 * @param intent
	 */
	public static void loginActivityResult(Activity context, int requestCode, int resultCode) {
		// Intent intent=new Intent(context, LoginActivity.class);
		// intent.putExtra(IntentConstants.ACTIVITY_RETURN, true);
		// intent.putExtra(IntentConstants.ACTIVITY_RETURN_RESULTCODE,
		// resultCode);
		// intentFowardResultNetWork(context,intent,requestCode);
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-26 下午5:12:53
	 * @Description: 加载抖动动画
	 * @param context
	 * @return
	 */
	 public static Animation getShakeAnimation(Context context){
	   return AnimationUtils.loadAnimation(context, R.anim.anim_shake);
	 }

	/**
	 * 友盟——Activity重新启动时调用
	 * 
	 * @param activity
	 */
	// public static void umengOnResume(Activity activity){
	// MobclickAgent.onResume(activity);
	// }

	/**
	 * 友盟——Activity暂停时调用
	 * 
	 * @param activity
	 */
	// public static void umengOnPause(Activity activity){
	// MobclickAgent.onPause(activity);
	// }

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-27 下午8:07:56
	 * @Description: 获取视图在屏幕的坐标
	 * @param view
	 * @return
	 */
	public static int[] getViewXYOnScreen(View view) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}

	/**
	 * @author ty
	 * @createdate 2012-10-15 下午2:13:05
	 * @Description: 获取航空公司图标
	 * @param flightNo
	 *            航空公司二字码 如：HU
	 * @return
	 */
	/*
	 * public static int getFlightIconResId(String airlineNo) { try {
	 * airlineNo=airlineNo.toLowerCase(); airlineNo="icon_"+airlineNo; Field
	 * idField = drawable.class.getDeclaredField(airlineNo); return
	 * idField.getInt(idField); } catch (Exception e) { //
	 * ItktLog.e("没有此航空公司icon："+airlineNo); // return R.drawable.icon_nolimit;
	 * //默认图片 } }
	 *//**
	 * @author ty
	 * @createdate 2012-10-17 下午4:37:46
	 * @Description: 机票折扣-
	 * @return
	 */
	/*
	 * public static String showDiscount(String discount){
	 * if(TextUtil.stringIsNull(discount)){ return Constants.BLANK_ES; } double
	 * dis=Double.parseDouble(discount); if(dis>100){ int i=(int) dis; return
	 * i+Constants.TICKET_FOLDSS; }else if(dis<100){ double num=dis%10;
	 * if(num==0.0d){ num=dis/10; return (int)num+Constants.TICKET_FOLD; }else{
	 * dis=dis/10; return dis+Constants.TICKET_FOLD; } }else{ return
	 * Constants.TICKET_PRICE; } }
	 */
	/**
	 * @author ty
	 * @createdate 2012-12-10 下午5:12:17
	 * @Description: 转换dip为px
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int convertDiptoPx(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}
}

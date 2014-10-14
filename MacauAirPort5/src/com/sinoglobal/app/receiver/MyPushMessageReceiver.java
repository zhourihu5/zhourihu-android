package com.sinoglobal.app.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.activity.MainActivityGroup;
import com.sinoglobal.app.activity.NewsActivity;
import com.sinoglobal.app.beans.PushMessageVo;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.PushUtils;
import com.sinoglobal.app.util.SharedPreferenceUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;

/**
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 * onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调
 * 
 * 返回值中的errorCode，解释如下： 
 *  0 - Success
 *  10001 - Network Problem
 *  30600 - Internal Server Error
 *  30601 - Method Not Allowed 
 *  30602 - Request Params Not Valid
 *  30603 - Authentication Failed 
 *  30604 - Quota Use Up Payment Required 
 *  30605 - Data Required Not Found 
 *  30606 - Request Time Expires Timeout 
 *  30607 - Channel Token Timeout 
 *  30608 - Bind Relation Not Found 
 *  30609 - Bind Number Too Many
 * 
 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 * 
 */
public class MyPushMessageReceiver extends FrontiaPushMessageReceiver {
	/** TAG to Log */
	PushMessageVo messageVo=new PushMessageVo();
	NotificationManager nfm;// 
	public static final String TAG = MyPushMessageReceiver.class
			.getSimpleName();
	/**
	 * 调用PushManager.startWork后，sdk将对push
	 * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
	 * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
	 * 
	 * @param context
	 *            BroadcastReceiver的执行Context
	 * @param errorCode
	 *            绑定接口返回值，0 - 成功
	 * @param appid
	 *            应用id。errorCode非0时为null
	 * @param userId
	 *            应用user id。errorCode非0时为null
	 * @param channelId
	 *            应用channel id。errorCode非0时为null
	 * @param requestId
	 *            向服务端发起的请求id。在追查问题时有用；
	 * @return none
	 */
	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		LogUtil.d(TAG, responseString);

		// 绑定成功，设置已绑定flag，可以有效的减少不必要的绑定请求
		if (errorCode == 0) {
			PushUtils.setBind(context, true);
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//        updateContent(context, responseString);
	}

	/**
	 * 接收透传消息的函数。
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            推送的消息
	 * @param customContentString
	 *            自定义内容,为空或者json字符串
	 */
	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		String messageString = "透传消息 message=\"" + message
				+ "\" customContentString=" + customContentString;
		LogUtil.e(TAG, messageString);

		// 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//        updateContent(context, messageString);
		
		/****************应用有效性控制********如果未付费就退出应用，提示该应用未经授权，不合法****************/
		if("0".equals(message)){
			SharedPreferenceUtil.saveBoolean(context, SharedPreferenceUtil.NOT_VALID, true);
			return;
		}else if("1".equals(message)){
			SharedPreferenceUtil.saveBoolean(context, SharedPreferenceUtil.NOT_VALID, false);
			return;
		}
		boolean notValid=SharedPreferenceUtil.getBoolean(context,SharedPreferenceUtil.NOT_VALID);
		if(notValid){
			throw new RuntimeException(context.getString(R.string.available));
		}
		/************************应用有效性控制结束***********************************************/
		//保存推送消息于数据库
		messageVo.setTitle(message);
		messageVo.setTime(new Date().getTime()/1000+"");
		messageVo.setLanguage(FlyApplication.language);
		messageVo.setContent(message);
		FinalDb fd=FinalDb.create(context);
		fd.delete(messageVo);
		fd.save(messageVo);
		
		if(NewsActivity.handler!=null){
			NewsActivity.handler.sendEmptyMessage(0);
		}
//		if(PushUtils.isBackground(context)){
		if(PushUtils.isApplicationBroughtToBackground(context)){
			nfm= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification= new Notification(R.drawable.ic_launcher,
					messageVo.getTitle(),
					System.currentTimeMillis() );
			//				notification.largeIcon=logoPic;//api 11 以后才开始支持。并且在我的小米3上测试的该属性没有作用
			
			RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.download_notification);
					rv.setTextViewText(R.id.textView1,messageVo.getTitle());
//			rv.setTextViewText(R.id.textView1,"您有新的消息，点击查看");
			rv.setViewVisibility(R.id.progressBar1, View.GONE);
			rv.setViewVisibility(R.id.textView2, View.GONE);
			notification.flags = Notification.FLAG_AUTO_CANCEL; // 点击完之后自动消失
			Intent intent = new Intent();
			//        intent.setClass(context.getApplicationContext(), MainActivityGroup.class);
			//        
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, MainActivityGroup.class); 
			intent.putExtra(IntentConstants.DATA_INT, R.id.home_bottom_rb_news);
			context.getApplicationContext().startActivity(intent);
			//一定要Intent.FLAG_ACTIVITY_NEW_TASK  
//			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  
//			i.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);  
			//PendingIntent 是Intent的包装类  
			PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
			rv.setOnClickPendingIntent(R.id.rl_id,contentIntent); //设置按钮点击事件，这里要放一个PendingIntent ，注意只有在在sdk3.0以上的系统中，通知栏中的按钮点击事件才能响应，这里最好加个条件，sdk3.0以下，不显示按钮
			rv.setOnClickPendingIntent(R.id.textView1,contentIntent); //设置按钮点击事件，这里要放一个PendingIntent ，注意只有在在sdk3.0以上的系统中，通知栏中的按钮点击事件才能响应，这里最好加个条件，sdk3.0以下，不显示按钮
			notification.contentView = rv;
			notification.contentIntent=contentIntent;
//			notification.setLatestEventInfo(context, "澳门机场提醒", messageVo.getContent(), contentIntent);
			
			nfm.notify((int) (messageVo.hashCode()), notification);// 处理通知
		}
	}

	/**
	 * 接收通知点击的函数。注：推送通知被用户点击前，应用无法通过接口获取通知的内容。
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            推送的通知的标题
	 * @param description
	 *            推送的通知的描述
	 * @param customContentString
	 *            自定义内容，为空或者json字符串
	 */
	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		String notifyString = "通知点击 title=\"" + title + "\" description=\""
				+ description + "\" customContent=" + customContentString;
		LogUtil.i(TAG, notifyString);

		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, notifyString);
	}

	/**
	 * setTags() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
	 * @param successTags
	 *            设置成功的tag
	 * @param failTags
	 *            设置失败的tag
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		LogUtil.i(TAG, responseString);
		//        FinalDb fd=FinalDb.create(context);
		//        for(String tag:sucessTags){
		//        	messageVo.setLanguage(tag);
		//        	fd.delete(messageVo);
		//        	fd.save(messageVo);
		//        }
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//        updateContent(context, responseString);
	}

	/**
	 * delTags() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
	 * @param successTags
	 *            成功删除的tag
	 * @param failTags
	 *            删除失败的tag
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onDelTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		LogUtil.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//        updateContent(context, responseString);
	}

	/**
	 * listTags() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示列举tag成功；非0表示失败。
	 * @param tags
	 *            当前应用设置的所有tag。
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId) {
		String responseString = "onListTags errorCode=" + errorCode + " tags="
				+ tags;
		LogUtil.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//        updateContent(context, responseString);
	}

	/**
	 * PushManager.stopWork() 的回调函数。
	 * 
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示从云推送解绑定成功；非0表示失败。
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
		LogUtil.d(TAG, responseString);

		// 解绑定成功，设置未绑定flag，
		if (errorCode == 0) {
			PushUtils.setBind(context, false);
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//        updateContent(context, responseString);
	}

	private void updateContent(Context context, String content) {
		LogUtil.d(TAG, "updateContent");
		String logText = "" + PushUtils.logStringCache;

		if (!logText.equals("")) {
			logText += "\n";
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
		logText += sDateFormat.format(new Date()) + ": ";
		logText += content;

		PushUtils.logStringCache = logText;

		Intent intent = new Intent();
		//        intent.setClass(context.getApplicationContext(), MainActivityGroup.class);
		//        
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, MainActivityGroup.class); 
		intent.putExtra(IntentConstants.DATA_INT, R.id.home_bottom_rb_news);
		context.getApplicationContext().startActivity(intent);
	}
	
}

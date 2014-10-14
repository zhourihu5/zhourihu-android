package com.sinoglobal.app.util.download;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.sinoglobal.app.activity.FlyApplication;
import com.aims.mia.R;

/**
 * @author ty
 * @createdate 2012-11-3 下午9:59:27
 * @Description:下载通知
 */
public class VersionService extends Service {
	private NotificationManager notificationMrg;
	private int old_process = 0;
	private boolean isFirstStart=false;

	public void onCreate() {
		super.onCreate();
		isFirstStart=true;
		notificationMrg = (NotificationManager) this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mHandler.handleMessage(new Message());
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 1为出现，2为隐藏
			if(FlyApplication.LOADING_PROCESS>99){
				notificationMrg.cancel(0);
				stopSelf();
				return;
			}
			if(FlyApplication.LOADING_PROCESS>old_process){
				displayNotificationMessage(FlyApplication.LOADING_PROCESS);
			}
			
			new Thread() {
				public void run() {
					isFirstStart=false;
					Message msg = mHandler.obtainMessage();
					mHandler.sendMessage(msg);
				}
			}.start();
			old_process =FlyApplication.LOADING_PROCESS;
		}
	};

	private void displayNotificationMessage(int count) {

		// Notification的Intent，即点击后转向的Activity
		Intent notificationIntent1 = new Intent(this, this.getClass());
		notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0,notificationIntent1, 0);

		// 创建Notifcation
		Notification notification = new Notification(R.drawable.ic_launcher,
				getString(R.string.app_name)+"：正在下载中，请稍候...", System.currentTimeMillis());
		// 设定Notification出现时的声音，一般不建议自定义
		if(isFirstStart || FlyApplication.LOADING_PROCESS>97){
			notification.defaults |= Notification.DEFAULT_SOUND;// 设定是否振动
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		// 创建RemoteViews用在Notification中
		RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.download_notification_version);
//		contentView.setTextViewText(R.id.n_title,"升级提示");
		contentView.setTextViewText(R.id.n_text, "下载进度："+count+"% ");
		contentView.setProgressBar(R.id.n_progress, 100, count, false);

		notification.contentView = contentView;
		notification.contentIntent = contentIntent1;

		notificationMrg.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}


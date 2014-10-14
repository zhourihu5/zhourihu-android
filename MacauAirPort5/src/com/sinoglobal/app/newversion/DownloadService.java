package com.sinoglobal.app.newversion;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sinoglobal.app.beans.PushMessageVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.NetWorkUtil;
import com.sinoglobal.app.util.TextUtil;
import com.aims.mia.R;

public class DownloadService extends Service {
	String TAG="DownloadService";
	private String saveFileStr = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/"; // apk下载保存地址
	private NotificationManager nfm;
	public static final String APK_NAME="APK_NAME";
	public static final String APK_LOGO_PATH="APK_LOGO_PATH";
	public static final String APK_DOWNLOAD_URL="APK_DOWNLOAD_URL";
	public static final String APK_VERSION_UPDATE="APK_VERSION_UPDATE";//是否是版本更新

	public static final String APK_DOWNLOAD_PROGRESS="APK_DOWNLOAD_PROGRESS";//下载进度


	/**下载结果 是否下载结束*/
	public static final String APK_DOWNLOAD_RESULT="APK_DOWNLOAD_RESULT";
	/**下载完成*/
	public static final int APK_DOWNLOAD_COMPLETED=1;
	/**下载中 更新进度*/
	public static final int APK_DOWNLOAD_ING=0;
	/**新的推送消息*/
	public static final int MSG_NEW_PUSH=2;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		nfm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (!VersionUitls.ExistSDCard()) { // 判断sd卡是否存在
			saveFileStr = getFilesDir().getParent() + File.separator + "file"
					+ File.separator;
		}

	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年5月15日 上午9:57:14
	 * @Description: (用一句话描述该方法做什么) 如果是升级页面过来就发广播通知页面更新
	 *
	 */
	private void FinishActivity() {
		Intent intent = new Intent();
		intent.putExtra(APK_DOWNLOAD_RESULT,APK_DOWNLOAD_COMPLETED);
		intent.setAction(VersionUpgradeActivity.LOAD_ACTION);
		sendBroadcast(intent);
	}
	private void updateActivityProgress(int progress){
		// 更新界面
		Intent intent = new Intent();
		intent.putExtra(APK_DOWNLOAD_RESULT,APK_DOWNLOAD_ING);
		intent.setAction(VersionUpgradeActivity.LOAD_ACTION);
		intent.putExtra(APK_DOWNLOAD_PROGRESS,progress);
		sendBroadcast(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, final int startId) {
		LogUtil.i("DownloadService.onStartCommand flags=="+flags+"startId=="+startId);
		final String apkName=intent.getStringExtra(APK_NAME);
		final String logoPath=intent.getStringExtra(APK_LOGO_PATH);
		final String url=intent.getStringExtra(APK_DOWNLOAD_URL);
		final boolean isVersionUpdate=intent.getBooleanExtra(APK_VERSION_UPDATE, false);//是否是版本升级
		if(!NetWorkUtil.isNetAvailable(this)){
			Toast.makeText(this, "亲，暂无网络哦", 0).show();
			return super.onStartCommand(intent, flags, startId);
		}
		final FinalHttp fh= new FinalHttp();
		fh.download(logoPath,saveFileStr +apkName +"logo.jpg", new AjaxCallBack<File>() {
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);

			}
			@Override
			public void onSuccess(File t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				LogUtil.i("下载apk的logo成功");
				final Bitmap logoPic=BitmapFactory.decodeFile(t.getAbsolutePath());
				notifyNotification(startId, apkName, url, fh, logoPic);
			}
			private void notifyNotification(final int startId, final String apkName,
					final String url, final FinalHttp fh, final Bitmap logoPic) {
				File f = new File(saveFileStr);
				if (!f.exists()) {
					f.mkdirs();
				}
				final Notification notification= new Notification(R.drawable.ic_launcher,
						apkName + "正在下载",
						System.currentTimeMillis() + 20);
				//				notification.largeIcon=logoPic;//api 11 以后才开始支持。并且在我的小米3上测试的该属性没有作用

				final RemoteViews rv = new RemoteViews(getPackageName(), R.layout.download_notification);
				rv.setTextViewText(R.id.textView1,apkName+ "正在下载");
				rv.setImageViewBitmap(R.id.imageview, logoPic);
				notification.flags = Notification.FLAG_AUTO_CANCEL; // 点击完之后自动消失
				notification.contentView = rv;
				nfm.notify(startId, notification);// 处理通知
				fh.download(url, saveFileStr + apkName+".apk", new AjaxCallBack<File>() {
					@Override
					public void onLoading(long count, long current) {
						super.onLoading(count, current);
						LogUtil.i("VersionUpgradeActivity", count + "---" + current);
						int num = (int) (current * 100 / count);
						rv.setTextViewText(R.id.textView2, num + "%");
						rv.setProgressBar(R.id.progressBar1, 100, num, false);
						notification.contentView = rv;
						nfm.notify(startId, notification);// 处理通知
						if(isVersionUpdate){
							updateActivityProgress(num);
						}
					}

					public void onSuccess(File t) {
						if (t.exists() && t.isFile()) {
							rv.setViewVisibility(R.id.textView3, View.VISIBLE);
							notification.tickerText=apkName+ "下载完成";
							notification.when=System.currentTimeMillis() + 20;

							rv.setTextViewText(R.id.textView1, apkName
									+ "下载完成！");
							notification.contentView = rv;
							notification.flags = Notification.FLAG_AUTO_CANCEL; // 点击完之后自动消失
							PendingIntent pendingIntent = PendingIntent.getActivity(
									DownloadService.this, 0, VersionUitls.installApk(t), 0);
							notification.contentIntent = pendingIntent;
							nfm.notify(startId, notification);// 处理通知
							// 安装应用之前判断 该apk是保存位置
							// 1、sd卡 正常安装 2、 手机内存 注：下载到手机内存里面的apk文件 ，需要手动的修改该文件的权限
							if (!VersionUitls.ExistSDCard()) {
								VersionUitls.exec(t.toString());
							}
							stopSelf(startId);
							Intent installApk = VersionUitls.installApk(t);
							installApk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(installApk);
							if(isVersionUpdate){
								FinishActivity();
							}
						}
					}

					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						LogUtil.i(TAG,"下载失败 errorNo=="+errorNo);
						LogUtil.i(TAG,"下载失败 strMsg=="+strMsg);
						super.onFailure(t, errorNo, strMsg);
						nfm.cancel(startId);
						stopSelf(startId);
						Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT)
						.show();
						if(isVersionUpdate){
							FinishActivity();
						}
					};
				});
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				LogUtil.i("下载apk的logo失败");
				int drawableRes=R.drawable.ic_launcher;
				try {
					drawableRes=Integer.parseInt(logoPath);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					drawableRes=R.drawable.ic_launcher;
				}
				final Bitmap logoPic=BitmapFactory.decodeResource(getResources(),drawableRes);//下载logo图失败设置默认图。
				notifyNotification(startId, apkName, url, fh, logoPic);
			}

		});


		return super.onStartCommand(intent, flags, startId);

	}
}

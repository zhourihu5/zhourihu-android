package com.sinoglobal.app.newversion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sinoglobal.app.activity.AbstractActivity;
import com.aims.mia.R;

/**
 * 
 * @author czz
 * @createdate 2014-2-19 下午2:35:00
 * @Description: TODO(版本升级核心类)
 */
public class VersionUpgradeActivity extends AbstractActivity {

	private ProgressBar progressBar1;
	public static final String DOWNLOAD = "downloadUrl"; // Intent 传值所用key
	public String url = ""; // 升级下载apk所用地址 http://down.apk.hiapk.com/mc/d?i=451
	private TextView textView2;
	private Button button1;
	public static final String  LOAD_ACTION = "com.version.upgrade";
	private LoadBroadCastReceiver loadBroadCastReceiver;
	private Intent version_intent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//  隐藏标题栏
		isTemplate=false;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.newversion_view);
		init();
		//注册一个广播接受者
		IntentFilter filter = new IntentFilter(LOAD_ACTION);
		loadBroadCastReceiver = new LoadBroadCastReceiver();
		registerReceiver(loadBroadCastReceiver,filter);
		//开启一个服务
		url=getIntent().getStringExtra(DownloadService.APK_DOWNLOAD_URL);
		Intent service=new Intent(this,DownloadService.class);
		service.putExtra(DownloadService.APK_NAME,getString(R.string.app_name));
		service.putExtra(DownloadService.APK_LOGO_PATH,String.valueOf(R.drawable.ic_launcher));
		service.putExtra(DownloadService.APK_DOWNLOAD_URL, url);
		service.putExtra(DownloadService.APK_VERSION_UPDATE,true);
		
		startService(service);
		
		
	}

	private void init() {
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		textView2 = (TextView) findViewById(R.id.textView2);
		button1 = (Button) findViewById(R.id.button1);
		url = getIntent().getStringExtra(DOWNLOAD);
		progressBar1.setMax(100);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(loadBroadCastReceiver);
		super.onDestroy();
	}

	//屏蔽back键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 
	 * @author czz
	 * @createdate 2014-3-7 下午2:23:23
	 * @Description: TODO(接受广播)
	 */
	public class LoadBroadCastReceiver extends BroadcastReceiver    
	{   
	    @Override  
	    public void onReceive(Context context, Intent intent)   
	    {   
	    	//处理广播
	    	if(intent.getAction().equals(LOAD_ACTION)){
	    		if(intent.getIntExtra(DownloadService.APK_DOWNLOAD_RESULT,DownloadService.APK_DOWNLOAD_ING) == DownloadService.APK_DOWNLOAD_ING){
	    			int intExtra = intent.getIntExtra(DownloadService.APK_DOWNLOAD_PROGRESS, 0);
	    			progressBar1.setProgress(intExtra);
	    			textView2.setText(intExtra+"%");
	    		}
	    		else{
	    			//下载完成之后销毁服务
	    			finish();
	    			if(version_intent!=null){
	    				stopService(version_intent);
	    			}
	    			
	    		}
	    		
	    	}
	    }   
	       
	} 
}
package com.sinoglobal.app.activity;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.sinoglobal.app.beans.VersionVo;
import com.sinoglobal.app.newversion.DownloadService;
import com.sinoglobal.app.newversion.VersionUitls;
import com.sinoglobal.app.newversion.VersionUpgradeActivity;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.util.http.ConnectionUtil;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月9日 下午6:56:09
 * @Description: TODO(用一句话描述该类做什么) 导航主页
 */
public class MainActivityGroup extends ActivityGroup implements OnCheckedChangeListener{
	private LinearLayout container;
	private RadioGroup home_bottom_rg;
	RadioButton rbHome,rbFlight,rbNews,rbService,rbMore;
	private long mExitTime;
	private String versionUrl;
	private String versionDesc;
	public String url = "http://down.apk.hiapk.com/mc/d?i=451";
    int checkedId=R.id.home_bottom_rb_home;
    VersionVo versionVo;
    boolean isToNewsPush;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FlyUtil.addAppActivity(this);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_base);
		checkedId=getIntent().getIntExtra(IntentConstants.DATA_INT, R.id.home_bottom_rb_home);
		if(checkedId== R.id.home_bottom_rb_home){
			getNewVersion(false);
		}
		if(checkedId==R.id.home_bottom_rb_news){
			isToNewsPush=true;
		}
		init();
	}
	private void getNewVersion(final boolean isCache) {
		new AsyncTask<Void, Void, VersionVo>() {
			@Override
			protected VersionVo doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					return RemoteImpl.getInstance().geVersionVo(isCache);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			protected void onPostExecute(VersionVo result) {
				if (result != null) {
					//版本名检测
					boolean isSameVersionName=VersionUitls.getVersionName(MainActivityGroup.this).equals(result.getVersionNo());
					LogUtil.i("初始化","isSameVersionName=="+isSameVersionName);
					//版本名和版本号有一个不同则升级
					if(!isSameVersionName){
						showVersionDialog(result);
					}

				}
			};
		}.execute();
	}
	/**
	 * 
	 * @author czz
	 * @updatedate 2014-3-24 上午11:15:26
	 * @Description: (显示升级的dialog,如需要自定义dialog，可自己定义)
	 * @param result
	 * 
	 */
	private void showVersionDialog(final VersionVo result) {
		LogUtil.i("初始化","result=="+result);
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(getString(R.string.version_update_info));
//		builder.setMessage(result.getUpdateDate());
		builder.setCancelable(false);
		builder.setNegativeButton(getString(R.string.update), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MainActivityGroup.this,
						VersionUpgradeActivity.class);
				intent.putExtra(DownloadService.APK_NAME, getString(R.string.app_name));
				intent.putExtra(DownloadService.APK_LOGO_PATH, String.valueOf(R.drawable.ic_launcher));
				intent.putExtra(DownloadService.APK_DOWNLOAD_URL,ConnectionUtil.DOWNLOAD_URL+result.getVisitUrl());
				startActivity(intent);
			}
		});
		builder.setPositiveButton(R.string.btn_cancle, null);
		builder.show();
	}
	

	private void init() {
		home_bottom_rg = (RadioGroup) findViewById(R.id.home_bottom_rg);
		rbHome=(RadioButton)findViewById(R.id.home_bottom_rb_home);
		rbFlight=(RadioButton)findViewById(R.id.home_bottom_rb_flight);
		rbNews=(RadioButton)findViewById(R.id.home_bottom_rb_news);
		rbService=(RadioButton)findViewById(R.id.home_bottom_rb_service);
		rbMore=(RadioButton)findViewById(R.id.home_bottom_rb_more);
		container = (LinearLayout) findViewById(R.id.container);
		home_bottom_rg.setOnCheckedChangeListener(this);
		home_bottom_rg.check(checkedId);
	}

	public void switchActivity(int checkedId) {
		this.checkedId=checkedId;
		container.removeAllViews();
		Intent intent = null;
		switch (checkedId) {
		case R.id.home_bottom_rb_home:
			intent = new Intent(this, HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			Window subActivity1 = getLocalActivityManager().startActivity("subActivity1", intent);
			container.addView(subActivity1.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			break;
		case R.id.home_bottom_rb_flight:
			intent = new Intent(this, FlightActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			Window subActivity2 = getLocalActivityManager().startActivity("subActivity2", intent);
			container.addView(subActivity2.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			break;

		case R.id.home_bottom_rb_news:
			intent = new Intent(this, NewsActivity.class);
			intent.putExtra(IntentConstants.DATA_BOOL, isToNewsPush);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			Window subActivity3 = getLocalActivityManager().startActivity("subActivity3", intent);
			container.addView(subActivity3.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			isToNewsPush=false;
			break;
		case R.id.home_bottom_rb_service:
			intent = new Intent(this, ServeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			Window subActivity4 = getLocalActivityManager().startActivity("subActivity4", intent);
			container.addView(subActivity4.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			break;
		case R.id.home_bottom_rb_more:
			intent = new Intent(this, MoreActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			Window subActivity5 = getLocalActivityManager().startActivity("subActivity5", intent);
			container.addView(subActivity5.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchActivity(checkedId);
	}

	/**
	 * @author zhourihu
	 * @createdate 2013-10-8 下午2:46:39
	 * @Description: (用一句话描述该方法做什么) 点周back键两次，退出应用
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(!rbHome.isChecked()){
				rbHome.setChecked(true);
				return true;
			}
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
				return true;
			}else{
				FlyUtil.exitApp(false);
			}
			
		}
		return super.onKeyDown(keyCode, event);

	}
	
}
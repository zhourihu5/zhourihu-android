package com.sinoglobal.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.sinoglobal.app.beans.VersionVo;
import com.sinoglobal.app.newversion.DownloadService;
import com.sinoglobal.app.newversion.VersionUitls;
import com.sinoglobal.app.newversion.VersionUpgradeActivity;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.SharedPreferenceUtil;
import com.aims.mia.R;



public class WelcomActivity extends AbstractActivity {
	LinearLayout view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		isTemplate=false;
		super.onCreate(savedInstanceState);
		//获取屏幕的密度
		DisplayMetrics dm = new DisplayMetrics();  
		dm = getResources().getDisplayMetrics();  
		setContentView(R.layout.welcome);
		view = (LinearLayout) findViewById(R.id.l1);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(500);
		view.setAnimation(aa);
		aa.start();
	    aa.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				startIntent();
				
			}
		});
	}
	private void startIntent() {
		Intent intent=new Intent();
		//				if (SharedPreferenceUtil.getBoolean(WelcomActivity.this, "notfirstFlag")) {
		//					intent.setClass(WelcomActivity.this, HomeActivity.class);
		//					intent.putExtra(FlyApplication.FROM_ONE, "fromWelcome");//从欢迎页进入app
		//				} else {
		//					intent.setClass(WelcomActivity.this, LeadActivity.class);//是第一次进入
		//				}
		intent.setClass(WelcomActivity.this,MainActivityGroup.class);
		startActivity(intent);
		//				overridePendingTransition(R.anim.push_left_in,
		//						R.anim.push_left_out);
		SharedPreferenceUtil.saveBoolean(WelcomActivity.this, "notfirstFlag", true);
		finish();
	}
	
}

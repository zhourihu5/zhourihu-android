package com.sinoglobal.app.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;


/**
 * 
 * @author zhourihu
 * @createdate 2013-10-24 下午7:46:02
 * @Description: TODO(用一句话描述该类做什么)加载网页的类
 */
public class WebViewActivity extends AbstractActivity {
	public static final int FILECHOOSER_RESULTCODE = 0;
	public static final int BOOK=1;//旅行预订
	public static final int QUESTIONNAIRE=2;//问卷调查
	public static final int MAP=3;//地图

	String TAG="WebViewActivity";
	WebView mWebView;
	String url,title;
	int iType;
	String nextUrl;
	private ProgressBar progressBar1;
	private ValueCallback<Uri> mUploadMessage;
	String mCameraFilePath;
	//	LinkedList<String>urlList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		url=getIntent().getStringExtra(IntentConstants.DATA_STRING);
		LogUtil.i("webView的url=="+url);
		iType=getIntent().getIntExtra(IntentConstants.DATA_INT, 0);
		title=getIntent().getStringExtra(IntentConstants.DATA_STRING1);
		init();
		LogUtil.i("onCreate");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.i("onResume");
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtil.i("onStart");
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		LogUtil.i("onRestart");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.i("onPause");
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtil.i("onStop");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.i("onDestroy");
	}
	private boolean checkFlash() {  
		PackageManager pm = getPackageManager();  
		List<PackageInfo> infoList = pm  
				.getInstalledPackages(PackageManager.GET_SERVICES);  
		for (PackageInfo info : infoList) {  
			if ("com.adobe.flashplayer".equals(info.packageName)) {  
				return true;  
			}  
		}
		AlertDialog.Builder builder = new Builder(this);
		//		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(getString(R.string.flash_is_need_to_display_the_map));
		//		builder.setMessage(result.getUpdateDate());
		builder.setCancelable(false);
		builder.setNegativeButton(getString(R.string.download), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				installFlashApk();
			}

			private void installFlashApk() {
				Intent installIntent = new Intent(  
						"android.intent.action.VIEW");  
				installIntent.setData(Uri  
						.parse("market://details?id=com.adobe.flashplayer"));  
				startActivity(installIntent); 
				finish();
			}
		});
		builder.setPositiveButton(R.string.btn_cancle, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.show();

		return false;  
	}  

	@SuppressLint("NewApi")
	private void init() {
		templateButtonRight.setVisibility(View.INVISIBLE);
		templateButtonLeft.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		switch (iType) {
		case BOOK:
			titleView.setText(getString(R.string.more_book));
			templateButtonRight.setVisibility(View.VISIBLE);
			break;

		case QUESTIONNAIRE:
			titleView.setText(getString(R.string.home_questionaire));
			//			url="http://www.taiim.com/airport/question/questionnair.html";//测试问卷
			break;
		case MAP:
			titleView.setText(getString(R.string.home_map));
			checkFlash();
			break;
		default:
			titleView.setText(title);
		}

		progressBar1 = (ProgressBar)findViewById(R.id.progressBar1);
		progressBar1.setVisibility(View.GONE);
		mWebView=(WebView) findViewById(R.id.view1);
		mWebView.getSettings().setDefaultTextEncodingName("gbk") ;

		mWebView.requestFocus();// 获取触摸焦点
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 取消滚动条
		mWebView.getSettings().setBuiltInZoomControls(true); // 构建缩放控制
		mWebView.getSettings().setSupportZoom(true); // 设置支持缩放
		//第一种方法控制显示全部页面
//		LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
//
//		1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
//
//		2.NORMAL：正常显示不做任何渲染
//
//		3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
//
//		用SINGLE_COLUMN类型可以设置页面居中显示，页面可以放大缩小，但这种方法不怎么好，有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。
//		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		
		//设置加载进来的页面自适应手机屏幕 
		mWebView.getSettings().setUseWideViewPort(true); //显示全部页面大小
		mWebView.getSettings().setLoadWithOverviewMode(true);
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		int mDensity = metrics.densityDpi;
//
//		//第三种方法：（主要用于平板，针对特定屏幕代码调整分辨率）
//		if (mDensity == 120) {
//			mWebView.getSettings().setDefaultZoom(ZoomDensity.CLOSE);
//		}else if (mDensity == 160) {
//			mWebView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
//		}else if (mDensity == 240) {
//			mWebView.getSettings().setDefaultZoom(ZoomDensity.FAR);
//		}


		mWebView.getSettings().setBlockNetworkImage(false);
		mWebView.getSettings().setBlockNetworkLoads(false);
		mWebView.getSettings().setPluginState(PluginState.ON); 
		mWebView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript
		mWebView.addJavascriptInterface(this, "local_obj");
		mWebView.setWebViewClient(new WebViewClient(){       
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				view.loadUrl(url);       
				return true;       
			}    
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				progressBar1.setVisibility(View.VISIBLE);
				//				showWaitingDialog(getString(R.string.load), false, false, false);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				//				dissmissWaitingDialog();
				progressBar1.setVisibility(View.GONE);
				view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +

						"document.getElementsByTagName('html')[0].innerHTML+'</head>');");

				super.onPageFinished(view, url);
			}
		});   
		//启用数据库 
		mWebView.getSettings().setDatabaseEnabled(true); 
		String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath(); 
		//设置数据库路径 
		mWebView.getSettings().setDatabasePath(dir); 
		//使用localStorage则必须打开 
		mWebView.getSettings().setDomStorageEnabled(true); 
		mWebView.setWebChromeClient(new WebChromeClient(){
			//扩充数据库的容量（在WebChromeClinet中实现） 
			public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, 
					long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) { 
				quotaUpdater.updateQuota(estimatedSize * 2); 
			} 
		});

		mWebView.loadUrl(url);
	}
	@JavascriptInterface
	public void showSource(String html) {
		LogUtil.i("HTML", html);
	}
	@Override
	public void onBackPressed() {
		if(mWebView.canGoBack()){
			mWebView.goBack();
		}else{
			super.onBackPressed();
		}
	}

}

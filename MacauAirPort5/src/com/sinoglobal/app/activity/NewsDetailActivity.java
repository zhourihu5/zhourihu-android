package com.sinoglobal.app.activity;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.sinoglobal.app.adapter.NewsAdapter;
import com.sinoglobal.app.beans.PushMessageVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 新闻详情
 */
public class NewsDetailActivity extends AbstractActivity {
	WebView mWebView;
	PushMessageVo messageVo;
	ProgressBar progressBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		messageVo=(PushMessageVo) getIntent().getSerializableExtra(IntentConstants.DATA_OBJ);
		initView();
		if(TextUtil.stringIsNotNull(messageVo.getNid())){
			loadData();
		}else {
			setView(messageVo);
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
//		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.GONE);
		titleView.setText(messageVo.getTitle());
		progressBar=(ProgressBar)findViewById(R.id.progressBar1);
		mWebView=(WebView)findViewById(R.id.wv);
		mWebView.requestFocus();// 获取触摸焦点
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 取消滚动条
//		mWebView.getSettings().setBuiltInZoomControls(true); // 构建缩放控制
//		mWebView.getSettings().setSupportZoom(true); // 设置支持缩放
		mWebView.getSettings().setBlockNetworkImage(false);
		mWebView.getSettings().setBlockNetworkLoads(false);
		mWebView.getSettings().setDomStorageEnabled(true); 
		mWebView.getSettings().setPluginState(PluginState.ON); 
		mWebView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript
		mWebView.setWebViewClient(new WebViewClient(){       
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				view.loadUrl(url);       
				return true;       
			}    
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				//				dissmissWaitingDialog();
				progressBar.setVisibility(View.GONE);
//				view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
//
//						"document.getElementsByTagName('html')[0].innerHTML+'</head>');");

				super.onPageFinished(view, url);
			}
		});   

	}
	private void loadData() {
		try {
			setView(RemoteImpl.getInstance().getNewsDetail(true, messageVo.getNid()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, PushMessageVo>() {

			@Override
			public PushMessageVo before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getNewsDetail(false,messageVo.getNid());
			}

			@Override
			public void after(PushMessageVo result) {
				setView(result);
				
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub
				
			}
		}.execute();

	}
	private void setView(PushMessageVo result) {
		if(result!=null){
			mWebView.loadDataWithBaseURL(null, result.getContent(), "text/html", "UTF-8", null);
		}
	}
}
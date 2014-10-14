package com.sinoglobal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.widget.SlipButton;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 旅行预订 页面
 */
public class TravelBookActivity extends AbstractActivity implements OnClickListener{
	private static final String TAG = "MoreActivity";
	TextView tvBook;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_book);
		initView();
		addListener();
	}
	private void initView() {
//		templateButtonLeft.setVisibility(View.GONE);
		titleView.setText(getString(R.string.travel_book));
		templateButtonRight.setVisibility(View.INVISIBLE);
        tvBook=(TextView)findViewById(R.id.tv2);
	}
	private void addListener() {
		tvBook.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.tv2://预订旅程 跳转网页
			intent=new Intent(this,WebViewActivity.class);
			String url="http://ebiz.macau-airport.com/ticket.html?lang=mo";//pm給的旅行预订地址
			if("en".equals(FlyApplication.language)){
				url="http://ebiz.macau-airport.com/ticket.html?lang=en";
			}
			intent.putExtra(IntentConstants.DATA_STRING,url);
			intent.putExtra(IntentConstants.DATA_INT, WebViewActivity.BOOK);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		default:
			break;
		}
		
	}

}
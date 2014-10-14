package com.sinoglobal.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 热线界面
 */
public class HotLineActivity extends AbstractActivity implements OnClickListener{
	private static final String TAG = "HomeActivity";
    Button btnTellAirport,btnTellEmergency;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_line);
		initView();
	}
    private void initView() {
//    	templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.home_hotline));
        btnTellAirport=(Button)findViewById(R.id.btn_1);
        btnTellEmergency=(Button)findViewById(R.id.btn_2);
        btnTellAirport.setOnClickListener(this);
        btnTellEmergency.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		String tell="tel:999";
		//"tel:"+phone_number
		switch (v.getId()) {
		case R.id.btn_1://机场电话
			tell="tel:85328861111";
			break;

		case R.id.btn_2://紧急电话
			tell="tel:999";
			break;
		}
		Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(tell));
        startActivity(intent);
	}
}
package com.sinoglobal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么)机场交通
 */
public class TrafficActivity extends AbstractActivity implements OnClickListener{
	private static final String TAG = "HomeActivity";
    Button btnDirectLine,btnAirportLimo,btnBus,btnTaxi,btnCrossBorderCoach;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic);
		initView();
		addListener();
	}
    private void initView() {
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.service_transportation));
		
        btnDirectLine=(Button)findViewById(R.id.btn_1);
        btnAirportLimo=(Button)findViewById(R.id.btn_2);
        btnBus=(Button)findViewById(R.id.btn_3);
        btnTaxi=(Button)findViewById(R.id.btn_4);
        btnCrossBorderCoach=(Button)findViewById(R.id.btn_5);
	}
    private void addListener() {
		btnDirectLine.setOnClickListener(this);
		btnAirportLimo.setOnClickListener(this);
		btnBus.setOnClickListener(this);
		btnTaxi.setOnClickListener(this);
		btnCrossBorderCoach.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this,MapDetailActivity.class);
		String serveName=null;
		switch (v.getId()) {
		case R.id.btn_1://直通快线
			serveName="ztkx";
			break;
		case R.id.btn_2://机场豪华轿车服务
			serveName="jchhjcfw";
			break;
		case R.id.btn_3://公共汽车
			serveName="ggqc";
			break;
		case R.id.btn_4://的士
			serveName="ds";
			break;
		case R.id.btn_5://跨境客运
			serveName="kjky";
			
			break;
		}
		intent.putExtra(IntentConstants.DATA_STRING, serveName);
		FlyUtil.intentForwardNetWork(this, intent);
	}
}
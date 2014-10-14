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
 * @Description: TODO(用一句话描述该类做什么) 服务
 */
public class ServeActivity extends AbstractActivity implements OnClickListener{
	private static final String TAG = "HomeActivity";
	//Airport Service  Express Link Airport Lounge
    Button btnTravelService,btnAirportService,btnExpressLink,btnAirportLounge;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serve);
		initView();
		addListener();
	}
	private void initView() {
		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.GONE);
		titleView.setText(getString(R.string.service));
		
		btnTravelService=(Button)findViewById(R.id.btn_1);
        btnAirportService=(Button)findViewById(R.id.btn_2);
        btnExpressLink=(Button)findViewById(R.id.btn_3);
        btnAirportLounge=(Button)findViewById(R.id.btn_4);
//        btnTraffic=(Button)findViewById(R.id.btn_5);
//        btnVipRoom=(Button)findViewById(R.id.btn_6);
	}
	private void addListener() {
		btnTravelService.setOnClickListener(this);
		btnAirportService.setOnClickListener(this);
		btnExpressLink.setOnClickListener(this);
		btnAirportLounge.setOnClickListener(this);
//		btnTraffic.setOnClickListener(this);
//		btnVipRoom.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_1://旅行服務
			FlyUtil.intentForwardNetWork(this, TravelBookActivity.class);
			break;
		case R.id.btn_2://機場豪华专车服務
			Intent intent=new Intent(this,MapDetailActivity.class);
			String serveName=null;
			serveName="jchhjcfw";
			intent.putExtra(IntentConstants.DATA_STRING, serveName);
			intent.putExtra(IntentConstants.DATA_BOOL, true);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_3://直通快線服務
			serveName="ztkx";
			intent=new Intent(this,MapDetailActivity.class);
			intent.putExtra(IntentConstants.DATA_STRING, serveName);
			intent.putExtra(IntentConstants.DATA_BOOL, true);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_4://機場貴賓室
//			showShortToastMessage(getString(R.string.Coming_soon));
			serveName="jcgbs";//待和肖姐商量
			intent=new Intent(this,MapDetailActivity.class);
			intent.putExtra(IntentConstants.DATA_STRING, serveName);
			intent.putExtra(IntentConstants.DATA_BOOL, true);
			FlyUtil.intentForwardNetWork(this, intent);
			return;
		}
//		intent.putExtra(IntentConstants.DATA_STRING,url);
//		intent.putExtra(IntentConstants.DATA_STRING1,title);
//		FlyUtil.intentForward(this, intent);
		
		
	}

}
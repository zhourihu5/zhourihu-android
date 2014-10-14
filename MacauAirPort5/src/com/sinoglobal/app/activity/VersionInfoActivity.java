package com.sinoglobal.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 版本信息
 */
public class VersionInfoActivity extends AbstractActivity {
	private static final String TAG = "HomeActivity";
    Button btnFacility,btnMap,btnShop,btnRestaurant,btnTraffic,btnVipRoom;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version_info);
		initView();
	}
	private void initView() {
//		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.more_version));

	}

}
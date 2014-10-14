package com.sinoglobal.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinoglobal.app.service.parse.TestJson;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 关于机场
 */
public class AboutActivity extends AbstractActivity {
	TextView tvIntroduction;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
	}
	private void initView() {
//		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.more_about));
		tvIntroduction=(TextView)findViewById(R.id.tv3);
	}

}
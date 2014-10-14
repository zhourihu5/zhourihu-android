package com.sinoglobal.app.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinoglobal.app.activity.AbstractActivity.ItktOtherAsyncTask;
import com.sinoglobal.app.service.imp.RemoteImpl;
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
		loadData();
	}
	private void initView() {
//		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.more_about));
		tvIntroduction=(TextView)findViewById(R.id.tv3);
		
	}
	private void loadData() {
		try {
			tvIntroduction.setText(RemoteImpl.getInstance().getAboutOrDeclareInfo(true,"about" ));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, String>() {

			@Override
			public String before(Void... params) throws Exception {
				return RemoteImpl.getInstance().getAboutOrDeclareInfo(false,"about" );
			}

			@Override
			public void after(String result) {
				if(result!=null){
					tvIntroduction.setText(result);
				}
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub
				
			}
		}.execute();

	}

}
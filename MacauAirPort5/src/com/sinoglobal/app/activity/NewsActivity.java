package com.sinoglobal.app.activity;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sinoglobal.app.adapter.NewsAdapter;
import com.sinoglobal.app.beans.PushMessageVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 新闻
 */
public class NewsActivity extends AbstractActivity {
	private static final String TAG = "HomeActivity";
	RadioGroup radioGroup;
	ListView listView;
	NewsAdapter airportNewsAdapter,importantNewsAdapter;
	List<PushMessageVo> dataAirPort,dataImportant;
	Button btnNewsMore;
	FinalDb fd;
	public static Handler handler;
	RadioButton rbNewsAirport,rbNewsAirport2,rbNewsVip,rbNewsVip2;
	android.widget.CompoundButton.OnCheckedChangeListener changeListener=new android.widget.CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked){
				switch (buttonView.getId()) {
				case R.id.radio1:
					rbNewsAirport2.setChecked(true);
					rbNewsVip.setChecked(false);
					rbNewsVip2.setChecked(false);
					listView.setAdapter(airportNewsAdapter);
					if(airportNewsAdapter.getCount()>0){
						btnNewsMore.setVisibility(View.VISIBLE);
					}
					break;
					
				case R.id.radio2:
					rbNewsVip2.setChecked(true);
					rbNewsAirport.setChecked(false);
					rbNewsAirport2.setChecked(false);
					listView.setAdapter(importantNewsAdapter);
					btnNewsMore.setVisibility(View.GONE);
					
					break;
				}
			}
			
		}
	};
	boolean isToNewsPush;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		fd=FinalDb.create(this);
		isToNewsPush=getIntent().getBooleanExtra(IntentConstants.DATA_BOOL, false);
		initView();
		loadDataImportant();
		loadDataAirport();
		if(handler==null){
			handler=new Handler(){
				public void handleMessage(android.os.Message msg) {
					loadDataImportant();
					};
			};
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.GONE);
		titleView.setText(getString(R.string.news));
		btnNewsMore=(Button)findViewById(R.id.btn_1);
		btnNewsMore.setVisibility(View.GONE);
		radioGroup=(RadioGroup)findViewById(R.id.rangeSeekBar);
		rbNewsAirport=(RadioButton)findViewById(R.id.radio1);
		rbNewsAirport2=(RadioButton)findViewById(R.id.radio3);
		rbNewsVip=(RadioButton)findViewById(R.id.radio2);
		rbNewsVip2=(RadioButton)findViewById(R.id.radio4);
		
		listView=(ListView)findViewById(R.id.listview);
		listView.setDivider(null);
		airportNewsAdapter=new NewsAdapter(this,null);
		importantNewsAdapter=new NewsAdapter(this,null);
		listView.setAdapter(airportNewsAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(NewsActivity.this,NewsDetailActivity.class);
				if(listView.getAdapter()==airportNewsAdapter ){
					intent.putExtra(IntentConstants.DATA_OBJ, airportNewsAdapter.getItem(position));
				}else{
					intent.putExtra(IntentConstants.DATA_OBJ,importantNewsAdapter.getItem(position));
				}
				startActivity(intent);
				
			}
		});
		rbNewsAirport.setOnCheckedChangeListener(changeListener);
		rbNewsVip.setOnCheckedChangeListener(changeListener);
		if(isToNewsPush){
			rbNewsVip.setChecked(true);
			btnNewsMore.setVisibility(View.GONE);
		}else{
			rbNewsAirport.setChecked(true);
		}
//		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				switch (checkedId) {
//				case R.id.radio1://机场新闻
//					listView.setAdapter(airportNewsAdapter);
//					if(airportNewsAdapter.getCount()>0){
//						btnNewsMore.setVisibility(View.VISIBLE);
//					}
//					break;
//                     
//				case R.id.radio2://重要新闻
//					listView.setAdapter(importantNewsAdapter);
//					btnNewsMore.setVisibility(View.GONE);
//					break;
//				}
//
//			}
//		});
		btnNewsMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url="http://www.macau-airport.com/mo/media-centre/press-release";
				if("en".equals(FlyApplication.language)){
					url="http://www.macau-airport.com/en/media-centre/press-release";
				}
				Intent intent=new Intent(NewsActivity.this,WebViewActivity.class);
				intent.putExtra(IntentConstants.DATA_STRING, url);
				FlyUtil.intentForwardNetWork(NewsActivity.this, intent);
			}
		});

	}
	/**加载推送消息*/
	private void loadDataImportant() {
		importantNewsAdapter.setData(
				fd.findAllByWhere(
						PushMessageVo.class,
						"language="+"'"+FlyApplication.language+"'"
						+" order by time desc"));
	}
	/**加载机场新闻*/
	private void loadDataAirport() {
		try {
			setView(RemoteImpl.getInstance().getMessageVos(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, List<PushMessageVo>>() {

			@Override
			public List<PushMessageVo> before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getMessageVos(false);
			}

			@Override
			public void after(List<PushMessageVo> result) {
				setView(result);
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub
				
			}
            
			
		}.execute();

	}
	private void setView(List<PushMessageVo> result) {
		if(result!=null){
			airportNewsAdapter.setData(result);
			btnNewsMore.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onBackPressed() {
		if(isToNewsPush){
			Intent intent=new Intent(this,MainActivityGroup.class);
//			intent.putExtra(IntentConstants.DATA_INT,R.id.home_bottom_rb_more);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//			FlyUtil.exitApp(false);
			startActivity(intent);
		}else{
			super.onBackPressed();
		}
	}
}
package com.sinoglobal.app.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sinoglobal.app.adapter.StringArrayAdapter;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 城市列表
 */
public class CityActivity extends AbstractActivity {
	private static final String TAG = "HomeActivity";
	public static final String FROME="alist";//出发地城市列表
	public static final String TO="dlist";//到达地城市列表
    ListView listView;
    String type;//出发地还是到达地
    StringArrayAdapter cityAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_list);
		type=getIntent().getStringExtra(IntentConstants.DATA_STRING);
		initView();
		loadData();
//		loadData(false);
	}
    private void initView() {
//    	templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		if (FROME.equals(type)) {
			titleView.setText(getString(R.string.city_title_from));
		}else if (TO.equals(type)){
			titleView.setText(getString(R.string.city_title_to));
		}
		listView=(ListView)findViewById(R.id.listview);
		listView.setDivider(null);
		cityAdapter=new StringArrayAdapter(this,null);
        listView.setAdapter(cityAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				Intent data=getIntent();
				data.putExtra(IntentConstants.DATA_STRING, cityAdapter.getItem(position));
				setResult(RESULT_OK,data);
				finish();
			}
		});
	}
    private void loadData() {
    	try {
			cityAdapter.setData(RemoteImpl.getInstance().getCityList(true,type));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, List<String>>() {

			@Override
			public List<String> before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getCityList(false,type);
			}

			@Override
			public void after(List<String> result) {
				if(result!=null){
					cityAdapter.setData(result);
				}
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub
				
			}
		}.execute();

	}
    
}
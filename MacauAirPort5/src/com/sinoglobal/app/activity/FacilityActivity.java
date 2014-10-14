package com.sinoglobal.app.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sinoglobal.app.adapter.FacilityAdapter;
import com.sinoglobal.app.beans.MapDataVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 机场设施
 * 免稅商店，高樂雅咖啡，百福小廚，麥當勞餐廳，食通天，澳門國際機場餐廳，機場貴賓室，行李存放，銀行，吸煙室。
mssd  gyykf 	bfxc	mdlct 	stt	amgjjcct
jcgbs 	xlcf	 yh	xys

肖勇 2014/7/22 10:56:24
http://202.175.83.22:8095/RequestService?actionType=getServiceInfo&type=mssd&language=chinese

 * 
 */
public class FacilityActivity extends AbstractActivity {
	ListView listView;
	FacilityAdapter facilityAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facility);
		initView();
		loadData();
	}
    private void initView() {
//    	templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.service_facility));
		listView=(ListView)findViewById(R.id.listview);
		listView.setDivider(null);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(FacilityActivity.this,MapDetailActivity.class);
				intent.putExtra(IntentConstants.DATA_STRING, facilityAdapter.getItem(position).getClasscode());
				FlyUtil.intentForward(FacilityActivity.this, intent);
			}
		});
	}
    private void loadData() {
		new ItktOtherAsyncTask<Void, Void, List<MapDataVo>>() {

			@Override
			public List<MapDataVo> before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getFacilityList();
			}

			@Override
			public void after(List<MapDataVo> result) {
				if(result!=null){
					facilityAdapter=new FacilityAdapter(FacilityActivity.this, result);
					listView.setAdapter(facilityAdapter);
				}
				
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub
				
			}
		}.execute();

	}
}
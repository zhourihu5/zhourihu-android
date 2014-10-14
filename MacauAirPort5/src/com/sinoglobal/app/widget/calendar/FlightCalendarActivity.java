package com.sinoglobal.app.widget.calendar;

import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.sinoglobal.app.activity.AbstractActivity;
import com.sinoglobal.app.activity.FlightActivity;
import com.sinoglobal.app.activity.FlightListActivity;
import com.sinoglobal.app.activity.MainActivityGroup;
import com.sinoglobal.app.beans.FlightListVo;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;

public class FlightCalendarActivity extends AbstractActivity {

	private ExpandableListView expandableListView;
	private CalendarAdapter adapter;
	String city,type,flightNo;
	//	FlightListVo flightListVo;
	boolean isFromHome=false;//是否从9宫格过来
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandabe_listview);
		isFromHome=getIntent().getBooleanExtra(IntentConstants.DATA_BOOL, false);
		city=getIntent().getStringExtra(IntentConstants.DATA_STRING1);
		type=getIntent().getStringExtra(IntentConstants.DATA_STRING2);
		flightNo=getIntent().getStringExtra(IntentConstants.DATA_STRING3);
		LogUtil.i("city=="+city);
		initView();
		loadData();
		//		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
		//			
		//			@Override
		//			public boolean onGroupClick(ExpandableListView expandableListView, View groupView, int groupPosition,
		//					long id) {
		//				for(int i = 0; i < adapter.getGroupCount(); i++){
		//					if (i != groupPosition && expandableListView.isGroupExpanded(groupPosition)) { 
		//						expandableListView.collapseGroup(i);
		//					}
		//					
		//				}
		//				expandableListView.setSelectedGroup(groupPosition);
		//				expandableListView.expandGroup(groupPosition);
		//				
		//				return false;
		//			}
		//		});
		//		expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
		//			
		//			@Override
		//			public void onGroupCollapse(int arg0) {
		//				
		//				
		//			}
		//		});
	}
	private void initView() {
		templateButtonRight.setVisibility(View.INVISIBLE);
		if(isFromHome){
			templateButtonRight.setVisibility(View.VISIBLE);
//			templateButtonRight.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent=new Intent(FlightCalendarActivity.this,MainActivityGroup.class);
//					intent.putExtra(IntentConstants.DATA_INT,R.id.home_bottom_rb_flight);
//					intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//					startActivity(intent);
//				}
//			});
		}
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		//		adapter = new CalendarAdapter(FlightCalendarActivity.this);
		//		expandableListView.setAdapter(adapter);
		expandableListView.setDivider(null);
		TextView tvEmpty=(TextView)findViewById(R.id.tv1);
		expandableListView.setEmptyView(tvEmpty);
		//		expandableListView.setGroupIndicator(groupIndicator);
		//		expandableListView.setSelectedChild(0, 0, true);

		//当点击另一个条目后收起其他项
		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				for(int i = 0; i < adapter.getGroupCount(); i++){
					if (i != groupPosition && expandableListView.isGroupExpanded(groupPosition)) { 
						expandableListView.collapseGroup(i);
					}

				}
				expandableListView.setSelectedGroup(groupPosition);
			}
		});

	}
	private void loadData() {
		try {
			if(TextUtil.stringIsNotNull(flightNo)){//根据航班号查询
				setView( RemoteImpl.getInstance().searchFlight(true,type,flightNo));
			}else if(TextUtil.stringIsNotNull(city)){//根据城市查询
				setView(RemoteImpl.getInstance().searchFlight(true,type,city));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, FlightListVo>() {

			@Override
			public FlightListVo before(Void... params) throws Exception {
				if(TextUtil.stringIsNotNull(flightNo)){//根据航班号查询
					return RemoteImpl.getInstance().searchFlight(false,type,flightNo);
				}else if(TextUtil.stringIsNotNull(city)){//根据城市查询
					return RemoteImpl.getInstance().searchFlight(false,type,city);
				}
				return null;
			}

			@Override
			public void after(FlightListVo result) {
				setView(result);
			}


			@Override
			public void exception() {
				// TODO Auto-generated method stub
				showBodyInfo(getString(R.string.failure));
			}
		}.execute();

	}
	private void setView(FlightListVo result) {
		if(result!=null){
			boolean isLeave=false;
			if(FlightActivity.DSEARCH.equals(type)||FlightActivity.DFLIGHT_NUMBER.equals(type)){
				isLeave=true;
			}
			if(TextUtil.stringIsNotNull(result.getCity())){
				if(isLeave){
					titleView.setText(getString(R.string.Macau)+"→"+result.getCity());
				}else{
					titleView.setText(result.getCity()+"→"+getString(R.string.Macau));
				}
			}
			/*	flightListVo=result;
		FinalDb db=FinalDb.create(FlightCalendarActivity.this);
		if(result==null){//为空
			flightListVo=new FlightListVo();
			if(TextUtil.stringIsNotNull(flightNo)){//根据航班号查询
				List<FlightVo>list=db.findAllByWhere(FlightVo.class, "flightNumber="+"'"+flightNo+"'");
				flightListVo.setAir(list);
				if(list!=null&&!list.isEmpty()){
					if(isLeave){
						flightListVo.setCity(list.get(0).getDestination());
					}else{
						flightListVo.setCity(list.get(0).getOrigin());
					}
				}
			}else{
				flightListVo.setCity(city);
				List<FlightVo>list=null;
				if(isLeave){
					list=db.findAllByWhere(FlightVo.class, "destination="+"'"+city+"'");
				}else{
					list=db.findAllByWhere(FlightVo.class, "origin="+"'"+city+"'");
				}
				flightListVo.setAir(list);
			}
		}else if(TextUtil.stringIsNotNull(flightNo)){//根据航班号查询
			if(TextUtil.stringIsNotNull(city)&&!city.equals(result.getCity())){//同时包含城市查询 信息
				flightListVo=null;
				return ;
			}
		}

		if(result.getAir()!=null){
			//					db.deleteByWhere(FlightVo.class, null);
			for(FlightVo flightVo:result.getAir()){
				if(isLeave){
					flightVo.setDestination(result.getCity());
				}else{
					flightVo.setOrigin(result.getCity());
				}
				db.delete(flightVo);
				db.save(flightVo);
			}
		}else{
			db.deleteByWhere(FlightVo.class," dateOver<"+"'"+TimeUtil.parseDateToString(TimeUtil.sdf1, new Date())+"'");
		}*/
			//		if(result==null){
			//			showShortToastMessage(getString(R.string.no_data));
			//			finish();
			//		}else if(TextUtil.stringIsNull(result.getCity())||result.getAir()==null||result.getAir().isEmpty()){
			//			showShortToastMessage(getString(R.string.no_data));
			//			finish();
			//		}else{
			//		}
			adapter = new CalendarAdapter(FlightCalendarActivity.this);
			adapter.setFlightListVo(result);
			adapter.setLeave(isLeave);
			expandableListView.setAdapter(adapter);
			expandableListView.expandGroup(0);
			
		}

	}
}

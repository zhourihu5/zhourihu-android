package com.sinoglobal.app.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.view.listener.AbOnListViewListener;
import com.ab.view.pullview.AbPullListView;
import com.sinoglobal.app.adapter.FlightListAdapter;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.beans.MessageVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 航班列表
 */
public class FlightListActivity extends AbstractActivity implements AbOnListViewListener{
	private static final String TAG = "FlightListActivity";
	public static final String TYPE="type";
	public static final String LEAVE="leave";
	public static final String ARRIVE="arrive";

	AbPullListView listView;
	String type="leave";
	FlightListAdapter flightListAdapter;
	TextView tvHeadGate;
	TextView tvHeadCity;
	TextView tvHeadTitle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_list);
		type=getIntent().getStringExtra(TYPE);
		initView();
		addListener();
		loadListData(true);
		loadMessageVo();
	}
	private void loadMessageVo() {
		new AsyncTask<Void, Void, MessageVo>() {

			@Override
			protected MessageVo doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					return RemoteImpl.getInstance().getFlightMessageVo(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			protected void onPostExecute(MessageVo result) {
				if(result!=null&&TextUtil.stringIsNotNull(result.getMessage())){
					tvHeadTitle.setText(result.getMessage());
				}else{
					String time=TimeUtil.parseDateToString(TimeUtil.sdf1,new Date());
					tvHeadTitle.setText(time+"  "+getString(R.string.flight_information));
				}
			};
		}.execute();
		
	}
	private void initView() {
		templateButtonRight.setBackgroundResource(R.drawable.leave_arrive);
		templateButtonRight.setText(null);
		templateButtonRight.setPadding(0, 0, 0, 0);
		listView=(AbPullListView)findViewById(R.id.listview);
		listView.setAbOnListViewListener(this);
		listView.setPullLoadEnable(false);
//		View headView=LayoutInflater.from(this).inflate(R.layout.head_fliht_list, null);
		View headView=findViewById(R.id.l1);
		tvHeadTitle=(TextView) headView.findViewById(R.id.tv1);
		String time=TimeUtil.parseDateToString(TimeUtil.sdf1,new Date());
		tvHeadTitle.setText(time+"  "+getString(R.string.flight_information));
		tvHeadGate=(TextView)headView.findViewById(R.id.tv5);
		tvHeadCity=(TextView)headView.findViewById(R.id.tv3);
//		listView.addHeaderView(headView);
		flightListAdapter=new FlightListAdapter(this, null);
		listView.setAdapter(flightListAdapter);
		setViewState();

	}
	private void setViewState() {
		if(ARRIVE.equals(type)){
			titleView.setText(getString(R.string.flight_arrive_realtime));
			titleView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_flight_arrive, 0, 0, 0);
			tvHeadGate.setVisibility(View.GONE);
			tvHeadCity.setText(getString(R.string.from));
			flightListAdapter.setLeave(false);
			flightListAdapter.setRealTime(true);
		}else if(LEAVE.equals(type)){
			titleView.setText(getString(R.string.flight_departure_realtime));
			titleView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_flight_leave, 0, 0, 0);
			tvHeadGate.setVisibility(View.VISIBLE);
			tvHeadCity.setText(getString(R.string.to));
			flightListAdapter.setLeave(true);
			flightListAdapter.setRealTime(true);
		}else{

		}
	}
	private void addListener() { //点击事件放到adapter里了
		templateButtonRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(LEAVE.equals(type)){
					type=ARRIVE;
				}else{
					type=LEAVE;
				}
				setViewState();
				loadListData(true);
			}
		});
	}
	private void loadListData(boolean isShowDialog) {
		try {
			setView(RemoteImpl.getInstance().getFlightListRealTimeVo(true,type));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void,List<FlightVo>>(isShowDialog) {

			@Override
			public List<FlightVo> before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getFlightListRealTimeVo(false,type);
			}

			@Override
			public void after(List<FlightVo> result) {
				listView.stopLoadMore();
				listView.stopRefresh();
				setView(result);
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub

			}

		}.execute();

	}
	private void setView(List<FlightVo> result) {
		if(result!=null){
			flightListAdapter.setData(result);
		}
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		loadListData(false);
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
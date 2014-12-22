package com.sinoglobal.app.widget.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinoglobal.app.activity.AbstractActivity;
import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.activity.MainActivityGroup;
import com.sinoglobal.app.adapter.FlightListAdapter;
import com.sinoglobal.app.beans.FlightListVo;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.beans.MessageVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;

public class CalendarFlightListActivity extends AbstractActivity implements OnItemClickListener {

	private GridView title_gView;
	private GridView date_gView;
	private Calendar calStartDate = Calendar.getInstance();
	private ArrayList<java.util.Date> week = new ArrayList<Date>();
	private Date date;
	FlightListVo flightListVo;
	ListView listView;
	FlightListAdapter flightListAdapter;
	boolean isLeave=false;//离开澳门 
	TextView tvHeadTitle;
	int itemWidth=FlyApplication.context.getResources().getDisplayMetrics().widthPixels/7;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_search);
		Intent intent = getIntent();
		date = (Date) intent.getSerializableExtra("date");
		itemWidth=intent.getIntExtra(IntentConstants.DATA_INT, itemWidth);
		LogUtil.i("接收到的itemWidth=="+itemWidth);
		flightListVo=(FlightListVo) intent.getSerializableExtra(IntentConstants.DATA_OBJ);
		isLeave=intent.getBooleanExtra(IntentConstants.DATA_BOOL, false);
		calStartDate.setTime(date);
		getWeekOfDayList(calStartDate);
		initView();
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
					String time=TimeUtil.parseDateToString(TimeUtil.sdf1,date);
					tvHeadTitle.setText(time+"  "+getString(R.string.flight_information));
				}
			};
		}.execute();
		
	}

	private void initView() {
		title_gView = (GridView) findViewById(R.id.gv_title);
		title_gView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		TitleGridAdapter titleAdapter = new TitleGridAdapter(this);
		title_gView.setAdapter(titleAdapter);// 设置菜单Adapter

		date_gView = (GridView) findViewById(R.id.gv_data);
		date_gView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		date_gView.setAdapter(new DateGridAdapter(date));
		//		date_gView.setOnItemClickListener(this);
		listView=(ListView)findViewById(R.id.listview);
		//		View headView=LayoutInflater.from(this).inflate(R.layout.head_fliht_list, null);
		View headView=findViewById(R.id.l1);
		TextView tvCity=(TextView)headView.findViewById(R.id.tv3);
		if(isLeave){
			titleView.setText(getString(R.string.Macau)+"→"+flightListVo.getCity());
			tvCity.setText(getString(R.string.to));
		}else{
			titleView.setText(flightListVo.getCity()+"→"+getString(R.string.Macau));
			tvCity.setText(getString(R.string.from));
		}
		tvHeadTitle=(TextView) headView.findViewById(R.id.tv1);
		String time=TimeUtil.parseDateToString(TimeUtil.sdf1,date);
		tvHeadTitle.setText(time+"  "+getString(R.string.flight_information));
		TextView tvStartTime=(TextView)headView.findViewById(R.id.tv4);
		tvStartTime.setText(getString(R.string.flight_start_time));
		TextView tvEndTime=(TextView)headView.findViewById(R.id.tv6);
		tvEndTime.setText(getString(R.string.flight_end_time));
		TextView tvState=(TextView)headView.findViewById(R.id.tv5);
		tvState.setVisibility(View.GONE);

		//		listView.addHeaderView(headView);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int weekDay=calendar.get(Calendar.DAY_OF_WEEK);//默认的周日是 1 周六是7
		ArrayList<FlightVo>arrayList=new ArrayList<FlightVo>();
		if(flightListVo!=null&&flightListVo.getAir()!=null){
			for(FlightVo flightVo:flightListVo.getAir()){
				
				Date dateOver=TimeUtil.parseStringToDate(flightVo.getDateOver());
				Date dateBegin=TimeUtil.parseStringToDate(flightVo.getDateBegin());
				if(dateOver!=null&&dateBegin!=null){
					dateOver.setTime(dateOver.getTime()+24*60*60*1000);
					dateBegin.setTime(dateBegin.getTime()-24*60*60*1000);
					if(dateOver.after(date)&&dateBegin.before(date)){//在航期内
						String weekStr=flightVo.getWeekDay();
						if(weekStr!=null){
							if(parseToWeekDay(weekStr.split(",")).contains(weekDay)){//该航班 在改日有航班
								if(isLeave){
									//									holder.tvCity.setText(flightVo.getDestination());
									flightVo.setDestination(flightListVo.getCity());
								}else{
									flightVo.setOrigin(flightListVo.getCity());
								}
								arrayList.add(flightVo);
							}
						}
					}
				}
			}
		}
		flightListAdapter=new FlightListAdapter(this,arrayList);
		flightListAdapter.setLeave(isLeave);
		//		flightListAdapter.setDate(date);
		//		if(isLeave){//离开澳门
		//			tvHeadGate.setVisibility(View.VISIBLE);
		//			tvHeadCity.setText(getString(R.string.to));
		//		}else{
		//			tvHeadGate.setVisibility(View.GONE);
		//			tvHeadCity.setText(getString(R.string.from));
		//		}
		listView.setAdapter(flightListAdapter);
		if(flightListAdapter.getCount()==0){

		}
		templateButtonRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CalendarFlightListActivity.this,MainActivityGroup.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				intent.putExtra(IntentConstants.DATA_INT,R.id.home_bottom_rb_flight);
				startActivity(intent);
			}
		});
	}
	private ArrayList<Integer> parseToWeekDay( String[] weekStr) {
		ArrayList<Integer>weekdays=new ArrayList<Integer>();
		for(int j=0,size=weekStr.length;j<size;j++){
			try {
				int i=Integer.parseInt(weekStr[j])%7+1;//传给Calendar 以周日开始
				weekdays.add(i);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return weekdays;
	}

	private void getWeekOfDayList(Calendar c) {
		// TODO Auto-generated method stub
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		int weekPosition = 0;
		switch (weekDay) {
		case Calendar.SUNDAY:
			weekPosition = 0;
			break;
		case Calendar.SATURDAY:
			weekPosition = 6;
			break;
		case Calendar.FRIDAY:
			weekPosition = 5;
			break;
		case Calendar.THURSDAY:
			weekPosition = 4;
			break;
		case Calendar.WEDNESDAY:
			weekPosition = 3;
			break;
		case Calendar.TUESDAY:
			weekPosition = 2;
			break;
		case Calendar.MONDAY:
			weekPosition = 1;
			break;

		default:
			break;
		}
		c.add(Calendar.DAY_OF_MONTH, -weekPosition-1);
		for (int i = 1; i <= 7; i++) {
			c.add(Calendar.DAY_OF_MONTH, 1);
			week.add(calStartDate.getTime());
		}
	}

	public class TitleGridAdapter extends BaseAdapter {
		// 将titles存入数组
		int[] titles = new int[] { R.string.Sun, R.string.Mon, R.string.Tue,
				R.string.Wed, R.string.Thu, R.string.Fri, R.string.Sat };

		private Activity activity;

		// construct
		public TitleGridAdapter(Activity a) {
			activity = a;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 设置外观
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//			if(convertView==null){
			//				convertView=LayoutInflater.from(activity).inflate(R.layout.calendar_grid_item, null);
			//			}
			LinearLayout iv = new LinearLayout(activity);
			TextView txtDay = new TextView(activity);
			txtDay.setFocusable(false);
			txtDay.setBackgroundColor(Color.TRANSPARENT);
			iv.setOrientation(1);

			txtDay.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

			int i = (Integer) getItem(position);

			if (i == R.string.Sun) {
				// 周日
				txtDay.setTextColor(Color.RED);
			} else {
				txtDay.setTextColor(Color.BLACK);
			}

			txtDay.setText((Integer) getItem(position));

			iv.addView(txtDay, lp);

			return iv;
		}
	}

	public class DateGridAdapter extends BaseAdapter {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		public DateGridAdapter(Date date) {
			super();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 7;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return week.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=LayoutInflater.from(CalendarFlightListActivity.this).inflate(R.layout.calendar_grid_item, null);
			}
			convertView.findViewById(R.id.iv1).setVisibility(View.GONE);
			convertView.findViewById(R.id.img_id).setVisibility(View.GONE);
			Date i = (Date) getItem(position);
			TextView txtDay=(TextView)convertView.findViewById(R.id.tv);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(i);
			
			txtDay.setLayoutParams(new LinearLayout.LayoutParams(itemWidth,itemWidth));
			if(equalsDate(date, i)){
				txtDay.setTextColor(Color.WHITE);
				txtDay.setBackgroundResource(R.drawable.oval_blue);
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==1){
				txtDay.setTextColor(Color.RED);
				txtDay.setBackgroundResource(0);
			}
			else {
				txtDay.setTextColor(Color.BLACK);
				txtDay.setBackgroundResource(0);
			}
			String time = format.format(i);
			txtDay.setText(time);

			//			iv.addView(txtDay, lp);

			return convertView;
		}

	}

	private Boolean equalsDate(Date date1, Date date2) {
		Calendar calendar1=Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2=Calendar.getInstance();
		calendar2.setTime(date2);
		if(calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR)&&calendar1.get(Calendar.DAY_OF_YEAR)==calendar2.get(Calendar.DAY_OF_YEAR)){
			return true;
		}
		return false;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(week.get(arg2));
		Toast.makeText(CalendarFlightListActivity.this, time, 0).show();

	}

}

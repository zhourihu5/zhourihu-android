package com.sinoglobal.app.widget.calendar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.PSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinoglobal.app.beans.FlightListVo;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;

public class CalendarAdapter extends BaseExpandableListAdapter implements
OnItemClickListener {
	private Calendar calSelected = Calendar.getInstance(); // 选择的日历

	private Context context;
	Resources resources;
	private CalendarGridViewAdapter adapter;
	private FollowGridView toolbarGrid;
	private String time;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 日期格式化
    FlightListVo flightListVo;
    boolean isLeave;//离开澳门
    public void setFlightListVo(FlightListVo flightListVo) {
		this.flightListVo = flightListVo;
		notifyDataSetChanged();
	}
    public void setLeave(boolean isLeave) {
		this.isLeave = isLeave;
		notifyDataSetChanged();
	}
//	public void setFlightDays(ArrayList<Integer> flightDays) {
//		this.flightDays = flightDays;
//		notifyDataSetChanged();
//	}
	public CalendarAdapter(Context context) {
		this.context = context;
		resources = context.getResources();
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.calendar_followview, null);
		}
		toolbarGrid = (FollowGridView) convertView
				.findViewById(R.id.GridView_toolbar);
		adapter = new CalendarGridViewAdapter((Activity) context, (Calendar) getGroup(groupPosition));
//		adapter.setFlightDays(flightDays);
		adapter.setFlightListVo(flightListVo);
		toolbarGrid.setAdapter(adapter);// 设置菜单Adapter
		toolbarGrid.setOnItemClickListener(this);
		toolbarGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return convertView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		//		arg0 = arg0 + 1;
		//		String mou = (month + arg0) % 12 - 1 + "";
		//		return mou;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0 + arg0);
		return calendar;
	}

	@Override
	public int getGroupCount() {
		if(flightListVo==null||flightListVo.getAir()==null
				||TextUtil.stringIsNull(flightListVo.getCity())
				||flightListVo.getAir().isEmpty()){//默认显示三个月的
			return 0;
		}
		long maxValue=System.currentTimeMillis();
		for(FlightVo flightVo:flightListVo.getAir()){
			Date dateOver=TimeUtil.parseStringToDate(flightVo.getDateOver());
			if(dateOver!=null&&dateOver.getTime()>maxValue){
				maxValue=dateOver.getTime();
			}
		}
		Calendar nowCalendar=Calendar.getInstance();
		Calendar overCalendar=Calendar.getInstance();
		overCalendar.setTimeInMillis(maxValue);
		return overCalendar.get(Calendar.MONTH)-nowCalendar.get(Calendar.MONTH)+1;
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.calendar_group_item, null);
		} 
		TextView tv=(TextView) convertView.findViewById(R.id.tvHead);
		tv.setText(sdf.format(((Calendar) getGroup(groupPosition)).getTime()));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> adapterView, View convertView, int position, long id) {
		ImageView imageView=(ImageView) convertView.findViewById(R.id.img_id);
		if(imageView.getVisibility()==View.VISIBLE){
			Date date=null;
			if (adapter != null) {
				date= (Date) adapter.getItem(position);
				calSelected.setTime(date);
				adapter.setSelectedDate(calSelected);
				adapter.notifyDataSetChanged();
				
			}
			Intent intent = new Intent(context, CalendarFlightListActivity.class);
			intent.putExtra("date", date);
			intent.putExtra(IntentConstants.DATA_OBJ, flightListVo);
			intent.putExtra(IntentConstants.DATA_BOOL, isLeave);
			intent.putExtra(IntentConstants.DATA_INT,adapter.getItemWidth());
			LogUtil.i("传过去的itemWidth=="+adapter.getItemWidth());
			context.startActivity(intent);
		}
//		else{
//			Toast.makeText(context,context.getString(R.string.no_flight_day), 0).show();
//		}
	}


}

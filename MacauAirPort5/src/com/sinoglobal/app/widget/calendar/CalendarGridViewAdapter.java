package com.sinoglobal.app.widget.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.beans.FlightListVo;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.util.PxToDp;
import com.sinoglobal.app.util.TimeUtil;
import com.aims.mia.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CalendarGridViewAdapter extends BaseAdapter {

	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
	private Calendar calSelected = Calendar.getInstance(); // 选择的日历
	FlightListVo flightListVo;
	int itemWidth=FlyApplication.context.getResources().getDisplayMetrics().widthPixels/7-PxToDp.dip2px(FlyApplication.context, 8);
	String todayStr=FlyApplication.context.getResources().getString(R.string.today);
	public void setFlightListVo(FlightListVo flightListVo) {
		this.flightListVo = flightListVo;
		todayStr=FlyApplication.context.getResources().getString(R.string.today);
		notifyDataSetChanged();
	}
	public void setSelectedDate(Calendar cal)
	{
		calSelected=cal;
		notifyDataSetChanged();
	}
    public int getItemWidth() {
		return itemWidth;
	}
	private Calendar calToday = Calendar.getInstance(); // 今日
	private int iMonthViewCurrentMonth = 0; // 当前视图月
	// 根据改变的日期更新日历
	// 填充日历控件用
	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
        
		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}
	ArrayList<java.util.Date> titles;
	private ArrayList<java.util.Date> getDates() {

		UpdateStartDateForMonth();

		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();
		//遍历数组
		for (int i = 1; i <= 42; i++) {
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}

	private Activity activity;
	Resources resources;
	// construct
	public CalendarGridViewAdapter(Activity a,Calendar cal) {
		calStartDate=cal;
		activity = a;
		resources=activity.getResources();
		titles = getDates();
		int minWidth=a.getResources().getDimensionPixelSize(R.dimen.text_reversion);
		itemWidth=minWidth>itemWidth?minWidth:itemWidth;
		//		flightDays.add(3);//测试
	}

	public CalendarGridViewAdapter(Activity a) {
		activity = a;
		resources=activity.getResources();
		titles = getDates();
		//		flightDays.add(3);//测试
	}


	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(activity).inflate(R.layout.calendar_grid_item, null);
		}
		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		calCalendar.setTime(myDate);
		int iMonth = calCalendar.get(Calendar.MONTH);
		int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);
		convertView.setBackgroundColor(resources.getColor(R.color.white));
		ImageView imageView=(ImageView) convertView.findViewById(R.id.img_id);
		imageView.setVisibility(View.INVISIBLE);
		TextView txtDay = (TextView) convertView.findViewById(R.id.tv);// 日期
//		int realWidth= txtDay.getLayoutParams().width+PxToDp.dip2px(FlyApplication.context, 8)>itemWidth? txtDay.getLayoutParams().width+PxToDp.dip2px(FlyApplication.context, 8):itemWidth;
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(itemWidth,itemWidth);
//		layoutParams.setMargins(0, PxToDp.dip2px(FlyApplication.context, 8), 0, PxToDp.dip2px(FlyApplication.context, 8));
		txtDay.setLayoutParams(layoutParams);
		if(flightListVo!=null){
			outer:
				for(FlightVo flightVo:flightListVo.getAir()){
					Date overDate=TimeUtil.parseStringToDate(flightVo.getDateOver());
					overDate.setTime(overDate.getTime()+1000*60*60*24);//多加一天
					if(overDate!=null){
						if(myDate.getTime()<overDate.getTime()){
							String[]weekStr=flightVo.getWeekDay().split(",");
							if(parseToWeekDay(weekStr).contains(iDay)){
								imageView.setVisibility(View.VISIBLE);
								break outer;
							}
						}
					}
				}

		}

		//		TextView tvToday = (TextView) convertView.findViewById(R.id.tv2);// 今天

		int day=calCalendar.get(Calendar.DAY_OF_MONTH);
		txtDay.setText(String.valueOf(day));

		if (equalsDate(calSelected,calCalendar)) {// 设置选择的背景颜色
			txtDay.setBackgroundResource(R.drawable.oval_blue);;
			txtDay.setTextColor(Color.WHITE);
		}else
			if(equalsDate(calToday,calCalendar)){// 当前日期
				//			tvToday.setVisibility(View.VISIBLE);
				//			txtDay.setText(FlyApplication.context.getString(R.string.today));
				txtDay.setBackgroundColor(0xffffffff);
				txtDay.setTextColor(resources.getColor(R.color.text_blue));
//				txtDay.setText(todayStr);
				//txtDay.setTextColor(resources.getColor(R.color.text_white));
			}
		
		else if (iDay == 1) {// 判断周六周日
			txtDay.setBackgroundColor(0xffffffff);
			txtDay.setTextColor(resources.getColor(R.color.red));
		}
		else {
			txtDay.setBackgroundColor(0xffffffff);
			txtDay.setTextColor(Color.BLACK);
		}
		//		 判断是否是当前月
		if (iMonth == iMonthViewCurrentMonth) {
			convertView.setVisibility(View.VISIBLE);
		} else {
			convertView.setVisibility(View.GONE);;
		}

		return convertView;
	}
	private Boolean equalsDate(Calendar calendar1, Calendar calendar2) {

		if(calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR)&&calendar1.get(Calendar.DAY_OF_YEAR)==calendar2.get(Calendar.DAY_OF_YEAR)){
			return true;
		}
		return false;

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

}

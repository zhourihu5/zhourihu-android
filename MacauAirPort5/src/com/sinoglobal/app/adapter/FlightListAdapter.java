package com.sinoglobal.app.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinoglobal.app.activity.FlightDetailActivity;
import com.sinoglobal.app.activity.FlightListActivity;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.bitmapUtil.BitmapUtiles;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;


public class FlightListAdapter extends BaseAdapter{
	Activity context;
    List<FlightVo>flightVos;
    FinalBitmap fb;
    boolean isLeave,isRealTime;
//    String logoUrl="http://www.taiim.com/airport/logo/9C.png";//航空公司logo图片地址
    String logoUrl="http://202.175.83.22:8096/logo/9C.png";//航空公司logo图片地址
//    Date date=new Date();
//    public void setDate(Date date) {
//		this.date = date;
//	}
    public FlightListAdapter(Activity contex,List<FlightVo>flightVos) {
		this.context=contex;
		this.flightVos=flightVos;
		fb=FinalBitmap.create(contex);
//		logoUrl="http://202.175.83.22:8096/logo/9C.png?"+TimeUtil.parseDateToString(TimeUtil.sdf1, new Date());
	}
    public void setData(List<FlightVo> flightVos2) {
    	this.flightVos=flightVos2;
    	notifyDataSetChanged();
    }
    public void setLeave(boolean isLeave) {
		this.isLeave = isLeave;
		notifyDataSetChanged();
	}
    public void setRealTime(boolean isRealTime) {
    	this.isRealTime = isRealTime;
    	notifyDataSetChanged();
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return flightVos==null?0:flightVos.size();
	}

	@Override
	public FlightVo getItem(int position) {
		// TODO Auto-generated method stub
		return flightVos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.flight_list_item,null);
			holder=new ViewHolder();
			holder.imgLogo=(ImageView) convertView.findViewById(R.id.iv1);
			holder.imgWeather=(ImageView) convertView.findViewById(R.id.iv2);
			holder.tvCity=(TextView)convertView.findViewById(R.id.tv1);
			holder.tvNumber=(TextView)convertView.findViewById(R.id.tv2);
			holder.tvStartTime=(TextView)convertView.findViewById(R.id.tv3);
			holder.tvEndTime=(TextView)convertView.findViewById(R.id.tv4);
			holder.tvValve=(TextView)convertView.findViewById(R.id.tv5);
			holder.tvState=(TextView)convertView.findViewById(R.id.tv6);
			
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		FlightVo flightVo=getItem(position);
//		flightVo.setDate(TimeUtil.parseDateToString(TimeUtil.sdf1,date));
		//航空公司logo 下载图片
		fb.display(holder.imgLogo, logoUrl.replace("9C", flightVo.getAirline()));
		holder.imgWeather.setVisibility(View.GONE);
//		if(TextUtil.stringIsNotNull(flightVo.getWeatheren())){
//			int i=BitmapUtiles.getDrwableForName(context, flightVo.getWeatheren().toLowerCase().replace(" ", ""));
//			holder.imgWeather.setImageResource(i);
//			LogUtil.e("drawbleRes",""+i);
//			if(i==0){
//				holder.imgWeather.setVisibility(View.GONE);
//			}
//		}else{
//			holder.imgWeather.setVisibility(View.GONE);
//		}
		holder.tvNumber.setText(flightVo.getFlightNumber());//航班号
		holder.tvValve.setText(flightVo.getGate());//闸口
		holder.tvState.setText(flightVo.getStatue());//状态
		if(position%2!=0){
			convertView.setBackgroundColor(context.getResources().getColor(R.color.text_white));
		}else{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.listview_bg_colored));
		}
		if(isRealTime){//實時航班
			if("0".equals(flightVo.getTime())){
				holder.tvStartTime.setText("--");
			}else{
				holder.tvStartTime.setText(flightVo.getTime());
			}
			holder.tvEndTime.setVisibility(View.GONE);
			flightVo.setRealTime(isRealTime);
			if(isLeave){
				holder.tvValve.setVisibility(View.VISIBLE);
				holder.tvCity.setText(flightVo.getDestination());
				flightVo.setLeave(isLeave);
			}else{
				holder.tvValve.setVisibility(View.GONE);
				holder.tvCity.setText(flightVo.getOrigin());//出发地
			}
		}else{//計劃航班
			if("0".equals(flightVo.getArrtime())){
				holder.tvStartTime.setText("--");
			}else{
				holder.tvStartTime.setText(flightVo.getArrtime());//出发时间 接口返回的是到达时间
			}
//			holder.tvEndTime.setText(flightVo.getArrtime());//到达时间
			holder.tvEndTime.setVisibility(View.GONE);
			if("0".equals(flightVo.getDevtime())){
				holder.tvState.setText("--");
			}else{
				holder.tvState.setText(flightVo.getDevtime());//到达时间
			}
			holder.tvValve.setVisibility(View.GONE);
			if(isLeave){
				holder.tvCity.setText(flightVo.getDestination());
				flightVo.setLeave(isLeave);
			}else{
				holder.tvCity.setText(flightVo.getOrigin());//出发地
			}
		}
//		if(isLeave){
//			holder.tvValve.setVisibility(View.VISIBLE);
//			holder.tvCity.setText(flightVo.getDestination());
//			flightVo.setLeave(isLeave);
//		}else if(isRealTime){
//			holder.tvValve.setVisibility(View.GONE);
//			holder.tvCity.setText(flightVo.getOrigin());//出发地
//		}else{
//			holder.tvCity.setText(flightVo.getOrigin());//出发地
//			
//		}
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(isRealTime){//實時航班有詳情頁
//					Intent intent=new Intent(context,FlightDetailActivity.class);
//					intent.putExtra(IntentConstants.DATA_OBJ,getItem(position));
//					FlyUtil.intentForwardNetWork(context, intent);
//				}
//			}
//		});
		return convertView;
	}
   class ViewHolder{
   	  ImageView imgLogo,imgWeather;
   	  TextView tvCity,tvNumber,tvStartTime,tvEndTime,tvValve,tvState;
   }

	  
 }
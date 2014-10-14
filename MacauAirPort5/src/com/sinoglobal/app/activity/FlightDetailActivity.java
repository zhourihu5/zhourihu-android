package com.sinoglobal.app.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinoglobal.app.activity.AbstractActivity.ItktOtherAsyncTask;
import com.sinoglobal.app.beans.BaiduWeatherListVo;
import com.sinoglobal.app.beans.FlightListVo;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.beans.WeatherVo;
import com.sinoglobal.app.beans.BaiduWeatherListVo.BaiduWeatherResults.BaiduWeatherData;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.bitmapUtil.BitmapUtiles;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 航班详情
 */
public class FlightDetailActivity extends AbstractActivity {
	private static final String TAG = "HomeActivity";
	FlightVo flightVo;
	TextView tvFrom,tvTo,tvBoardingGate,tvDepartureYear,tvArriveYear,tvDepartureDay,tvArriveDay,
	tvDepartureTime,tvArriveTime,tvTempratureFrom,tvTempratureTo,tvFlightState,tvAirline;
	ImageView ivWeatherFrom,ivWeatherTo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_detail);
		flightVo=(FlightVo) getIntent().getSerializableExtra(IntentConstants.DATA_OBJ);
		initView();
		loadFlightTime();
		loadWeather();
	}
	private void initView() {
		titleView.setVisibility(View.INVISIBLE);
		tvFrom=(TextView)findViewById(R.id.tv1);
		tvTo=(TextView)findViewById(R.id.tv2);
//		tvCheckInCounter=(TextView)findViewById(R.id.tv3);
		tvBoardingGate=(TextView)findViewById(R.id.tv4);
		tvFlightState=(TextView)findViewById(R.id.tv5);
		tvDepartureYear=(TextView)findViewById(R.id.tvDepartureYear);
		tvArriveYear=(TextView)findViewById(R.id.tvArriveYear);
		tvDepartureDay=(TextView)findViewById(R.id.tvDepartureDay);
		tvArriveDay=(TextView)findViewById(R.id.tvArriveDay);
		tvDepartureTime=(TextView)findViewById(R.id.tvDepartureTime);
		tvArriveTime=(TextView)findViewById(R.id.tvArriveTime);
		tvTempratureFrom=(TextView)findViewById(R.id.tvTempratureFrom);
		tvTempratureTo=(TextView)findViewById(R.id.tvTempratureTo);
		tvAirline=(TextView)findViewById(R.id.tvAirLine);

		ivWeatherFrom=(ImageView)findViewById(R.id.iv1);
		ivWeatherTo=(ImageView)findViewById(R.id.iv2);
//		tvCheckInCounter.setVisibility(View.GONE);
		tvBoardingGate.setVisibility(View.GONE);
		tvTempratureFrom.setVisibility(View.GONE);
		tvTempratureTo.setVisibility(View.GONE);
//		tvAirline.setText(getString(R.string.flight_num)+" : "+flightVo.getFlightNumber());
		tvAirline.setText(flightVo.getFlightNumber());

		if(flightVo.getLeave()){
			tvFrom.setText(getString(R.string.Macau));
			tvTo.setText(flightVo.getDestination());
			tvDepartureTime.setText(flightVo.getTime());
			tvArriveTime.setText(null);
//			if(flightVo.getRealTime()){
//				tvBoardingGate.setVisibility(View.VISIBLE);
//				tvBoardingGate.setText(getString(R.string.boarding_gate)+flightVo.getGate());
//			}
			if(TextUtil.stringIsNotNull(flightVo.getWeatheren())){
				int res=BitmapUtiles.getDrwableForName(this, flightVo.getWeatheren().toLowerCase().replace(" ", "")+"_big");
				ivWeatherTo.setImageResource(res);
				if(res==0){
					ivWeatherTo.setVisibility(View.INVISIBLE);
				}
			}else{
				ivWeatherTo.setVisibility(View.INVISIBLE);
			}
			ivWeatherFrom.setVisibility(View.INVISIBLE);
		}else{
			tvTo.setText(getString(R.string.Macau));
			tvFrom.setText(flightVo.getOrigin());
			tvDepartureTime.setText(null);
			tvArriveTime.setText(flightVo.getTime());
			if(TextUtil.stringIsNotNull(flightVo.getWeatheren())){
				int res=BitmapUtiles.getDrwableForName(this, flightVo.getWeatheren().toLowerCase().replace(" ", "")+"_big");
				ivWeatherFrom.setImageResource(res);
				if(res==0){
					ivWeatherFrom.setVisibility(View.INVISIBLE);
				}
			}else{
				ivWeatherFrom.setVisibility(View.INVISIBLE);
			}
			ivWeatherTo.setVisibility(View.INVISIBLE);
		}
		if(TextUtil.stringIsNotNull(flightVo.getDate())){
			String[]date=flightVo.getDate().split("/");
			tvDepartureYear.setText(date[2]);
			tvArriveYear.setText(date[2]);
//			tvDepartureDay.setText(date[0]+"/"+date[1]);
//			tvArriveDay.setText(date[0]+"/"+date[1]);
			tvDepartureYear.setVisibility(View.GONE);
			tvArriveYear.setVisibility(View.GONE);
//			if("en".equals(FlyApplication.language)){
//				tvDepartureDay.setText(flightVo.getDate());
//				tvArriveDay.setText(flightVo.getDate());
//			}else{
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
//				Date date2=new Date(System.currentTimeMillis());
//				tvDepartureDay.setText(TimeUtil.parseDateToString(sdf, date2));
//				tvArriveDay.setText(TimeUtil.parseDateToString(sdf, date2));
//			}
		}
        String flightStateStr=null;
		if(TextUtil.stringIsNotNull(flightVo.getStatue())){
			flightStateStr=getString(R.string.flight_detail_state)+flightVo.getStatue();
		}
        if(TextUtil.stringIsNotNull(flightVo.getGate())){
        	flightStateStr=flightStateStr+"    "+getString(R.string.boarding_gate)+flightVo.getGate();
        }
        tvFlightState.setText(flightStateStr);
	}

	private void loadFlightTime() {
		
		try {
			if(flightVo.getLeave()){
				setViewFromFlightListVo(RemoteImpl.getInstance().searchFlight(true,FlightActivity.DFLIGHT_NUMBER,flightVo.getFlightNumber())); 
			}else{
				setViewFromFlightListVo(RemoteImpl.getInstance().searchFlight(true,FlightActivity.AFLIGHT_NUMBER,flightVo.getFlightNumber())); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new ItktOtherAsyncTask<Void, Void, FlightListVo>() {

			@Override
			public FlightListVo before(Void... params) throws Exception {
				if(flightVo.getLeave()){
					return RemoteImpl.getInstance().searchFlight(false,FlightActivity.DFLIGHT_NUMBER,flightVo.getFlightNumber());
				}else{
					return RemoteImpl.getInstance().searchFlight(false,FlightActivity.AFLIGHT_NUMBER,flightVo.getFlightNumber());
				}
			}

			@Override
			public void after(FlightListVo result) {
				if(result!=null&&result.getAir()!=null&&!result.getAir().isEmpty()){
					setViewFromFlightListVo(result);
				}
			}


			@Override
			public void exception() {
				// TODO Auto-generated method stub

			}
		}.execute();
	}
	private void setViewFromFlightListVo(FlightListVo result) {
		if(result!=null&&result.getAir()!=null&&!result.getAir().isEmpty()){
			String devTime=result.getAir().get(0).getArrtime();//接口返回的出发时间和到达时间反了，App为了适应接口 
			String arrTime=result.getAir().get(0).getDevtime();
			flightVo.setDevtime(devTime);
			flightVo.setArrtime(arrTime);
			tvDepartureTime.setText(devTime);
			tvArriveTime.setText(arrTime);
			int arrInt=0,depInt=0;
			try {
				arrInt=Integer.parseInt(arrTime.split(":")[0]);
				depInt=Integer.parseInt(devTime.split(":")[0]);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
//			sdf=SimpleDateFormat.getDateInstance();
			Date date2=new Date(System.currentTimeMillis());
			tvDepartureDay.setText(TimeUtil.parseDateToString(sdf, date2));
			if(arrInt>depInt){
				tvArriveDay.setText(TimeUtil.parseDateToString(sdf, date2));
			}else{//跨天时间
				date2.setTime(System.currentTimeMillis()+24*3600*1000);
				tvArriveDay.setText(TimeUtil.parseDateToString(sdf, date2));
			}
		}
	}
	//加载天气
	private void loadWeather() {
		try {
			setWeatherFromBaiduWeatherListVo(RemoteImpl.getInstance().getBaiduWeatherListVo(true));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new AsyncTask<Void, Void, BaiduWeatherListVo>(){

			@Override
			protected BaiduWeatherListVo doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					return RemoteImpl.getInstance().getBaiduWeatherListVo(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			protected void onPostExecute(BaiduWeatherListVo result) {
				setWeatherFromBaiduWeatherListVo(result);
			}

		}.execute();

	}
	private void setWeatherFromBaiduWeatherListVo(
			BaiduWeatherListVo result) {
		if(result!=null){
			try {
				Date date=TimeUtil.parseDate(TimeUtil.sdf1, result.getDate());
				List<BaiduWeatherData>weatherResults= result.getResults().get(0).getWeather_data();
				Calendar nowCalendar=Calendar.getInstance();
				Calendar weatherCalendar=Calendar.getInstance();
				weatherCalendar.setTime(date);
				int index=nowCalendar.get(Calendar.DAY_OF_YEAR)-weatherCalendar.get(Calendar.DAY_OF_YEAR);
				BaiduWeatherData baiduWeatherData=weatherResults.get(index);
				String imgUrl=baiduWeatherData.getDayPictureUrl();
				if(nowCalendar.get(Calendar.HOUR_OF_DAY)>18||nowCalendar.get(Calendar.HOUR_OF_DAY)<6){
					imgUrl=baiduWeatherData.getNightPictureUrl();
				}
				if(flightVo.getLeave()){
					ivWeatherFrom.setVisibility(View.VISIBLE);
					FinalBitmap.create(FlightDetailActivity.this).display(ivWeatherFrom,imgUrl);
				}else{
					ivWeatherTo.setVisibility(View.VISIBLE);
					FinalBitmap.create(FlightDetailActivity.this).display(ivWeatherTo,imgUrl);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
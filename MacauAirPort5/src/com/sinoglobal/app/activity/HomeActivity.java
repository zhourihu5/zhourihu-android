package com.sinoglobal.app.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.tsz.afinal.FinalBitmap;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.sinoglobal.app.adapter.BannerAdapter;
import com.sinoglobal.app.beans.BaiduWeatherListVo;
import com.sinoglobal.app.beans.BaiduWeatherListVo.BaiduWeatherResults;
import com.sinoglobal.app.beans.BaiduWeatherListVo.BaiduWeatherResults.BaiduWeatherData;
import com.sinoglobal.app.beans.BannerVo;
import com.sinoglobal.app.beans.QuestionNaireVo;
import com.sinoglobal.app.beans.WeatherVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;


/**
 * 
 * @author zhourihu
 * @createdate 2014年6月9日 下午6:56:40
 * @Description: TODO(用一句话描述该类做什么) 首页
 */
public class HomeActivity extends AbstractActivity implements OnClickListener{
	private List<BannerVo> banners;

	//	private List<String> banner_info;
//	private ScheduledExecutorService scheduledExecutorService;
	private RadioGroup banner_tab;
	private int currentItem = 0;
	private ViewPager banner_viewpager;
	ArrayList<RadioButton>radioButtons=new ArrayList<RadioButton>();
	LinearLayout btnArrive,btnDeparture,btnAirPlan,btnFacility,btnMap,btnTraffic,btnBook,btnAq,btnHotLine;
	TextView tvTemprature;
	ImageView ivWeather;
	String questionNaireUrl;
	int periods=5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		isTemplate=false;
		super.onCreate(savedInstanceState);
		LogUtil.i("HomeActivity.onCreate");
		setContentView(R.layout.activity_home);
		init();
		loadAdvertisement();
		loadWeather();
		loadQuestionNaireData();
		
	}

	private void init() {
		// 初始化控件
		//		banner_viewpager = (ViewPager) findViewById(R.id.banner_viewpager);
		//		banner_tab = (LinearLayout) findViewById(R.id.banner_tab);
		//		banner_info_tv = (TextView) findViewById(R.id.tv10);
		banner_viewpager = (ViewPager) findViewById(R.id.viewpager);
		((AutoScrollViewPager)banner_viewpager).startAutoScroll(periods*1000);
		((AutoScrollViewPager)banner_viewpager).setInterval(periods*1000);
		
		banner_tab = (RadioGroup) findViewById(R.id.rangeSeekBar);

		btnArrive=(LinearLayout)findViewById(R.id.bt_id1);
		btnDeparture=(LinearLayout)findViewById(R.id.bt_id2);
		btnAirPlan=(LinearLayout)findViewById(R.id.bt_id3);
		btnFacility=(LinearLayout)findViewById(R.id.bt_id4);
		btnMap=(LinearLayout)findViewById(R.id.bt_id5);
		btnTraffic=(LinearLayout)findViewById(R.id.bt_id6);
		btnBook=(LinearLayout)findViewById(R.id.bt_id7);
		btnAq=(LinearLayout)findViewById(R.id.bt_id8);
		btnHotLine=(LinearLayout)findViewById(R.id.bt_id9);
		tvTemprature=(TextView)findViewById(R.id.tv1);
		ivWeather=(ImageView)findViewById(R.id.iv1);
		tvTemprature.setVisibility(View.INVISIBLE);
		ivWeather.setVisibility(View.INVISIBLE);
		
		View view1=findViewById(R.id.view1);
		View view2=findViewById(R.id.view2);
		View view3=findViewById(R.id.view3);
		
		int width=(getResources().getDisplayMetrics().widthPixels-getResources().getDimensionPixelSize(R.dimen.nine_margin)*4)/3;
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(view1.getLayoutParams().width,width);
		view1.setLayoutParams(layoutParams);
		view2.setLayoutParams(layoutParams);
		view3.setLayoutParams(layoutParams);
		addListener();

	}
	//加载天气
	private void loadWeather() {
		try {
			setView(RemoteImpl.getInstance().getBaiduWeatherListVo(true));
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
				setView(result);
			}


		}.execute();

	}
	private void setView(BaiduWeatherListVo result) {
		if(result!=null){
			try {
				Date date=TimeUtil.parseDate(TimeUtil.sdf1, result.getDate());
				List<BaiduWeatherData>weatherResults= result.getResults().get(0).getWeather_data();
				Calendar nowCalendar=Calendar.getInstance();
				Calendar weatherCalendar=Calendar.getInstance();
				weatherCalendar.setTime(date);
				int index=nowCalendar.get(Calendar.DAY_OF_YEAR)-weatherCalendar.get(Calendar.DAY_OF_YEAR);
				BaiduWeatherData baiduWeatherData=weatherResults.get(index);
				//"31 ~ 24℃"
				try {
					String temprature=baiduWeatherData.getTemperature().replace(" ", "").replace("℃", "");
					int tl,th,tm;//低温，高温，交换临时变量
					tl=Integer.parseInt(temprature.split("~")[0]);
					th=Integer.parseInt(temprature.split("~")[1]);
					if(th<tl){//交换
						tm=tl;
						tl=th;
						th=tm;
					}
					tvTemprature.setText(tl+" ~ "+th+"℃");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					tvTemprature.setText(baiduWeatherData.getTemperature());
				}
				String imgUrl=baiduWeatherData.getDayPictureUrl();
				if(nowCalendar.get(Calendar.HOUR_OF_DAY)>18||nowCalendar.get(Calendar.HOUR_OF_DAY)<6){
					imgUrl=baiduWeatherData.getNightPictureUrl();
				}
				FinalBitmap.create(HomeActivity.this).display(ivWeather,imgUrl);
				tvTemprature.setVisibility(View.VISIBLE);
				ivWeather.setVisibility(View.VISIBLE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};
	//加载广告
	private void loadAdvertisement() {
		try {
			setView(RemoteImpl.getInstance().getBannerList(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, List<BannerVo>>() {

			@Override
			public List<BannerVo> before(Void... params) throws Exception {
				return RemoteImpl.getInstance().getBannerList(false);
			}

			@Override
			public void after(List<BannerVo> result) {
				setView(result);
			}


			@Override
			public void exception() {
			}
		}.execute();
	}
	private void setView(List<BannerVo> result) {
		if(result!=null){
			banners = result;
			BannerAdapter adapter = new BannerAdapter(HomeActivity.this,
					banners);
			radioButtons.clear();
			banner_tab.removeAllViews();
			// 广告ViewPager下标
			for (int i = 0; i < banners.size(); i++) {
				RadioButton radioButton=(RadioButton) LayoutInflater.from(HomeActivity.this).inflate(R.layout.radiobutton, null);
				radioButtons.add(radioButton);
//				radioButton.setClickable(false);
				radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							currentItem=radioButtons.indexOf(buttonView);
							if(currentItem!=banner_viewpager.getCurrentItem()){
								banner_viewpager.setCurrentItem(currentItem);
							}
						}

					}
				});
				banner_tab.addView(radioButton);
			}
			banner_viewpager.setAdapter(adapter);

//			scheduledExecutorService = Executors
//					.newSingleThreadScheduledExecutor();
//			// 指定两秒钟切花一张图片
//			scheduledExecutorService.scheduleAtFixedRate(
//					new ViewPageTask(), periods, periods, TimeUnit.SECONDS);
		}
	}

	private void addListener() {
		btnArrive.setOnClickListener(this);
		btnDeparture.setOnClickListener(this);
		btnAirPlan.setOnClickListener(this);
		btnFacility.setOnClickListener(this);
		btnMap.setOnClickListener(this);
		btnTraffic.setOnClickListener(this);
		btnAq.setOnClickListener(this);
		btnHotLine.setOnClickListener(this);
		btnBook.setOnClickListener(this);
		// 添加监听事件(页面改变事件)
		banner_viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			// 1-->2 1页面出去的时候启动的方法
			@Override
			public void onPageSelected(int position) {
				if(currentItem!=position){
					currentItem = position;
					radioButtons.get(currentItem).setChecked(true);
//					handler.sendEmptyMessage(0);
				}
			}

			// 1 -->2 2页面进来后启动的方法
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			// 页面滑动时调用的方法
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		if (scheduledExecutorService != null) {
//			scheduledExecutorService.shutdown();
//		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
//		if (scheduledExecutorService == null) {
//			scheduledExecutorService = Executors
//					.newSingleThreadScheduledExecutor();
//			// 指定两秒钟切花一张图片
//			scheduledExecutorService.scheduleAtFixedRate(new ViewPageTask(),periods,
//					periods, TimeUnit.SECONDS);
//		}
	}

	/**
	 * 
	 * @author sbk
	 * @createdate 2013-9-26 下午1:32:46
	 * @Description: 广告滑动
	 */
//	public class ViewPageTask implements Runnable {
//		@Override
//		public void run() {
//			currentItem = (currentItem + 1) % banners.size();
//			handler.sendEmptyMessage(0);
//		}
//
//	}

	/**
	 * 
	 * @author sbk
	 * @createdate 2013-9-26 下午1:33:31
	 * @Description: 接收消息，滑动广告
	 * 
	 */
//	private  Handler handler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == 0) {
//				if(!radioButtons.get(currentItem).isChecked()){
//					radioButtons.get(currentItem).setChecked(true);
//				}
//			}
//		}
//	};

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.bt_id1://实时抵达澳门
			intent=new Intent(this,FlightListActivity.class);
			intent.putExtra(FlightListActivity.TYPE,FlightListActivity.ARRIVE);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.bt_id2://实时离开澳门
			intent=new Intent(this,FlightListActivity.class);
			intent.putExtra(FlightListActivity.TYPE,FlightListActivity.LEAVE);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.bt_id3://航班时刻
//			intent=new Intent(this,MainActivityGroup.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//			intent.putExtra(IntentConstants.DATA_INT, R.id.home_bottom_rb_flight);
			intent=new Intent(this,FlightActivity.class);
			intent.putExtra(IntentConstants.DATA_BOOL, true);
			FlyUtil.intentForwardNetWork(this, intent);
			//			intent=new Intent(this,FlightActivity.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			//			intent.putExtra(IntentConstants.DATA_BOOL, true);
			//			FlyUtil.intentForwardNetWork(this, intent);

			break;
		case R.id.bt_id4://机场设施
			intent=new Intent(this,FacilityActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.bt_id5://地图
			intent=new Intent(this,MapActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			//			intent=new Intent(this,WebViewActivity.class);
			//			intent.putExtra(IntentConstants.DATA_STRING, "http://www.macau-airport.com/mo/passenger-guide/interactive-guide");
			//			intent.putExtra(IntentConstants.DATA_INT, WebViewActivity.MAP);
			//			FlyUtil.intentForwardNetWork(this, intent);

			break;
		case R.id.bt_id6://机场交通
			intent=new Intent(this,TrafficActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.bt_id7://旅行预订 跳网页
			intent=new Intent(this,TravelBookActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.bt_id8://问卷 可能跳转到网页？
			if(TextUtil.stringIsNotNull(questionNaireUrl)){
				intent=new Intent(this,WebViewActivity.class);
				intent.putExtra(IntentConstants.DATA_STRING,questionNaireUrl);
				intent.putExtra(IntentConstants.DATA_INT, WebViewActivity.QUESTIONNAIRE);
				FlyUtil.intentForwardNetWork(this, intent);
			}else{
				showShortToastMessage(getString(R.string.questionNaire_unavailable));
			}
			break;
		case R.id.bt_id9://热线
			intent=new Intent(this,HotLineActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		}

	}
	/**加载问卷数据*/
	private void loadQuestionNaireData() {
		try {
			setView(RemoteImpl.getInstance().getQuestionNaire(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, QuestionNaireVo>() {

			@Override
			public QuestionNaireVo before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getQuestionNaire(false);
			}

			@Override
			public void after(QuestionNaireVo result) {
				setView(result);

			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub

			}
		}.execute();

	}
	private void setView(QuestionNaireVo result) {
		if(result!=null){
			questionNaireUrl="http://202.175.83.22:8095"+result.getVisitUrl();
			LogUtil.i("questionNaireUrl=="+questionNaireUrl);
		}
	}
}

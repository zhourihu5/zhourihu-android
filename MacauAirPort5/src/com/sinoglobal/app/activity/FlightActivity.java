package com.sinoglobal.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.ValidUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.widget.calendar.FlightCalendarActivity;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:05:35
 * @Description: TODO(用一句话描述该类做什么) 航班首页
 */
public class FlightActivity extends AbstractActivity implements OnClickListener{
	private static final String TAG = "FlightActivity";
	TextView tvArriveRealTime,tvDepartureRealTime;
	Button btnSearch,btnIntroduction;
	RadioGroup rgSelect;
	RadioButton rbFromMacau1, rbFromMacau2, rbToMacau1,rbToMacau2;//澳门出发，澳门到达 
	TextView tvCity;
	EditText etFlightNo;
	final int CITY_REQUEST=0;//城市列表
	String type;
	//分别为城市查询的 到达澳门和离开澳门 ，航班号查询的到达澳门和离开澳门
	public static final String ASEARCH="asearch",DSEARCH="dsearch",DFLIGHT_NUMBER="lflightNumber",AFLIGHT_NUMBER="aflightNumber";
	boolean isFromHome=false;//从9宫格切换过来
	String city;
	CompoundButton.OnCheckedChangeListener checkedChangeListener= new CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton button, boolean isChecked) {
			if(isChecked){
				switch (button.getId()) {
				case R.id.radio1://澳门出发
					rbFromMacau2.setChecked(true);
					rbToMacau1.setChecked(false);
					rbToMacau2.setChecked(false);
					tvCity.setHint(getString(R.string.flight_city_to));
					tvCity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_to, 0,R.drawable.icon_arrow_right, 0);
					type=DSEARCH;
					break;
				case R.id.radio2://到达澳门
					rbToMacau2.setChecked(true);
					rbFromMacau1.setChecked(false);
					rbFromMacau2.setChecked(false);
					tvCity.setHint(getString(R.string.flight_city_from));
					tvCity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_from, 0, R.drawable.icon_arrow_right, 0);
					type=ASEARCH;
					break;
				default:
					break;
				}
				
			}
			
		}
		
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight);
	    isFromHome=getIntent().getBooleanExtra(IntentConstants.DATA_BOOL, false);
	    
		initView();
		addListener();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void initView() {
		templateButtonLeft.setVisibility(View.GONE);
//		titleView.setText(getString(R.string.flight_title));
		titleView.setText(getString(R.string.home_air_plan));
		templateButtonRight.setVisibility(View.GONE);
		if(isFromHome){
//			titleView.setText(getString(R.string.home_air_plan));
			templateButtonLeft.setVisibility(View.VISIBLE);
			findViewById(R.id.rl_id).setVisibility(View.GONE);
			templateButtonRight.setVisibility(View.INVISIBLE);
		}
		tvArriveRealTime=(TextView)findViewById(R.id.btn_1);
		tvDepartureRealTime=(TextView)findViewById(R.id.btn_2);
		tvCity=(TextView)findViewById(R.id.edit1);
		etFlightNo=(EditText)findViewById(R.id.edit2);
	
		btnSearch=(Button)findViewById(R.id.btn_3);
		btnSearch.requestFocus();
		btnIntroduction=(Button)findViewById(R.id.btn_4);
		rgSelect=(RadioGroup)findViewById(R.id.rangeSeekBar);
		rbFromMacau1=(RadioButton)findViewById(R.id.radio1);
		rbFromMacau2=(RadioButton)findViewById(R.id.radio3);
		rbToMacau1=(RadioButton)findViewById(R.id.radio2);
		rbToMacau2=(RadioButton)findViewById(R.id.radio4);
		
	}
	private void addListener() {
		tvArriveRealTime.setOnClickListener(this);
		tvDepartureRealTime.setOnClickListener(this);
		tvCity.setOnClickListener(this);
		//		tvFlightNo.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnIntroduction.setOnClickListener(this);
		mainBody.setOnClickListener(this);
		findViewById(R.id.l1).setOnClickListener(this);
//		rgSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				switch (checkedId) {
//				case R.id.radio1://澳门出发
//					tvCity.setHint(getString(R.string.flight_city_to));
//					tvCity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_to, 0,R.drawable.icon_arrow_right, 0);
//					type=DSEARCH;
//					break;
//				case R.id.radio2://到达澳门
//					tvCity.setHint(getString(R.string.flight_city_from));
//					tvCity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_from, 0, R.drawable.icon_arrow_right, 0);
//					type=ASEARCH;
//					break;
//				}
//
//			}
//		});
//		rgSelect.check(R.id.radio1);
		rbFromMacau1.setOnCheckedChangeListener(checkedChangeListener);
		rbToMacau1.setOnCheckedChangeListener(checkedChangeListener);
		rbFromMacau1.setChecked(true);
		
		etFlightNo.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(TextUtil.stringIsNotNull(s.toString())){
					tvCity.setText(null);
//					tvCity.setEnabled(false);
				}else{
					tvCity.setText(city);
//					tvCity.setEnabled(true);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		hideSoftInput();
		return super.onTouchEvent(event);
	}
	private void hideSoftInput() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		// //如果显示则隐藏，否则显示
		imm.hideSoftInputFromWindow(etFlightNo.getWindowToken(), 0); // 强制隐藏键盘
	}
	
	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.btn_1://实时抵达澳门
			intent=new Intent(this,FlightListActivity.class);
			intent.putExtra(FlightListActivity.TYPE,FlightListActivity.ARRIVE);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_2://实时离开澳门
			intent=new Intent(this,FlightListActivity.class);
			intent.putExtra(FlightListActivity.TYPE,FlightListActivity.LEAVE);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_3://搜索 跳到航班日历
			if(TextUtil.stringIsNull(tvCity.getText().toString())){
				String message=ValidUtil.validFlightNo(etFlightNo.getText().toString());
				if(!"".equals(message)){
					showShortToastMessage(message);
					return;
				}
			}
			intent=new Intent(this,FlightCalendarActivity.class);
			intent.putExtra(IntentConstants.DATA_STRING1, tvCity.getText().toString());
			
			if(rbFromMacau1.isChecked()){//澳门出发
				type=DSEARCH;
				if(TextUtil.stringIsNotNull(etFlightNo.getText().toString())){//如果输入航班号 就是航班号查询
					type=DFLIGHT_NUMBER;
				}
			}else{//到达澳门
				LogUtil.e("到达澳门");
				type=ASEARCH;
				if(TextUtil.stringIsNotNull(etFlightNo.getText().toString())){//如果输入航班号 就是航班号查询
					type=AFLIGHT_NUMBER;
					LogUtil.e("到达澳门航班号查询");
				}
			}
			intent.putExtra(IntentConstants.DATA_STRING2,type);
			intent.putExtra(IntentConstants.DATA_STRING3,etFlightNo.getText().toString());
			intent.putExtra(IntentConstants.DATA_BOOL, isFromHome);
			FlyUtil.intentForwardNetWork(this, intent);

			break;
		case R.id.btn_4://查询使用说明

			break;
		case R.id.edit1://到达城市
			if(rbFromMacau1.isChecked()){
				intent=new Intent(this,CityActivity.class);
				intent.putExtra(IntentConstants.DATA_STRING,CityActivity.TO);
				FlyUtil.intentFowardResultNetWork(this, intent,CITY_REQUEST);//让第二个界面给返回值
			}else{
				intent=new Intent(this,CityActivity.class);
				intent.putExtra(IntentConstants.DATA_STRING,CityActivity.FROME);
				FlyUtil.intentFowardResultNetWork(this, intent,CITY_REQUEST);
			}
			break;
		case R.id.l1:
		case R.id.view_mainBody://
			hideSoftInput();
			break;
		}

	}
	//	@Override
	//	public boolean onTouchEvent(MotionEvent event) {
	//		// TODO Auto-generated method stub
	//		return super.onTouchEvent(event);
	//	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(data!=null){
			city=data.getStringExtra(IntentConstants.DATA_STRING);
			tvCity.setText(city);
			etFlightNo.setText(null);
		}
	}
}
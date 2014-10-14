package com.sinoglobal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 机场设施
 */
public class FacilityActivity1 extends AbstractActivity implements OnClickListener{
	private static final String TAG = "HomeActivity";
    Button btnAirlineService,btnAirSafe,btnBaggageDeposit,btnPublicFacility,btnTrafficGround,btnMedicalService,btnQuarantineInspection,
           btnOtherService,btnTelecommunicationService,btnVisitorService;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facility1);
		initView();
		addListener();
	}
    private void initView() {
//    	templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.service_facility));
		
        btnAirlineService=(Button)findViewById(R.id.btn_1);
        btnAirSafe=(Button)findViewById(R.id.btn_2);
        btnBaggageDeposit=(Button)findViewById(R.id.btn_3);
        btnPublicFacility=(Button)findViewById(R.id.btn_4);
        btnTrafficGround=(Button)findViewById(R.id.btn_5);
        btnMedicalService=(Button)findViewById(R.id.btn_6);
        btnQuarantineInspection=(Button)findViewById(R.id.btn_7);
        btnOtherService=(Button)findViewById(R.id.btn_8);
        btnTelecommunicationService=(Button)findViewById(R.id.btn_9);
        btnVisitorService=(Button)findViewById(R.id.btn_10);
	}
    private void addListener() {
    	templateButtonRight.setOnClickListener(this);
    	btnAirlineService.setOnClickListener(this);
    	btnAirSafe.setOnClickListener(this);
    	btnBaggageDeposit.setOnClickListener(this);
    	btnPublicFacility.setOnClickListener(this);
    	btnTrafficGround.setOnClickListener(this);
    	btnMedicalService.setOnClickListener(this);
    	btnQuarantineInspection.setOnClickListener(this);
    	btnOtherService.setOnClickListener(this);
    	btnTelecommunicationService.setOnClickListener(this);
    	btnVisitorService.setOnClickListener(this);
    	findViewById(R.id.btn_11).setOnClickListener(this);
    	findViewById(R.id.btn_12).setOnClickListener(this);
    	findViewById(R.id.btn_13).setOnClickListener(this);
    	findViewById(R.id.btn_14).setOnClickListener(this);
    	findViewById(R.id.btn_15).setOnClickListener(this);
    	findViewById(R.id.btn_16).setOnClickListener(this);
    	findViewById(R.id.btn_17).setOnClickListener(this);
    	findViewById(R.id.btn_18).setOnClickListener(this);
    	findViewById(R.id.btn_19).setOnClickListener(this);
    	findViewById(R.id.btn_20).setOnClickListener(this);
    	findViewById(R.id.btn_21).setOnClickListener(this);
    	findViewById(R.id.btn_22).setOnClickListener(this);
    	findViewById(R.id.btn_23).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this,MapDetailActivity.class);
		String serveName=null;
		switch (v.getId()) {
		case R.id.title_but_right://右侧离开按钮
			intent.setClass(this, MainActivityGroup.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			startActivity(intent);
			return;
		case R.id.btn_1://咖啡厅
			serveName="kft";
			break;
		case R.id.btn_2://银行
			serveName="yh";
			break;
		case R.id.btn_3://旅游询问处
			serveName="lyxwc";
			break;
		case R.id.btn_4://自动提款机
			serveName="zdtkj";
			break;
		case R.id.btn_5://便利店
			serveName="bld";
			break;
		case R.id.btn_6://快餐店
			serveName="kcd";
			
			break;
		case R.id.btn_7://货币兑换店
			serveName="hbdhd";
			
			break;
		case R.id.btn_8://虚拟助理
			serveName="xnzl";
			break;
		case R.id.btn_9://饮水壶
			serveName="ysh";
			break;
		case R.id.btn_10://书店
			serveName="sd";
			break;
		case R.id.btn_11://麦当劳
			serveName="mdl";
			break;
		case R.id.btn_12://吸烟室
			serveName="xys";
			break;
		case R.id.btn_13://贵宾室
			serveName="gbs";
			break;
		case R.id.btn_14:// 红十字会
			serveName="hszh";
			break;
		case R.id.btn_15://洗手间
			serveName="xsj";
			break;
		case R.id.btn_16://行李寄存处
			serveName="xljcc";
			break;
		case R.id.btn_17://失物认领出
			serveName="swrlc";
			break;
		case R.id.btn_18://停车场
			serveName="tcc";
			break;
		case R.id.btn_19://手机加油站
			serveName="sjjyz";
			break;
		case R.id.btn_20://邮政局
			serveName="yzj";
			break;
		case R.id.btn_21://贵宾休息室
			serveName="gbxxs";
			break;
		case R.id.btn_22://电话亭
			serveName="dht";
			break;
		case R.id.btn_23://免税店
			serveName="msd";
			break;

		default:
			break;
		}
		intent.putExtra(IntentConstants.DATA_STRING, serveName);
		FlyUtil.intentForwardNetWork(this, intent);
	}
}
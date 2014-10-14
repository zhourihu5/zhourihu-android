package com.sinoglobal.app.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinoglobal.app.beans.QuestionNaireVo;
import com.sinoglobal.app.beans.VersionVo;
import com.sinoglobal.app.newversion.DownloadService;
import com.sinoglobal.app.newversion.VersionUitls;
import com.sinoglobal.app.newversion.VersionUpgradeActivity;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.SharedPreferenceUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.util.http.ConnectionUtil;
import com.sinoglobal.app.widget.SlipButton;
import com.sinoglobal.app.widget.SlipButton.OnChangedListener;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么)  更多
 */
public class MoreActivity extends AbstractActivity implements OnClickListener,OnChangedListener{
	private static final String TAG = "MoreActivity";
	Button btnLanguage,btnQuestionair,btnBook,btnLink,btnAbout,btnDisclaimer;
	RelativeLayout rlVersion;
	SlipButton slipButton;
	String questionNaireUrl;
	TextView tvVersionName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		initView();
		addListener();
//		loadQuestionNaireData();
	}
	private void initView() {
		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.GONE);
		titleView.setText(getString(R.string.more));
		slipButton=(SlipButton)findViewById(R.id.side_bar);
//		slipButton.setCheck(FlyApplication.isEnglishDefault());//根据保存的语言设置语言
		slipButton.setCheck(!FlyApplication.isEnglishDefault());//根据保存的语言设置语言
        btnQuestionair=(Button)findViewById(R.id.btn_2);
        btnBook=(Button)findViewById(R.id.btn_3);
        btnLink=(Button)findViewById(R.id.btn_4);
        rlVersion=(RelativeLayout)findViewById(R.id.btn_5);
        btnAbout=(Button)findViewById(R.id.btn_6);
        btnDisclaimer=(Button)findViewById(R.id.btn_7);
        
        btnQuestionair.setVisibility(View.GONE);
        btnBook.setVisibility(View.GONE);
        btnLink.setVisibility(View.GONE);
        tvVersionName=(TextView)findViewById(R.id.tvVersionName);
        tvVersionName.setText("v"+VersionUitls.getVersionName(this));
	}
	private void addListener() {
		btnQuestionair.setOnClickListener(this);
		btnBook.setOnClickListener(this);
		btnLink.setOnClickListener(this);
		rlVersion.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
		slipButton.SetOnChangedListener(this);
		btnDisclaimer.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_2://问卷调查
			LogUtil.i("questionNaireUrl=="+questionNaireUrl);
			if(TextUtil.stringIsNotNull(questionNaireUrl)){
				intent=new Intent(this,WebViewActivity.class);
				intent.putExtra(IntentConstants.DATA_STRING,questionNaireUrl);
				intent.putExtra(IntentConstants.DATA_INT, WebViewActivity.QUESTIONNAIRE);
				FlyUtil.intentForwardNetWork(this, intent);
			}else{
				showShortToastMessage(getString(R.string.questionNaire_unavailable));
			}
			break;
		case R.id.btn_3://旅行预订
			intent=new Intent(this,TravelBookActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_4://联系方式
			intent=new Intent(this,HotLineActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_5://版本信息  检测版本更新
			getNewVersion(false);
			break;
		case R.id.btn_6://关于机场
			intent=new Intent(this,AboutActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;
		case R.id.btn_7://免责声明
			intent=new Intent(this,DisclaimerActivity.class);
			FlyUtil.intentForwardNetWork(this, intent);
			break;

		default:
			break;
		}
		
	}
	@Override
	public void OnChanged(boolean CheckState) {
//		SharedPreferenceUtil.saveLanguage(CheckState);
		SharedPreferenceUtil.saveLanguage(!CheckState);
		FlyApplication.context.setLanguage();
		Intent intent=new Intent(this,MainActivityGroup.class);
		intent.putExtra(IntentConstants.DATA_INT,R.id.home_bottom_rb_more);
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//		FlyUtil.exitApp(false);
		startActivity(intent);
//		finish();
	}
	private void getNewVersion(final boolean isCache) {
		new AsyncTask<Void, Void, VersionVo>() {
			@Override
			protected VersionVo doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					return RemoteImpl.getInstance().geVersionVo(isCache);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			protected void onPostExecute(VersionVo result) {
				if (result != null) {
					//版本名检测
					boolean isSameVersionName=VersionUitls.getVersionName(MoreActivity.this).equals(result.getVersionNo());
					LogUtil.i("初始化","isSameVersionName=="+isSameVersionName);
					//版本名和版本号有一个不同则升级
					if(!isSameVersionName){
						showVersionDialog(result);
					}else{
						showShortToastMessage(getString(R.string.version_is_new));
					}

				}
			};
		}.execute();
	}
	/**
	 * 
	 * @author czz
	 * @updatedate 2014-3-24 上午11:15:26
	 * @Description: (显示升级的dialog,如需要自定义dialog，可自己定义)
	 * @param result
	 * 
	 */
	private void showVersionDialog(final VersionVo result) {
		LogUtil.i("初始化","result=="+result);
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(getString(R.string.version_update_info));
//		builder.setMessage(result.getUpdateDate());
		builder.setCancelable(false);
		builder.setNegativeButton(getString(R.string.update), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MoreActivity.this,
						VersionUpgradeActivity.class);
				intent.putExtra(DownloadService.APK_NAME, getString(R.string.app_name));
				intent.putExtra(DownloadService.APK_LOGO_PATH, String.valueOf(R.drawable.ic_launcher));
				intent.putExtra(DownloadService.APK_DOWNLOAD_URL,ConnectionUtil.DOWNLOAD_URL+result.getVisitUrl());
				startActivity(intent);
			}
		});
		builder.setPositiveButton(R.string.btn_cancle, null);
		builder.show();
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
		}
	}
}
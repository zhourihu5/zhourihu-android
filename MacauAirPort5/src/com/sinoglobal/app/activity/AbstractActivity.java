package com.sinoglobal.app.activity;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.ITask;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.NetWorkUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.constants.Constants;
import com.sinoglobal.app.util.exception.HttpTransportSENoConnectionException;
import com.sinoglobal.app.util.exception.NoDataException;
import com.aims.mia.R;
import com.umeng.analytics.MobclickAgent;

//import dalvik.system.VMRuntime;
public abstract class AbstractActivity extends Activity {

	private final int ERROR = 4;
	private final int NODATA = 5; // 没有数据
	private final int TIME_OUT = 6; // 超时
	protected TextView titleView; // 标题
	protected LinearLayout mainBody; // 主体显示
	private Dialog dialog;
	private View waitingView;
	private Dialog messageDialog; // 弹出的加载提示框

	protected boolean isTemplate = true; // 是否使用模板,不使用模板时请按照模板方式编写，使用时则不需要。
	protected Button templateButtonLeft; // 模板中左则Button,不使用模板时，此值为null;
	protected Button templateButtonRight; // 模板中右则Button,不使用模板时，此值为null;
	protected RelativeLayout titleRelativeLayout;
	private boolean isShowNoValue = false;
	protected View templateView;
	private AnimationDrawable logoAnimation;
	private Handler loadingHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		FlyUtil.addAppActivity(this);
		// VMRuntime.getRuntime().setTargetHeapUtilization(Constants.TARGET_HEAP_UTILIZATION);
		if (isTemplate) {
			setContentView(R.layout.template);
			titleRelativeLayout = (RelativeLayout) findViewById(R.id.title_iv_id);
			templateButtonLeft = (Button) findViewById(R.id.title_but_left);
			templateButtonRight = (Button) findViewById(R.id.title_but_right);
			// templateButtonRight.setBackgroundResource(R.drawable.button_style);
			templateButtonLeft.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					overridePendingTransition(R.anim.activity_on, R.anim.activity_off);
				}
			});
			templateButtonRight.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					goHome();
				}
			});
		}
		titleView = (TextView) findViewById(R.id.title_text);
		mainBody = (LinearLayout) findViewById(R.id.view_mainBody);
	}
	@Override
	public void setContentView(int layoutResID) {
		if (layoutResID == R.layout.template) {
			super.setContentView(layoutResID);
		} else {
			if (mainBody != null) {
				mainBody.removeAllViews();
				mainBody.addView(this.getLayoutInflater().inflate(layoutResID, null));
			} else {
				super.setContentView(layoutResID);
			}
		}
	}

	/**
	 * @author ty
	 * @createdate 2012-10-25 下午4:22:51
	 * @Description: 返回首页
	 */
	protected void goHome() {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		 FlyUtil.intentForward(AbstractActivity.this, MainActivityGroup.class,intent);
		// overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		// 解除以上方法
	}

	/**
	 * @author ty
	 * @createdate 2012-9-24 上午9:10:36
	 * @Description: 显示Toast消息
	 * @param message
	 */
	public void showShortToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setContentView(View view) {
		mainBody.removeAllViews();
		mainBody.addView(view);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		mainBody.removeAllViews();
		mainBody.addView(view, params);
	}

	/**
	 * @author ty
	 * @createdate 2012-6-6 下午1:56:24
	 * @Description: 显示 正在加载，请稍候。。。
	 * @param message
	 *            显示的信息
	 * @param cancelable
	 *            是否支持取消操作, 建议此值为true:允许取消
	 * @param flag
	 *            是否有背景
	 * @param isLoadingPrompt
	 *            是否使用运营推荐消息
	 */
	public void showWaitingDialog(String message, boolean cancelable, boolean flag, boolean isLoadingPrompt) {
		if (null == dialog) {
			dialog = new Dialog(this, R.style.dialog_windowFullscreen);
			waitingView = getLayoutInflater().inflate(R.layout.view_loading, null);
			ImageView imageView = (android.widget.ImageView) waitingView.findViewById(R.id.iv_id);
			logoAnimation = (AnimationDrawable) imageView.getBackground();
			dialog.setContentView(waitingView);
		}

		if (isLoadingPrompt) { // 使用运营推荐消息
			// message = getMessage(ItktApplication.LOADING_PROMPT);
			if (TextUtil.stringIsNull(message)) {
				message = getString(R.string.please_wait);
			}
		}

		if (flag) {
			waitingView.setBackgroundResource(R.drawable.img_loading_super_bg);
		} else {
			waitingView.setBackgroundColor(getResources().getColor(R.color.transparent));
		}
		waitingView.setBackgroundColor(getResources().getColor(R.color.transparent));
		waitingView.invalidate();
		// logoAnimation.start(); //开始动画
		// 使用Handler因为在某些手机不会转
		if (loadingHandler == null) {
			loadingHandler = new Handler();
		}
		loadingHandler.postDelayed(new Runnable() {
			public void run() {
				logoAnimation.start(); // 开始动画
			}
		}, 50);
		TextView textView = (TextView) waitingView.findViewById(R.id.tv_id);
		textView.setText(message);
		dialog.setCancelable(cancelable);
		dialog.show();
	}

	/**
	 * @author ty
	 * @createdate 2012-6-6 下午1:57:01
	 * @Description: 显示异步交互后的数据,隐藏加载框
	 */
	public void dissmissWaitingDialog() {
		if (logoAnimation != null && logoAnimation.isRunning()) { // 如果正在运行,就停止
			logoAnimation.stop();
		}
		if (null != mainBody) {
			mainBody.setVisibility(View.VISIBLE);
		}
		dialog.dismiss();
	}

	/**
	 * 
	 * @author ty
	 * @createdate 2012-6-6 下午1:59:16
	 * @Description: 提示各种操作的提示语，在非UI线程操作此方法
	 * @param index
	 * @param task
	 *            回调异步操作
	 */
	@SuppressWarnings("rawtypes")
	private void showUiThreadInfo(final int index, final ITask task) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				switch (index) {
				case ERROR:
					showShortToastMessage(getString(R.string.error));
					break;
				case NODATA:
					showShortToastMessage(getString(R.string.no_data));
					break;
				case TIME_OUT:
//					showShortToastMessage(getString(R.string.));
//					break;
				default:
					break;
				}
				task.exception();
			}
		});
	}

	/**
	 * @author ty
	 * @createdate 2013-1-6 上午9:54:10
	 * @Description: 没有网络提示信息
	 */
	private void notNetWorkInfo(int type) {
		switch (type) {

		default:
			showShortToastMessage(getString(R.string.no_connections));
			break;
		}
	}

	/**
	 * @author ty
	 * @createdate 2013-1-8 上午11:22:14
	 * @Description: 解决产品要求在特定的页面中网络不可能提示用的信息，不使用封装的intentForward方法
	 * @param context
	 * @param type
	 *            类型
	 * @param intent
	 */
	protected void intentForwardNetWork(Context context, int type, Intent intent) {
		if (!NetWorkUtil.isNetAvailable(AbstractActivity.this)) {
			notNetWorkInfo(type);
			return;
		}
		FlyUtil.intentForward(context, intent);
	}

	/**
	 * 
	 * @author ty
	 * @createdate 2012-6-6 下午1:37:40
	 * @Description: 异步任务封装
	 * @param <Params>
	 *            启动任务执行的参数
	 * @param <Progress>
	 *            后台任务执行的百分比。
	 * @param <Result>
	 *            后台执行任务最终返回的结果
	 */
	protected abstract class ItktAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements
			ITask<Params, Progress, Result> {

		private boolean isNetWork = true;
		private String message = null;
		private boolean isLoadingPrompt = true; // isLoadingPrompt
												// 是否使用运营推荐消息,默认为true

		public ItktAsyncTask() {
		}

		/**
		 * @param isNetWork
		 *            是否需要进行网络判断， true,需要网络，false不需要网络，默认为true
		 */
		public ItktAsyncTask(boolean isNetWork) {
			this.isNetWork = isNetWork;
		}

		/**
		 * @param message
		 *            提示信息
		 * @param isLoadingPrompt
		 *            是否使用运营推荐消息
		 */
		public ItktAsyncTask(String message, boolean isLoadingPrompt) {
			this.message = message;
			this.isLoadingPrompt = isLoadingPrompt;
		}

		@Override
		protected void onPreExecute() {
			// 没有网络
			if (isNetWork && !NetWorkUtil.isNetAvailable(AbstractActivity.this)) {
				cancel(false);
			}
			if (mainBody != null) {
				mainBody.setVisibility(View.GONE);
			}
			showWaitingDialog(message, true, false, isLoadingPrompt);
			super.onPreExecute();
		}

		@Override
		protected Result doInBackground(Params... params) {
			try {
				return before(params);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (NoDataException e) {
				e.printStackTrace();
				showUiThreadInfo(NODATA, this);
				cancel(false);
			} catch (SocketException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (HttpTransportSENoConnectionException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (ConnectTimeoutException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (Exception e) {
				e.printStackTrace();
				showUiThreadInfo(ERROR, this);
				cancel(false);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
//			if (result == null) {
//				showBodyInfo(getString(R.string.failure));
//			} else {
//				after(result);
//			}
			after(result);
			dissmissWaitingDialog();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			dissmissWaitingDialog();
		}
	}

	/**
	 * 
	 * @author ty
	 * @createdate 2012-6-6 下午1:37:40
	 * @Description: 异步任务封装---带有ProgressDialog
	 * @param <Params>
	 *            启动任务执行的参数
	 * @param <Progress>
	 *            后台任务执行的百分比。
	 * @param <Result>
	 *            后台执行任务最终返回的结果
	 */
	public abstract class ItktOtherAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements
			ITask<Params, Progress, Result> {

		private String message;
		private boolean cancelable = true;
		private boolean isShowProgressDialog = true;
        boolean isNeedNetWork=false;
        public ItktOtherAsyncTask setNeedNetWork(boolean isNeedNetWork) {
			this.isNeedNetWork = isNeedNetWork;
			return this;
		}
		public ItktOtherAsyncTask() {
		}

		public ItktOtherAsyncTask(String message) {
			this.message = message;
		}

		/**
		 * 不显示进度对话框
		 * 
		 * @param isShowProgressDialog
		 *            false：为不显示
		 */
		public ItktOtherAsyncTask(boolean isShowProgressDialog) {
			this.isShowProgressDialog = isShowProgressDialog;
		}

		/**
		 * 是否显示进度对话框
		 * 
		 * @param isShowProgressDialog
		 *            false不显示请求网络提示框，true：显示请求网络提示框
		 * @param message
		 *            显示的信息
		 */
		public ItktOtherAsyncTask(boolean isShowProgressDialog, String message) {
			this.isShowProgressDialog = isShowProgressDialog;
			this.message = message;
		}

		/**
		 * 加载进度条提示语
		 * 
		 * @param message
		 *            显示的信息
		 * @param cancelable
		 *            是否支付取消操作, 建议此值为true,
		 */
		public ItktOtherAsyncTask(String message, boolean cancelable) {
			this.message = message;
			this.cancelable = cancelable;
		}

		@Override
		protected void onPreExecute() {
			// 没有网络 可以从缓存读取
			if (!NetWorkUtil.isNetAvailable(AbstractActivity.this)&&isNeedNetWork) {

				cancel(false);
			}
			if (isShowProgressDialog) {
				showWaitingDialog(message, cancelable, true, true);
			}
			super.onPreExecute();
		}

		@Override
		protected Result doInBackground(Params... params) {
			try {
				return before(params);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (NoDataException e) {
				e.printStackTrace();
				showUiThreadInfo(NODATA, this);
				cancel(false);
			} catch (SocketException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (HttpTransportSENoConnectionException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (ConnectTimeoutException e) {
				e.printStackTrace();
				showUiThreadInfo(TIME_OUT, this);
				cancel(false);
			} catch (Exception e) {
				e.printStackTrace();
				showUiThreadInfo(ERROR, this);
				cancel(false);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
//			if (result == null) {
//				showBodyInfo(getString(R.string.failure));
//			} else {
//				after(result);
//			}
			after(result);
			if (isShowProgressDialog) {
				if (logoAnimation.isRunning()) { // 如果正在运行,就停止
					logoAnimation.stop();
				}
				dialog.dismiss();
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (isShowProgressDialog) {
				// progressDialog.dismiss();
				dialog.dismiss();
			}
		}
	}

	/**
	 * @author ty
	 * @createdate 2012-10-19 上午11:28:15
	 * @Description: 显示Body显示的信息
	 * @param info
	 */
	protected void showBodyInfo(String info) {
		View view = getLayoutInflater().inflate(R.layout.template_failure, null);
		TextView textView = (TextView) view.findViewById(R.id.tv_id);
		textView.setText(info);
		mainBody.removeAllViews();
		mainBody.setGravity(Gravity.CENTER);
		mainBody.addView(view);
	}

	/**
	 * @author ty
	 * @createdate 2012-10-19 下午1:55:43
	 * @Description: 列表显示数据没有
	 * @param listView
	 * @param info
	 */
	protected void showListNoValue(String info) {
		templateView = getLayoutInflater().inflate(R.layout.template_failure, null);
		TextView textView = (TextView) templateView.findViewById(R.id.tv_id);
		textView.setText(info);
		if (mainBody != null) {
			mainBody.removeAllViews();
			mainBody.setGravity(Gravity.CENTER);
			mainBody.addView(templateView);
		}
	}

	/**
	 * @author ty
	 * @createdate 2012-10-19 下午1:55:43
	 * @Description: 
	 *               列表显示数据没有，这个方法用于列表无数据，可以添加数据，如果无数据就什么都不干，返回重查，直接用上面的方法showListNoValue
	 * @param listView
	 * @param info
	 */
	protected void showListNoValueChildGone(String info) {
		templateView = getLayoutInflater().inflate(R.layout.template_failure, null);
		TextView textView = (TextView) templateView.findViewById(R.id.tv_id);
		textView.setText(info);
		int count = mainBody.getChildCount();
		for (int i = 0; i < count; i++) {
			View vi = mainBody.getChildAt(i);
			vi.setVisibility(View.GONE);
		}
		mainBody.setGravity(Gravity.CENTER);
		mainBody.addView(templateView);
		isShowNoValue = true;// 说明执行过现实无数据这个方法，为了有数据后判断，如果执行过这个方法，有数据后才执行showListChildVisible方法里的代码
	}

	/**
	 * @author ty
	 * @createdate 2012-10-31 下午3:44:42
	 * @Description: 查询无数据，再添加数据以后，现实出来添加的数据用此方法；
	 *               加了isShowNoValue来判断，意思是执行这个方法体的前提是一定执行过showListNoValueChildGone方法
	 */
	protected void showListChildVisible() {
		if (isShowNoValue) {
			mainBody.removeView(templateView);
			templateView = null;
			int count = mainBody.getChildCount();
			for (int i = 0; i < count; i++) {
				View vi = mainBody.getChildAt(i);
				vi.setVisibility(View.VISIBLE);
			}
			isShowNoValue = false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (FlyApplication.IS_UMENG) {// 友盟统计控制开关，true允许使用，false则反（将在下次启动时生效）
			 MobclickAgent.onPause(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (FlyApplication.IS_UMENG) {// 友盟统计控制开关，true允许使用，false则反（将在下次启动时生效）
			 MobclickAgent.onResume(this);
//			AgentAnalysis.onResume(this);
//			AgentAnalysis.setDebugModle(true);
//			AgentAnalysis.setSessionLastMillis(6*1000);
			
		}
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-21 下午3:07:49
	 * @Description: 确认对话框
	 * @param context
	 *            Activity上下文
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框消息
	 * @param btnText
	 *            按钮文本
	 */
	public void showConfirmDialog(String title, String message, String btnText) {
		if (messageDialog != null) {
			messageDialog.dismiss();
			messageDialog = null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(btnText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setCancelable(true);
		messageDialog = builder.create();
		messageDialog.show();
	}

	/**
	 * @author ty
	 * @createdate 2012-10-26 下午4:58:24
	 * @Description: (用一句话描述该方法做什么)
	 * @param tipTitle
	 *            dialog标题
	 * @param tipContent
	 *            dialog内容
	 * @param telphone
	 *            电话号码
	 * 
	 */
	protected void makingCall(String tipTitle, String tipContent, final String telphone) {
		showConfirmDialog(tipTitle, tipContent, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(Constants.TEL + telphone));
				startActivity(intent);
			}
		});
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-24 下午3:34:26
	 * @Description: 确认对话框
	 * @param context
	 *            Activity上下文
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框消息
	 * @param sureListener
	 *            确认监听器
	 */
	public void showConfirmDialog(String title, String message, DialogInterface.OnClickListener sureListener) {
		if (messageDialog != null) {
			messageDialog.dismiss();
			messageDialog = null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.btn_sure_text, sureListener);
		builder.setNegativeButton(R.string.btn_cancle, null);
		builder.setCancelable(true);
		messageDialog = builder.create();
		messageDialog.show();
	}

	/**
	 * @author ty
	 * @createdate 2012-11-20 下午3:30:29
	 * @Description: 确认对话框 只有一个按钮
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框消息
	 * @param sureListener
	 *            确认监听器
	 * 
	 */
	public void showConfirmDialogPositiveBT(String title, String message, DialogInterface.OnClickListener sureListener) {
		if (messageDialog != null) {
			messageDialog.dismiss();
			messageDialog = null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.btn_sure_text, sureListener);
		builder.setCancelable(true);
		messageDialog = builder.create();
		messageDialog.show();
	}

	/**
	 * @author ty
	 * @createdate 2013-1-22 下午1:44:43
	 * @Description: 确认对话框 只有一个按钮 不可点击对话框以为区域
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框消息
	 * @param sureListener
	 *            确认监听器
	 * 
	 */
	public void showConfirmDialogPositive(String title, String message, DialogInterface.OnClickListener sureListener) {
		if (messageDialog != null) {
			messageDialog.dismiss();
			messageDialog = null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.btn_sure_text, sureListener);
		builder.setCancelable(false);
		messageDialog = builder.create();
		messageDialog.show();
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-22 下午8:17:58
	 * @Description
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		LogUtil.w("===================save==========================>>");
		// outState.putSerializable("USER", ItktApplication.USER);
		outState.putString("USER_ID", FlyApplication.USER_ID);

		// LogUtil.d("onSaveInstanceState==>ItktApplication.USER"+ItktApplication.USER);
		// LogUtil.d("onSaveInstanceState==>ItktApplication.USER_ID"+ItktApplication.USER_ID);
		super.onSaveInstanceState(outState);
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-22 下午8:17:58
	 * @Description
	 */
	protected void onResStoreData(Bundle savedInstanceState) {
		LogUtil.w("================restart=============================>>");
		if (savedInstanceState != null) {
			// ItktApplication.USER = (MemberInfoVo)
			// savedInstanceState.getSerializable("USER");
			FlyApplication.USER_ID = savedInstanceState.getString("USER_ID");

			// LogUtil.d("onResStoreData==>ItktApplication.USER" +
			// ItktApplication.USER);
			// LogUtil.d("onResStoreData==>ItktApplication.USER_ID" +
			// ItktApplication.USER_ID);
		}
	}

	/* 必须重写，否则点击MENU无反应 为了让他不显示，下面onMenuOpened（）必须返回false */
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// if (this instanceof MainActivity) {
	// return true;
	// }
	// MainActivity mainActivity = new MainActivity();
	// menu.add("menu");// 必须创建一项
	// // return super.onCreateOptionsMenu(menu);
	// return false;
	// }

	// /**
	// * 拦截MENU
	// */
	// @Override
	// public boolean onMenuOpened(int featureId, Menu menu) {
	// if(mPopupWindow==null){
	// initPopuWindow();
	// }
	// if(!mPopupWindow.isShowing()){
	// /*最重要的一步：弹出显示 在指定的位置(parent) 最后两个参数 是相对于 x / y 轴的坐标*/
	// mPopupWindow.showAtLocation(findViewById(R.id.view_mainBody),
	// Gravity.BOTTOM, 0, 0);
	// }
	// return false;// 返回为true 则显示系统menu
	// }

	/*
	 * private void initPopuWindow(){ 设置显示menu布局 view子VIEW View subView =
	 * getLayoutInflater().inflate(R.layout.menu_popup, null); 第一个参数弹出显示view
	 * 后两个是窗口大小 mPopupWindow = new PopupWindow(subView,
	 * LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); //
	 * mPopupWindow.setAnimationStyle(R.style.popup_in_out); 设置背景显示
	 * mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); 设置触摸外面时消失
	 * mPopupWindow.setOutsideTouchable(true); 设置系统动画
	 * mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
	 * mPopupWindow.update(); mPopupWindow.setTouchable(true);
	 * 设置点击menu以外其他地方以及返回键退出 mPopupWindow.setFocusable(true);
	 *//**
	 * 1.解决再次点击MENU键无反应问题 2.sub_view是PopupWindow的子View
	 */
	/*
	 * subView.setFocusableInTouchMode(true); subView.setOnKeyListener(new
	 * OnKeyListener() {
	 * 
	 * @Override public boolean onKey(View v, int keyCode, KeyEvent event) { if
	 * ((keyCode == KeyEvent.KEYCODE_MENU)&&(mPopupWindow.isShowing())) {
	 * mPopupWindow.dismiss();// 这里写明模拟menu的PopupWindow退出就行 return true; }
	 * return false; } }); 监听MENU事件 Button aboutBut = (Button)
	 * subView.findViewById(R.id.about_id); aboutBut.setOnClickListener(new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { Intent intent=new Intent();
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * ItktUtil.intentForward(AbstractActivity.this, AboutUsActivity.class,
	 * intent); mPopupWindow.dismiss(); } }); Button helpBut = (Button)
	 * subView.findViewById(R.id.help_id); helpBut.setOnClickListener(new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { Intent intent=new Intent();
	 * intent.putExtra(IntentConstants.TYPE, IntentConstants.USE_HELP);
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * ItktUtil.intentForward(AbstractActivity.this, WapClientActivity.class,
	 * intent); mPopupWindow.dismiss(); } }); Button opinionBut = (Button)
	 * subView.findViewById(R.id.opinion_id); opinionBut.setOnClickListener(new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { mPopupWindow.dismiss();
	 * if(ItktApplication.IS_FIRST){ //第一次使用，该功能是用户注册
	 * ItktUtil.intentForward(AbstractActivity.this, RegActivity.class); return;
	 * } Intent intent=new Intent(); if(ItktUtil.isLogin()){
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * ItktUtil.intentForward(AbstractActivity.this, FeedBackActivity.class,
	 * intent); }else{
	 * ItktUtil.intentForwardValidateLogin(AbstractActivity.this,
	 * FeedBackActivity.class, intent); } } }); Button exitBut = (Button)
	 * subView.findViewById(R.id.exit_id); exitBut.setOnClickListener(new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { ItktUtil.exitApp();
	 * android.os.Process.killProcess(android.os.Process.myPid()); } }); }
	 */

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * super.onCreateOptionsMenu(menu); // menu.add(Menu.NONE, Menu.FIRST+1, 1,
	 * Constants.MENU_NAME[0]).setIcon(R.drawable.img_menu_login);
	 * menu.add(Menu.NONE, Menu.FIRST+2, 2,
	 * Constants.MENU_NAME[0]).setIcon(R.drawable.img_menu_about);
	 * menu.add(Menu.NONE, Menu.FIRST+3, 3,
	 * Constants.MENU_NAME[1]).setIcon(R.drawable.img_menu_help);
	 * menu.add(Menu.NONE, Menu.FIRST+4, 4,
	 * Constants.MENU_NAME[2]).setIcon(R.drawable.img_menu_opinion);
	 * menu.add(Menu.NONE, Menu.FIRST+5, 5,
	 * Constants.MENU_NAME[3]).setIcon(R.drawable.img_menu_exit); return true; }
	 */

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { Intent
	 * intent; switch (item.getItemId()) { // case Menu.FIRST+1: //登录/注册 //
	 * showShortToastMessage(Constants.MENU_NAME[0]); // break; case
	 * Menu.FIRST+2: //关于我们 intent=new Intent();
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * ItktUtil.intentForward(this, AboutUsActivity.class, intent); break; case
	 * Menu.FIRST+3: //使用帮助 intent=new Intent();
	 * intent.putExtra(IntentConstants.TYPE, IntentConstants.USE_HELP);
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * ItktUtil.intentForward(this, WapClientActivity.class, intent); break;
	 * case Menu.FIRST+4: //意见反馈 if(ItktApplication.IS_FIRST){ //第一次使用，该功能是用户注册
	 * ItktUtil.intentForward(this, RegActivity.class); break; } intent=new
	 * Intent(); if(ItktUtil.isLogin()){
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * ItktUtil.intentForward(this, FeedBackActivity.class, intent); }else{
	 * ItktUtil.intentForwardValidateLogin(this,FeedBackActivity.class, intent);
	 * } break; case Menu.FIRST+5: //退出软件 ItktUtil.exitApp();
	 * android.os.Process.killProcess(android.os.Process.myPid()); break; }
	 * return false; }
	 */

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-12-17 下午2:07:26
	 * @Description: 随机获取运维推广的广告语消息
	 * @param messageList
	 * @return
	 */
	/*
	 * private String getMessage(String[] messageList){ if(messageList!=null &&
	 * messageList.length>0){ Random random = new Random(); int i =
	 * random.nextInt(messageList.length-1); return messageList[i]; } return
	 * null; }
	 */

	@Override
	protected void onDestroy() {
		mainBody = null;
		loadingHandler = null;
		FlyUtil.dismissAlert(dialog);
		FlyUtil.dismissAlert(messageDialog);
		FlyUtil.removeAppActivity(this);
		super.onDestroy();
	}

	@Override
	public void finish() {
		super.finish();

	}
}

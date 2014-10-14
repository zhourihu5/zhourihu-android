package com.sinoglobal.app.activity;

import net.tsz.afinal.FinalBitmap;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinoglobal.app.beans.MapDataVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.util.http.ConnectionUtil;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 点击地图的特定区域进入的界面 如贵宾室
 */
public class MapDetailActivity extends AbstractActivity {
   WebView mWebView;
    ImageView iv;
    MapDataVo mapDataVo;
    String serveName;
    LinearLayout llLink;
    TextView tvTel;//tvLocation;
    Button btnMore;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_detail);
		mapDataVo=(MapDataVo) getIntent().getSerializableExtra(IntentConstants.DATA_OBJ);
		serveName=getIntent().getStringExtra(IntentConstants.DATA_STRING);
		boolean hideRightButton=getIntent().getBooleanExtra(IntentConstants.DATA_BOOL, false);
		if(hideRightButton){
			templateButtonRight.setVisibility(View.INVISIBLE);
		}
		initView();
		setView(mapDataVo);
		if(serveName!=null){
			loadData();
		}
	}
    private void initView() {
    	mWebView=(WebView)findViewById(R.id.tv3);
    	mWebView.requestFocus();// 获取触摸焦点
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 取消滚动条
//		mWebView.getSettings().setBuiltInZoomControls(true); // 构建缩放控制
//		mWebView.getSettings().setSupportZoom(true); // 设置支持缩放
		mWebView.getSettings().setBlockNetworkImage(false);
		mWebView.getSettings().setBlockNetworkLoads(false);
		mWebView.getSettings().setDomStorageEnabled(true); 
		mWebView.getSettings().setPluginState(PluginState.ON); 
		mWebView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript
		mWebView.setWebViewClient(new WebViewClient(){       
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				view.loadUrl(url);       
				return true;       
			}    
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
//				view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
//
//						"document.getElementsByTagName('html')[0].innerHTML+'</head>');");

				super.onPageFinished(view, url);
			}
		});   

    	iv=(ImageView)findViewById(R.id.iv1);
    	int screenWidth=getResources().getDisplayMetrics().widthPixels;
    	iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,(int) (screenWidth*1.0f/447*337)));
    	llLink=(LinearLayout)findViewById(R.id.l2);
    	tvTel=(TextView)findViewById(R.id.tv1);
//    	tvLocation=(TextView)findViewById(R.id.tv2);
//    	tvLocation.setVisibility(View.GONE);
    	llLink.setVisibility(View.GONE);
    	btnMore=(Button)findViewById(R.id.btn_1);
    	if("jchhjcfw".equals(serveName)){//机场豪华轿车服务
    		btnMore.setBackgroundResource(R.color.book);
    		btnMore.setText(getString(R.string.book_now));
    		btnMore.setTextColor(getResources().getColor(R.color.text_white));
//    		templateButtonRight.setVisibility(View.INVISIBLE);
    	}
    	/*直通快线
		中文 http://www.macau-airport.com/mo/transportation/express-link-service
		英文 http://www.macau-airport.com/en/transportation/express-link-service

		机场豪华轿车服务
		中文 http://www.macau-airport.com/mo/transportation/limousine-service
		英文 http://www.macau-airport.com/en/transportation/limousine-service

		公共汽车
		中文 http://www.macau-airport.com/mo/transportation/public-transport/public-buses
		英文 http://www.macau-airport.com/en/transportation/public-transport/public-buses

		的士
		中文http://www.macau-airport.com/mo/transportation/public-transport/taxis
		英文http://www.macau-airport.com/en/transportation/public-transport/taxis

		跨境长途车
		中文 http://www.macau-airport.com/mo/transportation/transport-connection/cross-border-coach
		英文 http://www.macau-airport.com/en/transportation/transport-connection/cross-border-coach
*/
//    	btnMore.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				String url=null;
//				
//				if("en".equals(FlyApplication.language)){
//					url="http://www.macau-airport.com/en/passenger-guide/airportservices";
//					if("jchhjcfw".equals(serveName)){//机场豪华轿车服务
//						url="http://ebiz.macau-airport.com/limousine.html?lang=en";
//					}
//					else if("ztkx".equals(serveName)){//直通快线
//						url="http://www.macau-airport.com/en/transportation/express-link-service";
//						
//					}
//					else if("ggqc".equals(serveName)){//公共汽车
//						url="http://www.macau-airport.com/en/transportation/public-transport/public-buses";
//						
//					}
//					else if("ds".equals(serveName)){//的士
//						url="http://www.macau-airport.com/en/transportation/public-transport/taxis";
//						
//					}
//					else if("kjky".equals(serveName)){//跨境客运
//						url="http://www.macau-airport.com/en/transportation/transport-connection/cross-border-coach";
//						
//					}
//				}else{
//					url="http://www.macau-airport.com/mo/passenger-guide/airportservices";
//					if("jchhjcfw".equals(serveName)){//机场豪华轿车服务
//						url="http://ebiz.macau-airport.com/limousine.html?lang=cn";
//					}
//					else if("ztkx".equals(serveName)){//直通快线
//						url="http://www.macau-airport.com/mo/transportation/express-link-service";
//						
//					}
//					else if("ggqc".equals(serveName)){//公共汽车
//						url="http://www.macau-airport.com/mo/transportation/public-transport/public-buses";
//						
//					}
//					else if("ds".equals(serveName)){//的士
//						url="http://www.macau-airport.com/mo/transportation/public-transport/taxis";
//						
//					}
//					else if("kjky".equals(serveName)){//跨境客运
//						url="http://www.macau-airport.com/mo/transportation/transport-connection/cross-border-coach";
//						
//					}
//				}
//				Intent intent=new Intent(MapDetailActivity.this,WebViewActivity.class);
//				intent.putExtra(IntentConstants.DATA_STRING, url);
//				
//				FlyUtil.intentForwardNetWork(MapDetailActivity.this, intent);
//			}
//		});
	}
    private void loadData() {
    	try {
			setView(RemoteImpl.getInstance().getServeDetail(true,serveName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktAsyncTask<Void, Void, MapDataVo>() {

			@Override
			public MapDataVo before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getServeDetail(false,serveName);
			}

			@Override
			public void after(MapDataVo result) {
				setView(result);
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub
				
			}
		}.execute();

	}
    private void setView(MapDataVo result) {
		if(result!=null){
			mapDataVo=result;
			titleView.setText(result.getTitle());
			if(TextUtil.stringIsNotNull(mapDataVo.getImgUrl())){
				Bitmap defaultPic=BitmapFactory.decodeResource(getResources(), R.drawable.img_default);
				FinalBitmap.create(MapDetailActivity.this).display(iv, ConnectionUtil.DOWNLOAD_URL+mapDataVo.getImgUrl(),defaultPic,defaultPic);
			}else{
				iv.setVisibility(View.GONE);
			}
//	    	tvIntroduction.setText(mapDataVo.getIntroduction());
//	    	mWebView.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动  
//	    	if(TextUtil.stringIsNotNull(mapDataVo.getIntroduction())){
//	    		mWebView.setText(Html.fromHtml(mapDataVo.getIntroduction())); 
//	    	}
			mWebView.loadDataWithBaseURL(null, mapDataVo.getIntroduction(), "text/html", "UTF-8", null);
			if(TextUtil.stringIsNotNull(mapDataVo.getLinkname())){
				btnMore.setText(mapDataVo.getLinkname());
			}
			if(TextUtil.stringIsNotNull(mapDataVo.getLinkurl())){
				btnMore.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MapDetailActivity.this,WebViewActivity.class);
						intent.putExtra(IntentConstants.DATA_STRING, mapDataVo.getLinkurl());
						FlyUtil.intentForwardNetWork(MapDetailActivity.this, intent);
						
					}
				}
				);
			}
	    	if(TextUtil.stringIsNotNull(result.getTel())){
	    		tvTel.setText("Tel:"+result.getTel());
	    		llLink.setVisibility(View.VISIBLE);
	    		final String tel=result.getTel()
	    				.replace("Tel:", "")
	    				.replace("+", "")
	    				.replace(" ", "")
	    				.replace("(", "")
	    				.replace(")", "");
	    		tvTel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tel));
							startActivity(intent);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	    	}
		}
	}
}
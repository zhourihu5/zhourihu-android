package com.sinoglobal.app.activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.sinoglobal.app.beans.DetailMapDataVo;
import com.sinoglobal.app.beans.MapDataListVo;
import com.sinoglobal.app.beans.MapDataVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.util.http.ConnectionUtil;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么) 地图
 */

public class MapActivity extends AbstractActivity implements OnClickListener{
	Button btnPassengerFlow,btnDining,btnService,btnTransport,btnFacilities,btnShopping;
	View btnGoneView;
	RadioButton rbAll,rbGf,rbF1,rbF2;
	RadioGroup rg;
	ImageView iv;
	RelativeLayout rl;
	MapDataListVo mapDataListVo;
	DetailMapDataVo detailMapDataVo;
	float scaleSize;//缩放比例。
	int screenWidth,screenHeight,index;
	private PhotoViewAttacher mAttacher;
	ArrayList<ImageView> markViews=new ArrayList<ImageView>();
	String classCode;
	float left,top;
	RectF mRectF;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		titleView.setText(getString(R.string.home_map));
		templateButtonRight.setVisibility(View.INVISIBLE);
		initView();
		screenWidth=getResources().getDisplayMetrics().widthPixels;
		screenHeight=getResources().getDisplayMetrics().heightPixels;
		mRectF=new RectF(0, 0, screenWidth, screenHeight);
		loadMapData();
	}
	private void loadMapData() {
		try {
			setView(RemoteImpl.getInstance().getMapData(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ItktOtherAsyncTask<Void, Void, MapDataListVo>() {

			@Override
			public MapDataListVo before(Void... params) throws Exception {
				// TODO Auto-generated method stub
				return RemoteImpl.getInstance().getMapData(false);
			}

			@Override
			public void after(MapDataListVo result) {
				setView(result);
			}

			@Override
			public void exception() {
				// TODO Auto-generated method stub

			}
		}.execute();
	}
	private void setView(MapDataListVo result) {
		if(result!=null){
			mapDataListVo=result;
		}
		rg.clearCheck();
		rg.check(R.id.cb_1);
	}
	private void initView() {
		rl=(RelativeLayout)findViewById(R.id.rl_id);
		rg=(RadioGroup)findViewById(R.id.rangeSeekBar);
		iv=(ImageView)findViewById(R.id.iv1);
		setAttacher();
		btnPassengerFlow=(Button)findViewById(R.id.btn_1);
		btnDining=(Button)findViewById(R.id.btn_2);
		btnService=(Button)findViewById(R.id.btn_3);
		btnTransport=(Button)findViewById(R.id.btn_4);
		btnFacilities=(Button)findViewById(R.id.btn_5);
		btnShopping=(Button)findViewById(R.id.btn_6);
		btnGoneView=findViewById(R.id.view1);

		btnPassengerFlow.setOnClickListener(this);
		btnDining.setOnClickListener(this);
		btnService.setOnClickListener(this);
		btnTransport.setOnClickListener(this);
		btnFacilities.setOnClickListener(this);
		btnShopping.setOnClickListener(this);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				classCode=null;
				if(mapDataListVo!=null){
					float minimumScale=1.0f;

					switch (checkedId) {
					case R.id.cb_1://All
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.VISIBLE);
						btnShopping.setVisibility(View.VISIBLE);btnService.setVisibility(View.VISIBLE);btnGoneView.setVisibility(View.GONE);
						Bitmap defaultPic=null;
						defaultPic=BitmapFactory.decodeResource(getResources(), R.drawable.map_all);
						try {
							FinalBitmap.create(MapActivity.this).display(
									iv,
									ConnectionUtil.DOWNLOAD_URL+mapDataListVo.getResumeMapUrl(),
									screenWidth*3,screenHeight*3
									,defaultPic,defaultPic
									);
							//							iv.setImageResource(R.drawable.map_all);
							minimumScale=screenWidth/Float.parseFloat(mapDataListVo.getWidth());//<1?screenWidth*1.0f/Float.parseFloat(mapDataListVo.getWidth()):1;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LogUtil.i("All");
						//						showMapAllData();
						break;
					case R.id.cb_2://Gf
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.VISIBLE);
						btnShopping.setVisibility(View.GONE);btnService.setVisibility(View.GONE);btnGoneView.setVisibility(View.VISIBLE);
						index=0;
						defaultPic=BitmapFactory.decodeResource(getResources(), R.drawable.map_fg);
						try {
							detailMapDataVo=mapDataListVo.getDetailMap().get(index);
							FinalBitmap.create(MapActivity.this).display(iv, 
									ConnectionUtil.DOWNLOAD_URL+detailMapDataVo.getDetailMapUrl(),
									screenWidth*3,screenHeight*3,
									defaultPic,defaultPic);
							//							iv.setImageResource(R.drawable.map_fg);
							minimumScale=
									screenWidth/Float.parseFloat(detailMapDataVo.getDetailWidth());//<1?screenWidth*1.0f/Float.parseFloat(detailMapDataVo.getDetailWidth()):1;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LogUtil.i("Gf");
						//						showDetailMapdata();
						break;
					case R.id.cb_3://1f
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.VISIBLE);
						btnShopping.setVisibility(View.VISIBLE);btnService.setVisibility(View.VISIBLE);btnGoneView.setVisibility(View.GONE);
						index=1;
						try {
							detailMapDataVo=mapDataListVo.getDetailMap().get(index);
							defaultPic=BitmapFactory.decodeResource(getResources(), R.drawable.map_f1);
							FinalBitmap.create(MapActivity.this).display(iv,
									ConnectionUtil.DOWNLOAD_URL+mapDataListVo.getDetailMap().get(index).getDetailMapUrl(),
									screenWidth*3,screenHeight*3,
									defaultPic,defaultPic);
							//							iv.setImageResource(R.drawable.map_f1);
							minimumScale=
									screenWidth/Float.parseFloat(detailMapDataVo.getDetailWidth());//<1?screenWidth*1.0f/Float.parseFloat(detailMapDataVo.getDetailWidth()):1;

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LogUtil.i("1f");
						//						showDetailMapdata();
						break;
					case R.id.cb_4://2f
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.GONE);;
						btnShopping.setVisibility(View.GONE);btnService.setVisibility(View.VISIBLE);btnGoneView.setVisibility(View.VISIBLE);
						index=2;
						try {
							detailMapDataVo=mapDataListVo.getDetailMap().get(index);
							defaultPic=BitmapFactory.decodeResource(getResources(), R.drawable.map_f2);
							FinalBitmap.create(MapActivity.this).display(iv, 
									ConnectionUtil.DOWNLOAD_URL+mapDataListVo.getDetailMap().get(index).getDetailMapUrl(),
									screenWidth*3,screenHeight*3,
									defaultPic,defaultPic);
							//							iv.setImageResource(R.drawable.map_f2);
							minimumScale=
									screenWidth/Float.parseFloat(detailMapDataVo.getDetailWidth());//<1?screenWidth*1.0f/Float.parseFloat(detailMapDataVo.getDetailWidth()):1;

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LogUtil.i("2f");
						//						showDetailMapdata();
						break;

					default:
						break;
					}
					//					if(mAttacher.getScale()>=mAttacher.getMaximumScale()){
					//						mAttach
					//					}
					mAttacher.setMinimumScale(minimumScale);
					LogUtil.i("mAttacher.getScale()=="+mAttacher.getScale());
					LogUtil.i("mAttacher.getMinimumScale=="+mAttacher.getMinimumScale());
					LogUtil.i("mAttacher.getMaximumScale=="+mAttacher.getMaximumScale());
					mAttacher.setScale(mAttacher.getScale()<mAttacher.getMaximumScale()?mAttacher.getScale():mAttacher.getMaximumScale());
					//					mAttacher.setScale(minimumScale);
					//					mAttacher.onFling(left, top, 100, 100);

					//					mAttacher.onFling(0, 0, 5, 5);
					//					mAttacher.onGlobalLayout();
					//					rg.setOnTouchListener(new OnTouchListener() {
					//						
					//						@Override
					//						public boolean onTouch(View v, MotionEvent event) {
					//							showShortToastMessage("radiogroup触摸");
					//							mAttacher.onDoubleTap(event);
					//							return onTouch(rg, event);
					//						}
					//					});

				}
				else{
					switch (checkedId) {
					case R.id.cb_1://All
						iv.setImageResource(R.drawable.map_all);
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.VISIBLE);
						btnShopping.setVisibility(View.VISIBLE);btnService.setVisibility(View.VISIBLE);btnGoneView.setVisibility(View.GONE);

						break;
					case R.id.cb_2://Gf
						iv.setImageResource(R.drawable.map_fg);
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.VISIBLE);
						btnShopping.setVisibility(View.GONE);btnService.setVisibility(View.GONE);btnGoneView.setVisibility(View.VISIBLE);

						break;
					case R.id.cb_3://1f
						iv.setImageResource(R.drawable.map_f1);
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.VISIBLE);
						btnShopping.setVisibility(View.VISIBLE);btnService.setVisibility(View.VISIBLE);btnGoneView.setVisibility(View.GONE);

						break;
					case R.id.cb_4://2f
						iv.setImageResource(R.drawable.map_f2);
						btnDining.setVisibility(View.VISIBLE);btnTransport.setVisibility(View.GONE);;
						btnShopping.setVisibility(View.GONE);btnService.setVisibility(View.VISIBLE);btnGoneView.setVisibility(View.VISIBLE);

						break;

					default:
						break;
					}

				}

			}


		});
	}
	private void showMapAllData() {
		for(ImageView imageView:markViews){
			rl.removeView(imageView);
		}
		markViews.clear();
		if(mapDataListVo!=null&&mapDataListVo.getLocation()!=null&&!mapDataListVo.getLocation().isEmpty()){
			for(MapDataVo mapDataVo:mapDataListVo.getLocation()){
				if(TextUtil.stringIsNull(classCode)||classCode.equals(mapDataVo.getClasscode())){
					addImageView(mapDataVo);
				}
			}
		}
	}

	private void showDetailMapdata() {
		for(ImageView imageView:markViews){
			rl.removeView(imageView);
		}
		markViews.clear();
		if(mapDataListVo!=null&&mapDataListVo.getDetailMap()!=null&&!mapDataListVo.getDetailMap().isEmpty()){
			detailMapDataVo=mapDataListVo.getDetailMap().get(index);
			for(final MapDataVo mapDataVo:detailMapDataVo.getLocation()){
				if(TextUtil.stringIsNull(classCode)||classCode.equals(mapDataVo.getClasscode())){
					addImageView(mapDataVo);
				}
			}
		}
	}

	private void setAttacher() {
		// The MAGIC happens here!
		if(mAttacher==null){
			mAttacher = new PhotoViewAttacher(iv);
			mAttacher.setScaleType(ScaleType.FIT_CENTER);
			// Lets attach some listeners, not required though!
			mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
			mAttacher.setOnPhotoTapListener(new PhotoTapListener());
		}

	}

	@SuppressLint("NewApi")
	private void addImageView(final MapDataVo mapDataVo) {
		int locX=(int) (Float.parseFloat(mapDataVo.getLocx())*scaleSize+left);
		int locY=(int) (Float.parseFloat(mapDataVo.getLocy())*scaleSize+top);
		//		int width=(int) (40*(mRectF.right-mRectF.left)/screenWidth);
		//		int height=(int) (40*(mRectF.right-mRectF.left)/screenWidth);
		int width=(int) (40*scaleSize);
		int height=(int) (40*scaleSize);
		if(locX>screenWidth||locY>screenHeight||width+locX<0||height+locY<0){
			return;
		}
		RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(width,height);
		int right=0,bottom=0;
		if(locX+width>screenWidth){
			right=screenWidth-width-locX;
		}
		if(locY+height>screenHeight){
			bottom=screenHeight-height-locY;
			//			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}
		layoutParams.setMargins(locX, locY,right,bottom);
		ImageView imageView=new ImageView(MapActivity.this);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageView.setLayoutParams(layoutParams);

		if(TextUtil.stringIsNotNull(mapDataVo.getTitle())){
			imageView.setImageResource(R.drawable.icon_mark);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(MapActivity.this,MapDetailActivity.class);
					intent.putExtra(IntentConstants.DATA_OBJ, mapDataVo);
					startActivity(intent);
				}
			});
		}
		else{
			imageView.setImageResource(R.drawable.icon_mark_n);
		}
		markViews.add(imageView);
		rl.addView(imageView);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_1://
			classCode="lklx";
			break;
		case R.id.btn_2://
			classCode="cy";

			break;
		case R.id.btn_3://
			classCode="fw";

			break;
		case R.id.btn_4://
			classCode="jt";
			break;
		case R.id.btn_5://
			classCode="sb";
			break;
		case R.id.btn_6://
			classCode="gw";
			break;

		default:
			break;
		}
		switch (rg.getCheckedRadioButtonId()) {
		case R.id.cb_1://All
			//			iv.setImageResource(R.drawable.map_all);
			showMapAllData();
			break;
		default:
			showDetailMapdata();
			break;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//		showShortToastMessage("activity.onTouchEvent");
		//		mAttacher.onDoubleTap(event);
		return super.onTouchEvent(event);
	}
	private class PhotoTapListener implements OnPhotoTapListener {

		@Override
		public void onPhotoTap(View view, float x, float y) {
			//            float xPercentage = x * 100f;
			//            float yPercentage = y * 100f;
			//            showShortToastMessage("x="+x+";y="+y);
		}
	}
	private class MatrixChangeListener implements OnMatrixChangedListener {

		@Override
		public void onMatrixChanged(RectF rect) {
			//			        	 showShortToastMessage("rect=="+rect.toString());
			LogUtil.i("rec=="+rect.toString());
			mRectF=rect;
			left=rect.left;
			top=rect.top;
			switch (rg.getCheckedRadioButtonId()) {
			case R.id.cb_1://All
				try {
					scaleSize=(rect.right-rect.left)/Float.parseFloat(mapDataListVo.getWidth());
					//					scaleSize=(rect.right-rect.left)/960;
					showMapAllData();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			default:
				try {
					scaleSize=(rect.right-rect.left)/Float.parseFloat(detailMapDataVo.getDetailWidth());
					//					scaleSize=(rect.right-rect.left)/960;
					showDetailMapdata();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
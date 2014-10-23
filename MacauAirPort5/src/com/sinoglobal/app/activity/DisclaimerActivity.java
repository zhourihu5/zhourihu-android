package com.sinoglobal.app.activity;

import net.tsz.afinal.FinalBitmap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinoglobal.app.activity.AbstractActivity.ItktOtherAsyncTask;
import com.sinoglobal.app.beans.MapDataVo;
import com.sinoglobal.app.service.imp.RemoteImpl;
import com.sinoglobal.app.service.parse.TestJson;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.http.ConnectionUtil;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月6日 上午9:04:31
 * @Description: TODO(用一句话描述该类做什么)  免责声明
 */
public class DisclaimerActivity extends AbstractActivity {
	TextView tvIntroduction;
	ImageView image;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disclaimer);
		initView();
		loadData();
	}
	private void initView() {
//		templateButtonLeft.setVisibility(View.GONE);
		templateButtonRight.setVisibility(View.INVISIBLE);
		titleView.setText(getString(R.string.disclaimer));
     	tvIntroduction=(TextView)findViewById(R.id.tv2);
		image=(ImageView)findViewById(R.id.image);
	}
	private void loadData() {
		try {
			setView(RemoteImpl.getInstance().getServeDetail(true,"about"));
		    LogUtil.i("缓存中读取的关于介绍信息 OK");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			new ItktOtherAsyncTask<Void, Void, MapDataVo>() {

				@Override
				public MapDataVo before(Void... params){
					// TODO Auto-generated method stub
					try {
						return RemoteImpl.getInstance().getServeDetail(false,"about");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
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
			Bitmap defaultPic=BitmapFactory.decodeResource(getResources(), R.drawable.img_default);
			FinalBitmap.create(this).display(image, ConnectionUtil.DOWNLOAD_URL+result.getImgUrl(),defaultPic,defaultPic);
			tvIntroduction.setText(Html.fromHtml(result.getIntroduction()));
			LogUtil.i("网络获取的关于介绍信息 OK");
		}
	}
}
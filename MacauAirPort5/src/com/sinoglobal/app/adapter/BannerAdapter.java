package com.sinoglobal.app.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sinoglobal.app.activity.WebViewActivity;
import com.sinoglobal.app.beans.BannerVo;
import com.sinoglobal.app.util.FlyUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.constants.IntentConstants;
import com.sinoglobal.app.util.http.ConnectionUtil;
import com.aims.mia.R;


public class BannerAdapter extends PagerAdapter {
	private List<BannerVo> banners; // 广告列表
	private ArrayList<ImageView> imageviews; // 存放广告图片的列表
	public static Context context;
    Bitmap defaultPic;
	public BannerAdapter(final Context context, List<BannerVo> banners) {
		this.banners = banners;
		BannerAdapter.context = context;
		imageviews = new ArrayList<ImageView>();
		FinalBitmap fb;
		fb = FinalBitmap.create(context);
		defaultPic=BitmapFactory.decodeResource(context.getResources(),R.drawable.img_default);
		for (int i = 0; i < banners.size(); i++) {
			ImageView img = new ImageView(context);
			img.setScaleType(ImageView.ScaleType.CENTER_CROP);
			final BannerVo bannerVo=banners.get(i);
			fb.display(img,ConnectionUtil.DOWNLOAD_URL+bannerVo.getImgUrl(),defaultPic,defaultPic);
//			img.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					if(TextUtil.stringIsNotNull(bannerVo.getUrl())){
//						Intent intent=new Intent(context,WebViewActivity.class);
//						intent.putExtra(IntentConstants.DATA_STRING, bannerVo.getUrl());
//						FlyUtil.intentForwardNetWork(context, intent);
//					}
//					
//					
//				}
//			});
			imageviews.add(img);
		}
	}

	// 代表的是当前传进来的对象，是不是要在我当前页面显示的
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;// 如果当前显示的View跟你传进来的是同一个View,说明就是要显示的 view
	}

	@Override
	// 图片的数量
	public int getCount() {
		// TODO Auto-generated method stub
		return banners.size();
	}

	/**
	 * 移除当前一张图片
	 * */
	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(view, position, object);
		view.removeView(imageviews.get(position));
	}

	/**
	 * 添加一张图片
	 * */
	@Override
	public Object instantiateItem(ViewGroup view, final int position) {
		// TODO Auto-generated method stub
		view.addView(imageviews.get(position));
		ImageView iv = imageviews.get(position);
		// iv.setOnClickListener(new OnClickListener() {});
		return iv;
	}

}
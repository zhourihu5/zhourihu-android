package com.sinoglobal.app.widget.calendar;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.AbsListView.LayoutParams;

public class FollowGridView extends GridView {
	Context mContext;
	public FollowGridView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		setGirdView();
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}
	private void setGirdView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		setLayoutParams(params);
		setNumColumns(7);// 设置每行列数
		setGravity(Gravity.CENTER_VERTICAL);// 位置居中
//		setVerticalSpacing(1);// 垂直间隔
//		setHorizontalSpacing(1);// 水平间隔
		//设置背景
//		setBackgroundColor(getResources().getColor(R.color.calendar_background));
	//设置参数
//		WindowManager windowManager = ((Activity)mContext).getWindowManager();
//		Display display = windowManager.getDefaultDisplay();
//		int i = display.getWidth() / 7;
//		int j = display.getWidth() - (i * 7);
//		int x = j / 2;
//		setPadding(x, 0, 0, 0);// 居中
	}
}

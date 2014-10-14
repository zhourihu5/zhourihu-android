package com.sinoglobal.app.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.aims.mia.R;

/**
 * 
 * @author zhourihu
 * @createdate 2014-3-10 下午4:47:35
 * @Description: TODO(用一句话描述该类做什么)自定义地图
 */
public class MapView extends View implements OnTouchListener{
    Bitmap bgViewBitmap,markView;
    PointF[] points;
    Paint paint=new Paint();
    Matrix matrix=new Matrix();
	public MapView(Context context,Bitmap bgViewBitmap,Bitmap markView ,PointF[] points) {
		super(context);
		this.bgViewBitmap=bgViewBitmap;
		this.markView=markView;
		this.points=points;
	}
    public void setMarkView(Bitmap markView) {
		this.markView = markView;
		invalidate();
	}
    public void setBgViewBitmap(Bitmap bgViewBitmap) {
		this.bgViewBitmap = bgViewBitmap;
		invalidate();
	}
    public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
		invalidate();
	}
    public void setPoints(PointF[] points) {
		this.points = points;
		invalidate();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(bgViewBitmap,matrix, paint);
		for(int i=0,lenth=points.length;i<lenth;i++){
			canvas.drawBitmap(markView, points[i].x,points[i].y, paint);
		}
		
	}
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	// TODO Auto-generated method stub
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
}
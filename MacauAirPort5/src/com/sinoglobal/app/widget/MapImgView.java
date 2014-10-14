package com.sinoglobal.app.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * @author zhourihu
 * @createdate 2014-3-10 下午4:47:35
 * @Description: TODO(用一句话描述该类做什么)自定义地图
 */
//public class MapImgView extends PhotoView {
public class MapImgView extends ImageView {
	Bitmap markView;
//	PointF[] points;
	RectF[]rectFs;
	Paint paint=new Paint();
	Matrix matrix=new Matrix();
	public MapImgView(Context context) {
		super(context);
	}

	public MapImgView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MapImgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setMarkView(Bitmap markView) {
		this.markView = markView;
		invalidate();
	}
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
		invalidate();
	}
//	public void setPoints(PointF[] points) {
//		this.points = points;
//		invalidate();
//	}
//	public PointF[] getPoints() {
//		return points;
//	}
	public void setRectFs(RectF[] rectFs) {
		this.rectFs = rectFs;
		invalidate();
	}
	public RectF[] getRectFs() {
		return rectFs;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(rectFs!=null&& markView!=null){
			for(int i=0,lenth=rectFs.length;i<lenth;i++){
				canvas.drawBitmap(markView, rectFs[i].left,rectFs[i].top, paint);
			}
		}

	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
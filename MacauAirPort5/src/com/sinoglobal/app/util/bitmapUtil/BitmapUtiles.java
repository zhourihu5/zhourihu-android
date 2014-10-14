package com.sinoglobal.app.util.bitmapUtil;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.aims.mia.R;
import com.aims.mia.R.drawable;

/**
 * @author ty
 * @createdate 2013-4-19 上午11:54:31
 * @Description: 把drowable里的图片转换成bitmap并进行缩放处理
 */
public class BitmapUtiles {

	public static Drawable scaleBitmap(Bitmap bitmapOrg, int setwh, int setht) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);

		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		// 定义预转换成的图片的宽度和高度
		int newWidth = setwh;
		int newHeight = setht;

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);

		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

		return bmd;
	}

	/********************* Drawable转Bitmap ************************/
	public static Bitmap drawabletoBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicWidth();

		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);

		drawable.draw(canvas);

		return bitmap;
	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月13日 下午2:59:41
	 * @Description: (用一句话描述该方法做什么) 以图片名从drawable 资源里加载资源图片
	 * @param drawableName  资源图片名
	 * @param failRes  加载失败时的默认资源图片
	 * @return
	 *
	 */
	public static int getDrwableForName(String drawableName,int failRes) {
		Class<drawable> drawable = R.drawable.class;
		Field field = null;
		int r_id = -1;
		try {
			field = drawable.getField(drawableName);
			r_id = field.getInt(field.getName());
		} catch (Exception e) {
			r_id = failRes;
		}
		return r_id;
	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月13日 下午5:39:56
	 * @Description: (用一句话描述该方法做什么) 根据资源图片名加载资源图片。
	 * @param context 
	 * @param drawableName
	 * @return
	 *
	 */
	public static int getDrwableForName(Context context,String drawableName) {
		 Resources resource = context.getResources();
	     String pkgName = context.getPackageName();
	     int resId = resource.getIdentifier(drawableName, "drawable", pkgName);
		 return resId;
	}
}
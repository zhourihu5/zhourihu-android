package com.sinoglobal.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 
 * @author qiwx
 * @createdate 2014-6-16 下午1:39:20
 * @Description:去掉滚动特性
 */
public class NoScrollListView extends ListView{  
	  
    public NoScrollListView(Context context, AttributeSet attrs){  
         super(context, attrs);  
    }  
 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
         super.onMeasure(widthMeasureSpec, mExpandSpec);  
    }  
}

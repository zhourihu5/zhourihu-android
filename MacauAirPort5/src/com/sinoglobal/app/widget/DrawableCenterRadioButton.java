package com.sinoglobal.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class DrawableCenterRadioButton extends RadioButton{

	public DrawableCenterRadioButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DrawableCenterRadioButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public DrawableCenterRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	 @Override  
	    protected void onDraw(Canvas canvas) {  
	        Drawable[] drawables = getCompoundDrawables();  
	        if (drawables != null) {  
	            Drawable drawableLeft = drawables[0];  
	            if (drawableLeft != null) {  
	                float textWidth = getPaint().measureText(getText().toString());  
	                int drawablePadding = getCompoundDrawablePadding();  
	                int drawableWidth = 0;  
	                drawableWidth = drawableLeft.getIntrinsicWidth();  
	                float bodyWidth = textWidth + drawableWidth + drawablePadding;  
	                canvas.translate((getWidth() - bodyWidth) / 2, 0);  
	            }  
	        }  
	        super.onDraw(canvas);  
	    }  
}

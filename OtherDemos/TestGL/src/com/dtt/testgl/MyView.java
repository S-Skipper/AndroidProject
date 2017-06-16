package com.dtt.testgl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class MyView extends View {

	public MyView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setARGB(200, 200, 0, 0);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(10);
		canvas.drawRect(100, 200, 300, 400, paint);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(200, 200, 100, paint);
		 
		/*paint.setTextSize(20);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText("Apple", 50, 50, paint);
		canvas.drawLine(0, 50, 200, 50, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(
				   getResources(), R.drawable.ic_launcher), 100, 100, paint);*/
		
		super.onDraw(canvas);
	}
}

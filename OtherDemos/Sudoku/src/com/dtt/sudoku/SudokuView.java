package com.dtt.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

public class SudokuView extends View {

	private float width;
	private float height;
	private Game game = new Game();
	private int px,py;
	
	public SudokuView(Context context) {
		super(context);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.width = w/9f;
		this.height = h/9f;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		}
		
		px = (int) (event.getX()/width);
		py = (int) (event.getY()/height);
		
		int usedNumbers[] = game.getUsedNumbers(px, py);
		if (usedNumbers!=null) {
			new KeyDialog(getContext(), usedNumbers,this).show();
		}
		
		return true;
	}
	
	public void setNumber(int Num){
		if (game.setNumberValid(px,py,Num)){
			invalidate();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint bgPaint = new Paint();
		bgPaint.setColor(getResources().getColor(R.color.sudoku_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
		
		Paint hlPaint = new Paint();
		hlPaint.setColor(getResources().getColor(R.color.sudoku_highlight));
		
		Paint ltPaint = new Paint();
		ltPaint.setColor(getResources().getColor(R.color.sudoku_light));
		
		Paint dkPaint = new Paint();
		dkPaint.setColor(getResources().getColor(R.color.sudoku_dark));
		
		for(int i=0;i<9;i++){
			if(i%3==0){
				canvas.drawLine(0, i*height, getWidth(), i*height, dkPaint);
				canvas.drawLine(0, i*height+1, getWidth(), i*height+1, hlPaint);
				canvas.drawLine(i*width, 0, i*width, getHeight(), dkPaint);
				canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hlPaint);
			}
			else{
				//ºáÏß
				canvas.drawLine(0, i*height, getWidth(), i*height, ltPaint);
				canvas.drawLine(0, i*height+1, getWidth(), i*height+1, hlPaint);
				//ÊúÏß
				canvas.drawLine(i*width, 0, i*width, getHeight(), ltPaint);
				canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hlPaint);
			}
		}
		
		//number pen
		Paint numberPaint = new Paint();
		numberPaint.setColor(Color.BLACK);
		numberPaint.setStyle(Style.STROKE);
		numberPaint.setTextSize(height*0.75f);
		numberPaint.setTextAlign(Align.CENTER);
		
		FontMetrics fm = numberPaint.getFontMetrics();
		float x = width/2;
		float y = height/2-(fm.ascent+fm.descent)/2;
		
		for(int i=0;i<9;i++)
		for(int j=0;j<9;j++)
		{
			canvas.drawText(game.getNumberStr(i, j), i*width+x, j*height+y, numberPaint);
		}
		
		super.onDraw(canvas);
	}
}

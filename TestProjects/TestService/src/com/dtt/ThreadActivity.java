package com.dtt;

import com.dtt.service.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ThreadActivity extends Activity {

	public static final int UPDATE_TEXT = 1;
	
	private TextView textView;
	private Button changeText;
	
	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg){
			switch (msg.what) {
			case UPDATE_TEXT:
				textView.setText("Nice to meet you!");
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thread_layout);
		
		changeText = (Button) findViewById(R.id.change_text);
		textView = (TextView) findViewById(R.id.textView);
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				textView.setText("Welcome to thread!");
				textView.setTextColor(Color.RED);
			}
		}).start();*/
		
		changeText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						//textView.setText("Nice to meet you!");
						Message message = new Message();
						message.what = UPDATE_TEXT;
						handler.sendMessage(message);
					}
				}).start();
			}
		});
	}
}

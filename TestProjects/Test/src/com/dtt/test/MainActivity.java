package com.dtt.test;

import java.util.Calendar;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    private String message;
    private Button btn1;
    private Button btn2;
    private EditText text;
    private ImageView imageView;
    private ProgressBar progressBar;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //Òþ²Ø±êÌâÀ¸
		setContentView(R.layout.activity_main);
		
		/*Button titleBack = (Button)findViewById(R.id.title_back);
		Button titleGo = (Button)findViewById(R.id.title_go);
		
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getContext(), "Clicked Back", Toast.LENGTH_SHORT).show();
			    finish();
			}
		});
		
		titleGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Clicked Go", Toast.LENGTH_SHORT).show();
			}
		});*/
		
		
		btn1 = (Button)findViewById(R.id.Btn1);
		btn2 = (Button)findViewById(R.id.Btn2);
		text = (EditText)findViewById(R.id.msg);
		imageView = (ImageView)findViewById(R.id.image_view);
		progressBar = (ProgressBar)findViewById(R.id.progress_bar);
		
		btn1.setOnClickListener(new OnClickListener() {
			int flag = 1;
			@Override
			public void onClick(View v) {
				if (flag == 1) {
					imageView.setImageResource(R.drawable.ic_launcher);
					flag=0;
				}else {
					imageView.setImageResource(R.drawable.logo);
					flag=1;
				}
				
				message = text.getText().toString();
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Usage of Dialog
				ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setTitle("Progress Dialog");
				progressDialog.setMessage("Loading...");
				//progressDialog.setCancelable(true);
				progressDialog.show();
				
				/*AlertDialog.Builder dialogBuilder = new 
						            AlertDialog.Builder(MainActivity.this);
				
				dialogBuilder.setTitle("Title:Alert Dialog");
				dialogBuilder.setMessage("Message:Hello!");
				dialogBuilder.setCancelable(false);
				
				dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@SuppressLint("ShowToast")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
					}
				});
				
				dialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					@SuppressLint("ShowToast")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
					}
				} );
				dialogBuilder.show();*/
				
				//Usage of Progress Bar
				/*int progress = progressBar.getProgress();
				if(progress>=100) progressBar.setVisibility(View.GONE);
				progress += 10;
				progressBar.setProgress(progress);
				*/
				
				/*if (progressBar.getVisibility()==View.GONE) {
					progressBar.setVisibility(View.VISIBLE);
				}else {
					progressBar.setVisibility(View.GONE);
				}*/
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
		//return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
			break;
		case R.id.about_item:
			Toast.makeText(this, "All rights (c) DTT", Toast.LENGTH_SHORT).show();
			break;
		case R.id.quit_item:
			finish();
			break;

		default:
			break;
		}
		return true;
	}

	private String getCurrentTime(){
		Calendar currentTime = Calendar.getInstance();
		return String.format("%d:%d:%d",
				currentTime.get(Calendar.HOUR_OF_DAY),
				currentTime.get(Calendar.MINUTE),
				currentTime.get(Calendar.SECOND));
	}
}

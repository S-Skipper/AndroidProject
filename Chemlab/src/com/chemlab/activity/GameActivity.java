package com.chemlab.activity;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class GameActivity extends Activity implements OnClickListener{

	private String linkPhantoscope = "file:///android_asset/www/phantoscope/index.html";
	private String link2048 = "file:///android_asset/www/2048.html";
	private String linkMine = "file:///android_asset/www/mine/index.html";
	private String linkBird = "file:///android_asset/www/puzzle/index.html";
	private String linkPTable = "file:///android_asset/www/ptable/index.html";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		
		((Button)findViewById(R.id.button_back)).setOnClickListener(this);
		((TextView)findViewById(R.id.textview_title)).setText("小游戏");
		((BootstrapButton)findViewById(R.id.game_mine)).setOnClickListener(this);
		((BootstrapButton)findViewById(R.id.game_puzzle)).setOnClickListener(this);
		((BootstrapButton)findViewById(R.id.game_2048)).setOnClickListener(this);
		((BootstrapButton)findViewById(R.id.game_phantoscope)).setOnClickListener(this);
		((BootstrapButton)findViewById(R.id.game_ptable)).setOnClickListener(this);
		
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.game_mine:
			WebViewActivity.actionStart(GameActivity.this, "扫雷",linkMine);
			break;
		case R.id.game_puzzle:
			WebViewActivity.actionStart(GameActivity.this, "拼图",linkBird);
			break;
		case R.id.game_2048:
			WebViewActivity.actionStart(GameActivity.this, "2048",link2048);
			break;
		case R.id.game_phantoscope:
			WebViewActivity.actionStart(GameActivity.this, "万花筒",linkPhantoscope);
			break;
		case R.id.game_ptable:
			WebViewActivity.actionStart(GameActivity.this, "元素周期表",linkPTable);
			break;
		case R.id.button_back:
			finish();
			break;
		default:
			break;
		}
	}
	
}

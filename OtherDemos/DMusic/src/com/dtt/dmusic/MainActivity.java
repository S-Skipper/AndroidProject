package com.dtt.dmusic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private List<Music> musicList = new ArrayList<Music>();
	private ListView listView;
	MusicAdapter adapter;
	
	private TextView songState;
	private TextView songMsg;
	private Button playButton;
	private Button lastButton;
	private Button nextButton;
	private SeekBar seekBar;
	private TextView currentPosition;
	private TextView duration;

	private SimpleDateFormat timeFormat = new SimpleDateFormat("m:ss");
	private String fileDirectory = Environment.getExternalStorageDirectory().toString()+"/songs/";
	private int current_music = 0;
	
	private MusicService musicService;
	
	Handler handler = new Handler();

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			if (seekBar.getProgress()== seekBar.getMax()) {
				musicService.stopMusic();
				updatePlayStaus();
			}
			seekBar.setProgress(seekBar.getMax()*musicService.getCurrentPosition()/musicService.getDuration());
			currentPosition.setText(timeFormat.format(musicService.getCurrentPosition()));
			duration.setText(timeFormat.format(musicService.getDuration()));
			handler.postDelayed(r, 500);
		}
	};
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			musicService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			musicService = ((MusicService.PlayerBinder)binder).getService();
			updatePlayStaus();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initMusic();
		bindMusicService();
		
		adapter = new MusicAdapter(MainActivity.this, R.layout.music_item, musicList);
		listView = (ListView) findViewById(R.id.song_list);
		listView.setAdapter(adapter);
		
		songState = (TextView) findViewById(R.id.song_state);
		songMsg = (TextView) findViewById(R.id.song_info);
		playButton = (Button) findViewById(R.id.play);
		lastButton = (Button) findViewById(R.id.last);
		nextButton = (Button) findViewById(R.id.next);
		seekBar = (SeekBar) findViewById(R.id.seek_bar);
		
		currentPosition = (TextView) findViewById(R.id.current_position);
		duration = (TextView) findViewById(R.id.song_duration);
		
		playButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		
		//Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					final int position, long id) {
				
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
				dialog.setMessage("确认删除吗?");
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (current_music == position) {
							Toast.makeText(MainActivity.this, "正在播放此歌", Toast.LENGTH_SHORT).show();
						}else {
							musicList.remove(position);
							adapter.notifyDataSetChanged();
						}
						
					}
				});
				
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				
				vibrator.vibrate(20);
				dialog.show();
				
				return true;
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Music music = musicList.get(position);
				current_music = position;
				songMsg.setText(music.getName());
				musicService.playNewMusic(fileDirectory+music.getName());
				updatePlayStaus();
			}
		});
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekbar) {
				//songMsg.setText("stop");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				//songMsg.setText("start");
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					musicService.setCurrentPosition((float)(progress)/100);
				}
			}
		});
	}

	private void bindMusicService() {
		Intent intent = new Intent(MainActivity.this,MusicService.class);
		startService(intent);
		bindService(intent, connection, BIND_AUTO_CREATE);
	}

	private void initMusic() {
		List<String> list = new ArrayList<String>();
		int tmp = 1;
		findAllMusic(fileDirectory, list);
		Collections.sort(list);
		
		for (String musicName : list) {
			musicList.add(new Music(musicName,tmp++));
		}
		/*for (int i = 0; i < 10; i++) {
			musicList.add(new Music("music.aac",i));
		}*/
	}
	
	public void findAllMusic(String path,List<String> list){
		File file = new File(path);
		File [] subFiles = file.listFiles();
		if (subFiles!=null) {
			for (File mfile : subFiles) {
				if (mfile.isFile()&&isMusic(mfile)) {
					list.add(mfile.getName());
				}
			}
		}
	}
	public boolean isMusic(File file){
		if(file.getName().endsWith(".mp3")
		 ||file.getName().endsWith(".aac")
		 ||file.getName().endsWith(".wav")
		 ||file.getName().endsWith(".wma")
		 ||file.getName().endsWith(".au")
		 ||file.getName().endsWith(".mid")
		 ||file.getName().endsWith(".ogg")
		){
			return true;
		}
		
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			musicService.playMusic();
			break;
		case R.id.last:
			current_music--;
			if (current_music>=0) {
				musicService.playNewMusic(fileDirectory+musicList.get(current_music).getName());
			}else {
				current_music=0;
				Toast.makeText(getApplication(), "已经是第一首", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.next:
			current_music++;
			if (current_music < musicList.size()) {
				musicService.playNewMusic(fileDirectory+musicList.get(current_music).getName());
			}else {
				current_music=musicList.size()-1;
				Toast.makeText(getApplication(), "已经是最后一首", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		updatePlayStaus();
	}
	
	private void updatePlayStaus() {
		//这里显示歌曲信息
		songMsg.setText(musicList.get(current_music).getName());
		if (musicService.getState()== MusicService.PLAYING) {
			handler.post(r);
			playButton.setBackgroundResource(R.drawable.stopmusic);
			songState.setText("正在播放：");
		} else {
			handler.removeCallbacks(r);
			playButton.setBackgroundResource(R.drawable.playmusic);
			songState.setText("已暂停：");
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(connection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.setting:
			break;
		case R.id.about:
			Toast.makeText(getApplication(), "All rights (c) DTT @SMIE-SYSU", 
					        Toast.LENGTH_SHORT).show();	
			break;
		case R.id.quit:
			handler.removeCallbacks(r);
			Intent i = new Intent(MainActivity.this,MusicService.class);
			stopService(i);
			finish();
			break;
		default:
			break;
		}
		return true;
	}
}

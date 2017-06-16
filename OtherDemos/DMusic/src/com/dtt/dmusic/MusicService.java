package com.dtt.dmusic;

import java.io.File;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {

	public static final int IDLE = 0;
	public static final int STOP = 1;
	public static final int PLAYING = 2;
	public static final int PAUSE = 3;
	
	
	private int state;
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	private PlayerBinder mBinder = new PlayerBinder(); 
	
	class PlayerBinder extends Binder{
		MusicService getService(){
			return MusicService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		state = IDLE;
		//mediaPlayer = new MediaPlayer();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
	
	public void playNewMusic(String filePath){
		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.reset();
			state = IDLE;
		}
		
		try {
			mediaPlayer.setDataSource(filePath);
			mediaPlayer.prepare();
			mediaPlayer.start();
			state = PLAYING;
		} catch (Exception e) {
			Toast.makeText(getApplication(), "无法播放!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void playMusic(){
		if (state == IDLE) {
			try {
				mediaPlayer.reset();
				File file = new File(Environment.getExternalStorageDirectory()+"/songs/music.aac");
				//File file = new File("/data/msic/music.wav");
				mediaPlayer.setDataSource(file.getPath());
				mediaPlayer.prepare();
				mediaPlayer.start();
				state = PLAYING;
			} catch (Exception e) {
				Toast.makeText(getApplication(), "找不到文件!", Toast.LENGTH_SHORT).show();
			}
		} else if (state == PLAYING) {
			mediaPlayer.pause();
			state = PAUSE;
		} else {
			mediaPlayer.start();
			state = PLAYING;
		}
	}
	
	public void stopMusic(){
		mediaPlayer.stop();
		state = STOP;
		
		try {
			mediaPlayer.prepare();
			mediaPlayer.seekTo(0);
		} catch (Exception e) {
		}
	}
	
	public void setCurrentPosition(float percent){
		if (state != STOP) {
			mediaPlayer.seekTo((int)(percent*mediaPlayer.getDuration()));
		}
	}

	public int getDuration(){
		if (state == IDLE) {
			return 0;
		}
		return mediaPlayer.getDuration();
	}
	
	public int getCurrentPosition(){
		if (state == IDLE) {
			return 0;
		}
		return mediaPlayer.getCurrentPosition();
	}
	
	public int getState(){
		return state;
	}
}

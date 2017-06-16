package com.dtt;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayerActivity extends Activity implements OnClickListener{

	private Button playAudio;
	private Button pauseAudio;
	private Button stopAudio;
	private Button playVideo;
	private Button pauseVideo;
	private Button replayVideo;
	
	private VideoView videoView;
	
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_layout);
		
		playAudio = (Button) findViewById(R.id.play_audio);
		pauseAudio = (Button) findViewById(R.id.pause_audio);
		stopAudio = (Button) findViewById(R.id.stop_audio);
		playVideo = (Button) findViewById(R.id.play_video);
		pauseVideo = (Button) findViewById(R.id.pause_video);
		replayVideo = (Button) findViewById(R.id.replay_video);
		videoView = (VideoView) findViewById(R.id.video_view);
		
		playAudio.setOnClickListener(this);
		pauseAudio.setOnClickListener(this);
		stopAudio.setOnClickListener(this);
		playVideo.setOnClickListener(this);
		pauseVideo.setOnClickListener(this);
		replayVideo.setOnClickListener(this);
		
		initMediaPlayer();
		initVideoPath();
	}

	private void initVideoPath() {
		try {
			File videoFile = new File(Environment.getExternalStorageDirectory(),
	                "movie.mp4");
			videoView.setVideoPath(videoFile.getPath());
		} catch (Exception e) {
			Toast.makeText(this, "File can't open!",Toast.LENGTH_SHORT).show();
		}
	}

	private void initMediaPlayer() {
		try {
			File audioFile = new File(Environment.getExternalStorageDirectory(),
					                    "music.wav");
			mediaPlayer.setDataSource(audioFile.getPath());
			mediaPlayer.prepare();
		} catch (IOException e) {
			Toast.makeText(this, "File can't open!",Toast.LENGTH_SHORT).show();
			//e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_audio:
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
			break;
		case R.id.pause_audio:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
			break;
		case R.id.stop_audio:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.reset();
				initMediaPlayer();
			}
			break;
		case R.id.play_video:
			if (!videoView.isPlaying()) {
				videoView.start();
			}
			break;
		case R.id.pause_video:
			if (videoView.isPlaying()) {
				videoView.pause();
			}
			break;
		case R.id.replay_video:
			if (videoView.isPlaying()) {
				videoView.resume();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		
		if (videoView !=null) {
			videoView.suspend();
		}
	}
}

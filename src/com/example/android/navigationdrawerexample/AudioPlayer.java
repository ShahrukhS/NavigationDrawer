package com.example.android.navigationdrawerexample;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioPlayer extends BaseActivity{
	
	MediaPlayer mediaPlayer;
	String url = "http://soundz.mp3slash.net/indian/yrfstunningdancetracks/[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.audioplayer, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		Button btnPause = (Button) findViewById(R.id.btnPause);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(url);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // might take long! (for buffering, etc)
		btnPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mediaPlayer.isPlaying() == true){
					mediaPlayer.pause();
				}else{
					mediaPlayer.start();
				}			
			}
		});
	}

}

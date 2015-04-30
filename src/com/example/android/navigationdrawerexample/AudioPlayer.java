package com.example.android.navigationdrawerexample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AudioPlayer extends BaseActivity {
	private static String fileName = "donwload.mp3";
    private static final String MY_URL = "http://soundz.mp3slash.net/indian/yrfstunningdancetracks/[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
	MediaPlayer mediaPlayer;
	ImageButton btnPlay, btnBackward, btnForward, btnDownload;
	String theurl = "http://soundz.mp3slash.net/indian/yrfstunningdancetracks/[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.audioplayer, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnBackward = (ImageButton) findViewById(R.id.btnBackward);
		btnForward = (ImageButton) findViewById(R.id.btnForward);
		btnDownload = (ImageButton) findViewById(R.id.btnDownload);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(theurl);
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
		}
		btnPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mediaPlayer.isPlaying() == true){
					mediaPlayer.pause();
					btnPlay.setImageResource(R.drawable.btn_play);
				}else{
					mediaPlayer.start();
					btnPlay.setImageResource(R.drawable.btn_pause);
				}
			}
		});
		btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audioDownload();
			}
		});
	}
	public void audioDownload(){
		URL url;
		try {
			url = new URL(MY_URL);
			HttpURLConnection c;
			c = (HttpURLConnection) url.openConnection();		
	        c.setRequestMethod("GET");
	        c.setDoOutput(true);
	        c.connect();
	
	        String PATH = Environment.getExternalStorageDirectory()+ "/FaizaneAshrafi/";
	        Log.d("Abhan", "PATH: " + PATH);
	        File file = new File(PATH);
	        if(!file.exists()) {
	           file.mkdirs();
	        }
	        File outputFile = new File(file, fileName);
	        FileOutputStream fos = new FileOutputStream(outputFile);
	        InputStream is = c.getInputStream();
	        byte[] buffer = new byte[1024];
	        int len1 = 0;
	        while ((len1 = is.read(buffer)) != -1) {
	            fos.write(buffer, 0, len1);
	        }
	        //fos.flush();
	        fos.close();
	        is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*Runnable run = new Runnable() {
		@Override
		public void run() {
			seekUpdation();
			}
		};
	public void seekUpdation() {
		seek_bar.setProgress(player.getCurrentPosition());
		seekHandler.postDelayed(run, 1000);
	}*/

}

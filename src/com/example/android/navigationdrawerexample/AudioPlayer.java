package com.example.android.navigationdrawerexample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AudioPlayer extends BaseActivity {
	private static String fileName = "[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
    private static final String MY_URL = "http://soundz.mp3slash.net/indian/yrfstunningdancetracks/";
	MediaPlayer mediaPlayer;
	SeekBar seekbar; Handler seekHandler = new Handler();
	ImageButton btnPlay, btnBackward, btnForward, btnDownload;
	TextView txtTitle, txtVocalist, txtSize;
	String theurl = "http://soundz.mp3slash.net/indian/yrfstunningdancetracks/[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
	
	public void init(){
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnBackward = (ImageButton) findViewById(R.id.btnBackward);
		btnForward = (ImageButton) findViewById(R.id.btnForward);
		btnDownload = (ImageButton) findViewById(R.id.btnDownload);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtVocalist = (TextView) findViewById(R.id.txtVocalist);
		txtSize = (TextView) findViewById(R.id.txtSize);
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		
		Bundle backpack = getIntent().getExtras();
		txtTitle.setText(backpack.getString("title"));
		txtVocalist.setText(backpack.getString("desc"));
		txtSize.setText(backpack.getString("size"));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.audioplayer, frameLayout);
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		init();
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
		
		seekbar.setProgress(mediaPlayer.getCurrentPosition());
		seekbar.setMax(mediaPlayer.getDuration());
		
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
		seekHandler.removeCallbacks(run);
		seekHandler.postDelayed(run, 1000);
		btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new audioDownload(getBaseContext()).execute(MY_URL, fileName);
			}
		});
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser){
					mediaPlayer.seekTo(progress);
			        seekbar.setProgress(progress);
				}
			}
		});
		/*btnForward.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mediaPlayer.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mediaPlayer.getDuration()){
                    // forward song
                	mediaPlayer.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                	mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });*/
	}
	Runnable run = new Runnable() {
		@Override
		public void run() {
				if(mediaPlayer.isPlaying())
					Toast.makeText(getBaseContext(), String.valueOf(mediaPlayer.getDuration()), Toast.LENGTH_SHORT).show();
					seekbar.setProgress(mediaPlayer.getCurrentPosition());
					seekHandler.postDelayed(this, 1000);
			}
		};
	private class audioDownload extends AsyncTask<String, Void, String> {
		Context c;
		audioDownload(Context context){
			 c = context;
		}
		private void downloader(String theurl, String thefile){
			URL url;
			try {
				url = new URL(theurl + thefile);
				Log.e("Online Url", "download url:" + url.toString());
				HttpURLConnection c;
				c = (HttpURLConnection) url.openConnection();		
		        c.setRequestMethod("GET");
		        c.setDoOutput(true);
		        c.connect();
		
		        String PATH = Environment.getExternalStorageDirectory()+ "/FaizaneAshrafi/";
		        Log.e("Phone Path", "PATH: " + PATH);
		        File file = new File(PATH);
		        if(!file.exists()) {
		           file.mkdirs();
		        }
		        File outputFile = new File(file, thefile);
		        FileOutputStream fos = new FileOutputStream(outputFile);
		        InputStream is = c.getInputStream();
		        byte[] buffer = new byte[1024*4];
		        int len1 = 0;
		        while ((len1 = is.read(buffer)) != -1) {
		            fos.write(buffer, 0, len1);
		            fos.flush();
		        }
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
	    protected String doInBackground(String... urls) {
	        downloader(urls[0], urls[1]);
	        return urls[1];
	    }
	    protected void onPostExecute(String result) {
	    	super.onPostExecute(result);
	    	Toast.makeText(c, result+" audio has been downloaded", Toast.LENGTH_LONG).show();
	    }
	}
}

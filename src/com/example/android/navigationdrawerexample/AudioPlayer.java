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
import android.media.MediaPlayer.OnErrorListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AudioPlayer extends BaseActivity {    
	MediaPlayer mediaPlayer;
	SeekBar seekbar; Handler seekHandler = new Handler();
	ImageButton btnPlay, btnBackward, btnForward;
	Button btnDownload;
	TextView txtTitle, txtVocalist;
	TextView txtLoader;
	
	//"http://soundz.mp3slash.net/indian/yrfstunningdancetracks/[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
	private String fileName = "";
	String downloadURL="";
	private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
	String theurl = "";
	
	public void init(){
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnBackward = (ImageButton) findViewById(R.id.btnBackward);
		btnForward = (ImageButton) findViewById(R.id.btnForward);
		btnDownload = (Button) findViewById(R.id.btnDownload);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtVocalist = (TextView) findViewById(R.id.txtVocalist);
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		txtLoader = (TextView) findViewById(R.id.txtLoader);
		
		Bundle backpack = getIntent().getExtras();
		txtTitle.setText(backpack.getString("title"));
		txtVocalist.setText(backpack.getString("desc"));
		theurl = backpack.getString("URL").replace(" ","%20");
		String tempfile[] = theurl.split("/");
		fileName = tempfile[tempfile.length-1];
		/*int index = theurl.length() - fileName.length();
		downloadURL = theurl.substring(0, index);*/
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
			//theurl = "http://soundz.mp3slash.net/indian/yrfstunningdancetracks/[Songs.PK]%20YRF%20Stunning%20Dance%20Tracks%20-%2001%20-%20Ishq%20Shava.mp3";
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
				if(mediaPlayer != null){
					if(mediaPlayer.isPlaying() == true){
						mediaPlayer.pause();
						btnPlay.setImageResource(R.drawable.btn_play);
					}else{
						mediaPlayer.start();
						btnPlay.setImageResource(R.drawable.btn_pause);
					}
				}else{
					Toast.makeText(getBaseContext(), "Error occurred while playing audio.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		seekHandler.removeCallbacks(run);
		seekHandler.postDelayed(run, 10000);
		btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new audioDownload(getBaseContext()).execute(theurl, fileName);
			}
		});
		/*mediaPlayer.setOnInfoListener(new OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                	spinner.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    spinner.setVisibility(View.GONE);
                }
                return false;
            }
        });*/
		mediaPlayer.setOnErrorListener(new OnErrorListener(){
		    public boolean onError(MediaPlayer mp, int what, int extra){
		    	Toast.makeText(getBaseContext(), "Error occurred while playing audio.", Toast.LENGTH_SHORT).show();
			    mediaPlayer.stop();
			    seekHandler.removeCallbacks(run);
			    mediaPlayer.release();
			    mediaPlayer=null;
			    return true;
		    }
		});
		
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                mediaPlayer.release();
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
		btnForward.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
            	if(mediaPlayer != null){
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
            	}else{
					Toast.makeText(getBaseContext(), "Error occurred while playing audio.", Toast.LENGTH_SHORT).show();
				}
            }
        });
		btnBackward.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
            	if(mediaPlayer != null){
	                // get current song position
	                int currentPosition = mediaPlayer.getCurrentPosition();
	                // check if seekBackward time is greater than 0 sec
	                if(currentPosition - seekBackwardTime >= 0){
	                    // forward song
	                    mediaPlayer.seekTo(currentPosition - seekBackwardTime);
	                }else{
	                    // backward to starting position
	                	mediaPlayer.seekTo(0);
	                }
            	}else{
    				Toast.makeText(getBaseContext(), "Error occurred while playing audio.", Toast.LENGTH_SHORT).show();
    			}
            }
        });
	}
	Runnable run = new Runnable() {
		@Override
		public void run() {
				if(mediaPlayer.isPlaying()){
					seekbar.setProgress(mediaPlayer.getCurrentPosition());
					seekHandler.postDelayed(this, 1000);
				}
			}
		};
	class audioDownload extends AsyncTask<String, Void, String> {
		Context c;
		audioDownload(Context context){
			 c = context;
		}
		private void downloader(String theurl, String thefile){
			URL url;
			try {
				url = new URL(theurl);
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
		        byte[] buffer = new byte[1024];
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
	    	Toast.makeText(c, result+" audio has been downloaded", Toast.LENGTH_SHORT).show();
	    }
	}
}

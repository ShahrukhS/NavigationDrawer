package com.example.android.navigationdrawerexample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends BaseActivity{
	ListView mMainListView;
	TextView txtnoData;
	MainModel[] values;
	String title;
	String parameter;
	private ProgressBar spinner;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.fragment_main, frameLayout);
		mMainListView = (ListView) findViewById(R.id.mylist);
		txtnoData = (TextView) findViewById(R.id.txtnoData);
		spinner = (ProgressBar) findViewById(R.id.progressBar);
		
		mDrawerList.setItemChecked(position, true);
		title = mPlanetTitles[position];
		setTitle(title);
		parameter = getIntent().getStringExtra("param");
		new Read().execute(this);
		mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent travel = new Intent(getApplicationContext(), AudioPlayer.class);
				Bundle backpack = new Bundle();
				backpack.putString("title", values[position].getTitle());
				backpack.putString("desc", values[position].getDesc());
				backpack.putString("URL", values[position].getUrl());
				travel.putExtras(backpack);
				startActivity(travel);
			}
		
		});
	}
	private class Read extends AsyncTask<Context, Void, Void>{
		Context c;
		public String parseData() throws ClientProtocolException, IOException, JSONException{
			HttpClient client = new DefaultHttpClient();
			String URL = "http://faizaneashraf.com/android_update/bayan.php";
			HttpPost request = new HttpPost(URL);
			if(!parameter.isEmpty()){
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
	                    2);
	            nameValuePairs.add(new BasicNameValuePair("type", parameter));
	            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}else{
				Toast.makeText(getBaseContext(), "Cannot get the request data", Toast.LENGTH_SHORT).show();
			}
				HttpResponse response = client.execute(request);
				int status = response.getStatusLine().getStatusCode();
				if(status == 200){
					HttpEntity e = response.getEntity();
					String data = EntityUtils.toString(e);
					return data;
				}else{
					Log.e("Context", "Failed to retreive");
					return null;
				}
		}
		
		@Override
		protected Void doInBackground(Context... params) {
			// TODO Auto-generated method stub
			spinner.setVisibility(View.VISIBLE);
			c = params[0];
	        try {
	        	String response = parseData();
	        	if (response.isEmpty() == false && response != null) {
	        		JSONArray jArray = new JSONArray(response);
					values = new MainModel[jArray.length()];
					for(int i=0; i<jArray.length(); i++){
						//Log.d("data",);
						JSONObject jObject = jArray.getJSONObject(i);
						values[i] = new MainModel(jObject.getString("vocalist"), R.drawable.ic_launcher, jObject.getString("file"));
					}
				}else{
					values = new MainModel[0];
					Toast.makeText(c, "Couldn't get any data from the server", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	        return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			spinner.setVisibility(View.GONE);
			if(values.length > 0)
				mMainListView.setAdapter(new MainAdapter(c, values));
			else{
				mMainListView.setVisibility(View.GONE);
				txtnoData.setVisibility(View.VISIBLE);
				txtnoData.setText("There is no data for "+title);
			}
		}
	}
}

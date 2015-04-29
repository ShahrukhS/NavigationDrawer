package com.example.android.navigationdrawerexample;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenu extends BaseActivity{
	ListView mMainListView;
	MainModel[] values;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.fragment_main, frameLayout);
		mMainListView = (ListView) findViewById(R.id.mylist);
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		new Read().execute(this);
		/*mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String title = values[position].getTitle();
			}
		
		});*/
	}
	private class Read extends AsyncTask<Context, Void, Void>{
		Context c;
		public String parseData() throws ClientProtocolException, IOException, JSONException{
			String URL = "http://faizaneashraf.com/android_update/bayan.php";
			HttpPost request = new HttpPost(URL);
			HttpClient client = new DefaultHttpClient();
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
			c = params[0];
	        try {
	        	String response = parseData();
	        	if (response.isEmpty() == false && response != null) {
	        		JSONArray jArray = new JSONArray(response);
					values = new MainModel[jArray.length()];
					for(int i=0; i<jArray.length(); i++){
						//Log.d("data",);
						JSONObject jObject = jArray.getJSONObject(i);
						values[i] = new MainModel(jObject.getString("name"), "something", R.drawable.ic_launcher, 2.68);
					}
				}else{
					values = new MainModel[1];
					values[0] = new MainModel("baby", "something", R.drawable.ic_launcher, 2.68);
					Toast.makeText(c, "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	        return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mMainListView.setAdapter(new MainAdapter(c, values));
		}
	}
}

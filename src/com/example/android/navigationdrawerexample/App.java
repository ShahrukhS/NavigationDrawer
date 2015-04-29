package com.example.android.navigationdrawerexample;

import android.app.Application;
import android.content.Context;

public class App extends Application {

	public static Context myContext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myContext = getApplicationContext();
	}
 
 
}

package com.helixs.weather;

import android.app.Application;
import android.content.Context;

import com.helixs.compat.UtilConfig;
import com.helixs.net.Net;
import com.helixs.tools.BuildConfig;

/**
 * Created by helixs on 2017/2/17.
 */

public class App extends Application {
	public static App context;
	@Override
	public void onCreate() {
		context = this;
		UtilConfig.init(context, BuildConfig.DEBUG);
		Net.init();
		super.onCreate();
	}
	public static Context getContext(){
		return context.getApplicationContext();
	}
}

package com.helixs.weather;

import android.content.Intent;

/**
 * Created by helixs on 2017/2/28.
 */

public class LocationBroadcastReciver{
	public static final String ACTION = "com.yokou.weather.broadcastreceiver.loaction";
	public static final String IP_ADRESS = "IP_ADRESS";



	public static void sendIpAdress(String ip) {
		Intent intent = new Intent(ACTION);
		intent.putExtra(IP_ADRESS, ip);
		App.getContext().sendBroadcast(intent);
	}
}

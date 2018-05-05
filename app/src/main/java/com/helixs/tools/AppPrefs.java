package com.helixs.tools;

import android.content.Context;
import android.support.annotation.NonNull;

import com.helixs.weather.App;

import java.lang.ref.WeakReference;

/**
 * Created by helixs on 2017/2/28.
 */

public class AppPrefs extends Prefs {
	private static final String PREFS_NAME = "app_pref";
	private static AppPrefs cachedPrefs;
	private final WeakReference<Context> context;
	private static final String LAST_IP_ADRESS = "ipAdress";

	public final StringPref ipAdressPref = new StringPref(LAST_IP_ADRESS, "");


	protected AppPrefs(Context context) {
		super(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE));
		this.context = new WeakReference<>(context.getApplicationContext());
	}

	/**
	 * 获取AppPrefs 实例,其context为App
	 *
	 * @return AppPrefs 实例
	 */
	public static AppPrefs get() {
		return getInstance(App.getContext());
	}

	/**
	 * 获取AppPrefs 实例,获取的实例将被缓存,如context相同则下次获取直接返回缓存的
	 *
	 * @param context SharedPreferences所依赖的context
	 * @return AppPrefs 实例
	 */
	public static AppPrefs getInstance(@NonNull Context context) {
		AppPrefs prefs;
		if (cachedPrefs == null ||
				!context.getApplicationContext().equals(cachedPrefs.context.get())) {
			prefs = new AppPrefs(context);
			cachedPrefs = prefs;
			return prefs;
		}
		return cachedPrefs;
	}
}

package com.helixs.compat;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by helixs on 16/9/7.
 * 工具的配置
 */
public class UtilConfig {

	private static Context sContext;
	private static boolean debug;

	public static void init(@NonNull Context context, boolean debug){
		sContext = context;
		UtilConfig.debug = debug;
	}

	public static Context getContext(){
		return sContext;
	}

	public static boolean debuggable(){
		return debug;
	}

}

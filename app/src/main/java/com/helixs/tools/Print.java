package com.helixs.tools;

import android.content.Context;
import android.util.Log;


import java.io.File;
import java.util.UUID;

public class Print {
	/**
	 * log开关,发布应关闭
	 */
	private static final boolean isTest = BuildConfig.DEBUG;


	private static final int ALL     = 7;
	private static final int VERBOSE = 5;
	private static final int DEBUG   = 4;
	private static final int INFO    = 3;
	private static final int WARN    = 2;
	private static final int ERROR   = 1;
	private static final int NONE    = 0;

	private static final int FILE_LOG_LEVEL = BuildConfig.DEBUG ? DEBUG : NONE;

	//	private static final boolean isTest = true;
	public static void e(String tag, String msg) {
		if (isTest) {
			eOfLong(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (isTest) {
			Log.e(tag, msg, tr);
		}
	}

	public static void d(String tag, String msg) {
		if (isTest) {
			dOfLong(tag, msg);
		}
	}

	public static void d(String tag, Object... msgs) {
		if (isTest) {
			if (msgs == null || msgs.length == 0) {
				Log.d(tag, "");
				return;
			}
			dOfLong(tag, String.format(genFormatter(msgs), msgs));
		}
	}

	private static String genFormatter(Object... msgs) {
		StringBuilder sb = new StringBuilder();
		for (Object object : msgs) {
			if (object == null) {
				sb.append("n:%s,");
				continue;
			}
			String canonicalName = object.getClass().getCanonicalName();
			if (canonicalName == null) {
				sb.append("N:%s,");
				continue;
			}
			switch (canonicalName) {
				case "java.lang.Integer":
				case "java.lang.Long":
				case "java.lang.Short":
					sb.append("%d,");
					break;
				case "java.lang.Byte":
					sb.append("%#x,");
					break;
				case "java.lang.Float":
				case "java.lang.Double":
					sb.append("%f,");
					break;
				case "java.lang.Character":
					sb.append("%c,");
					break;
				case "java.lang.Boolean":
					sb.append("%b,");
					break;
				case "java.lang.String":
				default:
					sb.append("%s,");
					break;
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static void w(String tag, String msg) {
		if (isTest) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (isTest) {
			Log.w(tag, msg, tr);
		}

	}

	public static void v(String tag, String msg) {
		if (isTest) {
			Log.v(tag, msg);
		}

	}

	public static void i(String tag, String msg) {
		if (isTest) {
			//			Log.i(tag, msg);
			iOfLong(tag, msg);
		}
	}

	public static void stackTrace(Throwable e) {
		if (isTest) {
			e.printStackTrace();
		}

	}

	public static void iOfLong(String tag, String msg) {
		if(msg == null){
			Log.i(tag,"null");
			return;
		}
		// 如果msg长度超过4000字符，就分条打印
		int end;
		int length = msg.length();
		for (int i = 0; i < length; i += MAX_LENGTH) {
			end = (i + MAX_LENGTH) > length ? length : i + MAX_LENGTH;
			Log.i(tag, msg.substring(i, end));
		}

	}

	public static void dOfLong(String tag, String msg) {
		if(msg == null){
			Log.d(tag,"null");
			return;
		}
		// 如果msg长度超过4000字符，就分条打印
		int end;
		int length = msg.length();
		for (int i = 0; i < length; i += MAX_LENGTH) {
			end = (i + MAX_LENGTH) > length ? length : i + MAX_LENGTH;
			Log.d(tag, msg.substring(i, end));
		}
	}

	public static void eOfLong(String tag, String msg) {
		if(msg == null){
			Log.e(tag,"null");
			return;
		}
		// 如果msg长度超过4000字符，就分条打印
		int end;
		int length = msg.length();
		for (int i = 0; i < length; i += MAX_LENGTH) {
			end = (i + MAX_LENGTH) > length ? length : i + MAX_LENGTH;
			Log.e(tag, msg.substring(i, end));
		}
	}

	private static final int MAX_LENGTH = 4000;


	private static File currentLogFile;

	public static void initFileLog(Context context) {
		if(FILE_LOG_LEVEL <= NONE){
			return;
		}
		File cacheDir = new File(context.getExternalCacheDir(), "wlog");
		if (!cacheDir.exists()) {
			if (!cacheDir.mkdirs()) {
				// cant access log dir
				return;
			}
		}
		String log = UUID.randomUUID().toString() + ".log";
		currentLogFile = new File(cacheDir, log);
	}



}

package com.helixs.compat;

import android.os.Build;
import android.util.ArrayMap;
import android.util.ArraySet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by helixs on 2017/2/17.
 */

public class Compat {
	public static <K, V> Map<K, V> createMap() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return new ArrayMap<>();
		} else {
			return new HashMap<>();
		}
	}
	/**
	 * 创建Set,类似createMap,返回ArrayMap/HashMap
	 * @return 创建的Set
	 */
	public static <T> Set<T> createSet() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return new ArraySet<>();
		}else{
			return new HashSet<>();
		}
	}

}

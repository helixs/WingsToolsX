package com.helixs.tools;

import java.util.Collection;
import java.util.Map;

/**
 * Created by helixs on 2017/3/2.
 */

public class CollectionUtil {
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isEmpty(float[] arr) {
		return arr == null || arr.length == 0;
	}

	public static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}
}

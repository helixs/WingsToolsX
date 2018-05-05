package com.helixs.compat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by helixs on 2017/8/15.
 */

public final class Res {
	public static String getString(@NonNull Context context,
	                               @StringRes int stringId,
	                               Object... formatArgs) {
		if (formatArgs.length > 0) {
			return context.getString(stringId, formatArgs);
		}
		return context.getString(stringId);
	}

	public static String getString(@StringRes int stringId, Object... formatArgs) {
		Context context = UtilConfig.getContext();
		if (formatArgs.length > 0) {
			return context.getString(stringId, formatArgs);
		}
		return context.getString(stringId);
	}
}

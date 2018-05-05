package com.helixs.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by helixs on 2017/2/28.
 */

public class PermissionTools {

	public static boolean requestLocation(Activity activity, int requestCode) {
		if (ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
				.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			Pop.toast("未获取到位置权限,请检查你的权限");
			ActivityCompat.requestPermissions(activity,
					new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
					requestCode);
			return false;
		}
		return true;
	}
}

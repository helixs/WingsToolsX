package com.helixs.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.helixs.api.Api;
import com.helixs.model.IPRes;
import com.helixs.net.AppNetCallBack;
import com.helixs.tools.AppPrefs;
import com.helixs.tools.Pop;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by helixs on 2017/2/21.
 */

public class AppLocation {
	private LocationManager locationManager;
	private AppLocationlisten listener;

	private Context context;
	private LocationChangelistener locationChangelistener;

	public static void getIpAdress() {
		Pop.toast("自动获取地址中");
		Api.getIp(new AppNetCallBack<IPRes>() {

			@Override
			public void ok(IPRes ipRes) {
				if (!TextUtils.isEmpty(ipRes.ip)) {
					AppPrefs.get().ipAdressPref.set(ipRes.ip);
					LocationBroadcastReciver.sendIpAdress(ipRes.ip);
				} else {
					String ip = AppLocation.getIpAddressString();
					if (!TextUtils.isEmpty(ip)) {
						if (ip.indexOf("192.168.") != -1) {
							AppPrefs.get().ipAdressPref.set(ip);
							LocationBroadcastReciver.sendIpAdress(ip);
						} else {
							Pop.toast("自动获取地址失败");
						}
					} else {
						Pop.toast("自动获取地址失败");
					}
				}
			}

			@Override
			public void netFailure() {
				Pop.toast("自动获取地址失败");
				super.netFailure();
			}
		});
	}

	public static String getIpAddressString() {
		try {
			for (Enumeration<NetworkInterface> enNetI = NetworkInterface
					.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
				NetworkInterface netI = enNetI.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = netI
						.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "";
	}

	public AppLocation(Context context) {
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		listener = new AppLocationlisten();

	}

	public AppLocation(Context context, LocationChangelistener locationChangelistener) {
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		listener = new AppLocationlisten(locationChangelistener);
		this.locationChangelistener = locationChangelistener;
	}

	//开始请求地理位置更是
	public void startRequestLocationUpdates(LocationChangelistener locationChangelistener) {

//		this.locationChangelistener = locationChangelistener;
//		if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
//			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
//		} else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
//			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
//		} else {
//			Pop.toast("没有网络提供者");
//		}
	}
	//停止地理位置更新

	public void stopRequestLocationUpdates() {
		if (ActivityCompat.checkSelfPermission(context,
				Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			Pop.toast("未获取到位置权限,请检查你的权限");
			return;
		}
		locationManager.removeUpdates(listener);
	}

	public Location getCurrentLocation() {
		return listener.getLocation();
	}

	public interface LocationChangelistener {
		void changeLocation(boolean isVaild, Location location);
	}

	static class AppLocationlisten implements LocationListener {
		Location mLastLocation;
		boolean mValid = false;
		LocationChangelistener mLocationChangelistener;

		public AppLocationlisten() {
		}

		public AppLocationlisten(LocationChangelistener mLocationChangelistener) {
			this.mLocationChangelistener = mLocationChangelistener;
		}

		@Override
		public void onLocationChanged(Location location) {
			if (location.getLatitude() == 0.0
					&& location.getLongitude() == 0.0) {
				// Hack to filter out 0.0,0.0 locations
				return;
			}
			mLastLocation.set(location);
			mValid = true;
			if (mLocationChangelistener != null) {
				mLocationChangelistener.changeLocation(true, mLastLocation);
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
				case LocationProvider.OUT_OF_SERVICE:
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					mValid = false;
					if (mLocationChangelistener != null) {
						mLocationChangelistener.changeLocation(false, null);
					}
					break;
			}
		}

		@Override
		public void onProviderEnabled(String provider) {


		}

		@Override
		public void onProviderDisabled(String provider) {
			mValid = false;
			if (mLocationChangelistener != null) {
				mLocationChangelistener.changeLocation(false, null);
			}
		}

		public Location getLocation() {
			return mValid ? mLastLocation : null;
		}
	}
}

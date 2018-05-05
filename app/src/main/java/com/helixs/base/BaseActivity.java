package com.helixs.base;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.helixs.tools.PermissionTools;
import com.helixs.tools.R;
import com.helixs.weather.AppLocation;

/**
 * Created by helixs on 2017/2/28.
 */

public class BaseActivity extends AppCompatActivity {
	private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 333;

	private TextView toolbarTitle;
	private AppLocation appLocation;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected Toolbar initToolbar(boolean showHomeAsUp) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
		}
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) {
			setShowHome(showHomeAsUp);
			if (toolbarTitle != null) {
				supportActionBar.setDisplayShowTitleEnabled(false);
			}
		}
		return toolbar;
	}

	protected void setShowHome(boolean showHomeAsUp) {
		ActionBar supportActionBar = getSupportActionBar();
		if (supportActionBar != null) {
			supportActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
			supportActionBar.setDisplayShowHomeEnabled(showHomeAsUp);
		}
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		super.onTitleChanged(title, color);
		if (toolbarTitle != null) {
			toolbarTitle.setText(title);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length >= 1
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
						== PackageManager.PERMISSION_GRANTED) {

				} else {

				}
				return;
			}
		}
	}


	protected void requestLocation() {
		if (PermissionTools.requestLocation(this, MY_PERMISSIONS_REQUEST_READ_CONTACTS)) {

		}
	}

	private void go() {

		appLocation = new AppLocation(this);
		appLocation.startRequestLocationUpdates(new AppLocation.LocationChangelistener() {
			@Override
			public void changeLocation(boolean isVaild, Location location) {
				String loca = location.getLatitude() + ":" + location.getLongitude();
			}
		});
	}
}

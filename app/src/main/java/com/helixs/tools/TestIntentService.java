package com.helixs.tools;

import android.app.IntentService;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by helixs on 2017/4/6.
 */

public class TestIntentService extends IntentService {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public TestIntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {

	}
	public static class TestContentProvider extends ContentProvider{

		@Override
		public boolean onCreate() {
			return false;
		}

		@Nullable
		@Override
		public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
			return null;
		}

		@Nullable
		@Override
		public String getType(@NonNull Uri uri) {
			return null;
		}

		@Nullable
		@Override
		public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
			return null;
		}

		@Override
		public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
			return 0;
		}

		@Override
		public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
			return 0;
		}
	}
}


package com.hqf.demo.aidldemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by huangqianfang on 2017/7/14.
 * email huangqianfanghn@gmail.com
 */

public class BookProvider extends ContentProvider {
	private static final String TAG = "BookProvider";
	@Override
	public boolean onCreate() {
		Log.d(TAG , "onCreate , currnt thread :" +Thread.currentThread().getName());

		return false;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Log.d(TAG , "query , currnt thread :" +Thread.currentThread().getName());
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		Log.d(TAG , "getType ");

		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		Log.d(TAG , "insert ");
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		Log.d(TAG , "delete ");
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		Log.d(TAG , "update ");
		return 0;
	}
}

package com.kdcm.aidongdong.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class DataTools {
	static SharedPreferences settings;
	static SharedPreferences.Editor editor;

	public static void saveDaTa(Context context, String key, String data) {
		settings = context.getSharedPreferences("Data", 0);
		editor = settings.edit();
		editor.putString(key, data);
		editor.commit();
	}

	public static String readData(Context context,String key) {
		String data = null;
		settings = context.getSharedPreferences("Data", 0);
		data=settings.getString(key, null);
		return data;

	}

}

package com.kdcm.aidongdong.tools;

import com.kdcm.aidongdong.Date.Conf;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class AppUtil {
	/**
	 * 网络连接检查
	 * 
	 * @param Activity
	 * @return false 失败 true 成功
	 */
	private static boolean isNetworkAvailable(Activity mActivity) {
		ConnectivityManager cm = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = cm.getActiveNetworkInfo();
		if (network != null) {
			return network.isAvailable();
		}
		return false;

	}

	/**
	 * 检测网络是否存在
	 */

	public static void isNetwork(Activity mActivity) {
		if (!isNetworkAvailable(mActivity)) {
			// 更改NETWORK_ON的网络连接标示
			Conf.NETWORK_ON = false;
			String errorStr = "网络连接失败，请检查网络！";
			Toast.makeText(mActivity, errorStr, 1000).show();
			

		} else {
			Conf.NETWORK_ON = true;
		}
	}

}
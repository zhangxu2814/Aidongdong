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
	/** 
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的 
     * @param context 
     * @return true 表示开启 
     */  
    public static final boolean isGPSOpen(final Context context) {  
        LocationManager locationManager   
                                 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）  
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）  
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);  
        if (gps || network) {  
            return true;  
        }  
  
        return false;  
    }
}
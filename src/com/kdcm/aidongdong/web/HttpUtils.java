package com.kdcm.aidongdong.web;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HttpUtils {
	public static final String base_url = "http://www.haoapp123.com/app/localuser/aidongdong/api.php?";
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(10); // 设置链接超时，如果不设置，默认为10s
	}

	public static AsyncHttpClient getClient() {
		return client;
	}
	/**
	 * 进行登录
	 * 
	 * @param loginName
	 * @param pwd
	 * @param res
	 */
	public static void login(String loginName, String pwd,
			AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{

		String url = base_url + "m=user&a=login&login_name=" + loginName
				+ "&login_password=" + pwd;
		System.out.println(url);
		client.post(url, res);
	}
	public static void getMy(){
		
	}
}

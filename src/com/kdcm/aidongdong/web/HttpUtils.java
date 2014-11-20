package com.kdcm.aidongdong.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
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

	public static void getMy() {

	}

	/**
	 * 添加订单
	 * 
	 * @param res
	 * @param ids
	 */
	public static void addOrder(AsyncHttpResponseHandler res, String ids,int coins) {
		String url = base_url + "m=user&a=addOrder&shopping_ids=" + ids+"&coins="+coins;
		client.post(url, res);
	}

	/**
	 * 添加支付订单
	 * 
	 * @param res
	 * @param id
	 */
	public static void payOrder(AsyncHttpResponseHandler res, String id) {
		String url = base_url + "m=user&a=payOrder&order_id=" + id;
		client.post(url, res);
	}

	/**
	 * 获取送出的金币
	 * 
	 * @param res
	 */
	public static void getReceivedCoins(AsyncHttpResponseHandler res) {
		String url = base_url + "m=user&a=getReceivedCoins";
		client.post(url, res);
	}

	/**
	 * 好友排行
	 * 
	 * @param res
	 */
	@SuppressLint("SimpleDateFormat")
	public static void getFriends(AsyncHttpResponseHandler res) {
		String startTime;
		Date time = new Date();
		startTime = (new SimpleDateFormat("yyyy-MM-dd")).format(time);
		String url = base_url + "m=user&a=getFriendDurations&date=" + startTime;
		Log.i("friend", url);
		client.post(url, res);
	}

	public static void addProductComment(AsyncHttpResponseHandler res,
			String id, String desc) {
		String url = base_url + "m=user&a=addProductComment"+"&product_id="+id+"&desc="+desc;
		client.post(url, res);
	}
}

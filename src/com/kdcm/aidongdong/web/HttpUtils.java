package com.kdcm.aidongdong.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	public static void addOrder(AsyncHttpResponseHandler res, String ids,
			int coins, String receiver_id, String shiping_method) {
		String url = base_url + "m=user&a=addOrder&shopping_ids=" + ids
				+ "&receiver_id=" + receiver_id + "&shiping_method="
				+ shiping_method + "&coins=" + coins;
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
	public static void getFriendDurations(AsyncHttpResponseHandler res) {
		String startTime;
		Date time = new Date();
		startTime = (new SimpleDateFormat("yyyy-MM-dd")).format(time);
		String url = base_url + "m=user&a=getFriendDurations&date=" + startTime;
		Log.i("friend", url);
		client.post(url, res);
	}

	public static void addProductComment(AsyncHttpResponseHandler res,
			String id, String desc) {
		String url = base_url + "m=user&a=addProductComment" + "&product_id="
				+ id + "&desc=" + desc;
		client.post(url, res);
	}

	/**
	 * 查找用户
	 * 
	 * @param res
	 * @param uID
	 */
	public static void searchUser(AsyncHttpResponseHandler res, String uID) {
		String url = base_url + "m=user&a=search&id=" + uID;
		client.post(url, res);
	}

	/**
	 * 查询用户收货地址
	 * 
	 * @param res
	 */
	public static void getReceivers(AsyncHttpResponseHandler res) {

		String url = base_url + "m=user&a=getReceivers";
		client.post(url, res);
	}

	/**
	 * 删除收货地址
	 * 
	 * @param res
	 * @param id
	 */
	public static void delReceiver(AsyncHttpResponseHandler res, String id) {
		String url = base_url + "m=user&a=delReceiver&receiver_id=" + id;
		client.post(url, res);
	}

	/**
	 * 添加收货地址
	 * 
	 * @param res
	 * @param phone
	 * @param receiver
	 * @param address
	 */
	public static void addReceiver(AsyncHttpResponseHandler res, String phone,
			String receiver, String address) {

		String url = base_url + "m=user&a=addReceiver" + "&phone=" + phone
				+ "&reciver=" + receiver + "&address=" + address;
		client.post(url, res);
	}

	/**
	 * 测试
	 * 
	 * @param res
	 */
	public static void test(AsyncHttpResponseHandler res) {
		String url = "http://www.haoapp123.com/app/localuser/aidongdong/api.php?m=user&a=addOrder&shopping_ids=30";
		client.post(url, res);
	}

	/**
	 * 获取订单详情
	 * 
	 * @param res
	 * @param id
	 */
	public static void getOrders(AsyncHttpResponseHandler res, String id) {
		String url = base_url + "m=user&a=getOrders&order_id=" + id;
		client.post(url, res);
	}

	/**
	 * 查询运动天数
	 * 
	 * @param res
	 */
	public static void getMonthMoveDays(AsyncHttpResponseHandler res, int day) {
		String startTime;
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		startTime = (new SimpleDateFormat("yyyy-MM-dd")).format(calendar
				.getTime());
		String url = base_url + "m=user&a=getMonthMoveDays&start_time="
				+ startTime;
		client.post(url, res);
	}

	/**
	 * 删除好友
	 * 
	 * @param res
	 * @param id
	 */
	public static void delFriend(AsyncHttpResponseHandler res, String id) {
		String url = base_url + "m=user&a=delFriend&friend_id=" + id;
		client.post(url, res);
	}

	/**
	 * 获取好友请求
	 * 
	 * @param res
	 */
	public static void getMyAccepts(AsyncHttpResponseHandler res) {
		String url = base_url + "m=user&a=getMyAccepts";
		client.post(url, res);
	}

	/**
	 * 拒绝或添加好友
	 * 
	 * @param res
	 * @param type
	 *            0：拒绝 1：添加
	 * @param id
	 */
	public static void acceptreject(AsyncHttpResponseHandler res, int type,
			String id) {
		String url = null;
		if (type == 0) {// 拒绝
			url = base_url + "m=user&a=rejectFriend&friend_id=" + id;
		} else if (type == 1) {
			url = base_url + "m=user&a=acceptFriend&friend_id=" + id;
		}
		client.post(url, res);
	}

	/**
	 * 添加好友 id和phone 只用一个就成
	 * 
	 * @param res
	 * @param friend_id
	 * @param friend_phone
	 */
	public static void addFriend(AsyncHttpResponseHandler res,
			String friend_id, String friend_phone) {
		String url = null;
		if (friend_phone == null) {
			url = base_url + "m=user&a=addFriend&friend_id=" + friend_id;
		}
		if (friend_id == null) {
			url = base_url + "m=user&a=addFriend&friend_phone=" + friend_phone;
		}
		client.post(url, res);
		Log.i("url", url);
	}

	/**
	 * 获取好友资料
	 * 
	 * @param res
	 */
	public static void getFriends(AsyncHttpResponseHandler res) {
		String url = base_url + "m=user&a=getFriends";
		client.post(url, res);
	}

	/**
	 * 赠送金币接口
	 * 
	 * @param res
	 * @param id
	 * @param coins
	 */
	public static void giveCoins(AsyncHttpResponseHandler res, String id,
			String coins) {
		String url = base_url + "m=user&a=giveCoins&friend_id=" + id
				+ "&coins=" + coins;
		client.post(url, res);
	}

	/**
	 * 查询送出的金币
	 * 
	 * @param res
	 */
	public static void getGivedCoins(AsyncHttpResponseHandler res) {
		String url = base_url + "m=user&a=getGivedCoins";
		client.post(url, res);
	}

	/**
	 * 确认收货
	 * 
	 * @param res
	 * @param id
	 */
	public static void confirmReceipt(AsyncHttpResponseHandler res, String id) {
		String url = base_url + "m=user&a=confirmReceipt&order_id=" + id;
		client.post(url, res);
	}
}

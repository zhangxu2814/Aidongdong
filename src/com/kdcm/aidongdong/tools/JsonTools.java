package com.kdcm.aidongdong.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class JsonTools {
	public static Person getPerson(String key, String jsonString) {
		Person person = new Person();
		if (!jsonString.equals("ERROR")) {
			try {
				JSONObject jsonObj = new JSONObject(jsonString);
				JSONObject personObj = jsonObj.getJSONObject(key);
				person.setId(personObj.getString("id"));
				person.setUsername(personObj.getString("username"));
				person.setNickname(personObj.getString("nickname"));
				person.setSex(personObj.getString("sex"));
				person.setPhone(personObj.getString("phone"));
			} catch (Exception e) {

			}
		}
		return person;

	}

	public static String getPhone_registered(String key, String jsonString) {
		String phone_registered = null;
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			phone_registered = jsonObj.getString(key);

		} catch (Exception e) {
			phone_registered = null;
		}
		return phone_registered;

	}

	public static List<Map<String, Object>> getMy(String jsonstring) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObj = new JSONObject(jsonstring);
			JSONObject personObj = jsonObj.getJSONObject("data");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nickname", personObj.get("nickname").toString());
			map.put("phone", personObj.get("phone"));
			map.put("id", personObj.get("id"));
			map.put("coins", personObj.get("coins"));
			map.put("sex", personObj.get("sex"));
			map.put("balance", personObj.get("balance"));
			data.add(map);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static List<Map<String, Object>> getFriends(String jsonstring) {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", jo.get("nickname").toString());
				map.put("phone", jo.get("phone"));
				map.put("id", jo.get("id"));
				map.put("coins", jo.get("coins"));
				map.put("month_move_days", jo.get("month_move_days").toString());
				String duration = jo.get("duration").toString();
				map.put("duration", duration);
				Log.i("duration", duration);
				data.add(map);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static List<Map<String, Object>> getGivedcoins(String jsonstring) {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = jsonArray.length() - 1; i >= 0; i--) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", jo.get("nickname").toString());
				map.put("phone", jo.get("phone"));
				map.put("coins_paid", jo.get("coins_paid"));
				map.put("paid_time", jo.get("paid_time"));
				data.add(map);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static List<Map<String, Object>> getScan(String jsonstring) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", jo.get("nickname").toString());
				map.put("phone", jo.get("phone"));
				map.put("id", jo.get("id"));
				map.put("duration", "null");
				data.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static ArrayList<HashMap<String, Object>> getProducts(
			String jsonstring) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", jo.get("name").toString());
				map.put("id", jo.get("id"));
				map.put("max_deduction", jo.get("max_deduction"));
				map.put("category_id", jo.get("category_id"));
				map.put("roll_pics", jo.get("roll_pics"));
				map.put("price", jo.get("price"));
				map.put("sold_num", jo.get("sold_num"));
				map.put("size", jo.get("size"));
				map.put("color", jo.get("color"));
				map.put("desc", jo.get("desc"));
				JSONArray pics_Array = new JSONArray(jo.get("roll_pics")
						.toString());
				JSONObject item = pics_Array.getJSONObject(0);
				map.put("URL", item.get("pic"));
				data.add(map);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static List<Map<String, Object>> getReview(String jsonstring) {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", jo.get("nickname").toString());
				map.put("create_time", jo.get("create_time"));
				map.put("desc", jo.get("desc"));
				data.add(map);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static List<Map<String, Object>> getSPCar(String jsonstring) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		double zongjia = 0;
		double dikou = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		String ids = "";
		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				map = new HashMap<String, Object>();
				map.put("number", jo.get("number").toString());
				double price = jo.getDouble("price"); // 价格价格
				map.put("price", price + "");
				map.put("name", jo.getString("name"));
				zongjia += price * jo.getDouble("number");
				dikou += jo.getInt("number") * jo.getInt("max_deduction");
				map.put("shopping_id", jo.get("shopping_id").toString());
				map.put("zongjia", String.format("%.2f", zongjia));
				map.put("dikou", String.format("%.2f", dikou));
				ids += jo.getString("shopping_id") + ",";
				map.put("ids", ids);
				JSONArray pics_Array = null;
				try {
					pics_Array = new JSONArray(jo.getString("roll_pics"));
					JSONObject item = pics_Array.getJSONObject(1);
					Log.i("URL", item.get("pic") + "");
					map.put("URL_img",
							"http://www.haoapp123.com/app/localuser/aidongdong/"
									+ item.getString("pic"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				data.add(map);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static List<Map<String, Object>> getOrders(String jsonstring,
			String status) {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", jo.getString("id"));
				map.put("need_cash", jo.get("need_cash").toString());
				map.put("create_time", jo.get("create_time"));
				map.put("order_no", jo.get("order_no".toString()));
				if (!jo.getString("shopping_carts").equals("null")) {
					JSONArray carts_Array = new JSONArray(
							jo.getString("shopping_carts"));
					map.put("shopping_carts", carts_Array.length() + "");
				} else {
					map.put("shopping_carts", "0");
				}
				if (jo.getString("status").equals(status)) {
					data.add(map);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;

	}
}

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
			phone_registered=null;
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
				data.add(map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}
	public static List<Map<String, Object>> getGivedcoins(String jsonstring) {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", jo.get("nickname").toString());
				map.put("phone", jo.get("phone"));
				map.put("coins_paid", jo.get("coins_paid"));
				map.put("paid_time", jo.get("paid_time"));
				data.add(map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
				data.add(map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

}

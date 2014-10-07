package com.kdcm.aidongdong.tools;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

		}
		return phone_registered;

	}

	public static List<String> getFriends(String jsonstring) {

		List<String> data = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				data.add("昵称" + jo.getString("nickname") + "   电话"
						+ jo.getString("phone"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	public static List<String> getScan(String jsonstring) {

		List<String> data = new ArrayList<String>();

		try {
			JSONObject jsonObject = new JSONObject(jsonstring);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				data.add("昵称:" + jo.getString("nickname") + ";电话:"+ jo.getString("phone")+"ID"+jo.getString("id"));
//				data.add(jo.getString("id"));
//				data.add(jo.getString("nickname"));
//				data.add(jo.getString("phone"));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

}

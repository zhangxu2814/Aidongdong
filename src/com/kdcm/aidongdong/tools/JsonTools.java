package com.kdcm.aidongdong.tools;

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
	public static String getPhone_registered(String key, String jsonString){
		String phone_registered=null;
		try{
			JSONObject jsonObj=new JSONObject(jsonString);
			phone_registered=jsonObj.getString(key);
			
		}catch (Exception e) {

		}
		return phone_registered;
		
	}

}

package com.kdcm.aidongdong.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.kdcm.aidongdong.Date.Conf;

import android.content.Context;
import android.util.Log;

public class HttpUtil {
	static String mResult = null;

	public static String getJsonContent(Context context,String urlStr) {
		HttpGet httpRequest = new HttpGet(urlStr);
		httpRequest.setHeader("Cookie", DataTools.readData(context,"username"));
		
		String strResult = null;
		try {
			// HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();

			// 获得HttpResponse对象
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的数据
				strResult = EntityUtils.toString(httpResponse.getEntity());
				
			}else{
				strResult="ERROR";
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return strResult;
		
		
	}

	
	/**
	 * 得到result判断是状态
	 * 
	 * @param jsonstring
	 * @return
	 */
	public static String getResult(String jsonstring) {
		if (!jsonstring.equals("ERROR")) {
			try {
				JSONTokener jsonParser = new JSONTokener(jsonstring);
				JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
				mResult = jsonObj.getString("result");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return mResult;

	}

	
		
	
}
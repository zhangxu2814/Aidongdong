package com.kdcm.aidongdong.tools;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
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


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtil {
	 private static final String LOG_TAG = "ImageGetFromHttp";
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

	 public static Bitmap downloadBitmap(String url) {
    	 final HttpGet getRequest = new HttpGet(url);
    	 final HttpClient client = new DefaultHttpClient();
       
                                                               
        try {
        	 
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(LOG_TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }
                                                                   
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    FilterInputStream fit = new FlushedInputStream(inputStream);
                    return BitmapFactory.decodeStream(fit);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return null;
    }
                                                       
    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
               
        
        /*
         *Android对于InputStream流有个小bug在慢速网络的情况下可能产生中断，
         *可以考虑重写FilterInputStream处理skip方法来解决这个bug。
         * BitmapFactory类的decodeStream方法在网络超时或较慢的时候无法获取完整的数据
         * ，这里我 们通过继承FilterInputStream类的skip方法来强制实现flush流中的数据，主要原理就是检查是否到文件末端
         * ，告诉http类是否继续。 
         */
        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}

		
	

package com.signalripple.fitnessbike.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.signalripple.fitnessbike.utils.ShareDB;

public class MRequset{

	private Context context;
	private static MRequset mRequset;
	private RequestQueue requestQueue;
	
	private MRequset(Context context)
	{
		this.context = context;
		requestQueue = Volley.newRequestQueue(context);
	}
	
	public static MRequset getInstance(Context context)
	{
		if(mRequset == null)
			mRequset = new MRequset(context);
		return mRequset;
	}
	
	/**
	 *  new JsonObjectRequest(url,jsonObject,listener)去构造一个请求request。
  		url:请求的地址。
 		jsonObject:需要传递的数据，如果是以get方式传递，则为null，如果是以post方式传递，则需要设置数据。
	 * @param url
	 * @param jsonRequest
	 * @param listener
	 * @param errorListener
	 */
	public void requestForJsonObject(String url,JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener)
	{
		JsonObjectRequest request = new JsonObjectRequest(url, jsonRequest, listener, errorListener)
		{
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				// TODO Auto-generated method stub
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("appkey", "12345");
				headers.put("udid", "imei");
				headers.put("os", "Android");
				headers.put("osversion", "4.2");
				headers.put("appversion", "1.0.0");
				headers.put("sourceid", "Yek_test");
				headers.put("Ver", "0.9");
				headers.put("userid", ShareDB.getStringFromDB(context, "account"));
				headers.put("usersession", "xxssyy");
				headers.put("unique", "zxcvbnmm");
				
				headers.put("token", ShareDB.getStringFromDB(context, "token"));
				return headers;
			}
		};
		requestQueue.add(request);
	}
	
//	appkey	String	Y	123456	软件身份key
//	udid	String	Y	udid or imei	手机客户端的唯一标识
//	os	String	Y	Iphone os	操作系统名称
//	osversion	String	Y	5.0	操作系统版本
//	appversion	String	Y	1.0.0	APP版本
//	sourceid	String	Y	Yek_test	推广ID
//	ver	String	Y	0.9	通讯协议版本
//	userid	String	N	12345	用户ID
//	usersession	String	N	Cbaq4fxvb	登陆后得到的用户唯一性标识
//	unique	String	N	xvbvsfsgdsg	激活后得到的设备唯一性标识
	public void requestForJsonArray(String url , Listener<JSONArray> listener, ErrorListener errorListener)
	{
		JsonArrayRequest request = new JsonArrayRequest(url, listener, errorListener){
		@Override
		public Map<String, String> getHeaders() throws AuthFailureError {
			// TODO Auto-generated method stub
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("appkey", "12345");
			headers.put("udid", "imei");
			headers.put("os", "Android");
			headers.put("osversion", "4.2");
			headers.put("appversion", "1.0.0");
			headers.put("sourceid", "Yek_test");
			headers.put("Ver", "0.9");
			headers.put("userid", "1234");
			headers.put("usersession", "xxssyy");
			headers.put("unique", "zxcvbnmm");
			
			headers.put("token", ShareDB.getStringFromDB(context, "token"));
			return headers;
		}};
		requestQueue.add(request);
	}
	
	/**
	 * 加载图片
	 * @param url
	 * @param listener
	 * 
	 * ImageLoad.getImageListener(imageView,  
        R.drawable.default_image, R.drawable.failed_image);   
	 */
	public void imageLoadForView(String url, ImageListener listener)
	{
		//
		ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
		imageLoader.get(url, listener);
	}
}

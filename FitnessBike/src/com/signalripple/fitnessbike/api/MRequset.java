package com.signalripple.fitnessbike.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Bitmap;

public class MRequset {

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
	
	public void requestForJsonObject(String url,JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener)
	{
		JsonObjectRequest request = new JsonObjectRequest(url, jsonRequest, listener, errorListener);
		requestQueue.add(request);
	}
	
	public void requestForJsonArray(String url , Listener<JSONArray> listener, ErrorListener errorListener)
	{
		JsonArrayRequest request = new JsonArrayRequest(url, listener, errorListener);
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

package com.signalripple.fitnessbike;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.bean.PKBean;
import com.signalripple.fitnessbike.interfaces.IActivity;
import com.signalripple.fitnessbike.utils.ShareDB;
import com.signalripple.fitnessbike.utils.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class PKSelectActivity extends BaseActivity implements IActivity, OnClickListener{
	
	private ImageView ivNlzw;
	private ImageView ivJsgj;
	private MRequset mRequset;
	private String defenderid;
	private String defierid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pkselect);
		
		mRequset = MRequset.getInstance(this);
		
		Intent intent = getIntent();
		defierid   = intent.getStringExtra("defierid");
		defenderid = intent.getStringExtra("defenderid");
		Log.i("XU", "Intent数据是否为空:"+defierid+"  -- "+defenderid);
		
		initView();
		initValue();
		initEvent();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ivJsgj = (ImageView)this.findViewById(R.id.ivJsgj);
		ivNlzw = (ImageView)this.findViewById(R.id.ivNlzw);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		ivJsgj.setOnClickListener(this);
		ivNlzw.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivNlzw:
			requset(API.nlzw);
			break;
		case R.id.ivJsgj:
			requset(API.jsgj);
			break;
		default:
			break;
		}
	}
	
	
	private void requset(int type)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("defierid", defierid);
		map.put("defenderid", defenderid);
		Log.i("XU", "你对"+defenderid+"发起了PK");
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiCompitition, map), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if("success".equals(response.getString("respone")))
					{
						ToastUtil.show(PKSelectActivity.this, "已经把您的请求发送给对方", ToastUtil.INFO);
					}
					else
					{
						ToastUtil.show(PKSelectActivity.this, "请求失败", ToastUtil.ERROR);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				ToastUtil.show(PKSelectActivity.this, "请求失败", ToastUtil.ERROR);
				Log.i("XU", "请求ak时错误"+error.toString());
			}
		});
	}
	


}

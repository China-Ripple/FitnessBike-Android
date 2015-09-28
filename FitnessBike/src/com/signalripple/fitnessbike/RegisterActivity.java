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
import com.signalripple.fitnessbike.interfaces.IActivity;
import com.signalripple.fitnessbike.utils.Activity2Activity;
import com.signalripple.fitnessbike.utils.EditTextUtil;
import com.signalripple.fitnessbike.utils.ShareDB;
import com.signalripple.fitnessbike.utils.ToastUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity implements IActivity, OnClickListener{
	
	private EditText etUserName;
	private EditText etPassWord;
	private Button btnRegister;
	private TextView txtReturn;
	private TextView txtRegister;
	private MRequset mRequset;
	private ProgressDialog dialog;
	private TextView txtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		initValue();
		initEvent();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		etUserName = (EditText)this.findViewById(R.id.etUserName);
		etPassWord = (EditText)this.findViewById(R.id.etPassWord);
		btnRegister   = (Button)this.findViewById(R.id.btnRegister);
		txtReturn  = (TextView)this.findViewById(R.id.include_view_btnLeft);
		txtRegister= (TextView)this.findViewById(R.id.include_view_btnRight);
		txtRegister.setVisibility(View.GONE);
		txtTitle = (TextView)this.findViewById(R.id.include_view_titlebar_text);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		mRequset = MRequset.getInstance(this);
		txtTitle.setText(getString(R.string.string_register));
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		btnRegister.setOnClickListener(this);
		txtReturn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 注册 的按钮
	case R.id.btnRegister:
		register();
		break;
		//返回
	case R.id.include_view_btnLeft:
		finish();
		break;
	}
	}

	String account;
	String password;
	private void register() {
		// TODO Auto-generated method stub
		account = EditTextUtil.getTextValue(etUserName);
		password = EditTextUtil.getTextValue(etPassWord);
		
		if(account == null || password == null)
			return;
		
		dialog = ProgressDialog.show(this, "温馨提示", "正在验证,请稍等");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("account", account);
		map.put("password", password);
		map.put("checknum", "623645");
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiRegister, map), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if(response != null)
					{
//getURL(API.apiRegister, map)==http://wx.rongtai-china.com/fitnessbike/signup?password=5556&account=1552555&checknum=623645

						dialog.dismiss();
						String result = response.getString("response");
						//etUserName.setText(response.toString());
						// 服务器验证成功 ，注册成功
						if("success".equals(result))
						{
							ToastUtil.show(RegisterActivity.this, "注册成功", ToastUtil.SUCC);
							Log.i("XU", "token==>"+response.getString("token"));
							// add by xushiyong for recoder some information
							ShareDB.save2DB(RegisterActivity.this, "token", response.getString("token"));
							ShareDB.save2DB(RegisterActivity.this, "account", account);
							ShareDB.save2DB(RegisterActivity.this, "password", password);
							Activity2Activity.gotoNewActivity(RegisterActivity.this, MainTabActivity.class);
						}
						else
						{
							ToastUtil.show(RegisterActivity.this, "注册失败", ToastUtil.ERROR);
						}
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
				ToastUtil.show(RegisterActivity.this, "出现未知异常", ToastUtil.ERROR);
			}
		});
	}
	

}
